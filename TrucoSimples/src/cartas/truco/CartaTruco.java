package cartas.truco;

import cartas.framework.Carta;

public class CartaTruco extends Carta {
    private final int forca; // armazena a força da carta

    public CartaTruco(String valor, String naipe, int forca) {
        super(valor, naipe); // usa o construtor da classe pai
        this.forca = forca;
    }

    public int getForca() { return forca; }

    @Override
    public String toString(){
        // como a carta deve aparecer na interface
        return getValor() + " de " + getNaipe();
    }
}
