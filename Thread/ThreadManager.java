package Thread;
import Elements.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Thread.CharacterThread;

import GUI.*;

public class ThreadManager {

    private Map<Integer,Thread> threads;

    public ThreadManager() {
        this.threads = new HashMap<Integer,Thread>() ;
    }

    public void addThread(CharacterThread thread) {
        threads.put(thread.getIdentificador(),thread);
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
        for (Thread thread : threads.values()) {
            thread.start();
        }
    }

    public void stopAllThreads() {
        for (Thread thread : threads.values()) {
            thread.interrupt();
        }
    }

}