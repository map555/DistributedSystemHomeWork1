import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private final HashMap<Integer, Profile> clients = new HashMap<>();

    public void runServer() throws IOException {
        try (ServerSocket server = new ServerSocket(5432)) {
            while (true) {
                new Handler(server.accept()).start();
            }
        }
    }

    private int getId(Profile profile) {
        for (Map.Entry entry : clients.entrySet()) {
            if (profile.equals(entry.getValue())) {
                return (Integer) entry.getKey();
            }
        }
        return -1;
    }

    private Profile login(String credentials) {
        String[] parts = credentials.split(";");
        String name = parts[0].substring(parts[0].indexOf(':') + 1);
        String ageString = parts[1].substring(parts[1].indexOf(':') + 1);
        int age = Integer.parseInt(ageString);
        String email = parts[2].substring(parts[2].indexOf(':') + 1);
        Client client = new Client(-1, age, name, email);
        Profile profile = new Profile(client);
        if (clients.containsValue(profile)) {
            profile.getClient().setId(getId(profile));
        } else {
            profile.getClient().setId(clients.size());
            clients.put(profile.getClient().getId(), profile);
        }
        return profile;
    }

    public static void main(String args[]) {
        Server app = new Server();
        try {
            app.runServer();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public class Handler extends Thread {
        private Profile profile;
        private final Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        private void menu(String choice) throws IOException, ClassNotFoundException {
            String message;
            String ID;
            Profile chosenProfile;
            switch (choice) {
                case "1":
                    message = "Enter the profile's ID";
                    out.writeObject(message);
                    out.flush();
                    ID = (String) in.readObject();
                    chosenProfile = clients.get(Integer.parseInt(ID));
                    message = chosenProfile.printProfileData() + "\n\nPlease Choose\n1 to consult a profile\n2 to post a comment on a profile\n3 disconnect";
                    out.writeObject(message);
                    out.flush();
                    break;

                case "2":
                    message = "Enter the profile's ID";
                    out.writeObject(message);
                    out.flush();
                    ID = (String) in.readObject();
                    chosenProfile = clients.get(Integer.parseInt(ID));
                    out.writeObject("Enter the comment to post");
                    out.flush();
                    String commentString = (String) in.readObject();
                    Comment comment = new Comment(profile.getClient().getId(), commentString);
                    chosenProfile.addComment(comment);
                    message = "Comment added!\n\nPlease Choose\n1 to consult a profile\n2 to post a comment on a profile\n3 disconnect";
                    out.writeObject(message);
                    out.flush();
                    break;

                case "3":
                    out.writeObject("disconnect");
                    out.flush();
                    break;

                default:
                    out.writeObject("Enter 1,2 or 3\n\nPlease Choose\n1 to consult a profile\n2 to post a comment on a profile\n3 disconnect");
                    out.flush();
                    break;
            }
        }

        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                in = new ObjectInputStream(socket.getInputStream());

                String credentials = (String) in.readObject();
                profile = login(credentials);

                String message = "You are registered, your ID is : " + profile.getClient().getId() + "\nPlease Choose\n1 to consult a profile\n2 to post a comment on a profile\n3 disconnect";
                out.writeObject(message);
                out.flush();
                do {
                    menu((String) in.readObject());
                } while (true);


            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
