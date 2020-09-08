import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientApp {
    Client client;
    Socket clientSocket;
    ObjectOutputStream sortie;
    ObjectInputStream entree;



    private void connectToServer() {
        try {
            clientSocket = new Socket(
                    InetAddress.getByName("127.0.0.1"), 5432);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerClient() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter your name");
        String name = sc.nextLine();

        System.out.println("Please enter your age");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.println("Please enter your e-mail address");
        String email = sc.nextLine();

        try {
            sortie = new ObjectOutputStream(clientSocket.getOutputStream());
            sortie.writeObject("Name:" + name + ";Age:" + age + ";email:" + email);
            sortie.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true); //TODO: remove this, it's just for testing
    }

    public static void main(String args[]) {

        ClientApp app = new ClientApp();

        app.connectToServer();

        app.registerClient();

    }
}
