package GUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

import Elements.*;
import Elements.GameCharacter;
import Thread.*;

public class Maze extends JPanel implements KeyListener, Runnable{
    private JFrame mainFrame;
    private Puzzle puzzle;
    private static int VISIBILITY_RADIUS = 50; // Defina o raio de visão do jogador aqui
    private static final int CELL_SIZE = 27;
    private static final int DESCRIPTION_OFFSET_Y = 20;
    private ArrayList <GameCharacter> characters;
    private TileManager tileManager;
    private final int  height;
    private Direction lastDirection;
    private int idIterator = 0;

    private int shotX = -1;
    private int shotY = -1;

    //Threads 
    private ThreadManager threadManager;



    public Maze() {
        this.puzzle = new Puzzle();
        this.tileManager = new TileManager();
        this.characters = new ArrayList<>();
        this.threadManager = new ThreadManager();
        createCharacters();
        initializeWindow();
        this.height = puzzle.getHeight();
        this.lastDirection= Direction.NONE;
        threadManager.startAllThreads();
    }



    private void initializeWindow() {
        this.mainFrame = new JFrame("Maze Solver");
        // IntroductionDialog dialog = new IntroductionDialog(mainFrame);
        // dialog.setModal(true);
        // dialog.setVisible(true);
       
         
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
        int playerX = characters.get(0).getX();
        int playerY = characters.get(0).getY();
        int distance=-1;
    
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                char c = puzzle.getLocation(row, col);
                if(c == '1' || c == '2' || c == '3' ){
                    System.out.println("row:"+ row + " col:"+ col );
                }                
                distance = Math.abs(row - playerX) + Math.abs(col - playerY);
                if (distance <= VISIBILITY_RADIUS) {

                   g.drawImage(this.tileManager.getTileImage(String.valueOf('.')), col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                    BufferedImage tileImage = tileManager.getTileImage(String.valueOf(c));
                    if (tileImage != null) {
                        g.drawImage(tileImage, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                    }
                } else {
                    g.setColor(new Color(50, 54, 51));
                    g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
        drawCharacters(g,distance);
        drawHeart(g);
        drawShot(g);

    }

    private void drawShot(Graphics g) {
        if (shotX != -1 && shotY != -1) {
            
            g.setColor(Color.RED);
            g.fillOval(shotY * CELL_SIZE, shotX * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    private void createCharacters(){
        this.characters = new ArrayList<>();
        Player player = new Player(1, 1, puzzle,idIterator++);
        this.characters.add(player);
        LumberJack lumberJack = new LumberJack(12, 27, puzzle,idIterator++);
        this.characters.add(lumberJack);
        Snake s1 = new Snake(5, 50, puzzle,idIterator++);
        this.characters.add(s1);
        Snake s2 = new Snake(9, 42, puzzle,idIterator++);
        this.characters.add(s2);
        Snake s3 = new Snake(1, 43, puzzle,idIterator++);
        this.characters.add(s3);
        threadManager.initiateThreads(characters, this);
    }

    private void drawCharacters(Graphics g,int distance){
        for(GameCharacter character: characters){
           if(character instanceof Player){
               g.drawImage(this.tileManager.getTileImage(String.valueOf(character.getSymbol())), character.getY() * CELL_SIZE, character.getX() * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
           } else {
               if(character.isAlive()){
                    int lumberJackX = character.getX();
                    int lumberJackY = character.getY();
                    int playerX = characters.get(0).getX();
                    int playerY = characters.get(0).getY();
                    int distanceFromPlayer = Math.abs(playerX - lumberJackX) + Math.abs(playerY - lumberJackY);
                    if (distanceFromPlayer <= VISIBILITY_RADIUS) {
                        g.drawImage(this.tileManager.getTileImage(String.valueOf(character.getSymbol())), character.getY() * CELL_SIZE, character.getX() * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                    }
               }
           }
        }
    }
    
    private void drawHeart(Graphics g){
        String coracoes="";
        for(int i=0; i<characters.get(0).getVidas(); i++){
            coracoes+="♥";
        }
        g.setColor(Color.RED);
        Font fonte2 = new Font("Segoe UI Emoji", Font.PLAIN, 20); 
        g.setFont(fonte2);
        g.drawString( coracoes, 20, height * CELL_SIZE + DESCRIPTION_OFFSET_Y);

    }
        @Override
    public void keyPressed(KeyEvent e) {
        int x = characters.get(0).getX();
        int y = characters.get(0).getY();

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
            case KeyEvent.VK_E:
                // Verificar se o jogador está ao lado do LumberJack
                if (isAdjacentToLumberJack(x, y)) {
                    JOptionPane.showMessageDialog(mainFrame, "Você deve achar um machado, porque algumas páginas podem estar entre as árvores.", "Atenção", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case KeyEvent.VK_SPACE:
                
                break;

                case KeyEvent.VK_R:
                    
            default:
            
                break;
        }
        
        
        movePlayer(x, y);
    }

    

    private boolean isAdjacentToLumberJack(int x, int y) {
        int lumberJackX = characters.get(1).getX();
        int lumberJackY = characters.get(1).getY();
    
        if ((Math.abs(x - lumberJackX) == 1 && y == lumberJackY) || (Math.abs(y - lumberJackY) == 1 && x == lumberJackX)) {
            return true;
        }
    
        return false;
    }

    public void movePlayer(int x, int y){
        if (x >= 0 && x < puzzle.getHeight() && y >= 0 && y < puzzle.getWidth()) {
            char c = puzzle.getLocation(x, y);
            if(isCharacterAtPosition(x, y)){
                return;
            }
            if (c == '.' || c == 'A' ) {
                characters.get(0).setX(x);
                characters.get(0).setY(y);
            } 
            if(c == 'L'){
                VISIBILITY_RADIUS+=1;
                puzzle.setLocation(x, y, '.');
                return;
            }
            if(c == 'M'){
                VISIBILITY_RADIUS=50;
                puzzle.setLocation(x, y, '.');

                return;
            }
        }
        repaint();
    }
    public boolean isCharacterAtPosition(int x,int y){
        for(GameCharacter character: characters){
            if(character.getX()==x && character.getY()==y){
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

	@Override
	public void run() {
	
	}
    public boolean isLumberjackAlive(){
        return characters.get(1).isAlive();
    }


    

}