import java.util.Scanner;

// Node class for each Student
class Student {
    int rollNo;
    String name;
    int age;
    String grade;
    Student next;

    Student(int rollNo, String name, int age, String grade) {
        this.rollNo = rollNo;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.next = null;
    }
}

// Linked List to manage Student records
class StudentLinkedList {
    private Student head;

    // Add student at beginning
    public void addAtBeginning(int rollNo, String name, int age, String grade) {
        Student newStudent = new Student(rollNo, name, age, grade);
        newStudent.next = head;
        head = newStudent;
    }

    // Add student at end
    public void addAtEnd(int rollNo, String name, int age, String grade) {
        Student newStudent = new Student(rollNo, name, age, grade);
        if (head == null) {
            head = newStudent;
            return;
        }
        Student temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newStudent;
    }

    // Add student at a specific position
    public void addAtPosition(int pos, int rollNo, String name, int age, String grade) {
        if (pos <= 1) {
            addAtBeginning(rollNo, name, age, grade);
            return;
        }

        Student newStudent = new Student(rollNo, name, age, grade);
        Student temp = head;
        for (int i = 1; temp != null && i < pos - 1; i++) {
            temp = temp.next;
        }

        if (temp == null) {
            System.out.println("Position out of range. Adding at the end.");
            addAtEnd(rollNo, name, age, grade);
        } else {
            newStudent.next = temp.next;
            temp.next = newStudent;
        }
    }

    // Delete a student by roll number
    public void deleteByRollNo(int rollNo) {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        if (head.rollNo == rollNo) {
            head = head.next;
            System.out.println("Student with Roll No " + rollNo + " deleted.");
            return;
        }

        Student temp = head;
        while (temp.next != null && temp.next.rollNo != rollNo) {
            temp = temp.next;
        }

        if (temp.next == null) {
            System.out.println("Student not found.");
        } else {
            temp.next = temp.next.next;
            System.out.println("Student with Roll No " + rollNo + " deleted.");
        }
    }

    // Search student by roll number
    public void searchByRollNo(int rollNo) {
        Student temp = head;
        while (temp != null) {
            if (temp.rollNo == rollNo) {
                System.out.println("Student Found:");
                System.out.println("Roll No: " + temp.rollNo);
                System.out.println("Name: " + temp.name);
                System.out.println("Age: " + temp.age);
                System.out.println("Grade: " + temp.grade);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Student not found.");
    }

    // Update student's grade
    public void updateGrade(int rollNo, String newGrade) {
        Student temp = head;
        while (temp != null) {
            if (temp.rollNo == rollNo) {
                temp.grade = newGrade;
                System.out.println("Grade updated successfully for Roll No " + rollNo);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Student not found.");
    }

    // Display all records
    public void display() {
        if (head == null) {
            System.out.println("No student records to display.");
            return;
        }

        Student temp = head;
        System.out.println("\n--- Student Records ---");
        while (temp != null) {
            System.out.println("Roll No: " + temp.rollNo + ", Name: " + temp.name +
                               ", Age: " + temp.age + ", Grade: " + temp.grade);
            temp = temp.next;
        }
    }
}

// Main class to test
public class StudentRecordManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentLinkedList list = new StudentLinkedList();

        while (true) {
            System.out.println("\n--- Student Record Management ---");
            System.out.println("1. Add Student at Beginning");
            System.out.println("2. Add Student at End");
            System.out.println("3. Add Student at Specific Position");
            System.out.println("4. Delete Student by Roll No");
            System.out.println("5. Search Student by Roll No");
            System.out.println("6. Update Student Grade");
            System.out.println("7. Display All Records");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                case 2:
                case 3:
                    System.out.print("Enter Roll No: ");
                    int roll = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Grade: ");
                    String grade = sc.nextLine();

                    if (choice == 1) list.addAtBeginning(roll, name, age, grade);
                    else if (choice == 2) list.addAtEnd(roll, name, age, grade);
                    else {
                        System.out.print("Enter Position: ");
                        int pos = sc.nextInt();
                        list.addAtPosition(pos, roll, name, age, grade);
                    }
                    break;

                case 4:
                    System.out.print("Enter Roll No to Delete: ");
                    list.deleteByRollNo(sc.nextInt());
                    break;

                case 5:
                    System.out.print("Enter Roll No to Search: ");
                    list.searchByRollNo(sc.nextInt());
                    break;

                case 6:
                    System.out.print("Enter Roll No to Update Grade: ");
                    int rollUpdate = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Grade: ");
                    String newGrade = sc.nextLine();
                    list.updateGrade(rollUpdate, newGrade);
                    break;

                case 7:
                    list.display();
                    break;

                case 8:
                    System.out.println("Exiting program...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
