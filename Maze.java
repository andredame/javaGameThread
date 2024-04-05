import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Font;

public class Maze extends JPanel implements KeyListener, Runnable{
    private JFrame mainFrame;
    private Player player;
    private Mapa puzzle;
    private static int VISIBILITY_RADIUS = 3; // Defina o raio de visão do jogador aqui
    private static final int CELL_SIZE = 20;
    private static final int HORIZONTAL_OFFSET = 0;
    private static final int VERTICAL_OFFSET = 0;
    private static final int DESCRIPTION_OFFSET_Y = 20;
    private final int  height;
    private Direction lastDirection;
    private boolean shooting;
    

    public Maze() {
        this.puzzle = new Mapa();
        this.player = new Player(1, 1);
        initializeWindow();
        this.height = puzzle.getHeight();
        this.lastDirection= Direction.NONE;
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
                    if (c == '#') {
                        g.setColor(Color.BLACK);
                        g.drawString("■", col * CELL_SIZE , row * CELL_SIZE );
                    } else if (c == 'V') {
                        g.setColor(Color.GREEN);
                        Font fonte = new Font("Segoe UI Emoji", Font.PLAIN, 20); // Escolha uma fonte que suporte emojis
                        g.setFont(fonte);
                        g.drawString("🌳", col * CELL_SIZE, row * CELL_SIZE);
                    } else if (c == 'A') {
                        g.setColor(Color.BLACK);
                        Font fonte = new Font("Segoe UI Emoji", Font.PLAIN, 15); // Escolha uma fonte que suporte emojis
                        g.setFont(fonte);
                        g.drawString("🪓", col * CELL_SIZE , row * CELL_SIZE );
                    } else if(c =='L'){
                        g.setColor(Color.BLACK);
                        g.drawString("🔦", col * CELL_SIZE , row * CELL_SIZE );
                    }
                    else if(c =='M'){
                        //map
                        g.setColor(Color.BLACK);
                        g.drawString("🗺️", col * CELL_SIZE + HORIZONTAL_OFFSET, row * CELL_SIZE + VERTICAL_OFFSET);
                    }
                    else {
                        g.setColor(Color.WHITE);
                        g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                } else {
                    g.setColor(Color.GRAY);
                    g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }


    
        // Desenhar o jogador
        drawPlayer(g);
    
        // Desenhar os corações de vida
        drawHeart(g);
        
    
    }
    
    // Método para desenhar o machado
    private void drawAxe(Graphics g, int x, int y) {
        g.setColor(Color.RED);
        g.fillOval(bulletX, bulletY, BULLET_SIZE, BULLET_SIZE);
    }

    private void drawPlayer(Graphics g) {
        g.setColor(Color.BLACK);
        Font fonte = new Font("Segoe UI Emoji", Font.PLAIN, 10); // Escolha uma fonte que suporte emojis
        g.setFont(fonte);
        g.drawString(String.valueOf(player.getSymbol()), player.getY() * CELL_SIZE, player.getX() * CELL_SIZE);
    }

    private void drawHeart(Graphics g){
        // Desenha a descrição abaixo do labirinto
        String coracoes="";
        for(int i=0; i<player.getVidas(); i++){
            coracoes+="♥";
        }
        //desenha os coracoes
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
                x++; // Movimento para baixo, então aumenta o valor de y
                this.lastDirection=Direction.DOWN;

                break;
            case KeyEvent.VK_LEFT:
                y--; // Movimento para a esquerda, então diminui o valor de x
                this.lastDirection=Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                y++; // Movimento para a direita, então aumenta o valor de x
                this.lastDirection=Direction.RIGHT;

                break;
            case KeyEvent.VK_SPACE:
                
                
                break;
            default:

                break;
        }
        
        // Agora movemos o jogador com as novas coordenadas calculadas
        movePlayer(x, y);
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
                //aumenta o raio de visão
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
    private void shootGun(){
        if(player.hasAmmo()){

        }
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