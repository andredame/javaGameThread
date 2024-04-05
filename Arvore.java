import java.awt.Color;

public class Arvore implements ElementoMapa {
    private Color cor;
    private Character simbolo;

    public Arvore(Character simbolo, Color cor) {
        this.simbolo = simbolo;
        this.cor = cor;
    }

    @Override
    public Character getSimbolo() {
        return simbolo;
    }

    @Override
    public Color getColor() {
        return cor;
        
    }

    @Override
    public boolean isCaminhavel() {
        return false;
    }
    
    
}
