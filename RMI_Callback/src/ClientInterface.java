import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
     void PrintResult(String result) throws RemoteException;
}
