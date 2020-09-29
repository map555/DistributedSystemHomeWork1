import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {



    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
        int port=42020;
        String url=String.format("rmi://localhost:%d/CalculatorManager",port);
        Remote remoteCalculatorManagerInterface= Naming.lookup(url);

        CalculatorManagerInterface calculatorManagerInterface=(CalculatorManagerInterface) remoteCalculatorManagerInterface;

        ClientInterface callbackObj=new ClientInterfaceImp();

        Scanner input=new Scanner(System.in);
        int value1;
        int value2;
        char charInput;
        boolean quit=false;

        while (!quit){

            System.out.print("Please enter value1 (integer): ");
            value1= input.nextInt();

            System.out.print("Please enter the operator of the operation ( + , - , * or / ): ");
            charInput=input.next().charAt(0);

            System.out.print("Please enter value2 (integer): ");
            value2= input.nextInt();

            System.out.print(value1+" "+charInput+" "+value2+" = ");


            calculatorManagerInterface.Compute(value1,value2,charInput,callbackObj);

            System.out.print("Do you want to quit? (y/n)");
            charInput=input.next().charAt(0);
            if(charInput=='y'){
                quit=true;
            }

        }

        System.exit(0);

    }
}
