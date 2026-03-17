package cartas.truco;

import cartas.framework.Carta;

public class CartaTruco extends Carta {
    private final int forca; // armazena a força da carta

    public CartaTruco(String valor, String naipe, int forca) {
        super(valor, naipe); // usa o construtor da classe pai
        this.forca = forca;
    }

    public int getForca() { return forca; }

    // traduz o naipe para o simbolo
    private String getSimboloNaipe() {
        switch (getClasse()) {
            case Constants.COPAS: return Constants.SIMBOLO_COPAS;
            case Constants.OUROS: return Constants.SIMBOLO_OUROS;
            case Constants.ESPADAS: return Constants.SIMBOLO_ESPADAS;
            case Constants.PAUS: return Constants.SIMBOLO_PAUS;
            default: return getClasse(); // Fallback de segurança
        }
    }

    @Override
    public String toString() {
        // Substituí o " de " por um espaço simples para o botão não ficar com texto muito longo
        // Exemplo de saída: "4 ♣" ou "Ás ♠"
        return getValor() + " " + getSimboloNaipe();
    }
}
