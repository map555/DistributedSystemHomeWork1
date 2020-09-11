import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {
    private final HashSet<Profile> clients = new HashSet<>(); //TODO: this should be hashMap<Profile, Profile>
    ObjectOutputStream output;
    ObjectInputStream input;

    public void runServeur() throws IOException {
        ServerSocket server = new ServerSocket(5432);
        try {
            while (true) {
                new Handler(server.accept()).start();
            }
        } finally {
            server.close();
        }
    }

    private Profile login(String credentials) {
        String[] parts = credentials.split(";");
        String name = parts[0].substring(parts[0].indexOf(':') + 1);
        String ageString = parts[1].substring(parts[1].indexOf(':') + 1);
        int age = Integer.parseInt(ageString);
        String email = parts[2].substring(parts[2].indexOf(':') + 1);
        Client client = new Client(-1, age, name, email);
        Profile profile = new Profile(client);
        if(clients.contains(profile)) {
            //retrieve id and set it in profile
            return profile;
        }
        clients.add(profile);
        return profile;
    }

    public static void main(String args[]) {
        Server app = new Server();
        try {
            app.runServeur();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public class Handler extends Thread {
        private Profile profile;
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public Handler(Socket socket) { this.socket = socket; }

        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                in = new ObjectInputStream(socket.getInputStream());

                String credentials = (String) in.readObject();
                profile = login(credentials);

                String message = "You are registered, your ID is : " + profile.getClient().getId();
                out.writeObject(message);
                out.flush();


            }catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
