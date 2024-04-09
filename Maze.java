import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import Elements.*;


public class Maze extends JPanel implements KeyListener, Runnable{
    private JFrame mainFrame;
    private Player player;
    private Mapa puzzle;
    private static int VISIBILITY_RADIUS = 100; // Defina o raio de visão do jogador aqui
    private static final int CELL_SIZE = 30;
    private static final int HORIZONTAL_OFFSET = 0;
    private static final int VERTICAL_OFFSET = 0;
    private static final int DESCRIPTION_OFFSET_Y = 20;

     // Declare as variáveis para armazenar as imagens
    private BufferedImage treeImage;
    private BufferedImage axeImage;
    private BufferedImage lampImage;
    private BufferedImage mapImage;
    private BufferedImage wallImage;
    private BufferedImage pathImage;
    private BufferedImage playerImage;
    private BufferedImage houseImage;

    private final int  height;
    private Direction lastDirection;

    public Maze() {
        this.puzzle = new Mapa();
        this.player = new Player(1, 1);
        initializeWindow();
        this.height = puzzle.getHeight();
        this.lastDirection= Direction.NONE;


        // Carregue as imagens durante a inicialização
        try {
            treeImage = ImageIO.read(new File("./assets/tree.png"));
            axeImage = ImageIO.read(new File("./assets/axe.png"));
            lampImage = ImageIO.read(new File("./assets/lamp.png"));
            mapImage = ImageIO.read(new File("./assets/map.png"));
            wallImage = ImageIO.read(new File("./assets/path.png"));
            pathImage = ImageIO.read(new File("./assets/stone.png"));
            playerImage = ImageIO.read(new File("./assets/player.png"));
            houseImage = ImageIO.read(new File("./assets/house.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initializeWindow() {
        this.mainFrame = new JFrame("Maze Solver");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(this);
        mainFrame.setSize(800, 450);
        mainFrame.addKeyListener(this);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

    }
     @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = puzzle.getWidth();
        int height = puzzle.getHeight();
        int playerX = player.getX();
        int playerY = player.getY();

        // Desenhar o labirinto
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                char c = puzzle.getLocation(row, col);

                int distance = Math.abs(row - playerX) + Math.abs(col - playerY);

                if (distance <= VISIBILITY_RADIUS) {
                    g.setColor(Color.WHITE);
                    g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    

                    if (c == '#') {
                        g.drawImage(wallImage, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                    } else{
                        g.drawImage(pathImage, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                        if(c == 'T'){
                            g.drawImage(treeImage, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                        }
                        else if(c == 'A'){
                            g.drawImage(axeImage, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                        }
                        else if(c == 'L'){
                            g.drawImage(lampImage, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                        }
                        else if(c == 'M'){
                            g.drawImage(mapImage, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                        }
                        else if(c =='H'){
                            g.drawImage(houseImage, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                        }
                    }
                    
                } 
                else {
                    g.setColor(new Color(50, 54, 51));
                    g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        drawPlayer(g);
        drawHeart(g);
    }


    private void drawPlayer(Graphics g) {
        int playerX = player.getX();
        int playerY = player.getY();
        g.drawImage(playerImage, playerY * CELL_SIZE, playerX * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
    }

    private void drawHeart(Graphics g){
        String coracoes="";
        for(int i=0; i<player.getVidas(); i++){
            coracoes+="♥";
        }
        g.setColor(Color.RED);
        Font fonte2 = new Font("Segoe UI Emoji", Font.PLAIN, 20); 
        g.setFont(fonte2);
        g.drawString( coracoes, 20, height * CELL_SIZE + DESCRIPTION_OFFSET_Y);

    }
        @Override
    public void keyPressed(KeyEvent e) {
        int x = player.getX();
        int y = player.getY();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                x--; // Movimento para cima, então diminui o valor de y
                this.lastDirection=Direction.UP;

                break;
            case KeyEvent.VK_DOWN:
                x++; 
                this.lastDirection=Direction.DOWN;

                break;
            case KeyEvent.VK_LEFT:
                y--; 
                this.lastDirection=Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                y++; 
                this.lastDirection=Direction.RIGHT;

                break;
            case KeyEvent.VK_SPACE:
                
                
                break;
            default:

                break;
        }
        
        
        movePlayer(x, y);
    }
    public int getCellSize(){
        return CELL_SIZE;
    }

    public void movePlayer(int x, int y){
        if (x >= 0 && x < puzzle.getHeight() && y >= 0 && y < puzzle.getWidth()) {
            char c = puzzle.getLocation(x, y);
            if (c == '.') {
                player.setX(x);
                player.setY(y);
            } else if (c == 'A') {
                player.reloadGun();
                player.setX(x);
                player.setY(y);
            }
            else if(c == 'L'){
                VISIBILITY_RADIUS+=2;
                puzzle.setLocation(x, y, '.');
            }
            else if(c == 'M'){
                VISIBILITY_RADIUS=50;
                puzzle.setLocation(x, y, '.');
            }
        }
        repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

 

    public static void main(String[] args) {
        new Maze();
    }

	@Override
	public void run() {
		
	}


    

}