package cartas.truco;

import cartas.framework.Baralho;
import cartas.framework.Carta;
import cartas.framework.Jogador;
import cartas.framework.JogoBase;

import javax.swing.*;
import java.awt.*;

public class JogoTrucoRegras extends JogoBase {

    private Dupla[] duplas;
    private Carta[] cartasNaMesa;
    private int estadoTruco;
    private int pontuacaoAtual;
    private boolean aguardandoRespostaTruco;
    private int jogadorQuePediuTruco;
    private int rodadaAtual;
    private int[] placarParcial = new int[2];
    private int cartasJogadasNaRodada;
    private int duplaQuePodeAumentar = -1; // -1 = ninguém; 0 ou 1 = dupla com direito de aumentar

    public JogoTrucoRegras() {
        super(); // chama o construtor da classe pai

        // inicializa as duplas
        duplas = new Dupla[]{new Dupla(0), new Dupla(1)};
        cartasNaMesa = new Carta[4];

        // cria os 4 jogadores e adiciona na lista do framework
        for (int i = 0; i < 4; i++){
            Jogador jogador = new Jogador(i);
            this.jogadores.add(jogador);

            // atribui o jogador a uma dupla
            duplas[i % 2].adicionarJogador(i);
        }
    }

    @Override
    protected void prepararBaralho() {
        this.baralho = new Baralho();
        // usa a factory para preencher com as 40 cartas do baralho
        BaralhoTruco.preencherBaralho(this.baralho);
    }

    @Override
    protected void distribuirCartas() {
        // limpa a mão antes de distribuir as cartas
        for (Jogador j : this.jogadores) j.getMao().getCartas().clear();

        // distribui 3 cartas por jogador
        for (int carta = 0; carta < 3; carta++) {
            for (Jogador j : jogadores) {
                Carta c = baralho.comprarCarta();
                if (c != null) j.getMao().adicionar(c);
            }
        }
    }

    @Override
    protected void iniciarPrimeiroTurno() {
        // limpa a mesa
        for (int i = 0; i < 4; i++) {
            cartasNaMesa[i] = null;
        }

        estadoTruco = Constants.TRUCO_NAO_TRUCADO;
        pontuacaoAtual = 1;
        aguardandoRespostaTruco = false;
        rodadaAtual = 0;
        cartasJogadasNaRodada = 0;
        placarParcial[0] = 0;
        placarParcial[1] = 0;
        duplaQuePodeAumentar = -1;
        this.jogadorAtual = 0; // framework herdou essa variável

    }

    @Override
    public void jogarCarta(int jogadorId, int indice) {
        // verifica se o jogador pode jogar
        if (jogadorId != jogadorAtual || aguardandoRespostaTruco) return;

        Jogador jogador = getJogador(jogadorId);
        if (indice >= jogador.getMao().tamanho()) return;

        // remove a carta da mão e coloca na mesa
        Carta carta = jogador.getMao().remover(indice);
        cartasNaMesa[jogadorId] = carta;
        cartasJogadasNaRodada++;

        // passar a vez
        jogadorAtual = (jogadorAtual + 1) % 4;

        // verifica se a rodada terminou
        if (cartasJogadasNaRodada == 4) finalizarRodada();

        // notifica os observadores
        notificarEstadoAlterado();
    }

    private void finalizarRodada() {
        // determina o vencedor da rodada
        int vencedor = determinarVencedorRodada();
        int duplaVencedora = getDuplaDoJogador(vencedor);
        placarParcial[duplaVencedora]++;

        // verifica se alguém ganhou a mão
        if (placarParcial[0] == 2 || placarParcial[1] == 2 ||
                (rodadaAtual == 2 && placarParcial[0] != placarParcial[1])) {

            // adiciona pontos com base no placar
            int duplaMao = placarParcial[0] > placarParcial[1] ? 0 : 1;
            duplas[duplaMao].adicionarPontos(pontuacaoAtual);

            // verifica se alguma dupla venceu
            if (duplas[0].getPontuacao() >= Constants.PONTOS_VITORIA ||
                    duplas[1].getPontuacao() >= Constants.PONTOS_VITORIA) {
                String msg = (duplas[0].getPontuacao() >= Constants.PONTOS_VITORIA ? "Dupla 0" : "Dupla 1") +
                                " VENCEU O JOGO!";
                notificarFimDeJogo(msg);
            }else{
                notificarMensagem("Mão terminada! Dupla " + duplaMao + " ganhou " + pontuacaoAtual + " ponto(s).");
                reiniciarMao();
            }
        } else {
            // limpar a mesa ao final da vazia
            for (int i = 0; i < 4; i++) {
                cartasNaMesa[i] = null; // limpa as cartas jogadas
            }
            rodadaAtual++;
            jogadorAtual = vencedor; // quen ganha, joga a primeira na próxima
            cartasJogadasNaRodada = 0;
        }
    }

