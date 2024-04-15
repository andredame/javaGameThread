package GUI;
import java.awt.Font;
import javax.swing.*;

public class IntroductionDialog extends JDialog {
    public IntroductionDialog(JFrame parent) {
        super(parent, "Bem-vindo ao Maze Solver", true);
        JLabel messageLabel = new JLabel("<html><div style='text-align: center; font-family: Arial; font-size: 14px;'>Você estava indo para a Convenção dos Programadores,<br>mas enquanto passava pela floresta da Bruxa, você acabou batendo o carro, devido a neblina intensa.<br> Agora você precisa encontrar as peças do carro para sair da floresta.<br>Fique longe das Cobras!<br>Para interagir com qualquer coisa no mapa, pressione E.</div></html>");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(messageLabel);
        setSize(600, 400);
        setLocationRelativeTo(parent);
    }
}
