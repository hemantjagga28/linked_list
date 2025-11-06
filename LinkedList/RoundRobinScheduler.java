import java.util.Scanner;

// Node class representing a Process
class Process {
    int pid;          // Process ID
    int burstTime;    // Remaining Burst Time
    int priority;
    int originalBurst; // To calculate waiting/turnaround times
    Process next;

    public Process(int pid, int burstTime, int priority) {
        this.pid = pid;
        this.burstTime = burstTime;
        this.priority = priority;
        this.originalBurst = burstTime;
        this.next = null;
    }
}

// Circular Linked List for Round Robin Scheduling
class CircularProcessList {
    private Process head = null;

    // Add process at end
    public void addProcess(int pid, int burstTime, int priority) {
        Process newProcess = new Process(pid, burstTime, priority);

        if (head == null) {
            head = newProcess;
            head.next = head; // circular link
        } else {
            Process temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = newProcess;
            newProcess.next = head;
        }
        System.out.println("Process " + pid + " added successfully.");
    }

    // Remove process by Process ID
    public void removeProcess(int pid) {
        if (head == null) {
            System.out.println("No processes in the queue.");
            return;
        }

        Process temp = head, prev = null;

        // Find the process
        do {
            if (temp.pid == pid) {
                if (temp == head && temp.next == head) { // only one process
                    head = null;
                } else {
                    if (temp == head) {
                        Process last = head;
                        while (last.next != head) last = last.next;
                        head = head.next;
                        last.next = head;
                    } else {
                        prev.next = temp.next;
                    }
                }
                System.out.println("Process " + pid + " completed and removed.");
                return;
            }
            prev = temp;
            temp = temp.next;
        } while (temp != head);

        System.out.println("Process with ID " + pid + " not found.");
    }

    // Check if list is empty
    public boolean isEmpty() {
        return head == null;
    }

    // Display all processes
    public void displayProcesses() {
        if (head == null) {
            System.out.println("No processes in the queue.");
            return;
        }

        Process temp = head;
        System.out.println("\n--- Current Process Queue ---");
        do {
            System.out.println("PID: " + temp.pid + " | Burst Time: " + temp.burstTime + " | Priority: " + temp.priority);
            temp = temp.next;
        } while (temp != head);
    }

    // Simulate Round Robin Scheduling
    public void simulateRoundRobin(int timeQuantum) {
        if (head == null) {
            System.out.println("No processes to schedule.");
            return;
        }

        int totalWaitingTime = 0, totalTurnaroundTime = 0, completedProcesses = 0;
        int currentTime = 0;
        Process temp = head;

        System.out.println("\n--- Starting Round Robin Scheduling ---");

        while (!isEmpty()) {
            if (temp.burstTime > 0) {
                System.out.println("\nExecuting Process " + temp.pid);

                if (temp.burstTime > timeQuantum) {
                    currentTime += timeQuantum;
                    temp.burstTime -= timeQuantum;
                } else {
                    currentTime += temp.burstTime;
                    temp.burstTime = 0;

                    int turnaroundTime = currentTime;
                    int waitingTime = turnaroundTime - temp.originalBurst;
                    totalTurnaroundTime += turnaroundTime;
                    totalWaitingTime += waitingTime;
                    completedProcesses++;

                    int completedPID = temp.pid;
                    Process nextProcess = temp.next;
                    removeProcess(completedPID);
                    temp = nextProcess;

                    displayProcesses();
                    continue; // skip to next process
                }
                displayProcesses();
            }
            temp = temp.next;
        }

        System.out.println("\nAll processes have been executed.");
        System.out.printf("Average Waiting Time: %.2f\n", (double) totalWaitingTime / completedProcesses);
        System.out.printf("Average Turnaround Time: %.2f\n", (double) totalTurnaroundTime / completedProcesses);
    }
}

// Main class
public class RoundRobinScheduler {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CircularProcessList scheduler = new CircularProcessList();

        while (true) {
            System.out.println("\n--- Round Robin CPU Scheduling ---");
            System.out.println("1. Add New Process");
            System.out.println("2. Display All Processes");
            System.out.println("3. Simulate Round Robin Scheduling");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Process ID: ");
                    int pid = sc.nextInt();
                    System.out.print("Enter Burst Time: ");
                    int burst = sc.nextInt();
                    System.out.print("Enter Priority: ");
                    int priority = sc.nextInt();
                    scheduler.addProcess(pid, burst, priority);
                    break;

                case 2:
                    scheduler.displayProcesses();
                    break;

                case 3:
                    System.out.print("Enter Time Quantum: ");
                    int quantum = sc.nextInt();
                    scheduler.simulateRoundRobin(quantum);
                    break;

                case 4:
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

