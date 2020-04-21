package main.java.ser322;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;


public class Main   {

    public static void main(String[] args) throws SQLException, ParseException {
        Controller.initializeDatabase();
//        Controller controller = new Controller();
//        Guest.createGuestTable(getConnection());
//        Guest.selectGuestInfo(getConnection());
//        Reservation.selectReservationInfo(getConnection());
//        controller.selectRoomTypes(getConnection());
//        Guest.insertGuest(getConnection(), 837, "Porter", "Robinson", "prob@gmail.com", "8467387266");
//        controller.insertCreditCard(getConnection(), 837, "7464746474647464", 756, 10, 3, "Porter", "Robinson");
//        Reservation.createReservation(getConnection(), 609, "7464746474647464", 2, 437.67f, "2020/10/1", "2020/12/13");


        AppFrame main = new AppFrame();

    }
}
