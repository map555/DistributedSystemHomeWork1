import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    Socket clientSocket;
    ObjectOutputStream out;
    ObjectInputStream in;

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
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject("Name:" + name + ";Age:" + age + ";email:" + email);
            out.flush();

            Thread listenerThread = new Thread(() -> {
                String message = "";
                while (true) {
                    try {
                        message = (String) in.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    System.out.println(message);
                }
            });
            listenerThread.start();


            String answer = "";
            while (true) {
                answer = sc.nextLine();
                out.writeObject(answer);
                out.flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {

        ClientApp app = new ClientApp();

        app.connectToServer();

        app.registerClient();

    }
}
