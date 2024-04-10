import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import Elements.*;
import Elements.Character;


public class Maze extends JPanel implements KeyListener, Runnable{
    private JFrame mainFrame;
    private Puzzle puzzle;
    private static int VISIBILITY_RADIUS = 50; // Defina o raio de visão do jogador aqui
    private static final int CELL_SIZE = 27;
    private static final int DESCRIPTION_OFFSET_Y = 20;
    private ArrayList <Character> characters;
    private TileManager tileManager;
    private final int  height;
    private Direction lastDirection;

    private int shotX = -1;
    private int shotY = -1;
    private Thread shotThread;
    private boolean shotThreadRunning = false;


    public Maze() {
        this.puzzle = new Puzzle();
        this.tileManager = new TileManager();
        this.characters = new ArrayList<>();
        createCharacters();
        initializeWindow();
        this.height = puzzle.getHeight();
        this.lastDirection= Direction.NONE;

        startLumberJackThread();
        startSnakeThread(2);
        startSnakeThread(3);
        startSnakeThread(4);

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
            // Desenhe o tiro na posição atual (shotX, shotY)
            // Aqui você precisa implementar a lógica para desenhar o tiro
            // Por exemplo, você pode desenhar um círculo representando o tiro
            g.setColor(Color.RED);
            g.fillOval(shotY * CELL_SIZE, shotX * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    private void createCharacters(){
        this.characters = new ArrayList<>();
        Player player = new Player(1, 1, puzzle);
        LumberJack lumberJack = new LumberJack(12, 27, puzzle);
        Snake s1 = new Snake(5, 50, puzzle);
        Snake s2 = new Snake(9, 42, puzzle);
        Snake s3 = new Snake(1, 43, puzzle);
        this.characters.add(player);
        this.characters.add(lumberJack);
        this.characters.add(s1);
        this.characters.add(s2);
        this.characters.add(s3);
    }

    private void drawCharacters(Graphics g,int distance){
        for(Character character: characters){
           if(character instanceof Player){
               g.drawImage(this.tileManager.getTileImage(String.valueOf(character.getSymbol())), character.getY() * CELL_SIZE, character.getX() * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
           } else {
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
                if (!shotThreadRunning) {
                    startShotThread();
                    shotThreadRunning = true;
                    shotX = characters.get(0).getX();
                    shotY= characters.get(0).getY();
                }


            
                break;
            default:
            
                break;
        }
        
        
        movePlayer(x, y);
    }

    private void startLumberJackThread() {
        if (characters.get(1).isAlive()) {
            Thread lumberJackThread = new Thread() {
                public void run() {
                    while (characters.get(1).isAlive()) {
                        int moveX = 0;
                        int moveY = 0;
                        int direction = (int) (Math.random() * 4); 
                        switch (direction) {
                            case 0: // cima
                                moveX = -1;
                                break;
                            case 1: // baixo
                                moveX = 1;
                                break;
                            case 2: // esquerda
                                moveY = -1;
                                break;
                            case 3: // direita
                                moveY = 1;
                                break;
                        }
                        int newX = characters.get(1).getX() + moveX;
                        int newY = characters.get(1).getY() + moveY;
                        if (
                            newX >= 0 && newX < puzzle.getHeight()
                            && newY >= 0 
                            && newY < puzzle.getWidth() 
                            && puzzle.isWalkable(newX, newY)
                            && !isCharacterAtPosition(newX, newY)
                        ) {
                            characters.get(1).setX(newX);
                            characters.get(1).setY(newY);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        repaint();
                    }
                }
            };
            // Inicie a thread do LumberJack
            lumberJackThread.start();
        }
    }


    private void startShotThread() {
        shotThread = new Thread() {
            public void run() {
                while (true) {
                    // Se houve um tiro disparado
                    if (shotX != -1 && shotY != -1) {
                        // Verifique se atingiu algum personagem
                        checkShotHit();

                        // Mova o tiro
                        moveShot();

                        // Redesenha o painel
                        repaint();
                    }

                    // Aguarde um tempo antes de verificar novamente
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // Inicie a thread de tiro
        shotThread.start();
    }

    private void startSnakeThread(int index) {
        if (characters.get(index).isAlive()) {
            Thread snakeThread = new Thread() {
                public void run() {
                    while (characters.get(2).isAlive()) {
                        int moveX = 0;
                        int moveY = 0;
                        int direction = (int) (Math.random() * 4); 
                        switch (direction) {
                            case 0: // cima
                                moveX = -1;
                                break;
                            case 1: // baixo
                                moveX = 1;
                                break;
                            case 2: // esquerda
                                moveY = -1;
                                break;
                            case 3: // direita
                                moveY = 1;
                                break;
                        }
                        int newX = characters.get(2).getX() + moveX;
                        int newY = characters.get(2).getY() + moveY;
                        if (
                            newX >= 0 && newX < puzzle.getHeight()
                            && newY >= 0 
                            && newY < puzzle.getWidth() 
                            && puzzle.isWalkable(newX, newY)
                            && !isCharacterAtPosition(newX, newY)
                        ) {
                            characters.get(index).setX(newX);
                            characters.get(index).setY(newY);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        repaint();
                    }
                }
            };
            snakeThread.start();
        }
    }
    


    
    public int getCellSize(){
        return CELL_SIZE;
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
    private boolean isCharacterAtPosition(int x,int y){
        for(Character character: characters){
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

 

    public static void main(String[] args) {
        new Maze();
    }

	@Override
	public void run() {
	
	}

    private void moveShot() {
        
        switch (lastDirection) {
            case UP:
                shotX--;
                break;
            case DOWN:
                shotX++;
                break;
            case LEFT:
                shotY--;
                break;
            case RIGHT:
                shotY++;
                break;
            default:
                // Se o jogador não tiver se movido antes de disparar, o tiro não se move
                break;
        }
    }
    
    private void checkShotHit() {
        // Verifique se o tiro atingiu algum personagem (LumberJack, Snake, etc.)
        for (int i = 1; i < characters.size(); i++) { // Comece do índice 1 para evitar verificar o jogador
            Character character = characters.get(i);
            if (character.getX() == shotX && character.getY() == shotY) {
                characters.remove(i);
                shotX = -1;
                shotY = -1;
                shotThreadRunning = false;
                break; 
            }
        }
    }


    

}