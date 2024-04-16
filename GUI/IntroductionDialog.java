package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class IntroductionDialog extends JDialog {
    public IntroductionDialog(JFrame parent) {
        super(parent, "Welcome to Maze Solver", true);
        
        // Create a panel for the content
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create a label for the message with HTML formatting
        JLabel messageLabel = new JLabel("<html><div style='text-align: center; font-family: Arial; font-size: 16px;'>" +
                                           "You were heading to the Programmers' Convention,<br>" +
                                           "but while passing through the Witch's forest, you ended up crashing your car due to intense fog.<br>" +
                                           "<b><font color='red'>Now you need to find the car parts to escape the forest.</b></font><br>" +
                                           "<font color='red'>Stay away from the Snakes!</font><br>" +
                                           "<b>To interact with anything on the map, press E</b>.</div></html>");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setForeground(Color.BLACK);
        
        contentPane.add(messageLabel, BorderLayout.CENTER);
        
        setContentPane(contentPane);
        
        setSize(600, 400);
        setLocationRelativeTo(parent);
    }
}

