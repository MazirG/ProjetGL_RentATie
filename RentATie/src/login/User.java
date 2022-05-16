package login;

import bdd.Request;

public abstract class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Connection à la bdd
    public void getConnection() throws Exception {
        Request.getConnection();
    }
}

