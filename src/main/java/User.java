public class User {
    private final String firstName;
    private final String lastName;
    private final String userName;
    private final long id;

    public User(String firstName, String lastName, String userName, long id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public long getId() {
        return id;
    }


}
