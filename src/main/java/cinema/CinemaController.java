package cinema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
public class CinemaController {

    private static ConcurrentMap<String,Seat> tokens = new ConcurrentHashMap<>();

    Room cinema = new Room(9, 9);

    @GetMapping("/seats")
    public Room getRooms() {
        return cinema;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> seatStatus(@RequestBody Seat seat) {

        for (int i = 0; i < cinema.getAvailable_seats().size(); i++) {

            if (cinema.getAvailable_seats().get(i).getRow() == seat.getRow() && cinema.getAvailable_seats().get(i).getColumn() == seat.getColumn() && cinema.getAvailable_seats().get(i).isFree()) {
                cinema.getAvailable_seats().get(i).setFree(false);
                Token token = new Token(cinema.getAvailable_seats().get(i));
                String tempToken = token.getToken();
                tokens.put(tempToken,cinema.getAvailable_seats().get(i));
                Token result = new Token(tempToken,tokens.get(tempToken));
                return new ResponseEntity<Token>(result, HttpStatus.OK);
            } else if (seat.getColumn() > 9 || seat.getRow() > 9 || seat.getColumn() < 1 || seat.getRow() < 1) {
                Errors err = new Errors(false);
                return new ResponseEntity<Errors>(err, HttpStatus.BAD_REQUEST);
            }
        }
        Errors err = new Errors(true);
        return new ResponseEntity<Errors>(err, HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/return")
    public ResponseEntity<?>returnTok(@RequestBody Token token) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        String tokenAsString = token.getToken();
        if (!tokens.containsKey(tokenAsString)) {
            Errors wrong = new Errors();
            return new ResponseEntity<Errors>(wrong, HttpStatus.BAD_REQUEST);
        }else{
            Returned returned = new Returned(tokens.get(tokenAsString));
            tokens.get(tokenAsString).setFree(true);
            tokens.remove(tokenAsString);

            return new ResponseEntity<Returned>(returned,HttpStatus.OK);
        }

    }

    @PostMapping("/stats")
    public ResponseEntity<?>returnStat(@RequestParam(required = false) Map<String, String> password)  {
        if(password.containsKey("password")&&password.get("password").equals("super_secret")){
            int income = 0;
            int number_of_available_seats = 0;
            int number_of_purchased_tickets = 0;
            for(int i = 0; i < cinema.getAvailable_seats().size();i++){
                if(cinema.getAvailable_seats().get(i).isFree()==false){
                    number_of_purchased_tickets++;
                    income+=cinema.getAvailable_seats().get(i).getPrice();

                }else{
                    number_of_available_seats++;
                }
            }
            Stats stats = new Stats(income,number_of_available_seats,number_of_purchased_tickets);
            return new ResponseEntity<Stats>(stats,HttpStatus.OK);
        }
        Errors err = new Errors("error");
        return new ResponseEntity<Errors>(err,HttpStatus.UNAUTHORIZED);
    }
}