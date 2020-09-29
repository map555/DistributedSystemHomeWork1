import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;

public class CalculatorInterfaceImp extends UnicastRemoteObject implements CalculatorInterface {
    protected CalculatorInterfaceImp() throws RemoteException {}


    @Override
    public void Addition(int value1, int value2, ClientInterface clientCallBackObject) throws RemoteException, InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        clientCallBackObject.PrintResult(Integer.toString(value1 + value2));
    }


    @Override
    public void Substraction(int value1, int value2, ClientInterface clientCallBackObject) throws RemoteException, InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        clientCallBackObject.PrintResult(Integer.toString(value1 - value2));
    }


    @Override
    public void Multiplication(int value1, int value2, ClientInterface clientCallBackObject) throws RemoteException, InterruptedException {

        TimeUnit.SECONDS.sleep(10);
        clientCallBackObject.PrintResult(Integer.toString(value1 * value2));

    }


    @Override
    public void Division(int value1, int value2, ClientInterface clientCallBackObject) throws RemoteException, InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        clientCallBackObject.PrintResult(Integer.toString(value1 / value2));

    }


    @Override
    public void InvalidOperator(ClientInterface clientCallBackObject) throws RemoteException {
        clientCallBackObject.PrintResult("INVALID OPERATOR\n\tThe accepted operators are \"+\", \"-\", \"*\" and \"/\".");
    }


}
