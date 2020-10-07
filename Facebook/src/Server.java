import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/*Cette classe représente le serveur qui s'occupe de gérer les requêtes de tous les clients*/

public class Server {
    private final HashMap<Integer, Profile> clients = new HashMap<>();

    public void runServer() throws IOException {
        //On exécute le server sur le port 5432 et on accepte les connexions des clients, pour chaque connexion on alloue un nouveau thread qui s'occupera du client qui vient de se connecter.
        try (ServerSocket server = new ServerSocket(5432)) {
            while (true) {
                new Handler(server.accept()).start();
            }
        }
    }

    //Cette méthode sert à retrouver un id à partir d'un profile, utile pour savoir si un utilisateur est déjà existant
    private int getId(Profile profile) {
        for (Map.Entry entry : clients.entrySet()) {
            if (profile.equals(entry.getValue())) {
                return (Integer) entry.getKey();
            }
        }
        return -1;
    }

    //Dans cette méthode on parse les informations de l'utilisateur et on vérifie si son profile existe déjà dans la liste des profile, on l'ajoute s'il n'existe pas
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
        Profile chosenProfile = new Profile();


        public Handler(Socket socket) {
            this.socket = socket;
        }

        private String getMenuString() {
            return "\nPlease Choose\n1 to consult a profile\n2 to post a comment on a profile\n3 Send a chat to another user\n4 Consult chat box";
        }

        private void menu(String choice) throws IOException, ClassNotFoundException {
            String message;
            String ID;
            chosenProfile = new Profile();

            switch (choice) {
                //Si l'utilisateur entre le choix 1, il veut consulter un profile, on lui demande l'id du profile en question ensuite on fait la recherche dans la hashtable
                case "1" : {
                    message = "Enter the profile's ID";
                    out.writeObject(message);
                    out.flush();
                    ID = (String) in.readObject();
                    chosenProfile = clients.get(Integer.parseInt(ID));
                    if(chosenProfile!=null){
                        message = chosenProfile.printProfileData() + getMenuString();
                    }
                    else {
                        message="Profile does not exist!"+getMenuString();
                    }
                    out.writeObject(message);
                    out.flush();
                    break;
                }
                /*Si l'utilisateur entre le choix 2, il veut ajouter un commentaire sur un profile,
                on lui demande l'id du profile en question et le commentaire à ajouter et ensuite on ajoute le commentaire sur le bon profile*/
                case "2" : {
                    message = "Enter the profile's ID";
                    out.writeObject(message);
                    out.flush();
                    ID = (String) in.readObject();
                    chosenProfile = clients.get(Integer.parseInt(ID));
                    if(chosenProfile!=null)
                    {
                        out.writeObject("Enter the comment to post");
                        out.flush();
                        String commentString = (String) in.readObject();
                        Comment comment = new Comment(profile.getClient().getId(), commentString);
                        chosenProfile.addComment(comment);
                        message = "Comment added!" + getMenuString();
                    }
                    else {
                        message="Profile does not exist!"+getMenuString();
                    }

                    out.writeObject(message);
                    out.flush();
                    break;
                }
                /*Si l'utilisateur entre le choix 3, il veut envoyer un chat à un autre utilisateur, on lui demande
                * l'id de l'utilisateur et le message à envoyer, le message est ajouté à la boite de reception de l'utilisateur*/
                case "3" : {
                    message = "Enter the profile's ID";
                    out.writeObject(message);
                    out.flush();
                    ID = (String) in.readObject();
                    chosenProfile = clients.get(Integer.parseInt(ID));
                    if (chosenProfile != null) {
                        out.writeObject("Enter the message:");
                        out.flush();
                        String chat = (String) in.readObject();
                        chosenProfile.addChat(profile.getClient().getName() + " (" + profile.getClient().getId() + "): " + chat);
                        out.writeObject("Chat added!" + getMenuString());
                        out.flush();
                    } else {
                        out.writeObject("Profile does not exist!" + getMenuString());
                        out.flush();
                    }
                    break;
                }
                //Si l'utilisateur entre le choix 4, il veut consulter sa boite de reception, on ne fait que lui envoyer le contenu de sa boite.
                case "4" : {
                    out.writeObject(profile.getChats() + getMenuString());
                    out.flush();
                    break;
                }


                //Si l'utilisateur n'entre pas un choix valide, on lui dit et on réaffiche le menu.
                default : {
                    out.writeObject("Enter a valid string" + getMenuString());
                    out.flush();
                    break;
                }
            }
        }

        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                in = new ObjectInputStream(socket.getInputStream());

                //On attend de recevoir les informations du client et ensuite on fait le login de l'utilisateur
                String credentials = (String) in.readObject();
                profile = login(credentials);

                //On envoie un message de confirmation ainsi que le menu principal au client lorsque le login est fait
                String message = "You are registered, your ID is : " + profile.getClient().getId() + getMenuString();
                out.writeObject(message);
                out.flush();

                //Ensuite on attend des réponses du client et on appelle la fonction menu() pour chaque réponse.
                while (true) {
                    String input = (String) in.readObject();
                    menu(input);
                }
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
