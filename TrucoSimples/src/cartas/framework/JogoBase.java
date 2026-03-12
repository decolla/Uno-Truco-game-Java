package cartas.framework;

import java.util.ArrayList;
import java.util.List;

/*
    PP: Template Method

    define o esqueleto de um jogo de cartas genérico e
    deixa as regras específicas (como distribuir cartas e validar jogadas) para as subclasses
 */
public abstract class JogoBase {

    protected List<Jogador> jogadores;
    protected Baralho baralho;
    protected int jogadorAtual; // indíce que simboliza jogador atual

    // lista de observadores
    private List<JogoListener> listeners;

    public JogoBase() {
        this.jogadores = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.jogadorAtual = 0;
    }

    // OBSERVERS

    public void adicionarListener(JogoListener listener) {
        this.listeners.add(listener);
    }

    protected void notificarEstadoAlterado() {
        for (JogoListener l : listeners) {
            l.onEstadoAlterado();
        }
    }

    protected void notificarMensagem(String msg) {
        for (JogoListener l : listeners) {
            l.onMensagem(msg);
        }
    }

    protected void notificarFimDeJogo(String msg) {
        for (JogoListener l : listeners) {
            l.onJogoTerminado(msg);
        }
    }

    public void removerListener(JogoListener listener) {
        listeners.remove(listener);
    }

    // TEMPLATE METHOD

    // esqueleto para iniciar qualquer jogo de cartas
    public final void iniciarJogo() {
        prepararBaralho();
        baralho.embaralhar();
        distribuirCartas();
        iniciarPrimeiroTurno();
        notificarEstadoAlterado();
    }

    // METODOS ABSTRATOS

    protected abstract void prepararBaralho();
    protected abstract void distribuirCartas();
    protected abstract void iniciarPrimeiroTurno();

    // valida e executa jogada
    public abstract void jogarCarta(int jogadorId, int indiceCarta);

    // RESTO

    public Baralho getBaralho() { return baralho; }

    public List<Jogador> getJogadores() { return jogadores; }

    public int getJogadorAtual() { return jogadorAtual; }

    public Jogador getJogador(int id) {
        // busca pelo id do jogador
        for(Jogador j : jogadores) {
            if(j.getId() == id) return j;
        }
        return null;
    }

}