    private int determinarVencedorRodada() {
        int vencedor = 0;
        // acessar a força da primeira carta na mesa
        int forcaMax = ((CartaTruco) cartasNaMesa[0]).getForca();

        // verificar as cartas restantes na mesa
        for (int i = 1; i < 4; i++) {
            if (cartasNaMesa[i] != null) {
                int forca = ((CartaTruco) cartasNaMesa[i]).getForca();
                if (forca > forcaMax) {
                    forcaMax = forca;
                    vencedor = i; // determina o i do vencedor
                }
            }
        }
        return vencedor;
    }

    private void reiniciarMao() {
        prepararBaralho();
        baralho.embaralhar();
        distribuirCartas();
        iniciarPrimeiroTurno();
        // a vez de quem começa a rodada
        jogadorAtual = (rodadaAtual + 1) % 4;
        notificarEstadoAlterado();
    }

    public void pedirTruco(int jogadorId) {
        if (aguardandoRespostaTruco || estadoTruco >= Constants.TRUCO_DOZE || cartasJogadasNaRodada >= 2)
            return;

        if (estadoTruco == Constants.TRUCO_NAO_TRUCADO) {
            estadoTruco = Constants.TRUCO_TRUCADO;
            pontuacaoAtual = 3;
            jogadorQuePediuTruco = jogadorId;
            aguardandoRespostaTruco = true;
            duplaQuePodeAumentar = getDuplaDoJogador(jogadorId); // verifica a dupla que pode aumentar
            notificarMensagem("Jogador " + jogadorId + " pediu TRUCO!");
            notificarEstadoAlterado();
        }
    }

    public void aceitarTruco(int jogadorId) {
        if (!ehDuplaAdversaria(jogadorId)) return;
        aguardandoRespostaTruco = false;
        duplaQuePodeAumentar = getDuplaDoJogador(jogadorId);
        notificarMensagem("Dupla " + getDuplaDoJogador(jogadorId) + " ACEITOU!");
        notificarEstadoAlterado();
    }

    public void correrTruco(int jogadorId) {
        if (!ehDuplaAdversaria(jogadorId)) return;
        int duplaPediu = getDuplaDoJogador(jogadorQuePediuTruco);
        int pontos = calcularPontosDesistencia();
        // passei para outra função o switch

        duplas[duplaPediu].adicionarPontos(pontos);
        notificarMensagem("Dupla correu! Dupla " + duplaPediu + " ganhou " + pontos + " ponto(s)!");

        if (duplas[duplaPediu].venceu()) {
            notificarFimDeJogo("Dupla " + duplaPediu + " VENCEU O JOGO!");
        } else {
            reiniciarMao();
        }
    }

    public void aumentarTruco(int jogadorId, int novoEstado, int novaPontuacao) {
        if (aguardandoRespostaTruco) return;

        // verifica se pode aumentar
        int estadoEsperado = obterEstadoAnterior(novoEstado);
        if (estadoEsperado == -1 || estadoTruco != estadoEsperado) return;
        if (getDuplaDoJogador(jogadorId) != duplaQuePodeAumentar) return;

        estadoTruco = novoEstado;
        pontuacaoAtual = novaPontuacao;
        jogadorQuePediuTruco = jogadorId;
        aguardandoRespostaTruco = true;
        duplaQuePodeAumentar = 1 - getDuplaDoJogador(jogadorId);

        String nomeAumento = novoEstado == Constants.TRUCO_SEIS ? "SEIS" : (novoEstado == Constants.TRUCO_NOVE ? "NOVE" : "DOZE");
        notificarMensagem("Jogador " + jogadorId + " pediu " + nomeAumento + "!");
        notificarEstadoAlterado();
    }

    private int getDuplaDoJogador(int jogadorId) {
        return duplas[0].contemJogador(jogadorId) ? 0 : 1;
    }

    private boolean ehDuplaAdversaria(int jogadorId) {
        return aguardandoRespostaTruco && getDuplaDoJogador(jogadorId) != getDuplaDoJogador(jogadorQuePediuTruco);
    }

    // retorna os pontos que a dupla correu
    private int calcularPontosDesistencia() {
        switch (estadoTruco) {
            case Constants.TRUCO_SEIS: return 3;
            case Constants.TRUCO_NOVE: return 6;
            case Constants.TRUCO_DOZE: return 9;
            default: return 1;
        }
    }

    // usado em caso de correr truco
    private int obterEstadoAnterior(int novoEstado) {
        if (novoEstado == Constants.TRUCO_SEIS) return Constants.TRUCO_TRUCADO;
        if (novoEstado == Constants.TRUCO_NOVE) return Constants.TRUCO_SEIS;
        if (novoEstado == Constants.TRUCO_DOZE) return Constants.TRUCO_NOVE;
        return -1;
    }

    // getters para a interface gráfica ler o estado
    public int getEstadoTruco() { return estadoTruco; }
    public boolean isAguardandoRespostaTruco() { return aguardandoRespostaTruco; }
    public int getJogadorQuePediuTruco() { return jogadorQuePediuTruco; }
    public Carta[] getCartasNaMesa() { return cartasNaMesa; }
    public Dupla[] getDuplas() { return duplas; }
    public int getRodadaAtual() { return rodadaAtual; }
    public int getCartasJogadasNaRodada() { return cartasJogadasNaRodada; }
    public int getDuplaQuePodeAumentar() { return duplaQuePodeAumentar; }
}
