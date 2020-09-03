import java.util.Iterator;
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

        Iterator<Comment> commentIterator=commentsVector.iterator();
        while(commentIterator.hasNext()){
            System.out.print(" ");
            commentIterator.next().PrintComment();
        }
    }

    public void AddComment(Comment newComment){
        commentsVector.add(newComment);
    }


}
