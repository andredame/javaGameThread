import GUI.GameGUI;
import javax.swing.SwingUtilities;

public class Main {
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GameGUI gameGUI = new GameGUI();
            }
        });
    }
}
