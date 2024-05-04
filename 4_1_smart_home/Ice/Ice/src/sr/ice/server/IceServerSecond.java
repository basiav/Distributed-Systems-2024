package sr.ice.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

import java.util.Arrays;

public class IceServerSecond {
    public void t1(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try {
            // 1. Inicjalizacja ICE - utworzenie communicatora
            communicator = Util.initialize(args);

            // 2. Konfiguracja adaptera
            // METODA 1 (polecana produkcyjnie): Konfiguracja adaptera Adapter1 jest w pliku konfiguracyjnym podanym jako parametr uruchomienia serwera
            ObjectAdapter adapter = communicator.createObjectAdapter("SmartHomeAdapter2");

            // 3. Utworzenie serwanta/serwantów
            BulbI bulbServant = new BulbI("livingRoom");
            CarbonMonoxideDetectorI carbonMonoxideDetectorServant = new CarbonMonoxideDetectorI("bedroom");

            // 4. Dodanie wpisów do tablicy ASM, skojarzenie nazwy obiektu (Identity) z serwantem
            adapter.add(bulbServant, new Identity("livingRoomBulb", "Bulb"));
            adapter.add(carbonMonoxideDetectorServant, new Identity("bedroomDetect", "Detector"));

            // 5. Aktywacja adaptera i wejście w pętlę przetwarzania żądań
            adapter.activate();

            System.out.println("Entering event processing loop...");

            communicator.waitForShutdown();

        } catch (Exception e) {
            e.printStackTrace(System.err);
            status = 1;
        }
        if (communicator != null) {
            try {
                communicator.destroy();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                status = 1;
            }
        }
        System.exit(status);
    }


    public static void main(String[] args) {
        IceServerSecond app = new IceServerSecond();
        app.t1(args);
    }
}