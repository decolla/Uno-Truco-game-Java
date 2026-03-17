package cartas.uno;

import cartas.framework.Carta;
import cartas.framework.Jogador;
import cartas.framework.JogoBase;

public class JogoUno extends JogoBase {
    // última carta jogada
    private Carta cartaNaMesa;
    // 1 para horário, -1 para anti-horário
    private int sentidoJogo = 1;

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
        if(jogadorId != jogadorAtual){
            notificarMensagem("Não é a vez do jogador " + jogadorId + "!");
            return;
        }

        // variável para armazenar o jogador do índice atual
        Jogador jogador = getJogador(jogadorId);

        // variável para armazenar a carta escolhida
        CartaUno cartaEscolhida = (CartaUno) jogador.getMao().get(indiceCarta);

        // regra para verificar se a carta escolhida pode ser jogada
        if (
                cartaEscolhida.getClasse().equals(this.cartaNaMesa.getClasse()) ||
                        cartaEscolhida.getValor().equals(this.cartaNaMesa.getValor()) ||
                        cartaEscolhida.getClasse().equals("Preta")){


            jogador.getMao().remover(indiceCarta);
            this.cartaNaMesa = cartaEscolhida;

            // verificar condição de vitória
            if (jogador.getMao().estaVazia()){
                notificarFimDeJogo(" O Jogador " + jogadorId + " venceu a partida!");
                // encerra a execução
                return;
            }

            if (cartaEscolhida.getClasse().equals("Preta")){
                String[] opcoes = {"Vermelho", "Azul", "Verde", "Amarelo"};
                // janelinha que permite a escolha de cores
                int escolha = javax.swing.JOptionPane.showOptionDialog(
                        null, "Escolha a nova cor:", "Trocar Cor",
                        javax.swing.JOptionPane.DEFAULT_OPTION,
                        javax.swing.JOptionPane.QUESTION_MESSAGE,
                        null, opcoes, opcoes[0]
                );

                String novaCor = (escolha == -1) ? "Vermelho" : opcoes[escolha];

                this.cartaNaMesa = new CartaUno(cartaEscolhida.getValor(), novaCor);
            }

            // efeitos das cartas especiais
            if (cartaEscolhida.getValor().equals("Inverter")){
                if (this.sentidoJogo == 1){
                    this.sentidoJogo = -1;
                }
                else{
                    this.sentidoJogo = 1;
                }
            }

            if (cartaEscolhida.getValor().equals("Bloqueio")){
                // o bloqueio é basicamente uma execução extra da operação de percorrer o sentido do jogo
                this.jogadorAtual = (this.jogadorAtual + this.sentidoJogo + 4) % 4;
            }

            if (cartaEscolhida.getValor().equals("+2")){
                // passa para o atual para o próximo, que vai comprar 2 cartas e perder a vez
                this.jogadorAtual = (this.jogadorAtual + this.sentidoJogo + 4) % 4;
                Jogador vitima = getJogador(jogadorAtual);
                for (int i = 0; i < 2; i++){
                    vitima.getMao().adicionar(this.baralho.comprarCarta());
                }
            }

            if (cartaEscolhida.getValor().equals("+4")){
                // passa o atual para o próximo, que vai comprar 4 cartas e perder a vez

                this.jogadorAtual = (this.jogadorAtual + this.sentidoJogo + 4) % 4;
                Jogador vitima = getJogador(jogadorAtual);
                for (int i = 0; i < 4; i++){
                    vitima.getMao().adicionar(this.baralho.comprarCarta());
                }
            }

            // operação para percorrer no sentido atual. o +4 evita números negativos no módulo
            this.jogadorAtual = (this.jogadorAtual + this.sentidoJogo + 4) % 4;

            // atualizar o visual
            notificarEstadoAlterado();
        }
        else{
            notificarMensagem("Jogada inválida!");
        }
    }

    public void comprarCarta(int jogadorId){
        if(jogadorId != jogadorAtual){
            notificarMensagem("Não é a vez do jogador " + jogadorId + "!");
            return;
        }

        Jogador jogador = getJogador(jogadorId);

        // adiciona uma carta do baralho para o jogador
        jogador.getMao().adicionar(this.baralho.comprarCarta());

        // mesma lógica para passar a vez
        this.jogadorAtual = (this.jogadorAtual + this.sentidoJogo + 4) % 4;
        notificarEstadoAlterado();
    }

    public Carta getCartaNaMesa(){
        return this.cartaNaMesa;
    }
}