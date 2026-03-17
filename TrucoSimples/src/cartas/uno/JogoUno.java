package cartas.uno;

import cartas.framework.Carta;
import cartas.framework.Jogador;
import cartas.framework.JogoBase;

public class JogoUno extends JogoBase {
    Carta cartaNaMesa;
    public JogoUno(){
        // preenche com 4 jogadores
        super();
        for (int i = 0; i < 4; i++){
            this.jogadores.add(new Jogador(i));
        }

    }

    @Override
    protected void prepararBaralho(){
        this.baralho = new BaralhoUno();
    }

    @Override
    protected void distribuirCartas(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 7; j++){
                Jogador jogadorAtual = this.getJogador(i);
                Carta cartaPuxada = this.baralho.comprarCarta();
                jogadorAtual.getMao().adicionar(cartaPuxada);
            }
        }
    }

    @Override
    protected void iniciarPrimeiroTurno(){
        Carta cartaTopo = this.baralho.comprarCarta();

        while (cartaTopo.getClasse().equals("Preta")){
            // enquanto for uma carta "preta" (curinga ou +4), adiciona ela pro final do baralho
            this.baralho.adicionarCarta(cartaTopo);
            cartaTopo = this.baralho.comprarCarta();
        }
        this.cartaNaMesa = cartaTopo;
    }

    @Override
    public void jogarCarta(int jogadorId, int indiceCarta){

    }
}
