package cinema;

public class Errors {
    private String error;


    public String getError() {
        return error;
    }


    public Errors(boolean checking) {
        if (checking) {
            this.error = "The ticket has been already purchased!";
        } else {
            this.error = "The number of a row or a column is out of bounds!";
        }
    }

    ;

    public Errors() {
        this.error = "Wrong token!";
    }

    public Errors(String password) {
        this.error = "The password is wrong!";
    }

}