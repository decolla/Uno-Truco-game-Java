package cartas.uno;

import cartas.framework.Baralho;

public class BaralhoUno extends Baralho {
    public BaralhoUno() {
        this.preencherColoridas();
        this.preencherCuringas();
    }

    private void preencherColoridas() {
        String[] cores = new String[]{"Azul", "Vermelho", "Verde", "Amarelo"};

        for(String cor : cores) {
            this.adicionarCarta(new CartaUno("0", cor));

            for(int i = 0; i < 2; ++i) {
                for(int numero = 1; numero <= 9; ++numero) {
                    this.adicionarCarta(new CartaUno(String.valueOf(numero), cor));
                }

                this.adicionarCarta(new CartaUno("+2", cor));
                this.adicionarCarta(new CartaUno("Bloqueio", cor));
                this.adicionarCarta(new CartaUno("Inverter", cor));
            }
        }

    }

    private void preencherCuringas() {
        for(int i = 0; i < 4; ++i) {
            this.adicionarCarta(new CartaUno("+4", "Preta"));
            this.adicionarCarta(new CartaUno("Troca Cor", "Preta"));
        }

    }
}
