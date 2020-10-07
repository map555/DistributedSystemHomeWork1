import java.util.Vector;

/*Cette classe représente un profile d'un utilisateur, elle contient un objet client qui contient les informations de bases de client
* . on peut aussi retrouver les commentaires qui on été faits sur le profile de l'utilisateur ainsi que les chats qu'il a reçu.*/

public class Profile{
    private Client client;
    private Vector<Comment> commentsVector;
    private String chat = "";

    public Profile() {}

    public Profile(Client client) {
        this.client = client;
        commentsVector=new Vector<>();
    }

    public String printProfileData(){
        StringBuilder profile = new StringBuilder(client.PrintClient());
        profile.append("\ncomments:");

        for(Comment comment: commentsVector) {
            profile.append("\n ").append(comment);
        }
        return profile.toString();
    }

    public void addComment(Comment newComment){
        commentsVector.add(newComment);
    }

    @Override
    public int hashCode() {
        return client.hashCode();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this.hashCode() == otherObject.hashCode()) {
            return true;
        }
        return false;
    }

    public Client getClient() {
        return client;
    }

    public void addChat(String newChat) {
        chat += newChat + "\n";
    }

    public String getChats() {
        return chat;
    }
}
