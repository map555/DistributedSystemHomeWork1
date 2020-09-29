/*Cette classe représente l'objet client et contient les informations de l'utilisateur*/

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

    public String PrintClient(){
        return getName()+", Age: "+getAge()+", email: "+getEmail();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (age == 0 ? 0 : age);
        hash = 31 * hash + (name == null ? 0 : name.hashCode());
        hash = 31 * hash + (email == null ? 0: email.hashCode());
        return hash;
    }
}
