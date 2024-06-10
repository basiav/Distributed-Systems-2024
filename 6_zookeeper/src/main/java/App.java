import org.apache.zookeeper.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class App implements Watcher {
    private final ZooKeeper zooKeeper;
    private Process process;
    // Program to run
    private final String program;
    // Watched znode
    private final String znode = "/a";

    public App(String program) throws IOException {
        this.program = program;
        this.process = null;

//        String connectString = "127.0.0.1:2181, 127.0.0.1:2182, 127.0.0.1:2183";
        String connectString = "127.0.0.1:2182";
        this.zooKeeper = new ZooKeeper(connectString, 3000, this);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String program = "mspaint";
        if (args.length > 1) {
            program = args[0];
        }
        App app = new App(program);
        app.run();
    }

    public void run() throws InterruptedException, KeeperException, IOException {
        zooKeeper.addWatch(znode, AddWatchMode.PERSISTENT_RECURSIVE);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Type ls to print tree: ");
            String line = br.readLine();

            if ("ls".equals(line)) {
                if (zooKeeper.exists(znode, false) == null) {
                    ColoredOutput.printInColor(ColoredOutput.Color.RED, "znode: " + znode + " not existing, cannot ls!");
                    continue;
                }
                printChildrenTree(znode);
            }
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeCreated) {
            ColoredOutput.printInColor(ColoredOutput.Color.GREEN, "zNode created, path: " + event.getPath());

            if (Objects.equals(event.getPath(), znode)) {
                ColoredOutput.printInColor(ColoredOutput.Color.GREEN, "Opening app...");
                try {
                    process = Runtime.getRuntime().exec(program);
                    ColoredOutput.printInColor(ColoredOutput.Color.GREEN, "app " + process);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    String numberOfChildrenMsg = "\nzNode " + znode + " number of children: " + zooKeeper.getAllChildrenNumber(znode);
                    ColoredOutput.printInColor(ColoredOutput.Color.BLUE, numberOfChildrenMsg);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (event.getType() == Event.EventType.NodeDeleted) {
            ColoredOutput.printInColor(ColoredOutput.Color.RED, "\nzNode deleted, path: " + event.getPath());
            if (Objects.equals(event.getPath(), znode) && process != null) {
                ColoredOutput.printInColor(ColoredOutput.Color.RED, "Killing app...");
                process.destroy();
            }
        }
        // Custom additional functionality
        // EventType.NodeDataChanged - to print "/a" tree
        else if (event.getType() == Event.EventType.NodeDataChanged) {
            try {
                byte[] b = null;
                b = zooKeeper.getData(event.getPath(), null, null);
                String data = new String(b, "UTF-8");
                String dataChangedMsg = String.format("\nData of [%s] has been changed: %s\n", event.getPath(), data);
                ColoredOutput.printInColor(ColoredOutput.Color.PURPLE, dataChangedMsg);
                printChildrenTree(znode);
            } catch (KeeperException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void printChildrenTree(String nodePath) throws InterruptedException, KeeperException {
        printTree(nodePath, "");
    }

    public void printTree(String path, String indent) throws KeeperException, InterruptedException {
        if (zooKeeper.exists(path, false) != null) {
            ColoredOutput.printInColor(ColoredOutput.Color.YELLOW, indent + path);
            List<String> children = zooKeeper.getChildren(path, false);
            for (String child : children) {
                printTree(path + "/" + child, indent + "    ");
            }
        }
    }
}


class ColoredOutput {
    enum Color {
        BLUE("\u001B[34m"), GREEN("\u001B[32m"), PURPLE("\u001B[35m"), RED("\u001B[31m"), YELLOW("\u001B[33m"), RESET("\u001B[0m");

        private final String color;

        private Color(String color) {
            this.color = color;
        }
    }

    public static void printInColor(Color color, String string) {
        System.out.println(color.color + string + Color.RESET.color);
    }
}