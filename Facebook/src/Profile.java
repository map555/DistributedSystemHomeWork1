import java.util.Vector;

public class Profile {
    private Client client;
    private Vector<Comment> commentsVector;

    public Profile(Client client) {
        this.client = client;
        commentsVector=new Vector<>();
    }

    public Profile(Client client, Vector<Comment> commentsVector) {
        this.client = client;
        this.commentsVector = commentsVector;
    }

    public void PrintProfileData(){
        client.PrintClient();
        System.out.println("comments:");

        for(Comment comment: commentsVector) {
            System.out.print(" " + comment);
        }
    }

    public void AddComment(Comment newComment){
        commentsVector.add(newComment);
    }
}
