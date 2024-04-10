package Thread;
import Elements.*;
import java.util.HashMap;
import java.util.Map;
import GUI.*;

public class ThreadManager {

    private Map<Integer,Thread> threads;

    public Map<Integer, Thread> getThreads() {
        return threads;
    }

    public void setThreads(Map<Integer, Thread> threads) {
        this.threads = threads;
    }

    public ThreadManager() {
        this.threads = new HashMap<Integer,Thread>() ;
    }

    public void addThread(CharacterThread thread) {
        threads.put(thread.getIdentificador(),thread);
    }

    public void initiateThreads() {
        for (Thread thread : threads.values()) {
            thread.start();
        }
    }

    public void startAllThreads() {
        for (Thread thread : threads.values()) {
            thread.start();
        }
    }

    public void stopAllThreads() {
        for (Thread thread : threads.values()) {
            thread.interrupt();
        }
    }

    public boolean isCharacterAtPosition(int x, int y) {
        for (Thread thread : threads.values()) {
            GameCharacter character = ((CharacterThread) thread).getCharacter();
            if (character.getX() == x && character.getY() == y) {
                return true;
            }
        }
        return false;
    }
    



}   