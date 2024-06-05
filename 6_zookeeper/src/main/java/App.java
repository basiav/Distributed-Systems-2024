import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.*;

public class App implements Watcher {
    private final ZooKeeper zooKeeper;
    private Process process;
    // Program to run
    private final String program = "mspaint";
    // Watched znode
    private final String znode = "/a";

    public App() throws IOException {
        String connectString = "127.0.0.1:2181, 127.0.0.1:2182, 127.0.0.1:2183";
        this.zooKeeper = new ZooKeeper(connectString, 3000, this);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        App app = new App();
        app.run();
    }

    public void run() throws InterruptedException, KeeperException {
        zooKeeper.addWatch(znode, AddWatchMode.PERSISTENT_RECURSIVE);
        while (true) {

        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeCreated) {
            ColoredOutput.printInColor(ColoredOutput.Color.GREEN, "zNode created, path: " + event.getPath());
            try {
                printChildrenTree(znode);
            } catch (InterruptedException | KeeperException | IOException e) {
                e.printStackTrace();
            }

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
            if (Objects.equals(event.getPath(), znode)) {
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
                String dataChangedMsg = String.format("\nData of [%s] has been changed: %s\n" , event.getPath(), data);
                ColoredOutput.printInColor(ColoredOutput.Color.YELLOW, dataChangedMsg);
                printChildrenTree(znode);
            } catch (KeeperException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void printChildrenTree(String nodePath) throws IOException, InterruptedException, KeeperException {
        List<String> tree = getChildrenTree(nodePath);
        for (String child : tree) {
            StringTokenizer tokenizer = new StringTokenizer(child, "/");
            String token = null;
            while (tokenizer.hasMoreTokens()) {
                if (token != null)
                    System.out.print("\t");
                token = tokenizer.nextToken();
            }
            if (token != null)
                ColoredOutput.printInColor(ColoredOutput.Color.PURPLE, "/" + token);
        }
    }

    public List<String> getChildrenTree(String nodePath) throws InterruptedException, KeeperException {
        HashMap<String, Boolean> visited = new HashMap<>();
        visited.put(nodePath, true);
        return DFS(nodePath, visited, new ArrayList<>());
    }

    List<String> DFS(String node, HashMap<String, Boolean> visited, List<String> tree) throws InterruptedException, KeeperException {
        tree.add(node);
        List<String> children = zooKeeper.getChildren(node, false);
        for (String child : children) {
            String childPath = node + "/" + child;
            if (!visited.containsKey(childPath) || !visited.get(childPath)) {
                visited.put(child, true);
//                tree.add(childPath);
                DFS(childPath, visited, tree);
            }
        }
        return tree;
    }
}


class ColoredOutput {
    enum Color {
        BLUE("\u001B[34m"), GREEN("\u001B[32m"), PURPLE("\u001B[35m"), RED("\u001B[31m"), YELLOW("\u001B[33m"), RESET("\u001B[0m");

        private String color;

        private Color(String color) {
            this.color = color;
        }
    }

    public static void printInColor(Color color, String string) {
        System.out.println(color.color + string + Color.RESET.color);
    }
}