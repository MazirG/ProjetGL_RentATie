package testJUnit;

import login.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    User userTest= new User(13,"username", "password");

    @Test
    void getId() {
        int userIdTest = userTest.getId();
        Assertions.assertEquals(13,userIdTest);
    }

    @Test
    void getUsername() {
        String usernameTest=userTest.getUsername();
        Assertions.assertEquals("username",usernameTest);
    }
}