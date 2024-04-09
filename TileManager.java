import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class TileManager {
    private Map<String, BufferedImage> tileImages;

    public TileManager() {
        tileImages = new HashMap<>();
        // Adicionar imagens aos símbolos correspondentes
        try {
            BufferedImage treeImage = ImageIO.read(new File("./assets/tree.png"));
            BufferedImage axeImage = ImageIO.read(new File("./assets/axe.png"));
            BufferedImage lampImage = ImageIO.read(new File("./assets/lamp.png"));
            BufferedImage mapImage = ImageIO.read(new File("./assets/map.png"));
            BufferedImage houseImage = ImageIO.read(new File("./assets/house.png"));
            BufferedImage carImage = ImageIO.read(new File("./assets/car_crash.png"));
            BufferedImage lakeImage = ImageIO.read(new File("./assets/lake.png"));
            BufferedImage bridgeImage = ImageIO.read(new File("./assets/bridge_lake.png"));
            BufferedImage lumberjackImage = ImageIO.read(new File("./assets/lumberjack.png"));
            BufferedImage playerImage = ImageIO.read(new File("./assets/player.png"));
            BufferedImage stoneImage = ImageIO.read(new File("./assets/stone.png"));

            tileImages.put("#", treeImage);
            tileImages.put("T", treeImage);
            tileImages.put("A", axeImage);
            tileImages.put("L", lampImage);
            tileImages.put("M", mapImage);
            tileImages.put("H", houseImage);
            tileImages.put("C", carImage);
            tileImages.put("W", lakeImage);
            tileImages.put("B", bridgeImage);
            tileImages.put("X", lumberjackImage);
            tileImages.put("P", playerImage);
            tileImages.put(".", stoneImage);
        } catch (IOException e) {
            System.out.println("Erro ao carregar imagens dos tiles" + e);
        }
    }

    public void addTileImage(String symbol, BufferedImage image) {
        tileImages.put(symbol, image);
    }

    public BufferedImage getTileImage(String symbol) {
        return tileImages.get(symbol);
    }
}
