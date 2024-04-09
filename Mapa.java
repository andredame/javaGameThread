

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Mapa implements Runnable{
    private char[][] puzzle;
        
    public char[][] getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(char[][] puzzle) {
        this.puzzle = puzzle;
    }

    public Mapa(){
        createPuzzle();
    }

    public char getLocation(int x, int y) {
        return puzzle[x][y];
    }
    public int getWidth() {
        return puzzle[0].length;
    }
    public int getHeight() {
        return puzzle.length;
    }
    public void setLocation(int x, int y, char c) {
        puzzle[x][y] = c;
    }

    private void createPuzzle(){
        String mazeTxt = "maze.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(mazeTxt));
            String line;
            int rows = 0;
            int cols = 0;
            
            while ((line = br.readLine()) != null) {
                rows++;
                cols = line.length();
            }
            
            br.close();
            
            br = new BufferedReader(new FileReader(mazeTxt));
            puzzle = new char[rows][cols];
            int currentRow = 0;
            while ((line = br.readLine()) != null) {
                for (int j = 0; j < cols; j++) {
                    puzzle[currentRow][j] = line.charAt(j);
                }
                currentRow++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void run() {
        
    }

 

}
