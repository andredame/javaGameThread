package GUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

import Elements.*;
import Elements.GameCharacter;
import Threads.*;

public class GameGUI extends JPanel implements KeyListener{
    private JFrame mainFrame;
    private Puzzle puzzle;
    private static int VISIBILITY_RADIUS = 5; // Defina o raio de visão do jogador aqui
    private static final int CELL_SIZE = 27;
    private static final int DESCRIPTION_OFFSET_Y = 20;
   
    private TileManager tileManager;
    private final int  height;
    private Direction lastDirection;
    private int idIterator = 0;

    private int cameraX;
    private int cameraY;


    //Threads 
    private ThreadManager threadManager;

    private Player player ;



    public GameGUI() {
        this.player = new Player(1, 1, puzzle,idIterator++);
        this.puzzle = new Puzzle();
        this.tileManager = new TileManager();
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
        int playerX = player.getX();
        int playerY = player.getY();
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
        drawThreads(g);
        draw(g, this.tileManager.getTileImage(String.valueOf(player.getSymbol())), playerX, playerY);
        
        drawHeart(g);
        

    }


    private void createCharacters(){
        LumberJack lumberJack = new LumberJack(12, 27, puzzle,idIterator++);
        threadManager.addThread(new CharacterThread(this, lumberJack, puzzle, idIterator++));
    
        Snake s1 = new Snake(5, 50, puzzle,idIterator++);
        threadManager.addThread(new CharacterThread(this, s1, puzzle, idIterator++));
    
        Snake s2 = new Snake(9, 42, puzzle,idIterator++);
        threadManager.addThread(new CharacterThread(this, s2, puzzle, idIterator++));
    
        Snake s3 = new Snake(1, 43, puzzle,idIterator++);
        threadManager.addThread(new CharacterThread(this, s3, puzzle, idIterator++));

        Snake s4 = new Snake(1, 44, puzzle,idIterator++);
        threadManager.addThread(new CharacterThread(this, s4, puzzle, idIterator++));
    }


    private void drawThreads(Graphics g) {
        int playerX = player.getX();
        int playerY = player.getY();
    
        for (Thread thread : threadManager.getThreads().values()) {
            if (thread instanceof CharacterThread) {
                CharacterThread characterThread = (CharacterThread) thread;
                if (characterThread.getCharacter().isAlive()) {
                    GameCharacter character = characterThread.getCharacter();
                    int characterX = character.getX();
                    int characterY = character.getY();

                    int distance = Math.abs(playerX - characterX) + Math.abs(playerY - characterY);

                    if (distance <= VISIBILITY_RADIUS) {
                        draw(g, this.tileManager.getTileImage(String.valueOf(character.getSymbol())), characterX, characterY);
                    }

                } else {
                    threadManager.removeThread(characterThread.getIdentificador(), thread);
                }
            }

            if (thread instanceof ThrowableThread) {
                ThrowableThread throwableThread = (ThrowableThread) thread;
                int x = throwableThread.getX();
                int y = throwableThread.getY();
                int distance = Math.abs(playerX - x) + Math.abs(playerY - y);
                if (distance <= VISIBILITY_RADIUS) {
                    draw(g, this.tileManager.getTileImage(String.valueOf(throwableThread.getSymbol())), x, y);
                }
            }
        }
    }

    private void draw(Graphics g, Image image,int x, int y) {
        g.drawImage(image, y * CELL_SIZE, x * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
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
            case KeyEvent.VK_E:
                if (isAdjacentToLumberJack(x, y)) {
                    JOptionPane.showMessageDialog(mainFrame, "Você deve achar um machado, porque algumas páginas podem estar entre as árvores.", "Atenção", JOptionPane.INFORMATION_MESSAGE);
                }

                if(isAdjacentToLamp(x, y)){
                    VISIBILITY_RADIUS+=1;
                    return;
                }

                if(isAdjacentToAxe(x, y)){
                    player.setHasAxe(5);
                    return;
                }
                break;
            case KeyEvent.VK_SPACE:
                if(player.hasAxe() > 0){
                    startThrowable();
                }
                break;
                case KeyEvent.VK_R:
                    
            default:
            
                break;
        }
        
        
        movePlayer(x, y);
    }


    public void movePlayer(int x, int y){
        if (x >= 0 && x < puzzle.getHeight() && y >= 0 && y < puzzle.getWidth()) {
            char c = puzzle.getLocation(x, y);
            if(threadManager.isCharacterAtPosition(x, y)){
                return;
            }
            if (c == '.') {
                player.setX(x);
                player.setY(y);
            } 
        }
        repaint();
    }




    public void startThrowable() {
        if (lastDirection != Direction.NONE) {
            int x = player.getX();
            int y = player.getY();
            player.throwAxe();
            ThrowableThread throwableThread = new ThrowableThread(this, x, y, lastDirection, puzzle, idIterator++,"A");
            threadManager.addThread(throwableThread);
            throwableThread.start();
        }
    }


    
  

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    private boolean isAdjacentToLumberJack(int x, int y) {
        int lumberJackX = -1;
        int lumberJackY = -1;
        for (Thread thread : threadManager.getThreads().values()) {
            if (thread instanceof CharacterThread) {
                CharacterThread characterThread = (CharacterThread) thread;
                if (characterThread.getCharacter() instanceof LumberJack) {
                    lumberJackX = characterThread.getCharacter().getX();
                    lumberJackY = characterThread.getCharacter().getY();
                    break;
                }
            }
        }
    
        if (lumberJackX != -1 && lumberJackY != -1) {
            if (Math.abs(x - lumberJackX) == 1 && y == lumberJackY) {
                return true;
            }
            if (Math.abs(y - lumberJackY) == 1 && x == lumberJackX) {
                return true; 
            }
        }
    
        return false; 
    }

    private boolean isAdjacentToAxe (int x , int y){
        int axeX = -1;
        int axeY = -1;
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                if(puzzle.getLocation(x+i, y+j) == 'A'){
                    axeX = x+i;
                    axeY = y+j;
                    break;
                }
            }
        }
    
        if (axeX != -1 && axeY != -1) {
            if (Math.abs(x - axeX) == 1 && y == axeY) {
                puzzle.setLocation(axeX, axeY, '.');
                return true;
            }
            if (Math.abs(y - axeY) == 1 && x == axeX) {
                puzzle.setLocation(axeX, axeY, '.');
                return true; 
            }
        }
    
        return false;
    }

    

    private boolean isAdjacentToLamp (int x, int y) {
        int lampX = -1;
        int lampY = -1;
        for (int i=-1; i<2; i++){
            for (int j=-1; j<2; j++){
                if (puzzle.getLocation(x+i, y+j) == 'L'){
                    lampX = x+i;
                    lampY = y+j;
                    break;
                }
            }
        }
        if (lampX != -1 && lampY != -1) {
            if (Math.abs(x - lampX) == 1 && y == lampY) {
                puzzle.setLocation(lampX, lampY, '.');
                return true;
            }
            if (Math.abs(y - lampY) == 1 && x == lampX) {
                puzzle.setLocation(lampX, lampY, '.');
                return true; 
            }
        }
    
        return false; 
    }



    public ThreadManager getThreadManager() {
        return threadManager;
    }


}