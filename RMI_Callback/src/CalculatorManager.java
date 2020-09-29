import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*Cette classe représente l'aiguilleur*/

public class CalculatorManager {
    public static void main(String[] args)
    {
        try
        {
            //On ouvre une connexion sur le port 42020 pour l'aiguilleur et on créé le registre
            int port=42020;
            String url=String.format("rmi://localhost:%d/CalculatorManager",port);
            CalculatorManagerInterfaceImp CalculatorManagers= new CalculatorManagerInterfaceImp();
            Registry registry= LocateRegistry.createRegistry(port);

            Naming.rebind(url,CalculatorManagers);
            System.out.println("Calculator Manager started");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


}

