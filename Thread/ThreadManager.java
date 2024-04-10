package Thread;
import Elements.*;
import java.util.ArrayList;
import GUI.*;

public class ThreadManager {

    private ArrayList<Thread> threads;

    public ThreadManager() {
        this.threads = new ArrayList<>();
    }

    public void addThread(Thread thread) {
        threads.add(thread);
    }

    public void removeThread(Thread thread) {
        threads.remove(thread);
    }

    public ArrayList<Thread> getThreads() {
        return threads;
    }
    public void initiateThreads(ArrayList <GameCharacter> characters,Maze maze) {
        for (GameCharacter character : characters) {
           if(!(character instanceof Player)){
                CharacterThread thread = new CharacterThread(maze,character, character.getPuzzle(),character.getId());
                addThread(thread);
           }
        }
    }

    public void startAllThreads() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void stopAllThreads() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}