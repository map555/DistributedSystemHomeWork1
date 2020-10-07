import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/*Cette classe représente le client que l'utilisateur utilisera afin de communiquer avec le serveur*/
public class ClientApp {
    Socket clientSocket;
    ObjectOutputStream out;
    ObjectInputStream in;

    private void connectToServer() {
        try {
            //Le client se connecte au serveur qui est sur le port 5432
            clientSocket = new Socket(
                    InetAddress.getByName("127.0.0.1"), 5432);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerClient() {
        //Le client commence par entrer ses informations personnelles et les envoies au serveur à l'aide de l'objet ObjetOutputStream
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

            //Le client lance un nouveau thread qui écoute les message reçu par le serveur en permanence à l'aide de l'objet ObjectInputStream
            Thread listenerThread = new Thread(() -> {
                String message = "";
                while (true) {
                    try {
                        message = (String) in.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    //Lorsque l'on reçoit un message on l'imprime dans la console afin d'en informer l'utilisateur
                    System.out.println(message);
                }
            });
            listenerThread.start();


            //Le thread principal lui ne fait qu'envoyer au serveur tout ce que l'utilisateur écrit dans le termnial après qu'il ai appuyer sur ENTER
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
