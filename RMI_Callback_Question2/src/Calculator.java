import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Calculator {

    public  static void main(String[] args) throws RemoteException, MalformedURLException {
        String url="rmi://localhost:";
        int port=42020;
        int calculatorID;
        Scanner input = new Scanner(System.in);  // Create a Scanner object


        System.out.println("Enter the calculator number (1,2,3)");
        String inputString=input.nextLine();
        switch(inputString){
            case("1"):{
                calculatorID=1;
                port+=calculatorID;
                url+=Integer.toString(port)+"/Calculator1";
                break;
            }
            case ("2"):{
                calculatorID=2;
                port+=calculatorID;
                url+=Integer.toString(port)+"/Calculator2";
                break;
            }
            case("3"):{
                calculatorID=3;
                port+=calculatorID;
                url+=Integer.toString(port)+"/Calculator3";
                break;
            }
            default:{
                throw (new IllegalArgumentException(String.format("You must enter \"1\", \"2\" or \"3\". You entered the following value: %s.\n",inputString)));

            }
        }
        Registry registry= LocateRegistry.createRegistry(port);
        CalculatorInterfaceImp Calculator=new CalculatorInterfaceImp();
        Naming.rebind(url,Calculator);
        System.out.println("Calculator"+calculatorID+" started.");



    }
}
