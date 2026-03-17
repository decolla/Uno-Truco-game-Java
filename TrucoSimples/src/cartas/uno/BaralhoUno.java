package cartas.uno;


import cartas.framework.Baralho;

public class BaralhoUno extends Baralho{
    public BaralhoUno(){
        preencherColoridas();
        preencherCuringas();
    }

    private void preencherColoridas(){
        String[] cores = {"Azul","Vermelho","Verde","Amarelo"};

        for (String cor : cores) {
            // adiciona um único 0 de cada cor ao baralho
            this.adicionarCarta(new CartaUno("0", cor));

            // repete duas vezes para as outras
            for (int i = 0; i < 2; i++) {
                // for para simplificar a inserção de 1 a 9
                for (int numero = 1; numero <= 9; numero++) {
                    // transforma o valor inteiro em uma string
                    this.adicionarCarta(new CartaUno(String.valueOf(numero), cor));
                }
                // adiciona as especiais
                this.adicionarCarta(new CartaUno("+2", cor));
                this.adicionarCarta(new CartaUno("Bloqueio", cor));
                this.adicionarCarta(new CartaUno("Inverter", cor));
            }
        }
    }

    private void preencherCuringas(){
        for(int i = 0; i < 4; i++){
            this.adicionarCarta(new CartaUno("+4","Preta"));
            this.adicionarCarta(new CartaUno("Troca Cor", "Preta"));
        }
    }
}
