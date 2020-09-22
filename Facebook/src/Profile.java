import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class Profile{
    private Client client;
    private Vector<Comment> commentsVector;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Profile() {}

    public Profile(Client client) {
        this.client = client;
        commentsVector=new Vector<>();
    }

    public Profile(Client client, Vector<Comment> commentsVector) {
        this.client = client;
        this.commentsVector = commentsVector;
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

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
