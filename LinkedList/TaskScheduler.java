import java.util.Scanner;

// Node class for each Task
class Task {
    int taskId;
    String taskName;
    String priority;
    String dueDate;
    Task next;

    Task(int taskId, String taskName, String priority, String dueDate) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.priority = priority;
        this.dueDate = dueDate;
        this.next = null;
    }
}

// Circular Linked List to manage Tasks
class TaskCircularList {
    private Task head = null;
    private Task tail = null;
    private Task current = null; // to track the "current" task for scheduler rotation

    // Add task at the beginning
    public void addAtBeginning(int id, String name, String priority, String dueDate) {
        Task newTask = new Task(id, name, priority, dueDate);
        if (head == null) {
            head = tail = newTask;
            newTask.next = head;
        } else {
            newTask.next = head;
            head = newTask;
            tail.next = head;
        }
        System.out.println("Task added at the beginning.");
    }

    // Add task at the end
    public void addAtEnd(int id, String name, String priority, String dueDate) {
        Task newTask = new Task(id, name, priority, dueDate);
        if (head == null) {
            head = tail = newTask;
            newTask.next = head;
        } else {
            tail.next = newTask;
            tail = newTask;
            tail.next = head;
        }
        System.out.println("Task added at the end.");
    }

    // Add task at specific position
    public void addAtPosition(int pos, int id, String name, String priority, String dueDate) {
        if (pos <= 1 || head == null) {
            addAtBeginning(id, name, priority, dueDate);
            return;
        }

        Task newTask = new Task(id, name, priority, dueDate);
        Task temp = head;
        int count = 1;

        while (count < pos - 1 && temp.next != head) {
            temp = temp.next;
            count++;
        }

        newTask.next = temp.next;
        temp.next = newTask;

        if (temp == tail)
            tail = newTask;

        System.out.println("Task added at position " + pos + ".");
    }

    // Remove task by ID
    public void removeById(int id) {
        if (head == null) {
            System.out.println("No tasks to remove.");
            return;
        }

        Task temp = head;
        Task prev = tail;

        do {
            if (temp.taskId == id) {
                if (temp == head && temp == tail) { // only one node
                    head = tail = null;
                } else if (temp == head) {
                    head = head.next;
                    tail.next = head;
                } else if (temp == tail) {
                    tail = prev;
                    tail.next = head;
                } else {
                    prev.next = temp.next;
                }
                System.out.println("Task with ID " + id + " removed successfully.");
                return;
            }
            prev = temp;
            temp = temp.next;
        } while (temp != head);

        System.out.println("Task ID not found.");
    }

    // View current task and move to next
    public void viewAndMoveNext() {
        if (head == null) {
            System.out.println("No tasks available.");
            return;
        }

        if (current == null) current = head;

        System.out.println("\n--- Current Task ---");
        displayTask(current);
        current = current.next; // move to next task in the circular list
    }

    // Display all tasks starting from head
    public void displayAll() {
        if (head == null) {
            System.out.println("No tasks to display.");
            return;
        }

        System.out.println("\n--- All Tasks ---");
        Task temp = head;
        do {
            displayTask(temp);
            temp = temp.next;
        } while (temp != head);
    }

    // Search task by Priority
    public void searchByPriority(String priority) {
        if (head == null) {
            System.out.println("No tasks to search.");
            return;
        }

        Task temp = head;
        boolean found = false;
        System.out.println("\nTasks with priority \"" + priority + "\":");
        do {
            if (temp.priority.equalsIgnoreCase(priority)) {
                displayTask(temp);
                found = true;
            }
            temp = temp.next;
        } while (temp != head);

        if (!found)
            System.out.println("No tasks found with this priority.");
    }

    // Helper to display a single task
    private void displayTask(Task t) {
        System.out.println("Task ID: " + t.taskId + ", Name: " + t.taskName +
                ", Priority: " + t.priority + ", Due Date: " + t.dueDate);
    }
}

// Main class to test
public class TaskScheduler {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskCircularList scheduler = new TaskCircularList();

        while (true) {
            System.out.println("\n--- Task Scheduler Menu ---");
            System.out.println("1. Add Task at Beginning");
            System.out.println("2. Add Task at End");
            System.out.println("3. Add Task at Specific Position");
            System.out.println("4. Remove Task by ID");
            System.out.println("5. View Current Task and Move to Next");
            System.out.println("6. Display All Tasks");
            System.out.println("7. Search Task by Priority");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                case 2:
                case 3:
                    System.out.print("Enter Task ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Task Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Priority (High/Medium/Low): ");
                    String priority = sc.nextLine();
                    System.out.print("Enter Due Date (DD-MM-YYYY): ");
                    String date = sc.nextLine();

                    if (choice == 1)
                        scheduler.addAtBeginning(id, name, priority, date);
                    else if (choice == 2)
                        scheduler.addAtEnd(id, name, priority, date);
                    else {
                        System.out.print("Enter Position: ");
                        int pos = sc.nextInt();
                        scheduler.addAtPosition(pos, id, name, priority, date);
                    }
                    break;

                case 4:
                    System.out.print("Enter Task ID to Remove: ");
                    scheduler.removeById(sc.nextInt());
                    break;

                case 5:
                    scheduler.viewAndMoveNext();
                    break;

                case 6:
                    scheduler.displayAll();
                    break;

                case 7:
                    System.out.print("Enter Priority to Search: ");
                    String pri = sc.nextLine();
                    scheduler.searchByPriority(pri);
                    break;

                case 8:
                    System.out.println("Exiting program...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

