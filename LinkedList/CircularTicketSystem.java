import java.util.*;

class Ticket {
    int ticketID;
    String customerName;
    String movieName;
    String seatNumber;
    String bookingTime;
    Ticket next;

    public Ticket(int ticketID, String customerName, String movieName, String seatNumber, String bookingTime) {
        this.ticketID = ticketID;
        this.customerName = customerName;
        this.movieName = movieName;
        this.seatNumber = seatNumber;
        this.bookingTime = bookingTime;
        this.next = null;
    }
}

class TicketReservationSystem {
    private Ticket head = null;

    // Add new ticket at the end
    public void addTicket(int ticketID, String customerName, String movieName, String seatNumber, String bookingTime) {
        Ticket newTicket = new Ticket(ticketID, customerName, movieName, seatNumber, bookingTime);

        if (head == null) {
            head = newTicket;
            head.next = head; // circular link
            System.out.println("Ticket booked successfully!");
            return;
        }

        Ticket temp = head;
        while (temp.next != head) {
            temp = temp.next;
        }
        temp.next = newTicket;
        newTicket.next = head;
        System.out.println("Ticket booked successfully!");
    }

    // Remove ticket by Ticket ID
    public void removeTicket(int ticketID) {
        if (head == null) {
            System.out.println("No tickets to remove.");
            return;
        }

        Ticket temp = head, prev = null;

        // If only one node
        if (head.next == head && head.ticketID == ticketID) {
            head = null;
            System.out.println("Ticket " + ticketID + " removed successfully!");
            return;
        }

        do {
            if (temp.ticketID == ticketID) {
                if (temp == head) {
                    // Find last node
                    Ticket last = head;
                    while (last.next != head)
                        last = last.next;
                    head = head.next;
                    last.next = head;
                } else {
                    prev.next = temp.next;
                }
                System.out.println("Ticket " + ticketID + " removed successfully!");
                return;
            }
            prev = temp;
            temp = temp.next;
        } while (temp != head);

        System.out.println("Ticket ID not found.");
    }

    // Display all tickets
    public void displayTickets() {
        if (head == null) {
            System.out.println("No tickets booked yet.");
            return;
        }

        Ticket temp = head;
        System.out.println("\n--- Current Booked Tickets ---");
        do {
            System.out.println("Ticket ID: " + temp.ticketID +
                    " | Customer: " + temp.customerName +
                    " | Movie: " + temp.movieName +
                    " | Seat: " + temp.seatNumber +
                    " | Time: " + temp.bookingTime);
            temp = temp.next;
        } while (temp != head);
        System.out.println("------------------------------\n");
    }

    // Search ticket by Customer Name or Movie Name
    public void searchTicket(String keyword) {
        if (head == null) {
            System.out.println("No tickets booked yet.");
            return;
        }

        Ticket temp = head;
        boolean found = false;
        do {
            if (temp.customerName.equalsIgnoreCase(keyword) || temp.movieName.equalsIgnoreCase(keyword)) {
                if (!found) {
                    System.out.println("\n--- Search Results ---");
                }
                found = true;
                System.out.println("Ticket ID: " + temp.ticketID +
                        " | Customer: " + temp.customerName +
                        " | Movie: " + temp.movieName +
                        " | Seat: " + temp.seatNumber +
                        " | Time: " + temp.bookingTime);
            }
            temp = temp.next;
        } while (temp != head);

        if (!found) {
            System.out.println("No matching ticket found for: " + keyword);
        } else {
            System.out.println("-----------------------\n");
        }
    }

    // Count total booked tickets
    public void countTickets() {
        if (head == null) {
            System.out.println("Total Booked Tickets: 0");
            return;
        }

        Ticket temp = head;
        int count = 0;
        do {
            count++;
            temp = temp.next;
        } while (temp != head);

        System.out.println("Total Booked Tickets: " + count);
    }
}

public class CircularTicketSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TicketReservationSystem system = new TicketReservationSystem();

        while (true) {
            System.out.println("\n=== Online Ticket Reservation System ===");
            System.out.println("1. Add New Ticket");
            System.out.println("2. Remove Ticket by ID");
            System.out.println("3. Display All Tickets");
            System.out.println("4. Search Ticket (by Customer/Movie)");
            System.out.println("5. Count Total Booked Tickets");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Ticket ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Customer Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Movie Name: ");
                    String movie = sc.nextLine();
                    System.out.print("Enter Seat Number: ");
                    String seat = sc.nextLine();
                    System.out.print("Enter Booking Time: ");
                    String time = sc.nextLine();
                    system.addTicket(id, name, movie, seat, time);
                    break;

                case 2:
                    System.out.print("Enter Ticket ID to remove: ");
                    int remID = sc.nextInt();
                    system.removeTicket(remID);
                    break;

                case 3:
                    system.displayTickets();
                    break;

                case 4:
                    System.out.print("Enter Customer Name or Movie Name: ");
                    String keyword = sc.nextLine();
                    system.searchTicket(keyword);
                    break;

                case 5:
                    system.countTickets();
                    break;

                case 6:
                    System.out.println("Exiting the system... Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

