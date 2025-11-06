import java.util.*;

class TextState {
    String content;
    TextState prev;
    TextState next;

    TextState(String content) {
        this.content = content;
        this.prev = null;
        this.next = null;
    }
}

class TextEditor {
    private TextState head;       // First state
    private TextState current;    // Current active state
    private int size = 0;
    private final int MAX_HISTORY = 10; // Limit to 10 states

    // Add new text state (when user performs an action)
    public void addState(String newText) {
        TextState newState = new TextState(newText);

        // If no history yet
        if (head == null) {
            head = current = newState;
            size = 1;
            return;
        }

        // Remove all "redo" states after current
        current.next = null;

        // Attach new node after current
        newState.prev = current;
        current.next = newState;
        current = newState;
        size++;

        // Maintain only last 10 states
        if (size > MAX_HISTORY) {
            head = head.next; // remove oldest
            head.prev = null;
            size--;
        }
    }

    // Undo operation
    public void undo() {
        if (current != null && current.prev != null) {
            current = current.prev;
            System.out.println("Undo successful. Current state: " + current.content);
        } else {
            System.out.println("No more undos available!");
        }
    }

    // Redo operation
    public void redo() {
        if (current != null && current.next != null) {
            current = current.next;
            System.out.println("Redo successful. Current state: " + current.content);
        } else {
            System.out.println("No more redos available!");
        }
    }

    // Display current state
    public void displayCurrentState() {
        if (current == null) {
            System.out.println("No text available.");
        } else {
            System.out.println("Current text: \"" + current.content + "\"");
        }
    }

    // Display all states (for debugging)
    public void displayAllStates() {
        System.out.println("\n--- All States in History ---");
        TextState temp = head;
        int count = 1;
        while (temp != null) {
            String marker = (temp == current) ? " <== Current" : "";
            System.out.println(count + ". " + temp.content + marker);
            temp = temp.next;
            count++;
        }
        System.out.println("-----------------------------\n");
    }
}

public class TextEditorUndoRedo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TextEditor editor = new TextEditor();

        while (true) {
            System.out.println("\n=== Text Editor Undo/Redo System ===");
            System.out.println("1. Add new text");
            System.out.println("2. Undo");
            System.out.println("3. Redo");
            System.out.println("4. Display current text");
            System.out.println("5. Display all states (debug)");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter new text: ");
                    String text = sc.nextLine();
                    editor.addState(text);
                    System.out.println("Text added successfully.");
                    break;

                case 2:
                    editor.undo();
                    break;

                case 3:
                    editor.redo();
                    break;

                case 4:
                    editor.displayCurrentState();
                    break;

                case 5:
                    editor.displayAllStates();
                    break;

                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

