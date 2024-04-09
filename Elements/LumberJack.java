package Elements;

public class LumberJack extends Character implements Runnable {
    public LumberJack(int x, int y,Puzzle puzzle){
        super(x, y, 1, 'X', puzzle);
    }

    @Override
    public void run() {
        while(true) {
            // Adicione a lógica de movimento contínuo do LumberJack aqui
            // Por exemplo, mova o LumberJack para uma posição aleatória a cada intervalo de tempo
            int newX = getX() + (int) (Math.random() * 3) - 1; // Movimento aleatório na vertical
            int newY = getY() + (int) (Math.random() * 3) - 1; // Movimento aleatório na horizontal

            // Verifique se a nova posição é válida
            if (isValidMove(newX, newY)) {
                setX(newX);
                setY(newY);
            }
            .repaint();


            // Aguarde um intervalo de tempo antes do próximo movimento
            try {
                Thread.sleep(1000); // Aguarde 1 segundo entre os movimentos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidMove(int x, int y) {
        if (x < 0 || x >= super.getPuzzle().getHeight() || y < 0 || y >= super.getPuzzle().getWidth()) {
            return false;
        }
        if (super.getPuzzle().getLocation(x, y) == 'X') {
            return false; 
        }
        return true;
    }

}
