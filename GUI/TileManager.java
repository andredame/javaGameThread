package GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import Threads.CharacterThread;
import Threads.ThreadManager;
import Elements.GameCharacter;
import Elements.Snake;

public class TileManager {
    private Map<String, BufferedImage> tileImages;
    private GameGUI game;
    private char[][] mapTileChar;
    private ThreadManager threadManager;


    public TileManager(GameGUI game, ThreadManager threadManager) {
        this.game = game;
        mapTileChar = new char[game.maxWorldRow][game.maxWorldCol];
        this.threadManager = threadManager;
        tileImages = new HashMap<>();
        createThreadsFromTxt("./GUI/snake.csv");
        try {
            BufferedImage treeImage = resizeImage("/assets/tree.png", 16, 16);
            BufferedImage axeImage = resizeImage("/assets/axe.png", 16, 16);
            BufferedImage lampImage = resizeImage("/assets/lamp.png", 16, 16);
            BufferedImage carImage = resizeImage("/assets/car_crash.png", 16, 16);
            BufferedImage lumberjackImage = resizeImage("/assets/lumberjack.png", 16, 16);
            BufferedImage playerImage = resizeImage("/assets/player.png", 16, 16);
            BufferedImage earthImage = resizeImage("/assets/earth.png", 16, 16);
            BufferedImage snakeImage = resizeImage("/assets/snake.png", 16, 16);
            BufferedImage heartImage = resizeImage("/assets/heart.png", 10, 10);
            BufferedImage steeringWheelImage = resizeImage("/assets/steeringWheel.png", 16, 16);
            BufferedImage batteryImage = resizeImage("/assets/battery.png", 16, 16);
            BufferedImage gasImage = resizeImage("/assets/gas.png", 16, 16);
            BufferedImage wrenchImage = resizeImage("/assets/wrench.png", 16, 16);
            BufferedImage tireImage = resizeImage("/assets/tire.png", 16, 16);
            tileImages.put("9",heartImage);
            tileImages.put("1", steeringWheelImage);
            tileImages.put("2", batteryImage);
            tileImages.put("3", gasImage);
            tileImages.put("4", wrenchImage);
            tileImages.put("5", tireImage);
            tileImages.put("#", treeImage);
            tileImages.put("T", treeImage);
            tileImages.put("A", axeImage);
            tileImages.put("L", lampImage);
            tileImages.put("C", carImage);
            tileImages.put("X", lumberjackImage);
            tileImages.put("P", playerImage);
            tileImages.put(".", earthImage);
            tileImages.put("S", snakeImage);
        } catch (IOException e) {
            System.out.println("Erro ao carregar imagens dos tiles" + e);
        }
        loadMap("world.txt");
    }

    public void removeThread(int id, CharacterThread thread) {
        threadManager.removeThread(id, thread);
    }

    public void getObjectOfTheGround(int x,int y){
        mapTileChar[y][x] = '.';
        game.repaint();
    }

    private BufferedImage resizeImage(String imagePath, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public void addTileImage(String symbol, BufferedImage image) {
        tileImages.put(symbol, image);
    }

    public BufferedImage getTileImage(String symbol) {
        return tileImages.get(symbol);
    }

    public Thread foundCharacter(int x, int y){
        return threadManager.isCharacterAtPosition(x, y);
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
    
            int numRows = 0;
            int numCols = 0;
    
            String line;
            while ((line = br.readLine()) != null) {
                numRows++;
                numCols = Math.max(numCols, line.length());
            }
    
            br.close();
            is = getClass().getResourceAsStream(filePath);
            br = new BufferedReader(new InputStreamReader(is));
    
            mapTileChar = new char[numRows][numCols];
    
            int row = 0;
            
            while ((line = br.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    mapTileChar[row][col] = line.charAt(col);
                }
                row++;
            }
    
            br.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o mapa: " + e);
        }
    }

    public void draw(Graphics g) {
        int playerX = game.player.getX() / game.TILE_SIZE;
        int playerY = game.player.getY() / game.TILE_SIZE;
    
        for (int worldRow = 0; worldRow < game.maxWorldRow; worldRow++) {
            for (int worldCol = 0; worldCol < game.maxWorldCol; worldCol++) {
                char tile = threadManager.isThreadAtPosition(worldCol, worldRow)!=' ' ? threadManager.isThreadAtPosition(worldCol, worldRow) : mapTileChar[worldRow][worldCol];
    
                int worldX = worldCol * game.TILE_SIZE;
                int worldY = worldRow * game.TILE_SIZE;
    
                int screenX = worldX - game.player.worldX + game.player.screenX;
                int screenY = worldY - game.player.worldY + game.player.screenY;
    
                BufferedImage tileImage;
                int distance = Math.abs(playerX - worldCol) + Math.abs(playerY - worldRow);
                if (distance <= game.VISIBILITY_RADIUS) {
                    tileImage = getTileImage(String.valueOf(tile));
                    BufferedImage backgroundTileImage = getTileImage("."); // Imagem do terreno de fundo
                    g.drawImage(backgroundTileImage, screenX, screenY, game.TILE_SIZE, game.TILE_SIZE, null);
                } else {
                    tileImage = getTileImage("Fog");
                }
                
                g.drawImage(tileImage, screenX, screenY, game.TILE_SIZE, game.TILE_SIZE, null);
                
            }
        }
    }


    public char getTile(int x, int y) {
        return mapTileChar[y][x];
    }

    public boolean isWalkable(int x, int y) {
        if (x >= 0 && y < game.maxWorldRow && y >= 0 && x < game.maxWorldCol) {
            if(threadManager.isCharacterAtPosition(x, y)==null) return mapTileChar[y][x] == '.' || mapTileChar[y][x] == 'g';
            return false;
        } else {
            return false;
        }
    }


    public void createThreadsFromTxt(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] params = line.split(",");
                int x = Integer.parseInt(params[0].trim());
                int y = Integer.parseInt(params[1].trim());
                Snake snake = new Snake(x, y, ++game.id, game);
                CharacterThread snakeThread = new CharacterThread(game, snake, game.id, this);
                threadManager.addThread(snakeThread);
            }
            threadManager.startAllThreads();
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
    

    public boolean foundATree(int x, int y){
        return mapTileChar[y][x] == 'T';
    }

    public void removeTree(int x, int y){
        mapTileChar[y][x] = '.';
    }

    
}
