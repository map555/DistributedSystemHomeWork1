public class Client {
    private int id;
    private int age;
    private String name;
    private String email;

    public Client(int id, int age, String name, String email) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void PrintClient(){
        System.out.println(getName()+", Age: "+getAge()+", email: "+getEmail());
    }



}
