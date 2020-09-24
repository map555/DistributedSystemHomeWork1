import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Calculator {

    public ServerImpl() throws RemoteException {}

    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

    public Object execute(WorkRequest work) throws RemoteException {
        return work.execute();
    }

    public static void main(String[] args) {
        try {
            Calculator calculator = new ServerImpl();
            Naming.rebind("rmi://localhost:1099/Server", calculator);
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
