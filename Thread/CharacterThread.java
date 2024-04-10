package Thread;
import Elements.GameCharacter;
import Elements.Puzzle;
import GUI.Maze;
public class CharacterThread extends Thread {
    private GameCharacter character;


    private Puzzle puzzle;
    private Maze maze;
    private int identificador;

    public CharacterThread(Maze maze,GameCharacter character, Puzzle puzzle,int id) {
        this.character = character;
        this.puzzle = puzzle;
        this.maze = maze;
        this.identificador = id;
    }

    public int getIdentificador() {
        return identificador;
    }

    public GameCharacter getCharacter() {
        return character;
    }

    public void setCharacter(GameCharacter character) {
        this.character = character;
    }

    @Override
    public void run() {
        while (character.isAlive()) {
            int moveX = 0;
            int moveY = 0;
            int direction = (int) (Math.random() * 4); 
            switch (direction) {
                case 0: // cima
                    moveX = -1;
                    break;
                case 1:
                    moveX = 1;
                    break;
                case 2: // esquerda
                    moveY = -1;
                    break;
                case 3: // direita
                    moveY = 1;
                    break;
            }
            int newX = character.getX() + moveX;
            int newY = character.getY() + moveY;
            if (
                newX >= 0 && newX < puzzle.getHeight()
                && newY >= 0 
                && newY < puzzle.getWidth() 
                && puzzle.isWalkable(newX, newY)
                && !isCharacterAtPosition(newX, newY)
            ) {
                character.setX(newX);
                character.setY(newY);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            maze.repaint();
            if(character.isAlive() == false){
                this.interrupt();
            }
        }
    }

    private boolean isCharacterAtPosition(int x,int y) {
        return false;
    }

}
