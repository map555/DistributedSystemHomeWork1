/*Cette classe représente un commentaire et ne contient que l'id de l'utilisateur et le contenu du commentaire en question.*/

public class Comment {
    private int userID;
    private String content;

    public Comment(int userID, String content) {
        this.userID = userID;
        this.content = content;
    }

    private int getUserID() {
        return userID;
    }

    private String getContent() {
        return content;
    }

    @Override
    public String toString(){
        return "ID("+getUserID()+"): "+ getContent();
    }

}
