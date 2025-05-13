import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SudokuGame extends JFrame {
    private static final int GRID_SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private static final int CELL_SIZE = 60;
    private static final int BOARD_SIZE = GRID_SIZE * CELL_SIZE;

    private JTextField[][] cells;
    private int[][] solution;
    private int[][] puzzle;
    private int[][] originalPuzzle;
    private boolean gameWon;
    private int difficulty; // 1 = easy, 2 = medium, 3 = hard
    private long startTime;
    private Timer timer;
    private JLabel timeLabel;
    private boolean timerRunning;
    private JButton checkButton;
    private JButton solveButton;
    private JButton newGameButton;
    private JComboBox<String> difficultySelector;

    public SudokuGame() {
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(BOARD_SIZE + 100, BOARD_SIZE + 150);
        setResizable(false);
        setLocationRelativeTo(null);

        cells = new JTextField[GRID_SIZE][GRID_SIZE];
        solution = new int[GRID_SIZE][GRID_SIZE];
        puzzle = new int[GRID_SIZE][GRID_SIZE];
        originalPuzzle = new int[GRID_SIZE][GRID_SIZE];
        gameWon = false;
        difficulty = 1; // Default to easy

        initializeUI();
        generateNewPuzzle();

        setVisible(true);
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create the game board
        JPanel boardPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Create cells
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("SansSerif", Font.BOLD, 20));

                // Add borders to separate 3x3 subgrids
                int top = (row % SUBGRID_SIZE == 0) ? 2 : 1;
                int left = (col % SUBGRID_SIZE == 0) ? 2 : 1;
                int bottom = (row % SUBGRID_SIZE == SUBGRID_SIZE - 1) ? 2 : 1;
                int right = (col % SUBGRID_SIZE == SUBGRID_SIZE - 1) ? 2 : 1;

                cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

                // Add listeners
                final int currentRow = row;
                final int currentCol = col;

                cells[row][col].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        char key = e.getKeyChar();

                        if (originalPuzzle[currentRow][currentCol] != 0) {
                            // Cell was part of the original puzzle, don't allow changes
                            e.consume();
                            return;
                        }

                        // Only allow 1-9 to be entered
                        if (key < '1' || key > '9') {
                            e.consume();
                            return;
                        }

                        // Replace the text, don't append
                        SwingUtilities.invokeLater(() -> {
                            cells[currentRow][currentCol].setText(String.valueOf(key));
                            puzzle[currentRow][currentCol] = Character.getNumericValue(key);
                            checkForWin();
                        });

                        e.consume();
                    }
                });

                boardPanel.add(cells[row][col]);
            }
        }

        mainPanel.add(boardPanel, BorderLayout.CENTER);

        // Control panel (top)
        JPanel controlPanel = new JPanel();

        // Timer
        timeLabel = new JLabel("Time: 00:00");
        timeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        controlPanel.add(timeLabel);

        // Difficulty selector
        String[] difficulties = {"Easy", "Medium", "Hard"};
        difficultySelector = new JComboBox<>(difficulties);
        difficultySelector.addActionListener(e -> {
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            difficulty = cb.getSelectedIndex() + 1;
        });
        controlPanel.add(new JLabel("Difficulty:"));
        controlPanel.add(difficultySelector);

        mainPanel.add(controlPanel, BorderLayout.NORTH);

        // Button panel (bottom)
        JPanel buttonPanel = new JPanel();

        // New Game button
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener( e -> generateNewPuzzle());
        buttonPanel.add(newGameButton);

        // Check button
        checkButton = new JButton("Check Solution");
        checkButton.addActionListener(e -> checkSolution());
        buttonPanel.add(checkButton);

        // Solve button
        solveButton = new JButton("Solve Puzzle");
        solveButton.addActionListener(e -> solvePuzzle());
        buttonPanel.add(solveButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Set up timer
        timer = new Timer(1000, e -> updateTimer());
        timerRunning = false;
    }

    private void generateNewPuzzle() {
        // Stop timer if it's running
        if (timerRunning) {
            timer.stop();
        }

        // Generate a new solution
        generateSolution();

        // Create puzzle by removing numbers from solution
        createPuzzle();

        // Reset game state
        gameWon = false;
        startTime = System.currentTimeMillis();
        timeLabel.setText("Time: 00:00");
        timer.start();
        timerRunning = true;

        // Update the UI
        updateUI();
    }

    private void generateSolution() {
        // Initialize with zeros
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                solution[row][col] = 0;
            }
        }

        // Fill the solution using backtracking
        fillSolution(0, 0);
    }

    private boolean fillSolution(int row, int col) {
        // Move to next row when we reach the end of current row
        if (col == GRID_SIZE) {
            row++;
            col = 0;

            // If we've filled the entire grid, we're done
            if (row == GRID_SIZE) {
                return true;
            }
        }

        Random random = new Random();
        int[] numbers = new int[GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            numbers[i] = i + 1;
        }

        // Shuffle the numbers for randomness
        for (int i = 0; i < GRID_SIZE; i++) {
            int randPos = random.nextInt(GRID_SIZE);
            int temp = numbers[i];
            numbers[i] = numbers[randPos];
            numbers[randPos] = temp;
        }

        for (int num : numbers) {
            if (isValid(solution, row, col, num)) {
                solution[row][col] = num;

                if (fillSolution(row, col + 1)) {
                    return true;
                }

                // If placing num doesn't lead to a solution, backtrack
                solution[row][col] = 0;
            }
        }

        return false;
    }

    private void createPuzzle() {
        // Copy solution to puzzle
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                puzzle[row][col] = solution[row][col];
                originalPuzzle[row][col] = 0; // Reset original puzzle
            }
        }

        // Determine how many cells to remove based on difficulty
        int cellsToRemove;
        cellsToRemove = switch (difficulty) {
            case 1 -> 40; // Easy
            case 2 -> 50; // Medium
            case 3 -> 60; // Hard
            default -> 40;
        }; 
        
        

        Random random = new Random();

        // Remove cells randomly
        while (cellsToRemove > 0) {
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);

            if (puzzle[row][col] != 0) {
                puzzle[row][col] = 0;
                cellsToRemove--;
            }
        }

        // Save the original puzzle state
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                originalPuzzle[row][col] = puzzle[row][col];
            }
        }
    }

    private void updateUI() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (puzzle[row][col] == 0) {
                    cells[row][col].setText("");
                    cells[row][col].setBackground(Color.WHITE);
                    cells[row][col].setEditable(true);
                } else {
                    cells[row][col].setText(String.valueOf(puzzle[row][col]));
                    cells[row][col].setBackground(new Color(230, 230, 250)); // Light purple for original numbers
                    cells[row][col].setEditable(false);
                }
            }
        }
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        // Check row
        for (int c = 0; c < GRID_SIZE; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }

        // Check column
        for (int r = 0; r < GRID_SIZE; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }

        // Check 3x3 subgrid
        int subgridRowStart = (row / SUBGRID_SIZE) * SUBGRID_SIZE;
        int subgridColStart = (col / SUBGRID_SIZE) * SUBGRID_SIZE;

        for (int r = 0; r < SUBGRID_SIZE; r++) {
            for (int c = 0; c < SUBGRID_SIZE; c++) {
                if (board[subgridRowStart + r][subgridColStart + c] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private void checkSolution() {
        // Check if the puzzle is complete
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (puzzle[row][col] == 0) {
                    JOptionPane.showMessageDialog(this, "Puzzle is not complete yet!", "Incomplete", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        }

        // Check if the solution is correct
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (puzzle[row][col] != solution[row][col]) {
                    JOptionPane.showMessageDialog(this, "Your solution is incorrect. Keep trying!", "Incorrect", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        // If we get here, the solution is correct
        gameWon = true;
        timer.stop();
        timerRunning = false;
        JOptionPane.showMessageDialog(this, "Congratulations! You solved the puzzle!", "Victory", JOptionPane.INFORMATION_MESSAGE);
    }

    private void solvePuzzle() {
        // Show the solution
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                puzzle[row][col] = solution[row][col];
                cells[row][col].setText(String.valueOf(solution[row][col]));
                if (originalPuzzle[row][col] == 0) {
                    cells[row][col].setBackground(new Color(255, 220, 220)); // Light red for solved cells
                }
            }
        }

        timer.stop();
        timerRunning = false;
    }

    private void checkForWin() {
        if (gameWon) return;

        // Check if the puzzle is filled
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (puzzle[row][col] == 0) {
                    return; // Still has empty cells
                }
            }
        }

        // Check if the solution is correct
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (puzzle[row][col] != solution[row][col]) {
                    return; // Incorrect solution
                }
            }
        }

        // If we get here, the player won
        gameWon = true;
        timer.stop();
        timerRunning = false;
        JOptionPane.showMessageDialog(this, "Congratulations! You solved the puzzle!", "Victory", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateTimer() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        long minutes = elapsedSeconds / 60;
        long seconds = elapsedSeconds % 60;

        timeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    // Main method to launch the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuGame());
    }
}