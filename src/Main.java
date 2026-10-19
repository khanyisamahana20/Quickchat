import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        Login user = new Login(firstName, lastName);

        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Enter cellphone (+27...): ");
        String cellNumber = input.nextLine();

        String regMessage = user.registerUser(username, password, cellNumber);
        System.out.println(regMessage);

        if (regMessage.equals("User registered successfully")) {
            System.out.println("Username successfully captured.");
            System.out.println("Password successfully captured.");
            System.out.println("Cell number successfully added.");

            System.out.println("\n--- LOGIN ---");
            System.out.print("Username: ");
            String loginUsername = input.nextLine();

            System.out.print("Password: ");
            String loginPassword = input.nextLine();

            System.out.println(user.returnLoginStatus(loginUsername, loginPassword));

            if (user.loginUser(loginUsername, loginPassword)) {

                // FIX 1: welcome message
                System.out.println("Welcome to QuickChat.");

                System.out.print("How many messages do you want to send? ");
                int total = Integer.parseInt(input.nextLine());
                int i = 0; // how many messages have been entered so far

                // FIX 2: numeric menu that keeps running until the user quits
                boolean running = true;
                while (running) {
                    System.out.println("\n1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.print("Choose an option: ");
                    String option = input.nextLine();

                    if (option.equals("1")) {

                        if (i >= total) {
                            System.out.println("You have entered all " + total + " messages.");
                        } else {
                            System.out.println("\nMessage " + (i + 1) + ":");
                            System.out.print("Recipient (+27...): ");
                            String recipient = input.nextLine();
                            System.out.print("Message (max 250 chars): ");
                            String messageText = input.nextLine();

                            // FIX 5: use i (not i + 1) so the first message is number 0
                            Message msg = new Message(recipient, messageText, i);

                            String cellCheck = msg.checkRecipientCell();
                            String lengthCheck = msg.checkMessageLength();
                            System.out.println(cellCheck);
                            System.out.println(lengthCheck);

                            // FIX 3: only carry on if both checks passed
                            if (cellCheck.equals("Cell phone number successfully captured.")
                                    && lengthCheck.equals("Message ready to send.")) {

                                System.out.println("Message ID generated: " + msg.myMessageID);
                                System.out.println("Hash: " + msg.createMessageHash());

                                System.out.println("1) Send Message");
                                System.out.println("2) Disregard Message");
                                System.out.println("3) Store Message to send later");
                                System.out.print("Choose: ");
                                String choice = input.nextLine();

                                // FIX: SentMessage (with a t)
                                System.out.println(msg.SentMessage(choice));

                                // FIX 4: full details straight after sending
                                if (choice.equals("1")) {
                                    System.out.println("Message ID: " + msg.myMessageID);
                                    System.out.println("Message Hash: " + msg.myMessageHash);
                                    System.out.println("Recipient: " + msg.myRecipient);
                                    System.out.println("Message: " + msg.myMessageText);
                                }

                                i++;
                            }
                        }

                    } else if (option.equals("2")) {
                        System.out.println("Coming Soon.");

                    } else if (option.equals("3")) {
                        running = false;

                    } else {
                        System.out.println("Please choose 1, 2 or 3.");
                    }
                }

                System.out.println("\n--- SENT MESSAGES ---");
                System.out.println(Message.printMessages());
                // FIX: use the method, which must be static in Message.java
                System.out.println("Total messages: " + Message.returnTotalMessages());
            }
        }
        input.close();
    }
}