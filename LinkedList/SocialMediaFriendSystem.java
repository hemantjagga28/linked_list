import java.util.*;

// Node class representing each User
class User {
    int userID;
    String name;
    int age;
    List<Integer> friendIDs; // Store friend User IDs
    User next;

    public User(int userID, String name, int age) {
        this.userID = userID;
        this.name = name;
        this.age = age;
        this.friendIDs = new ArrayList<>();
        this.next = null;
    }
}

// Singly Linked List to manage Users
class SocialMedia {
    private User head;

    // Add a new user to the list
    public void addUser(int userID, String name, int age) {
        User newUser = new User(userID, name, age);
        if (head == null) {
            head = newUser;
        } else {
            User temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newUser;
        }
        System.out.println("User added successfully: " + name);
    }

    // Find user by User ID
    private User findUserByID(int userID) {
        User temp = head;
        while (temp != null) {
            if (temp.userID == userID) return temp;
            temp = temp.next;
        }
        return null;
    }

    // Add a friend connection (both sides)
    public void addFriendConnection(int userID1, int userID2) {
        User user1 = findUserByID(userID1);
        User user2 = findUserByID(userID2);

        if (user1 == null || user2 == null) {
            System.out.println("One or both users not found.");
            return;
        }

        if (user1.friendIDs.contains(userID2)) {
            System.out.println("They are already friends!");
            return;
        }

        user1.friendIDs.add(userID2);
        user2.friendIDs.add(userID1);
        System.out.println(user1.name + " and " + user2.name + " are now friends!");
    }

    // Remove a friend connection (both sides)
    public void removeFriendConnection(int userID1, int userID2) {
        User user1 = findUserByID(userID1);
        User user2 = findUserByID(userID2);

        if (user1 == null || user2 == null) {
            System.out.println("One or both users not found.");
            return;
        }

        if (!user1.friendIDs.contains(userID2)) {
            System.out.println("They are not friends.");
            return;
        }

        user1.friendIDs.remove(Integer.valueOf(userID2));
        user2.friendIDs.remove(Integer.valueOf(userID1));
        System.out.println("Friend connection removed between " + user1.name + " and " + user2.name);
    }

    // Display all friends of a specific user
    public void displayFriends(int userID) {
        User user = findUserByID(userID);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("\nFriends of " + user.name + ":");
        if (user.friendIDs.isEmpty()) {
            System.out.println("No friends yet.");
            return;
        }

        for (int id : user.friendIDs) {
            User friend = findUserByID(id);
            if (friend != null)
                System.out.println("- " + friend.name + " (UserID: " + friend.userID + ")");
        }
    }

    // Find mutual friends between two users
    public void findMutualFriends(int userID1, int userID2) {
        User user1 = findUserByID(userID1);
        User user2 = findUserByID(userID2);

        if (user1 == null || user2 == null) {
            System.out.println("One or both users not found.");
            return;
        }

        Set<Integer> mutual = new HashSet<>(user1.friendIDs);
        mutual.retainAll(user2.friendIDs);

        System.out.println("\nMutual Friends of " + user1.name + " and " + user2.name + ":");
        if (mutual.isEmpty()) {
            System.out.println("No mutual friends.");
            return;
        }

        for (int id : mutual) {
            User friend = findUserByID(id);
            if (friend != null)
                System.out.println("- " + friend.name + " (UserID: " + friend.userID + ")");
        }
    }

    // Search for a user by Name or User ID
    public void searchUser(String keyword) {
        User temp = head;
        boolean found = false;

        System.out.println("\nSearch Results for \"" + keyword + "\":");
        while (temp != null) {
            if (temp.name.equalsIgnoreCase(keyword) || String.valueOf(temp.userID).equals(keyword)) {
                System.out.println("User Found → ID: " + temp.userID + ", Name: " + temp.name + ", Age: " + temp.age);
                found = true;
            }
            temp = temp.next;
        }

        if (!found) System.out.println("No user found with the given name or ID.");
    }

    // Count number of friends for each user
    public void countAllFriends() {
        if (head == null) {
            System.out.println("No users in the system.");
            return;
        }

        System.out.println("\n--- Friend Count for Each User ---");
        User temp = head;
        while (temp != null) {
            System.out.println(temp.name + " (" + temp.userID + ") has " + temp.friendIDs.size() + " friends.");
            temp = temp.next;
        }
    }

    // Display all users
    public void displayAllUsers() {
        if (head == null) {
            System.out.println("No users in the network.");
            return;
        }

        System.out.println("\n--- All Registered Users ---");
        User temp = head;
        while (temp != null) {
            System.out.println("UserID: " + temp.userID + ", Name: " + temp.name + ", Age: " + temp.age);
            temp = temp.next;
        }
    }
}

// Main Class
public class SocialMediaFriendSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SocialMedia network = new SocialMedia();

        while (true) {
            System.out.println("\n=== Social Media Friend Connection System ===");
            System.out.println("1. Add New User");
            System.out.println("2. Add Friend Connection");
            System.out.println("3. Remove Friend Connection");
            System.out.println("4. Display Friends of a User");
            System.out.println("5. Find Mutual Friends");
            System.out.println("6. Search User by Name or ID");
            System.out.println("7. Count Friends for All Users");
            System.out.println("8. Display All Users");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter User ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    network.addUser(id, name, age);
                    break;

                case 2:
                    System.out.print("Enter First User ID: ");
                    int id1 = sc.nextInt();
                    System.out.print("Enter Second User ID: ");
                    int id2 = sc.nextInt();
                    network.addFriendConnection(id1, id2);
                    break;

                case 3:
                    System.out.print("Enter First User ID: ");
                    int rem1 = sc.nextInt();
                    System.out.print("Enter Second User ID: ");
                    int rem2 = sc.nextInt();
                    network.removeFriendConnection(rem1, rem2);
                    break;

                case 4:
                    System.out.print("Enter User ID: ");
                    int uid = sc.nextInt();
                    network.displayFriends(uid);
                    break;

                case 5:
                    System.out.print("Enter First User ID: ");
                    int m1 = sc.nextInt();
                    System.out.print("Enter Second User ID: ");
                    int m2 = sc.nextInt();
                    network.findMutualFriends(m1, m2);
                    break;

                case 6:
                    System.out.print("Enter Name or User ID: ");
                    String keyword = sc.nextLine();
                    network.searchUser(keyword);
                    break;

                case 7:
                    network.countAllFriends();
                    break;

                case 8:
                    network.displayAllUsers();
                    break;

                case 9:
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

