
public class Borrower {
    private int id;
    private String name;
    private String email;
    private String Address;
    private String ContactN;
    
    public Borrower(int id, String name, String email, String Address, String ContactN){
        this.id = id;
        this.name = name;
        this.email = email;
        this.Address = Address;
        this.ContactN = ContactN;
    }
    
     public Borrower(String name, String email, String Address, String ContactN){
        this.name = name;
        this.email = email;
        this.Address = Address;
        this.ContactN = ContactN;
    }

    Borrower() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getContactN() {
        return ContactN;
    }

    public void setContactN(String ContactN) {
        this.ContactN = ContactN;
    }
}
