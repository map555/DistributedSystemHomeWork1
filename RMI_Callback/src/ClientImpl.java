import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientImpl implements WorkListener {

    public static void main( String [] args) {
        new ClientImpl("localhost:1099");
    }

    public ClientImpl(String host) {
        try {
            Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Server");
            System.out.println(calculator.add(1,2));
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void workCompleted(WorkRequest request, Object result) throws RemoteException {
        System.out.println("result: " + result);
    }
}
