package kurt.test;

public class User {
    private String username;
    private String password;
    private String dob;
    private String email;
    private double reputation;
    private int posts;

    @Override
    // TODO: Delete this later.
    public String toString() {
        return String.format("[ Username: %s, Password: %s, Date of Birth: %s, Email: %s, Reputation: %.2f, Posts: %d ]",
                this.username, this.password, this.dob, this.email, this.reputation, this.posts);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setReputation(double reputation) {
        this.reputation = reputation;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public double getReputation() {
        return reputation;
    }

    public int getPosts() {
        return posts;
    }
}
