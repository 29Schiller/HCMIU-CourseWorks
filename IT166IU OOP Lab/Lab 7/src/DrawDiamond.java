import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawDiamond extends JFrame implements ActionListener {
    private JTextField inputField;
    private JButton okButton;
    private JButton cancelButton;

    public DrawDiamond() {
        super("Diamond Drawer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter diamond size:");
        add(label);

        inputField = new JTextField(10);
        add(inputField);

        okButton = new JButton("OK");
        okButton.addActionListener(this);
        add(okButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        add(cancelButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            try {
                int size = Integer.parseInt(inputField.getText());
                if (size <= 0) {
                    throw new AssertionError("Assertion Error is captured: Size should be > 0.");
                }
                drawDiamond(size);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number.", "Message", JOptionPane.ERROR_MESSAGE);
            } catch (AssertionError ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    private void drawDiamond(int size) {
        String[][] diamond = new String[size * 2 - 1][size * 2 - 1];
        for (int i = 0; i < size * 2 - 1; i++) {
            for (int j = 0; j < size * 2 - 1; j++) {
                diamond[i][j] = " ";
            }
        }

        int row = 0;
        for (int i = 0; i < size; i++) {
            for (int j = size - i - 1; j <= size + i - 1; j++) {
                diamond[i][j] = "*";
            }
            row++;
        }

        for (int i = size; i < size * 2 - 1; i++) {
            for (int j = size - (size * 2 - 1 - i) - 1; j <= size + (size * 2 - 1 - i) - 1; j++) {
                diamond[i][j] = "*";
            }
            row++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size * 2 - 1; i++) {
            for (int j = 0; j < size * 2 - 1; j++) {
                sb.append(diamond[i][j]);
            }
            sb.append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new DrawDiamond();
    }
}