import java.util.Scanner;

// Node class for each Item
class Item {
    String itemName;
    int itemId;
    int quantity;
    double price;
    Item next;

    Item(String itemName, int itemId, int quantity, double price) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.next = null;
    }
}

// Singly Linked List for managing Inventory
class InventoryLinkedList {
    private Item head = null;

    // Add item at the beginning
    public void addAtBeginning(String name, int id, int quantity, double price) {
        Item newItem = new Item(name, id, quantity, price);
        newItem.next = head;
        head = newItem;
        System.out.println("Item added at the beginning.");
    }

    // Add item at the end
    public void addAtEnd(String name, int id, int quantity, double price) {
        Item newItem = new Item(name, id, quantity, price);
        if (head == null) {
            head = newItem;
            System.out.println("Item added at the end.");
            return;
        }
        Item temp = head;
        while (temp.next != null)
            temp = temp.next;
        temp.next = newItem;
        System.out.println("Item added at the end.");
    }

    // Add item at a specific position
    public void addAtPosition(int pos, String name, int id, int quantity, double price) {
        if (pos <= 1 || head == null) {
            addAtBeginning(name, id, quantity, price);
            return;
        }

        Item newItem = new Item(name, id, quantity, price);
        Item temp = head;
        int count = 1;

        while (count < pos - 1 && temp.next != null) {
            temp = temp.next;
            count++;
        }

        newItem.next = temp.next;
        temp.next = newItem;
        System.out.println("Item added at position " + pos + ".");
    }

    // Remove item by ID
    public void removeById(int id) {
        if (head == null) {
            System.out.println("Inventory is empty.");
            return;
        }

        if (head.itemId == id) {
            head = head.next;
            System.out.println("Item with ID " + id + " removed successfully.");
            return;
        }

        Item temp = head;
        while (temp.next != null && temp.next.itemId != id)
            temp = temp.next;

        if (temp.next == null)
            System.out.println("Item ID not found.");
        else {
            temp.next = temp.next.next;
            System.out.println("Item with ID " + id + " removed successfully.");
        }
    }

    // Update quantity by ID
    public void updateQuantity(int id, int newQty) {
        Item temp = head;
        while (temp != null) {
            if (temp.itemId == id) {
                temp.quantity = newQty;
                System.out.println("Quantity updated for Item ID " + id);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Item ID not found.");
    }

    // Search by ID
    public void searchById(int id) {
        Item temp = head;
        while (temp != null) {
            if (temp.itemId == id) {
                displayItem(temp);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Item ID not found.");
    }

    // Search by Name
    public void searchByName(String name) {
        Item temp = head;
        boolean found = false;
        while (temp != null) {
            if (temp.itemName.equalsIgnoreCase(name)) {
                displayItem(temp);
                found = true;
            }
            temp = temp.next;
        }
        if (!found)
            System.out.println("No item found with that name.");
    }

    // Calculate total inventory value
    public void calculateTotalValue() {
        double total = 0;
        Item temp = head;
        while (temp != null) {
            total += temp.price * temp.quantity;
            temp = temp.next;
        }
        System.out.println("Total Inventory Value: ₹" + total);
    }

    // Sort inventory by Item Name
    public void sortByName(boolean ascending) {
        if (head == null || head.next == null) return;

        for (Item i = head; i != null; i = i.next) {
            for (Item j = i.next; j != null; j = j.next) {
                if ((ascending && i.itemName.compareToIgnoreCase(j.itemName) > 0) ||
                    (!ascending && i.itemName.compareToIgnoreCase(j.itemName) < 0)) {
                    swap(i, j);
                }
            }
        }
        System.out.println("Inventory sorted by Name (" + (ascending ? "Ascending" : "Descending") + ").");
    }

    // Sort inventory by Price
    public void sortByPrice(boolean ascending) {
        if (head == null || head.next == null) return;

        for (Item i = head; i != null; i = i.next) {
            for (Item j = i.next; j != null; j = j.next) {
                if ((ascending && i.price > j.price) ||
                    (!ascending && i.price < j.price)) {
                    swap(i, j);
                }
            }
        }
        System.out.println("Inventory sorted by Price (" + (ascending ? "Ascending" : "Descending") + ").");
    }

    // Helper to swap node data
    private void swap(Item a, Item b) {
        String tempName = a.itemName;
        a.itemName = b.itemName;
        b.itemName = tempName;

        int tempId = a.itemId;
        a.itemId = b.itemId;
        b.itemId = tempId;

        int tempQty = a.quantity;
        a.quantity = b.quantity;
        b.quantity = tempQty;

        double tempPrice = a.price;
        a.price = b.price;
        b.price = tempPrice;
    }

    // Display all items
    public void displayAll() {
        if (head == null) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("\n--- Inventory Items ---");
        Item temp = head;
        while (temp != null) {
            displayItem(temp);
            temp = temp.next;
        }
    }

    // Helper to display one item
    private void displayItem(Item i) {
        System.out.println("Item ID: " + i.itemId + ", Name: " + i.itemName +
                ", Quantity: " + i.quantity + ", Price: ₹" + i.price);
    }
}

// Main class
public class InventoryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InventoryLinkedList inventory = new InventoryLinkedList();

        while (true) {
            System.out.println("\n--- Inventory Management Menu ---");
            System.out.println("1. Add Item at Beginning");
            System.out.println("2. Add Item at End");
            System.out.println("3. Add Item at Specific Position");
            System.out.println("4. Remove Item by ID");
            System.out.println("5. Update Quantity by ID");
            System.out.println("6. Search by Item ID");
            System.out.println("7. Search by Item Name");
            System.out.println("8. Display All Items");
            System.out.println("9. Calculate Total Inventory Value");
            System.out.println("10. Sort by Item Name");
            System.out.println("11. Sort by Price");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                case 2:
                case 3:
                    System.out.print("Enter Item Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Item ID: ");
                    int id = sc.nextInt();
                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();
                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();

                    if (choice == 1)
                        inventory.addAtBeginning(name, id, qty, price);
                    else if (choice == 2)
                        inventory.addAtEnd(name, id, qty, price);
                    else {
                        System.out.print("Enter Position: ");
                        int pos = sc.nextInt();
                        inventory.addAtPosition(pos, name, id, qty, price);
                    }
                    break;

                case 4:
                    System.out.print("Enter Item ID to Remove: ");
                    inventory.removeById(sc.nextInt());
                    break;

                case 5:
                    System.out.print("Enter Item ID: ");
                    int idUpdate = sc.nextInt();
                    System.out.print("Enter New Quantity: ");
                    int newQty = sc.nextInt();
                    inventory.updateQuantity(idUpdate, newQty);
                    break;

                case 6:
                    System.out.print("Enter Item ID to Search: ");
                    inventory.searchById(sc.nextInt());
                    break;

                case 7:
                    System.out.print("Enter Item Name to Search: ");
                    String searchName = sc.nextLine();
                    inventory.searchByName(searchName);
                    break;

                case 8:
                    inventory.displayAll();
                    break;

                case 9:
                    inventory.calculateTotalValue();
                    break;

                case 10:
                    System.out.print("Sort Ascending? (true/false): ");
                    inventory.sortByName(sc.nextBoolean());
                    break;

                case 11:
                    System.out.print("Sort Ascending? (true/false): ");
                    inventory.sortByPrice(sc.nextBoolean());
                    break;

                case 12:
                    System.out.println("Exiting program...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

