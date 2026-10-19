
public class Login {
    String myUsername;
    String myFirstName;
    String myLastName;
    String myPassword;
    String cellPhoneNumber;

    public Login(String firstName, String lastName) {
        this.myFirstName = firstName;
        this.myLastName = lastName;
    }

    public boolean checkUsername(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        if (password.length() < 8) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);
            if (Character.isUpperCase(character)) {
                hasCapital = true;
            }
            if (Character.isDigit(character)) {
                hasNumber = true;
            }
            if (!Character.isLetterOrDigit(character)) {
                hasSpecialCharacter = true;
            }
        }
        return hasCapital && hasNumber && hasSpecialCharacter;
    }
// Regex pattern for SA cellPhone validation sourced from StackOverflow
    // Adapted to require international code +27 and 9 digits - ensures +27 format as per brief
    public boolean checkCellPhoneNumber(String cellNumber) {
        return cellNumber.matches("^\\+27[0-9]{9}$");
    }

    public String registerUser(String username, String password, String cellNumber) {
        if (!checkUsername(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.";
        }
        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number is incorrectly formatted or does not contain an international code.";
        }
        this.myUsername = username;
        this.myPassword = password;
        this.cellPhoneNumber = cellNumber;
        return "User registered successfully";
    }

    public boolean loginUser(String username, String password) {
        return username.equals(myUsername) && password.equals(myPassword);
    }

    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + myFirstName + " " + myLastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}