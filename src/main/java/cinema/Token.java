package cinema;

import java.util.UUID;

public class Token {
    String token;
    Seat ticket;

    public Token(Seat ticket) {
        this.ticket = ticket;
        generateToken();
    }

    public Token(String token, Seat ticket) {
        this.token = token;
        this.ticket = ticket;
    }

    public Token(){

    }

    private void generateToken(){
        UUID uuid = UUID.randomUUID();
        token = uuid.toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Seat getTicket() {
        return ticket;
    }

    public void setTicket(Seat ticket) {
        this.ticket = ticket;
    }


}