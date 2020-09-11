import java.util.Vector;

public class Profile{
    private final Client client;
    private Vector<Comment> commentsVector;

    public Profile(Client client) {
        this.client = client;
        commentsVector=new Vector<>();
    }

    public Profile(Client client, Vector<Comment> commentsVector) {
        this.client = client;
        this.commentsVector = commentsVector;
    }

    public String PrintProfileData(){
        StringBuilder profile = new StringBuilder(client.PrintClient());
        profile.append("\ncomments:");

        for(Comment comment: commentsVector) {
            profile.append("\n ").append(comment);
        }
        return profile.toString();
    }

    public void AddComment(Comment newComment){
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
}
