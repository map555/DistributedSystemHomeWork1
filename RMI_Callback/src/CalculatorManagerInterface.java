import java.rmi.Remote;
import java.rmi.RemoteException;

/*Cette interface déclare la méthode compute, qui a pour paramètre les informations de l'opérations ainsi que l'objet callback du client,
* qui sera utilisé pour appeler la méthode PrintResult pour envoyer la résultat au client */

public interface CalculatorManagerInterface extends Remote {
   public void Compute(int value1, int value2, char operator, ClientInterface clientObjectCallback) throws RemoteException, InterruptedException;
}
