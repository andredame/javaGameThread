package GUI;
import java.awt.*;
import java.awt.event.*;

import java.awt.image.*;
//imageio
import javax.imageio.ImageIO;

import javax.swing.*;


import Elements.*;
import Elements.GameCharacter;
import Threads.*;

public class GameGUI extends JPanel implements KeyListener{
    private JFrame mainFrame;
    // private Puzzle puzzle;
    public int VISIBILITY_RADIUS = 2; // Defina o raio de visão do jogador aqui
    

    //SCREEN SETTINGS
    final int originalTileSize=16;
    final int scale=3;
    public final int TILE_SIZE = originalTileSize *scale;
    final int maxScreenCol=16;
    final int maxScreenRow=10;
    public final int SCREEN_WIDTH = TILE_SIZE * maxScreenCol;
    public final int SCREEN_HEIGHT = TILE_SIZE * maxScreenRow;

    //world settings
    public final int maxWorldCol=118;
    public final int maxWorldRow=56;
    public final int worldWidth = TILE_SIZE * maxWorldCol;
    public final int worldHeight = TILE_SIZE * maxWorldRow;

    public int id=0;

    private final int DESCRIPTION_OFFSET_Y = 30;
   
    public TileManager tileManager;
    // private final int  height;
    private Direction lastDirection;
    private int idIterator = 0;
    //Threads 
    private ThreadManager threadManager;
    public Player player ;
    private boolean canTakeDamage=true;


    public GameGUI() {
        initializeWindow();
        this.player = new Player(1*TILE_SIZE, 2*TILE_SIZE,idIterator++,this);
        this.threadManager = new ThreadManager();
        this.tileManager = new TileManager(this,this.threadManager);
        this.lastDirection= Direction.NONE;
    }
    private void initializeWindow() {
        this.mainFrame = new JFrame("Maze Solver"); 
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(this);
        mainFrame.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        mainFrame.addKeyListener(this);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void draw(Graphics g, BufferedImage image, int x, int y) {
        g.drawImage(image, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        tileManager.draw(g);
        player.draw(g);

        String livesText = "Vidas: " + player.getVidas();
        g.setColor(Color.BLACK); // Define a cor do texto para branco
        g.setFont(new Font("Arial", Font.BOLD, 14)); // Define a fonte do texto
        g.drawString(livesText, 10, 20); // Desenha o texto no canto da tela
        drawHearts(g);
        
    }

    public void drawHearts(Graphics g){
        BufferedImage heartImage=null;
        try{
            heartImage = ImageIO.read(getClass().getResource("/assets/heart.png"));

        }catch (Exception e){
            System.out.println("Erro ao carregar imagem do coração");
        }
        int heartsX = 10;
        int heartsY = 20;

        // Desenhe uma imagem de coração para cada vida do jogador
        int lives = player.getVidas();
        for (int i = 0; i < lives; i++) {
            int heartPosX = heartsX + (i * (heartImage.getWidth() + 5)); // Adicione um espaço de 5 pixels entre os corações
            int heartPosY = heartsY;
            g.drawImage(heartImage, heartPosX, heartPosY, null);
        }

    }





    private void takeDamage(){
        if(canTakeDamage){
            player.lostLife();
        }
    }

    public void setCanTakeDamage(boolean value){
        canTakeDamage = value;
        if(!value){
            new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        canTakeDamage = true;
                    }
                }, 
                5000 
            );
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int x = player.getX();
        int y = player.getY();
        x = x / TILE_SIZE;
        y = y / TILE_SIZE;
    
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                this.lastDirection = Direction.UP;
                if (tileManager.isWalkable(x, y - 1)) {
                    player.setY((y - 1)*TILE_SIZE);
                }
                break;
            case KeyEvent.VK_DOWN:
                this.lastDirection = Direction.DOWN;

                if (tileManager.isWalkable(x, y + 1)) {
                    player.setY((y + 1)*TILE_SIZE);
                }
                break;
            case KeyEvent.VK_LEFT:
                this.lastDirection = Direction.LEFT;

                if (tileManager.isWalkable(x - 1, y)) {
                    player.setX((x - 1)*TILE_SIZE);
                }
                break;
            case KeyEvent.VK_RIGHT:
                this.lastDirection = Direction.RIGHT;
                if (tileManager.isWalkable(x + 1, y)) {
                    player.setX((x + 1)*TILE_SIZE);
                }
                break;

            //apertar E 
            case KeyEvent.VK_E:
                if(isAdjacentToALamp()){
                    this.VISIBILITY_RADIUS+=2;
                }
                if( isAdjacentToAAxe()){
                    System.out.println("true");
                    player.setHasAxe(8);
                }
                break;

            case KeyEvent.VK_SPACE:
                if(player.hasAxe()>0){
                    startThrowable();
                    player.throwAxe();
                }
                break;
        }
    
        repaint();
    }

    public void startThrowable() {
        if (lastDirection != Direction.NONE) {
            int x = player.getX();
            int y = player.getY();
            player.throwAxe();
            ThrowableThread throwableThread = new ThrowableThread(this, x/TILE_SIZE, y/TILE_SIZE, lastDirection, id++,"A",this.tileManager);
            threadManager.addThread(throwableThread);
            throwableThread.start();
        }
    }

    private boolean isAdjacentToALamp(){
        int playerX = player.getX() / TILE_SIZE;
        int playerY = player.getY() / TILE_SIZE;
        for(int i = -1; i<=1;i++){
            for(int j = -1; j<=1;j++){
                if(tileManager.getTile(playerX+i,playerY+j) == 'L'){
                    VISIBILITY_RADIUS+=1;
                    tileManager.getObjectOfTheGround(playerX+i,playerY+j);
                    return true;
                }
            }
        }
        return false;
    }



    private boolean isAdjacentToAAxe(){
        int playerX = player.getX() / TILE_SIZE;
        int playerY = player.getY() / TILE_SIZE;
        for(int i = -1; i<=1;i++){
            for(int j = -1; j<=1;j++){
                if(tileManager.getTile(playerX+i,playerY+j) == 'A'){
                    tileManager.getObjectOfTheGround(playerX+i,playerY+j);
                    return true;
                }
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

    public ThreadManager getThreadManager() {
        return threadManager;
    }


    public Player getPlayer() {
        return player;
    }
}