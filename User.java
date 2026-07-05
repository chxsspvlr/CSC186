public class User {
    private String userID;
    private String name;
    
    public static final String[] userID1 = {"U01", "U02", "U03", "U04"};
    public static final String[] name1   = {"Dhiya",  "Ain", "Afeeq", "Iman"};

    public User(String userID, String name) {
        this.userID = userID;
        this.name = name;
    }

    public void setUserID(String id) { this.userID = id; }
    public void setName(String nm) { this.name = nm; }
    
    
    public String getUserID() { return this.userID; }
    public String getName() { return this.name; }

     public String toString() {
        return "\nUser ID : " + userID +
               "\nName    : " + name;
    }
}
