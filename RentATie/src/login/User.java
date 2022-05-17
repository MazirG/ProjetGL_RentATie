package login;

import BCrypt.BCrypt;
import bdd.Request;
public abstract class User {

    private String username;

    private String salt;

    private String hashed;

    public User(String username, String password) {
        this.username = username;
        this.salt = BCrypt.gensalt();
        this.hashed = BCrypt.hashpw(password,this.salt);
    }

    public String getUsername() {
        return username;
    }

    public String getSalt() {
        return salt;
    }
    public String getHashed() {
        return hashed;
    }

    public void setPassword(String newPwd){
        this.salt = BCrypt.gensalt();
        this.hashed = BCrypt.hashpw(newPwd,this.salt);
    }

    // Connection à la bdd
    public void getConnection() throws Exception {
        Request.getConnection();
    }
}

