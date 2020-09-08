import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {
    private final HashSet<Profile> clients = new HashSet<>();
    ObjectOutputStream output;
    ObjectInputStream input;

    public void runServeur() {
        ServerSocket server;
        Socket connexion;

        try {
            server = new ServerSocket(5432, 1);

            while (true) {
                connexion = server.accept();
                System.out.println("connexion from: " + connexion.getInetAddress().getHostAddress());

                output = new ObjectOutputStream(connexion.getOutputStream());
                output.flush();
                input = new ObjectInputStream(connexion.getInputStream());

                output.writeObject("Connexion with server established");
                output.flush();

                String message = "";
                do {
                    try {
                        message = (String) input.readObject();
                        System.out.println(message);
                        createProfile(message);
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                } while (!message.equals("disconnect")); //TODO: change this condition

                output.close();
                input.close();
                connexion.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createProfile(String infos) {
        String[] parts = infos.split(";");
        String name = parts[0].substring(parts[0].indexOf(':') + 1);
        String ageString = parts[1].substring(parts[1].indexOf(':') + 1);
        int age = Integer.parseInt(ageString);
        String email = parts[2].substring(parts[2].indexOf(':') + 1);
        Client client = new Client(clients.size(), age, name, email);

        Profile profile = new Profile(client);

        //TODO: override hashcode and equals methods in Profile class
        clients.add(profile);
    }

    public static void main(String args[]) {
        Server app = new Server();
        app.runServeur();
    }


}
