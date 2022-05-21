package testJUnit;

import login.Officer;
import org.junit.jupiter.api.Assertions;

class OfficerTest {
    Officer officerTest = new Officer(300, "officer", "password");

    @org.junit.jupiter.api.Test
    void getOfficerIDTest() {
        int officerId = officerTest.getOfficerID();
        Assertions.assertEquals(300, officerId);
    }

    @org.junit.jupiter.api.Test
    void setOfficerIDTest() {
        officerTest.setOfficerID(200);
        int officerId = officerTest.getOfficerID();
        Assertions.assertEquals(200, officerId);
    }
}