import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculatorManagerInterface extends Remote {
   public void Compute(int value1, int value2, char operator, ClientInterface clientObjectCallback) throws RemoteException, InterruptedException;
}
