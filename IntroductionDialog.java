import java.awt.Font;
import javax.swing.*;

public class IntroductionDialog extends JDialog {
    public IntroductionDialog(JFrame parent) {
        super(parent, "Bem-vindo ao Maze Solver", true);
        JLabel messageLabel = new JLabel("<html>Você estava indo para a Convenção dos Programadores,<br>mas enquanto passava pela floresta da Bruxa, você caiu da montanha e ficou desacordado por alguns minutos.<br>Resgate os Algoritmos para chegar à convenção.</html>");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);

        // Definindo uma fonte personalizada
        Font font = new Font("Segoe UI", Font.PLAIN, 14);
        messageLabel.setFont(font);

        add(messageLabel);
        setSize(400, 200);
        setLocationRelativeTo(parent);
    }
}
