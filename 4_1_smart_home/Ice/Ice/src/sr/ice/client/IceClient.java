package sr.ice.client;

import Demo.BulbPrx;
import com.zeroc.Ice.*;

import java.lang.Exception;

public class IceClient {
	public static void main(String[] args) {
		int status = 0;
		Communicator communicator = null;

		try {
			// 1. Inicjalizacja ICE
			communicator = Util.initialize(args);

			// 2. Uzyskanie referencji obiektu na podstawie linii w pliku konfiguracyjnym (wówczas aplikację należy uruchomić z argumentem --Ice.config=config.client)
			//ObjectPrx base1 = communicator.propertyToProxy("Calc1.Proxy");

			// 2. Uzyskanie referencji obiektu - to samo co powyżej, ale mniej ładnie
//			ObjectPrx base1 = communicator.stringToProxy("calc/calc11:tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000 -z"); //opcja -z włącza możliwość kompresji wiadomości
			ObjectPrx base1 = communicator.stringToProxy("Bulb/bathroom:tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000 -z"); //opcja -z włącza możliwość kompresji wiadomości


			// 3. Rzutowanie, zawężanie (do typu Calc)
//			CalcPrx obj1 = CalcPrx.checkedCast(base1);
			BulbPrx obj1 = BulbPrx.checkedCast(base1);

			//CalcPrx obj1 = CalcPrx.uncheckedCast(base1); //na czym polega różnica?
			if (obj1 == null) throw new Error("Invalid proxy");



		} catch (LocalException e) {
			e.printStackTrace();
			status = 1;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			status = 1;
		}
		if (communicator != null) { //clean
			try {
				communicator.destroy();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				status = 1;
			}
		}
		System.exit(status);
	}

}