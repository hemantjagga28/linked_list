import java.util.Scanner;

// Node class for each Movie
class Movie {
    String title;
    String director;
    int year;
    double rating;
    Movie prev;
    Movie next;

    Movie(String title, String director, int year, double rating) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.prev = null;
        this.next = null;
    }
}

// Doubly Linked List for managing movies
class MovieLinkedList {
    private Movie head;
    private Movie tail;

    // Add movie at the beginning
    public void addAtBeginning(String title, String director, int year, double rating) {
        Movie newMovie = new Movie(title, director, year, rating);
        if (head == null) {
            head = tail = newMovie;
        } else {
            newMovie.next = head;
            head.prev = newMovie;
            head = newMovie;
        }
    }

    // Add movie at the end
    public void addAtEnd(String title, String director, int year, double rating) {
        Movie newMovie = new Movie(title, director, year, rating);
        if (tail == null) {
            head = tail = newMovie;
        } else {
            tail.next = newMovie;
            newMovie.prev = tail;
            tail = newMovie;
        }
    }

    // Add movie at a specific position
    public void addAtPosition(int pos, String title, String director, int year, double rating) {
        if (pos <= 1 || head == null) {
            addAtBeginning(title, director, year, rating);
            return;
        }

        Movie newMovie = new Movie(title, director, year, rating);
        Movie temp = head;

        for (int i = 1; i < pos - 1 && temp.next != null; i++) {
            temp = temp.next;
        }

        if (temp.next == null) {
            addAtEnd(title, director, year, rating);
        } else {
            newMovie.next = temp.next;
            newMovie.prev = temp;
            temp.next.prev = newMovie;
            temp.next = newMovie;
        }
    }

    // Remove movie by title
    public void removeByTitle(String title) {
        if (head == null) {
            System.out.println("No movie records available.");
            return;
        }

        Movie temp = head;

        while (temp != null && !temp.title.equalsIgnoreCase(title)) {
            temp = temp.next;
        }

        if (temp == null) {
            System.out.println("Movie not found.");
            return;
        }

        if (temp == head) {
            head = head.next;
            if (head != null) head.prev = null;
        } else if (temp == tail) {
            tail = tail.prev;
            if (tail != null) tail.next = null;
        } else {
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
        }

        System.out.println("Movie \"" + title + "\" removed successfully.");
    }

    // Search movies by director
    public void searchByDirector(String director) {
        if (head == null) {
            System.out.println("No movies to search.");
            return;
        }

        Movie temp = head;
        boolean found = false;
        System.out.println("\nMovies directed by " + director + ":");
        while (temp != null) {
            if (temp.director.equalsIgnoreCase(director)) {
                displayMovie(temp);
                found = true;
            }
            temp = temp.next;
        }
        if (!found) System.out.println("No movies found for this director.");
    }

    // Search movies by rating
    public void searchByRating(double rating) {
        if (head == null) {
            System.out.println("No movies to search.");
            return;
        }

        Movie temp = head;
        boolean found = false;
        System.out.println("\nMovies with rating " + rating + ":");
        while (temp != null) {
            if (temp.rating == rating) {
                displayMovie(temp);
                found = true;
            }
            temp = temp.next;
        }
        if (!found) System.out.println("No movies found with this rating.");
    }

    // Update rating by title
    public void updateRating(String title, double newRating) {
        Movie temp = head;
        while (temp != null) {
            if (temp.title.equalsIgnoreCase(title)) {
                temp.rating = newRating;
                System.out.println("Rating updated successfully for \"" + title + "\".");
                return;
            }
            temp = temp.next;
        }
        System.out.println("Movie not found.");
    }

    // Display all movies forward
    public void displayForward() {
        if (head == null) {
            System.out.println("No movies to display.");
            return;
        }

        System.out.println("\n--- Movie List (Forward) ---");
        Movie temp = head;
        while (temp != null) {
            displayMovie(temp);
            temp = temp.next;
        }
    }

    // Display all movies in reverse order
    public void displayReverse() {
        if (tail == null) {
            System.out.println("No movies to display.");
            return;
        }

        System.out.println("\n--- Movie List (Reverse) ---");
        Movie temp = tail;
        while (temp != null) {
            displayMovie(temp);
            temp = temp.prev;
        }
    }

    // Helper to print a movie
    private void displayMovie(Movie m) {
        System.out.println("Title: " + m.title + ", Director: " + m.director +
                ", Year: " + m.year + ", Rating: " + m.rating);
    }
}

// Main class to test
public class MovieManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MovieLinkedList list = new MovieLinkedList();

        while (true) {
            System.out.println("\n--- Movie Management System ---");
            System.out.println("1. Add Movie at Beginning");
            System.out.println("2. Add Movie at End");
            System.out.println("3. Add Movie at Specific Position");
            System.out.println("4. Remove Movie by Title");
            System.out.println("5. Search Movie by Director");
            System.out.println("6. Search Movie by Rating");
            System.out.println("7. Update Movie Rating");
            System.out.println("8. Display All Movies (Forward)");
            System.out.println("9. Display All Movies (Reverse)");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                case 2:
                case 3:
                    System.out.print("Enter Movie Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Director: ");
                    String director = sc.nextLine();
                    System.out.print("Enter Year of Release: ");
                    int year = sc.nextInt();
                    System.out.print("Enter Rating: ");
                    double rating = sc.nextDouble();

                    if (choice == 1)
                        list.addAtBeginning(title, director, year, rating);
                    else if (choice == 2)
                        list.addAtEnd(title, director, year, rating);
                    else {
                        System.out.print("Enter Position: ");
                        int pos = sc.nextInt();
                        list.addAtPosition(pos, title, director, year, rating);
                    }
                    break;

                case 4:
                    System.out.print("Enter Movie Title to Remove: ");
                    String removeTitle = sc.nextLine();
                    list.removeByTitle(removeTitle);
                    break;

                case 5:
                    System.out.print("Enter Director Name to Search: ");
                    String dir = sc.nextLine();
                    list.searchByDirector(dir);
                    break;

                case 6:
                    System.out.print("Enter Rating to Search: ");
                    double rate = sc.nextDouble();
                    list.searchByRating(rate);
                    break;

                case 7:
                    System.out.print("Enter Movie Title to Update Rating: ");
                    String upTitle = sc.nextLine();
                    System.out.print("Enter New Rating: ");
                    double newRating = sc.nextDouble();
                    list.updateRating(upTitle, newRating);
                    break;

                case 8:
                    list.displayForward();
                    break;

                case 9:
                    list.displayReverse();
                    break;

                case 10:
                    System.out.println("Exiting program...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
