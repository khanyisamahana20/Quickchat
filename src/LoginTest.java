import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    Login login = new Login("Khanyisa", "Mahana");

    @Test
    public void testUsernameCorrect() {
        assertTrue(login.checkUsername("kha_1"));
    }

    @Test
    public void testUsernameIncorrect() {
        assertFalse(login.checkUsername("khanyisa"));
    }

    @Test
    public void testPasswordMeetsComplexity() {
        assertTrue(login.checkPasswordComplexity("Password1!"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCellPhoneValid() {
        assertTrue(login.checkCellPhoneNumber("+27812345678"));
    }

    @Test
    public void testCellPhoneInvalid() {
        assertFalse(login.checkCellPhoneNumber("0812345678"));
    }

    @Test
    public void testRegisterSuccess() {
        String result = login.registerUser("kha_1", "Password1!", "+27812345678");
        assertEquals("User registered successfully", result);
    }

    @Test
    public void testLoginSuccess() {
        login.registerUser("kha_1", "Password1!", "+27812345678");
        assertTrue(login.loginUser("kha_1", "Password1!"));
    }

    @Test
    public void testLoginFailure() {
        login.registerUser("kha_1", "Password1!", "+27812345678");
        assertFalse(login.loginUser("kha_1", "wrongPass1!"));
    }

    @Test
    public void testReturnLoginStatusSuccess() {
        login.registerUser("kha_1", "Password1!", "+27812345678");
        assertEquals("Welcome Khanyisa Mahana it is great to see you again.", login.returnLoginStatus("kha_1", "Password1!"));
    }
}