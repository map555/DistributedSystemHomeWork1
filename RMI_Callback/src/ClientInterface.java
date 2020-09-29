import java.rmi.Remote;
import java.rmi.RemoteException;

/*Cette interface déclare les méthode qui seront utilisé pour communiquer avec le client,
c'est la méthode que la calculateur utilisera pour faire le callback au client lorsque le calcul est terminé*/

public interface ClientInterface extends Remote {
     void PrintResult(String result) throws RemoteException;
}
