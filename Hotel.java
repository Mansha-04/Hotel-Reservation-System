package hotel;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Hotel {
    public static int ID = 1001;
    public static int num = 10;
    static String rooms[] = new String[num];
    static String roomType[] = new String[num];
    static double prices[] = new double[num];
    static String names[] = new String[num];
    static String resID[] = new String[num];
    static String[][] dateList = new String[num][2];
    static int rescount = 0;

    public static void assignType() {
        roomType[0] = "Single";
        roomType[1] = "Double";
        roomType[2] = "Suite";
        roomType[3] = "Single";
        roomType[4] = "Double";
        roomType[5] = "Suite";
        roomType[6] = "Single";
        roomType[7] = "Double";
        roomType[8] = "Suite";
        roomType[9] = "Single";
    }

    public static void assignPrice() {
        for (int i = 0; i < num; i++) {
            if (roomType[i].equals("Single")) {
                prices[i] = 100;
            } else if (roomType[i].equals("Double")) {
                prices[i] = 150;
            } else {
                prices[i] = 250;
            }
        }
    }

    public static void assignName() {
        for (int i = 0; i < num; i++) {
            names[i] = "";
        }
    }

    public static void assignID() {
        for (int i = 0; i < num; i++) {
            resID[i] = "";
        }
    }

    public static void customerSection() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please Enter your Name: ");
        String name = sc.nextLine();
        String[] dates = new String[2];

        while (true) {
            System.out.println("\n1. Select By Room Type (single,double,suite)");
            System.out.println("2. Enter Check-in and Check-out Details");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Your Reservation Details");
            System.out.println("5. Exit");
            System.out.println("Please Enter your Choice");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Please select the type of room you want to reserve (Single, Double, Suite):");
                    String type = sc.nextLine();
                    selectbytype(type, name);
                    break;
                case 2:
                    checkinout(name);
                    break;
                case 3:
                    cancelreserv(name);
                    break;
                case 4:
                    viewreserv(name);
                    break;
                case 5:
                    System.out.println("Thank You for availing our services. Have a nice day!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }

    }

    public static void adminSection() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Admin Password: ");
        String password = sc.nextLine();
        if (password.equals("Admin@123")) {
            System.out.println("Admin logged in successfully");
            while (true) {
                System.out.println("1. Display Reservations Details");
                System.out.println("2. Update Room Details");
                System.out.println("3. Display Available Rooms");
                System.out.println("4. Mark Rooms for Maintenance");
                System.out.println("5. Show Room Status");
                System.out.println("6. Exit");
                System.out.println("Please Enter your Choice");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        reservedet(dateList);
                        break;
                    case 2:
                        updatedet();
                        break;
                    case 3:
                        dispavailable();
                        break;
                    case 4:
                        maintenance();
                        break;
                    case 5:
                        showrooms();
                        break;
                    case 6:
                        System.out.println("Thank You. Have a Nice Day!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");

                }
            }
        } else {
            System.out.println("Login failed. Invalid credentials");
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < num; i++) {
            rooms[i] = "Available";
        }
        assignType();
        assignPrice();
        assignName();
        assignID();
        System.out.println("Welcome to our Hotel Reservation System");
        while (true) {
            System.out.println("1. Customer\n2. Admin\n3. Exit");
            System.out.println("Please Enter your choice");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    customerSection();
                    break;
                case 2:
                    adminSection();
                    break;
                case 3:
                    System.out.println("Thank You for availing our services. Have a nice day!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    public static void selectbytype(String type, String name) {
        Scanner sc = new Scanner(System.in);
        System.out.println("These are the rooms that are available at the moment.");
        for (int i = 0; i < num; i++) {
            if (roomType[i].equals(type) && rooms[i].equals("Available")) {
                System.out.println("Room " + (i + 1) + ": $" + prices[i]);
            }
        }
        System.out.println();
        System.out.println("Enter the room number you want to reserve :");
        int roomno = sc.nextInt();
        if (rooms[roomno - 1].equals("Available")) {
            rooms[roomno - 1] = "Reserved";
            names[roomno - 1] = name;
            resID[roomno - 1] = String.valueOf(ID++);
            System.out.println("Room Number " + roomno + " successfully reserved under the name " + name + " and your Registration ID is " + resID[roomno - 1]);
        } else {
            System.out.println("Sorry, the room is already reserved. Please choose another room.");
        }
    }

    public static void checkinout(String name) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Registration ID: ");
        String resid = sc.nextLine().trim();
        int roomIndex = -1;
        for (int i = 0; i < num; i++) {
            if (resID[i] != null && resID[i].equals(resid)) {
                roomIndex = i;
                break;
            }
        }

        if (roomIndex == -1) {
            System.out.println("Invalid Reservation ID. No reservation found.");
            return;
        }

        LocalDate current = LocalDate.now();
        System.out.println("Enter Check-in Date (YYYY-MM-DD):");
        String checkin = sc.nextLine();
        while (!isValid(checkin) || LocalDate.parse(checkin).isBefore(current)) {
            System.out.println("Please Enter a valid Check-in Date.");
            checkin = sc.nextLine();
        }
        System.out.println("Enter Check-out Date(YYYY-MM-DD):");
        String checkout = sc.nextLine();
        while (!isValid(checkout) || LocalDate.parse(checkout).isBefore(LocalDate.parse(checkin))) {
            System.out.println("Please Enter a valid Check-out Date.");
            checkout = sc.nextLine();
        }

        dateList[roomIndex][0] = checkin;
        dateList[roomIndex][1] = checkout;
        System.out.println("Check-in and Check-out Dates have been successfully set.");
    }

    public static boolean isValid(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static void cancelreserv(String name) {
        for (int i = 0; i < num; i++) {
            if (names[i].equals(name)) {
                rooms[i] = "Available";
                names[i] = "";
                resID[i] = "";
                dateList[i] = null;
            }
        }
        System.out.println("Your reservation has been successfully cancelled.");
    }

    public static void viewreserv(String name) {
        System.out.println("Here are your Reservation Details: \n");
        boolean found = false;
        double totalPrice = 0.0;
        for (int i = 0; i < num; i++) {
            if (names[i].equals(name)) {
                System.out.println("Reservation ID: " + resID[i]);
                System.out.println("Room Number: " + (i + 1));
                System.out.println("Room Type: " + roomType[i]);
                System.out.println("Check-in Date: " + dateList[i][0]);
                System.out.println("Check-out Date: " + dateList[i][1]);
                System.out.println("Price per Room: $" + prices[i]);
                totalPrice += prices[i];
                found = true;
                System.out.println("-------------------------------------------");
            }
        }
        if (!found) {
            System.out.println("No active reservations found for " + name + ".");
        } else {
            System.out.println("Total Price for all your reservations: $" + totalPrice);
        }
    }

    private static void reservedet(String[][] dateList) {
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].equals("Reserved") && dateList.length > i) {
                System.out.println("Room Number: " + (i + 1));
                System.out.println("Reservation ID: " + resID[i]);
                System.out.println("Guest Name: " + names[i]);
                System.out.println("Room Type: " + roomType[i]);
                System.out.println("Check-in Date: " + dateList[i][0]);
                System.out.println("Check-out Date: " + dateList[i][1]);
                System.out.println("Total Price: $" + prices[i]);
            }
            System.out.println();
        }
    }

    private static void updatedet() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter room number whose details you want to update : ");
        int roomno = sc.nextInt();
        System.out.println("Please enter the new room type (Single, Double, Suite):");
        String type = sc.nextLine();
        if (type.equals("Single")) {
            prices[roomno - 1] = 100;
        } else if (type.equals("Double")) {
            prices[roomno - 1] = 150;
        } else if (type.equals("Suite")) {
            prices[roomno - 1] = 250;
        } else {
            while (!type.equals("Single") && !type.equals("Double") && !type.equals("Suite")) {
                System.out.println("Please enter valid Room Type (Single, Double, Suite):");
                type = sc.nextLine();
            }
        }
        rooms[roomno - 1] = type;
        System.out.println();
    }

    private static void dispavailable() {
        for (int i = 0; i < num; i++) {
            if (rooms[i].equals("Available")) {
                System.out.println("Room Number: " + (i + 1));
            }
        }
    }

    private static void maintenance() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please select the room you wish to mark for maintenance.");
        int roomno = sc.nextInt();
        if (rooms[roomno - 1].equals("Reserved")) {
            System.out.println("This room is already reserved and cannot be marked for maintenance.");
        } else if (rooms[roomno - 1].equals("Under Maintenance")) {
            System.out.println("This room is already under maintenance.");
        } else {
            rooms[roomno - 1] = "Under Maintenance";
            System.out.println("The room has been successfully marked for maintenance. It is now unavailable for reservations.");
        }
    }

    private static void showrooms() {
        for (int i = 0; i < num; i++) {
            System.out.println("Room Number " + (i + 1) + " : " + rooms[i]);
        }
    }
}
