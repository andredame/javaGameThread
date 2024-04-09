import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TileManager {
    Maze gp;
    BufferedImage[] images; // Array para armazenar as imagens

    public TileManager(Maze gp) {
        this.gp = gp;
        images = new BufferedImage[128]; // Ajuste o tamanho do array conforme necessário
        getTileImages(); // Carrega as imagens
    }

    public void getTileImages() {
        try {
            // Carregar imagens para diferentes elementos do labirinto
            images['#'] = ImageIO.read(getClass().getResourceAsStream("./assets/path.png"));
            images['.'] = ImageIO.read(getClass().getResourceAsStream("./assets/stone.png"));
            images['V'] = ImageIO.read(getClass().getResourceAsStream("./assets/tree.png"));
            images['L'] = ImageIO.read(getClass().getResourceAsStream("./assets/lamp.png"));
            images['A'] = ImageIO.read(getClass().getResourceAsStream("./assets/axe.png"));

            // Adicione outras imagens conforme necessário para os outros elementos do labirinto
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void drawWall(Graphics2D g, int x, int y) {
        g.drawImage(images['#'], x * gp.getCellSize(), y * gp.getCellSize(), gp.getCellSize(), gp.getCellSize(), null);
    }

    public void drawLamp(Graphics2D g, int x, int y) {
        g.drawImage(images['L'], x * gp.getCellSize(), y * gp.getCellSize(), gp.getCellSize(), gp.getCellSize(), null);
    }
public void draw(Graphics2D g, char[][] maze) {
    int width = maze[0].length;
    int height = maze.length;

    // Desenhar o chão primeiro
    for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
            char c = maze[row][col];
            if (c == '.' || c == '#') {
                if (images[c] != null) {
                    g.drawImage(images[c], col * gp.getCellSize(), row * gp.getCellSize(), gp.getCellSize(), gp.getCellSize(), null);
                }
            }
        }
    }

    // Desenhar os elementos que aparecem sobre o chão
    for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
            char c = maze[row][col];
            if (c != '.' && c != '#') {
                if (images[c] != null) {
                    g.drawImage(images[c], col * gp.getCellSize(), row * gp.getCellSize(), gp.getCellSize(), gp.getCellSize(), null);
                }
            }
        }
    }
}

}
