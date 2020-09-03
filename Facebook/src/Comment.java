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

    public void PrintComment(){
        System.out.println("ID("+getUserID()+"): "+getContent());
    }

}
