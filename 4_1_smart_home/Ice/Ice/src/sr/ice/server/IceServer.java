package sr.ice.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class IceServer {
	public void t1(String[] args) {
		int status = 0;
		Communicator communicator = null;

		try {
			// 1. Inicjalizacja ICE - utworzenie communicatora
			communicator = Util.initialize(args);

			// 2. Konfiguracja adaptera
			// METODA 1 (polecana produkcyjnie): Konfiguracja adaptera Adapter1 jest w pliku konfiguracyjnym podanym jako parametr uruchomienia serwera
			ObjectAdapter adapter = communicator.createObjectAdapter("SmartHomeAdapter1");

			// METODA 2 (niepolecana, dopuszczalna testowo): Konfiguracja adaptera Adapter1 jest w kodzie źródłowym
			//ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h 127.0.0.2 -p 10000");
			//ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h 127.0.0.2 -p 10000 : udp -h 127.0.0.2 -p 10000");
//			ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter2", "tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000 -z");

			// 3. Utworzenie serwanta/serwantów
//			CalcI calcServant1 = new CalcI();
//			CalcI calcServant2 = new CalcI();
			BulbI bulbServant = new BulbI("bathroomBulb");
			CarbonMonoxideDetectorI carbonMonoxideDetectorServant = new CarbonMonoxideDetectorI("bathroom");
			SmokeDetectorI smokeDetectorServant = new SmokeDetectorI("kitchen");


			// 4. Dodanie wpisów do tablicy ASM, skojarzenie nazwy obiektu (Identity) z serwantem
//			adapter.add(calcServant1, new Identity("calc11", "calc"));
			adapter.add(bulbServant, new Identity("bathroom", "Bulb"));
			adapter.add(carbonMonoxideDetectorServant, new Identity("bathroom", "Detector"));
			adapter.add(smokeDetectorServant, new Identity("kitchen", "Detector"));

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
		IceServer app = new IceServer();
		app.t1(args);
	}
}