package login;

import bCrypt.BCrypt;

/**
 * Represents a user
 */
public class User {

    private final int id;
    private String username;
    private String salt;
    private String hashed;

    /**
     * This constructor generates a salt associated to the password entered and hashes the combination password + salt
     * @param id
     * @param username
     * @param password
     * @return if the registration has been completed
     */
    public User(int id,String username, String password) {
        this.id=id;
        this.username = username;
        this.salt = BCrypt.gensalt();
        this.hashed = BCrypt.hashpw(password,this.salt);
    }


    /**
     * @return current user id
     */
    public int getId() {
        return id;
    }

    /**
     * @return current user username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return current salt associated to the password of the constructor
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @return current hashed password associated to the password of the constructor and its salt
     */
    public String getHashed() {
        return hashed;
    }

}

