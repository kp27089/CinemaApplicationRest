package cinema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private int total_rows;
    private int total_columns;
    private ArrayList<Seat> available_seats;


    public Room(int total_rows, int total_columns) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        this.available_seats = new ArrayList<Seat>();
        for (int i = 1; i <= total_rows; i++) {
            for (int j = 1; j <= total_columns; j++) {
                int price;
                if (i <= 4) {
                    price = 10;
                } else {
                    price = 8;
                }
                available_seats.add(new Seat(i, j, price));
            }
        }
    }


    public ArrayList<Seat> getAvailable_seats() {
        return available_seats;
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setAvailable_seats(ArrayList<Seat> available_seats) {
        this.available_seats = available_seats;
    }
}