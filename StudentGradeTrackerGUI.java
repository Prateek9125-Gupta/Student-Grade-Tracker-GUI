import javax.swing.*;   
import java.awt.*;      
import java.util.ArrayList;  

// This class represents 1 Student
class Student {
    String name;
    ArrayList<Integer> marks;

    // Constructor to create student with name
    Student(String name) {
        this.name = name;
        this.marks = new ArrayList<>();
    }

    // Add one mark to the list
    void addMark(int mark) {
        marks.add(mark);
    }

    // Find highest mark from all subjects
    int getHighest() {
        int high = 0;
        for (int m : marks) {
            if (m > high) high = m;
        }
        return high;
    }

    // Find lowest mark
    int getLowest() {
        int low = 100;
        for (int m : marks) {
            if (m < low) low = m;
        }
        return low;
    }

    // Calculate average of all subject marks
    double getAverage() {
        int total = 0;
        for (int m : marks) total += m;
        return (marks.size() == 0) ? 0 : (double) total / marks.size();
    }

    // Give grade based on average
    String getGrade() {
        double avg = getAverage();
        if (avg >= 90) return "A";
        else if (avg >= 70) return "B";
        else if (avg >= 40) return "C";
        else if (avg >= 30) return "D";
        else return "E";
    }

    // Return name
    String getName() {
        return name;
    }
}


// This is the main class that runs everything
public class StudentGradeTrackerGUI {
    public static void main(String[] args) {
        // Make pop-up windows look big and clear
        UIManager.put("OptionPane.minimumSize", new Dimension(500, 200));
        Font font = new Font("Arial", Font.PLAIN, 16);
        for (Object key : UIManager.getLookAndFeelDefaults().keySet()) {
            if (key.toString().toLowerCase().contains("font")) {
                UIManager.put(key, font);
            }
        }

        // Show welcome message
        JOptionPane.showMessageDialog(null, "Welcome to Student Grade Tracker!");

        // Store all students in a list
        ArrayList<Student> allStudents = new ArrayList<>();

        // Ask how many students
        int numStudents = askNumber("How many students?");

        // Repeat for each student
        for (int i = 1; i <= numStudents; i++) {
            // Ask for name
            String name = askText("Enter name of student " + i + ":");
            Student s = new Student(name);

            // Ask how many subjects
            int numSubjects = askNumber("How many subjects for " + name + "?");

            // Ask marks for each subject
            for (int j = 1; j <= numSubjects; j++) {
                int mark = askMark("Enter marks for subject " + j + " (0-100):");
                s.addMark(mark);
            }

            allStudents.add(s);  // Save student data
        }

        // Show final report window
        showReport(allStudents);
    }


    // === Small helper methods below ===

    // Ask for a number (positive integer)
    static int askNumber(String msg) {
        while (true) {
            String input = JOptionPane.showInputDialog(msg);
            if (input == null) System.exit(0); // Cancel button clicked
            try {
                int number = Integer.parseInt(input);
                if (number > 0) return number;
                else JOptionPane.showMessageDialog(null, "Enter number > 0");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
        }
    }

    // Ask for a mark (between 0 and 100)
    static int askMark(String msg) {
        while (true) {
            String input = JOptionPane.showInputDialog(msg);
            if (input == null) System.exit(0);
            try {
                int mark = Integer.parseInt(input);
                if (mark >= 0 && mark <= 100) return mark;
                else JOptionPane.showMessageDialog(null, "Enter mark between 0 and 100");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid mark.");
            }
        }
    }

    // Ask for text (name input)
    static String askText(String msg) {
        while (true) {
            String text = JOptionPane.showInputDialog(msg);
            if (text != null && !text.trim().isEmpty()) return text.trim();
            JOptionPane.showMessageDialog(null, "Input cannot be empty.");
        }
    }

    // Show final report in a scrollable window
    static void showReport(ArrayList<Student> students) {
        StringBuilder report = new StringBuilder();

        report.append(String.format("%-15s%-10s%-10s%-10s%-5s\n", "Name", "Avg", "High", "Low", "Grade"));
        report.append("-------------------------------------------------------------\n");

        double totalAvg = 0;
        int overallHigh = 0, overallLow = 100;

        for (Student s : students) {
            double avg = s.getAverage();
            int high = s.getHighest();
            int low = s.getLowest();
            String grade = s.getGrade();

            report.append(String.format("%-15s%-10.2f%-10d%-10d%-5s\n",
                    s.getName(), avg, high, low, grade));

            totalAvg += avg;
            if (high > overallHigh) overallHigh = high;
            if (low < overallLow) overallLow = low;
        }

        double classAvg = (students.size() == 0) ? 0 : totalAvg / students.size();
        report.append("-------------------------------------------------------------\n");
        report.append(String.format("Class Avg: %.2f, Highest: %d, Lowest: %d",
                classAvg, overallHigh, overallLow));

        // Display using JTextArea inside JFrame
        JTextArea textArea = new JTextArea(report.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        textArea.setEditable(false);

        JScrollPane scroll = new JScrollPane(textArea);
        JFrame frame = new JFrame("Student Report");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scroll);
        frame.setLocationRelativeTo(null); // center of screen
        frame.setVisible(true);
    }
}
