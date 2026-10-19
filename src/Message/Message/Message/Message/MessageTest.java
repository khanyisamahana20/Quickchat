import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        assertEquals("Message ready to send.", m.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        String longMsg = "a".repeat(251);
        Message m = new Message("+27718693002", longMsg, 0);
        assertEquals("Message exceeds 250 characters by 1; please reduce the size.", m.checkMessageLength());
    }

    @Test
    public void testRecipientSuccess() {
        Message m = new Message("+27718693002", "test", 0);
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell());
    }

    @Test
    public void testRecipientFailure() {
        Message m = new Message("08575975889", "test", 0);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", m.checkRecipientCell());
    }

    @Test
    public void testMessageHash() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        m.myMessageID = "0012345678";
        assertEquals("00:0:HITONIGHT", m.createMessageHash());
    }

    @Test
    public void testMessageID() {
        Message m = new Message("+27718693002", "test", 0);
        assertTrue(m.checkMessageID());
    }

    @Test
    public void testSentMessageSend() {
        Message.totalMessages = 0;
        Message m = new Message("+27718693002", "test", 0);
        assertEquals("Message successfully sent.", m.SentMessage("1"));
    }

    @Test
    public void testSentMessageDiscard() {
        Message m = new Message("+27718693002", "test", 0);
        assertEquals("Press 0 to delete the message.", m.SentMessage("2"));
    }

    @Test
    public void testSentMessageStore() {
        Message m = new Message("+27718693002", "test", 0);
        assertEquals("Message successfully stored.", m.SentMessage("3"));
    }

    @Test
    public void testTotalMessagesInLoop() {
        Message.totalMessages = 0;
        String[] msgs = {
                "Hi Mike, can you join us for dinner tonight?",
                "Hi Keegan, did you receive the payment?"
        };
        for (int i = 0; i < msgs.length; i++) {
            Message m = new Message("+27718693002", msgs[i], i);
            m.myMessageID = "00" + (12345678 + i);
            m.createMessageHash();
            if (i == 0) {
                m.SentMessage("1");
            }
        }
        assertEquals(1, Message.totalMessages);
    }
}