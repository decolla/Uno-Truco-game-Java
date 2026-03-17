package cartas.uno;

import cartas.framework.Carta;

public class CartaUno extends Carta {
    public CartaUno(String valor, String cor) {
        super(valor, cor);
    }

    public String toString() {
        if (this.getClasse().equals("Preta")) {
            return this.getValor();
        } else {
            String var10000 = this.getValor();
            return var10000 + " " + this.getClasse();
        }
    }
}