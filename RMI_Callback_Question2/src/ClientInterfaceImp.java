import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientInterfaceImp extends UnicastRemoteObject implements ClientInterface {


    protected ClientInterfaceImp() throws RemoteException {
    }

    @Override
    public void PrintResult(String result)throws RemoteException {
        System.out.print(result+"\n");
    }
}
