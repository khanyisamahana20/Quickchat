import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    // ---------- Message length (success and failure) ----------

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("0012345678", "+27718693002",
                "Hi Mike, can you join us for dinner tonight?", 0);
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        // 260 characters, so it is 10 too long
        String longText = "a".repeat(260);
        Message msg = new Message("0012345678", "+27718693002", longText, 0);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                msg.checkMessageLength());
    }

    // ---------- Recipient number (success and failure) ----------

    @Test
    public void testRecipientNumberSuccess() {
        Message msg = new Message("0012345678", "+27718693002",
                "Hi Mike, can you join us for dinner tonight?", 0);
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientNumberFailure() {
        Message msg = new Message("0012345678", "08575975889",
                "Hi Keegan, did you receive the payment?", 1);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg.checkRecipientCell());
    }

    // ---------- Message hash (first one, then the rest in a loop) ----------

    @Test
    public void testMessageHashTestCase1() {
        Message msg = new Message("0012345678", "+27718693002",
                "Hi Mike, can you join us for dinner tonight?", 0);
        assertEquals("00:0:HITONIGHT", msg.createMessageHash());
    }

    @Test
    public void testMessageHashLoop() {
        String[] messages = {
                "Hi Mike, can you join us for dinner tonight?",
                "Hi Keegan, did you receive the payment?"
        };
        String[] expected = {
                "00:0:HITONIGHT",
                "00:1:HIPAYMENT"
        };

        for (int i = 0; i < messages.length; i++) {
            Message msg = new Message("0012345678", "+27718693002", messages[i], i);
            assertEquals(expected[i], msg.createMessageHash());
        }
    }

    // ---------- Message ID ----------

    @Test
    public void testMessageIDCreated() {
        Message msg = new Message("+27718693002",
                "Hi Mike, can you join us for dinner tonight?", 0);
        assertTrue(msg.checkMessageID());
        assertEquals(10, msg.myMessageID.length());
    }

    // ---------- Message sent (send, disregard, store) ----------

    @Test
    public void testMessageSent() {
        Message msg = new Message("0012345678", "+27718693002",
                "Hi Mike, can you join us for dinner tonight?", 0);
        assertEquals("Message successfully sent.", msg.SentMessage("1"));
    }

    @Test
    public void testMessageDisregarded() {
        Message msg = new Message("0087654321", "08575975889",
                "Hi Keegan, did you receive the payment?", 1);
        assertEquals("Press 0 to delete the message.", msg.SentMessage("2"));
    }

    @Test
    public void testMessageStored() {
        Message msg = new Message("0011223344", "+27718693002",
                "Hi Mike, can you join us for dinner tonight?", 2);
        assertEquals("Message successfully stored.", msg.SentMessage("3"));
    }

    // ---------- Total number of messages sent ----------

    @Test
    public void testTotalMessagesIncreases() {
        int before = Message.returnTotalMessages();
        Message msg = new Message("0012345678", "+27718693002",
                "Hi Mike, can you join us for dinner tonight?", 0);
        msg.SentMessage("1");
        assertEquals(before + 1, Message.returnTotalMessages());
    }
}