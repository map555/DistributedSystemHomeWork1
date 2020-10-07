import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculatorInterface extends Remote {
    public void Addition(int value1, int value2, ClientInterface clientCallBackObject)throws RemoteException, InterruptedException;
    public void Substraction(int value1, int value2, ClientInterface clientCallBackObject)throws RemoteException, InterruptedException;
    public void Multiplication(int value1, int value2, ClientInterface clientCallBackObject)throws RemoteException, InterruptedException;
    public void Division(int value1, int value2, ClientInterface clientCallBackObject) throws RemoteException, InterruptedException;
    public void InvalidOperator(ClientInterface clientCallBackObject)throws RemoteException;


}
