import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class GradeCalculatorSwing extends JFrame {
    private JTextField numSubjectsField;
    private JButton calculateButton;
    private JLabel resultLabel;
    private static final int A_GRADE = 90;
    private static final int B_GRADE = 80;
    private static final int C_GRADE = 70;
    private static final int D_GRADE = 60;
    public GradeCalculatorSwing() {
        super("Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(3, 1));
        numSubjectsField = new JTextField(10);
        calculateButton = new JButton("Calculate Grade");
        resultLabel = new JLabel();
        add(new JLabel("Enter the number of subjects:"));
        add(numSubjectsField);
        add(calculateButton);
        add(resultLabel);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateGrade();
            }
        });
    }
    private void calculateGrade() {
        int numSubjects;
        try {
            numSubjects = Integer.parseInt(numSubjectsField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of subjects.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int totalMarks = 0;

        for (int i = 1; i <= numSubjects; i++) {
            String input = JOptionPane.showInputDialog(this, "Enter marks obtained in Subject " + i + ":");
            try {
                int marks = Integer.parseInt(input);
                totalMarks += marks;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid marks.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        double averagePercentage = (double) totalMarks / numSubjects;
        String grade;

        if (averagePercentage >= A_GRADE) {
            grade = "A";
        } else if (averagePercentage >= B_GRADE) {
            grade = "B";
        } else if (averagePercentage >= C_GRADE) {
            grade = "C";
        } else if (averagePercentage >= D_GRADE) {
            grade = "D";
        } else {
            grade = "F";
        }

        if (averagePercentage > 100.0) {
            resultLabel.setText("Invalid marks entered. Please check and try again.");
        } else {
            resultLabel.setText("Total Marks: " + totalMarks + " | Average Percentage: " + averagePercentage + "% | Grade: " + grade);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GradeCalculatorSwing().setVisible(true);
            }
        });
    }
}
