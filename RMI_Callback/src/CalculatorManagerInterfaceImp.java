import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/*Cette classe implémente l'interface CalculatorManagerInterface et défini la méthode Compute qui sera appelé par le client*/

public class CalculatorManagerInterfaceImp extends UnicastRemoteObject implements CalculatorManagerInterface {

    private int operationCounter;
    private CalculatorInterface calculator1Interface;
    private CalculatorInterface calculator2Interface;
    private CalculatorInterface calculator3Interface;


    /*Le constructeur initialise le compteur d'opération à 0 et va chercher les calculateur à l'aide de leur adresse réseau*/
    public CalculatorManagerInterfaceImp() throws RemoteException, MalformedURLException, NotBoundException {
        super();
        operationCounter = 0;
        calculator1Interface = (CalculatorInterface) Naming.lookup(getCalculatorURL(1));
        calculator2Interface = (CalculatorInterface) Naming.lookup(getCalculatorURL(2));
        calculator3Interface = (CalculatorInterface) Naming.lookup(getCalculatorURL(3));
        System.out.println("Connected successfully to the calculators 1,2 and 3.");
    }


    private static String getCalculatorURL(int calculatorID) {
        return String.format("rmi://localhost:4202%d/Calculator%d", calculatorID, calculatorID);
    }


    /*La méthode compute s'assure que la bonne méthode sera appelé sur le bon calculateur, les calculateurs sont choisis à tour de rôle */
    @Override
    public void Compute(int value1, int value2, char operator, ClientInterface clientObjectCallback) throws RemoteException, InterruptedException {

        switch (operationCounter % 3) {
            //calculator1
            case (0): {

                switch (operator) {

                    //addition from calculator1
                    case ('+'): {

                        calculator1Interface.Addition(value1, value2, clientObjectCallback);
                        break;

                    }

                    //substraction from calculator1
                    case ('-'): {

                        calculator1Interface.Substraction(value1, value2, clientObjectCallback);
                        break;

                    }

                    //multiplication from calculator1
                    case ('*'): {

                        calculator1Interface.Multiplication(value1, value2, clientObjectCallback);
                        break;

                    }

                    //division from calculator1
                    case ('/'): {

                        calculator1Interface.Division(value1, value2, clientObjectCallback);
                        break;

                    }

                    //invalid operator result from calculator1
                    default: {
                        System.out.print("INVALID OPERATOR\n\tThe accepted operators are \"+\", \"-\", \"*\" and \"/\".\n");
                        calculator1Interface.InvalidOperator(clientObjectCallback);
                    }

                }
                break;
            }

            //calculator2
            case (1): {

                switch (operator) {

                    //addition from calculator2
                    case ('+'): {

                        calculator2Interface.Addition(value1, value2, clientObjectCallback);
                        break;

                    }

                    //substraction from calculator2
                    case ('-'): {

                        calculator2Interface.Substraction(value1, value2, clientObjectCallback);
                        break;

                    }

                    //multiplication from calculator2
                    case ('*'): {

                        calculator2Interface.Multiplication(value1, value2, clientObjectCallback);
                        break;

                    }

                    //division from calculator2
                    case ('/'): {

                        calculator2Interface.Division(value1, value2, clientObjectCallback);
                        break;

                    }

                    //invalid operator result from calculator2
                    default: {

                        System.out.print("INVALID OPERATOR\n\tThe accepted operators are \"+\", \"-\", \"*\" and \"/\".\n ");
                        calculator2Interface.InvalidOperator(clientObjectCallback);

                    }

                }

                break;

            }

            //calculator3
            case (2): {

                switch (operator) {

                    //addition from calculator3
                    case ('+'): {

                        calculator3Interface.Addition(value1, value2, clientObjectCallback);
                        break;

                    }

                    //substraction from calculator3
                    case ('-'): {

                        calculator3Interface.Substraction(value1, value2, clientObjectCallback);
                        break;

                    }

                    //multiplication from calculator3
                    case ('*'): {

                        calculator3Interface.Multiplication(value1, value2, clientObjectCallback);
                        break;

                    }

                    //division from calculator3
                    case ('/'): {

                        calculator3Interface.Division(value1, value2, clientObjectCallback);
                        break;

                    }

                    //invalid operator result from calculator3
                    default: {

                        System.out.print(" =  INVALID OPERATOR\n\tThe accepted operators are \"+\", \"-\", \"*\" and \"/\".\n ");
                        calculator3Interface.InvalidOperator(clientObjectCallback);

                    }

                }
                break;
            }

        }
        System.out.println("Operation " + operationCounter + " done on calculator " + ((operationCounter % 3) + 1));
        operationCounter++;
    }

}
