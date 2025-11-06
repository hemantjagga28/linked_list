import java.util.Scanner;

// Node class to represent a Book
class Book {
    String title;
    String author;
    String genre;
    int bookID;
    boolean isAvailable;
    Book prev, next;

    public Book(String title, String author, String genre, int bookID, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.bookID = bookID;
        this.isAvailable = isAvailable;
        this.prev = null;
        this.next = null;
    }
}

// Doubly Linked List class
class Library {
    private Book head, tail;

    // Add book at beginning
    public void addAtBeginning(String title, String author, String genre, int bookID, boolean isAvailable) {
        Book newBook = new Book(title, author, genre, bookID, isAvailable);
        if (head == null) {
            head = tail = newBook;
        } else {
            newBook.next = head;
            head.prev = newBook;
            head = newBook;
        }
        System.out.println("Book added at the beginning.");
    }

    // Add book at end
    public void addAtEnd(String title, String author, String genre, int bookID, boolean isAvailable) {
        Book newBook = new Book(title, author, genre, bookID, isAvailable);
        if (head == null) {
            head = tail = newBook;
        } else {
            tail.next = newBook;
            newBook.prev = tail;
            tail = newBook;
        }
        System.out.println("Book added at the end.");
    }

    // Add book at specific position
    public void addAtPosition(int position, String title, String author, String genre, int bookID, boolean isAvailable) {
        if (position <= 1 || head == null) {
            addAtBeginning(title, author, genre, bookID, isAvailable);
            return;
        }

        Book newBook = new Book(title, author, genre, bookID, isAvailable);
        Book temp = head;

        for (int i = 1; temp.next != null && i < position - 1; i++) {
            temp = temp.next;
        }

        newBook.next = temp.next;
        if (temp.next != null) temp.next.prev = newBook;
        else tail = newBook;

        temp.next = newBook;
        newBook.prev = temp;

        System.out.println("Book added at position " + position + ".");
    }

    // Remove a book by Book ID
    public void removeBookByID(int bookID) {
        if (head == null) {
            System.out.println("Library is empty.");
            return;
        }

        Book temp = head;
        while (temp != null && temp.bookID != bookID) {
            temp = temp.next;
        }

        if (temp == null) {
            System.out.println("Book not found.");
            return;
        }

        if (temp == head) head = temp.next;
        if (temp == tail) tail = temp.prev;
        if (temp.prev != null) temp.prev.next = temp.next;
        if (temp.next != null) temp.next.prev = temp.prev;

        System.out.println("Book with ID " + bookID + " removed successfully.");
    }

    // Search for a book by Title or Author
    public void searchBook(String keyword) {
        if (head == null) {
            System.out.println("Library is empty.");
            return;
        }

        Book temp = head;
        boolean found = false;
        while (temp != null) {
            if (temp.title.equalsIgnoreCase(keyword) || temp.author.equalsIgnoreCase(keyword)) {
                System.out.println("\nBook Found:");
                System.out.println("ID: " + temp.bookID + ", Title: " + temp.title + ", Author: " + temp.author +
                        ", Genre: " + temp.genre + ", Available: " + (temp.isAvailable ? "Yes" : "No"));
                found = true;
            }
            temp = temp.next;
        }

        if (!found) System.out.println("No book found with Title or Author: " + keyword);
    }

    // Update Availability Status
    public void updateAvailability(int bookID, boolean status) {
        Book temp = head;
        while (temp != null) {
            if (temp.bookID == bookID) {
                temp.isAvailable = status;
                System.out.println("Availability updated for Book ID: " + bookID);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Book not found.");
    }

    // Display all books in forward order
    public void displayForward() {
        if (head == null) {
            System.out.println("Library is empty.");
            return;
        }

        System.out.println("\n--- Library Books (Forward Order) ---");
        Book temp = head;
        while (temp != null) {
            System.out.println("ID: " + temp.bookID + ", Title: " + temp.title + ", Author: " + temp.author +
                    ", Genre: " + temp.genre + ", Available: " + (temp.isAvailable ? "Yes" : "No"));
            temp = temp.next;
        }
    }

    // Display all books in reverse order
    public void displayReverse() {
        if (tail == null) {
            System.out.println("Library is empty.");
            return;
        }

        System.out.println("\n--- Library Books (Reverse Order) ---");
        Book temp = tail;
        while (temp != null) {
            System.out.println("ID: " + temp.bookID + ", Title: " + temp.title + ", Author: " + temp.author +
                    ", Genre: " + temp.genre + ", Available: " + (temp.isAvailable ? "Yes" : "No"));
            temp = temp.prev;
        }
    }

    // Count total number of books
    public void countBooks() {
        int count = 0;
        Book temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        System.out.println("Total number of books in library: " + count);
    }
}

// Main class to run the system
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Add Book at Beginning");
            System.out.println("2. Add Book at End");
            System.out.println("3. Add Book at Specific Position");
            System.out.println("4. Remove Book by ID");
            System.out.println("5. Search Book by Title/Author");
            System.out.println("6. Update Availability");
            System.out.println("7. Display Books (Forward)");
            System.out.println("8. Display Books (Reverse)");
            System.out.println("9. Count Total Books");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                case 2:
                case 3:
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter Genre: ");
                    String genre = sc.nextLine();
                    System.out.print("Is Available (true/false): ");
                    boolean status = sc.nextBoolean();

                    if (choice == 1)
                        library.addAtBeginning(title, author, genre, id, status);
                    else if (choice == 2)
                        library.addAtEnd(title, author, genre, id, status);
                    else {
                        System.out.print("Enter Position: ");
                        int pos = sc.nextInt();
                        library.addAtPosition(pos, title, author, genre, id, status);
                    }
                    break;

                case 4:
                    System.out.print("Enter Book ID to Remove: ");
                    library.removeBookByID(sc.nextInt());
                    break;

                case 5:
                    System.out.print("Enter Title or Author to Search: ");
                    String keyword = sc.nextLine();
                    library.searchBook(keyword);
                    break;

                case 6:
                    System.out.print("Enter Book ID: ");
                    int updateID = sc.nextInt();
                    System.out.print("Enter New Availability (true/false): ");
                    boolean newStatus = sc.nextBoolean();
                    library.updateAvailability(updateID, newStatus);
                    break;

                case 7:
                    library.displayForward();
                    break;

                case 8:
                    library.displayReverse();
                    break;

                case 9:
                    library.countBooks();
                    break;

                case 10:
                    System.out.println("Exiting... Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

