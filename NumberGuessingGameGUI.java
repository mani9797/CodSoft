import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGameGUI {
    private JFrame frame;
    private JTextField guessField;
    private JLabel resultLabel;
    private int randomNumber;
    private int attemptsLimit = 5;
    private int attemptsLeft;
    private int score = 0;

    public NumberGuessingGameGUI() {
        frame = new JFrame("Number Guessing Game");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel instructionLabel = new JLabel("Enter your guess (1-100):");
        panel.add(instructionLabel);

        guessField = new JTextField(10);
        panel.add(guessField);

        JButton guessButton = new JButton("Guess");
        panel.add(guessButton);

        resultLabel = new JLabel("");
        panel.add(resultLabel);

        frame.add(panel, BorderLayout.CENTER);

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        startGame();
    }

    private void startGame() {
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1;
        attemptsLeft = attemptsLimit;
        score = 0;
        resultLabel.setText("");
    }

    private void checkGuess() {
        String guessText = guessField.getText();
        if (!guessText.isEmpty()) {
            int userGuess = Integer.parseInt(guessText);
            attemptsLeft--;

            if (userGuess == randomNumber) {
                score++;
                resultLabel.setText("Congratulations! You guessed the correct number.");
                startGame();
            } else if (userGuess < randomNumber) {
                resultLabel.setText("Too low! Try a higher number. Attempts left: " + attemptsLeft);
            } else {
                resultLabel.setText("Too high! Try a lower number. Attempts left: " + attemptsLeft);
            }

            if (attemptsLeft == 0) {
                resultLabel.setText("Out of attempts. The correct number was " + randomNumber);
                startGame();
            }
        } else {
            resultLabel.setText("Please enter a number.");
        }
        guessField.setText("");
    }

    public void display() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NumberGuessingGameGUI game = new NumberGuessingGameGUI();
                game.display();
            }
        });
    }
}