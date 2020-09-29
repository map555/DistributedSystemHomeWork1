import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/*Cette class implémente l'interface ClientInterface et défnini la méthode PrintResult qui sera utilisé par le
* calculateur pour afficher le résultat de l'opération au client*/

public class ClientInterfaceImp extends UnicastRemoteObject implements ClientInterface {


    protected ClientInterfaceImp() throws RemoteException {}

    @Override
    public void PrintResult(String result)throws RemoteException {
        System.out.print(result+"\n");
    }
}
