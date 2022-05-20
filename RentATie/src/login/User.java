package login;

import bCrypt.BCrypt;
import bdd.Request;
public class User {

    private int id;
    private String username;
    private String salt;
    private String hashed;

    public User(int id,String username, String password) {
        this.id=id;
        this.username = username;
        this.salt = BCrypt.gensalt();
        this.hashed = BCrypt.hashpw(password,this.salt);
    }

    public int getId() {
        return id;
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

    // Connection à la bdd
   /* public void getConnection() throws Exception {
        Request.getConnection();
    }
    */

}

