package cartas.truco;

// Dupla.java
public class Dupla {
    private final int id;
    private int pontuacao;
    private int qtdJogadores = 0;

    // a classe de dupla deve ser responsável por guardar os Ids dos jogadores pertecentes
    private final int[] idsJogadores = new int[2];

    public Dupla(int id) {
        this.id = id;
        this.pontuacao = 0;
    }

    // adiciona jogador na dupla
    public void adicionarJogador(int jogadorId) {
        if (qtdJogadores < 2) {
            idsJogadores[qtdJogadores] = jogadorId;
            qtdJogadores++;
        }
    }

    // verifica se a dupla cotém um jogador pelo id
    public boolean contemJogador(int jogadorId) {
        return idsJogadores[0] == jogadorId || idsJogadores[1] == jogadorId;
    }

    public int getId() { return id; }
    public int getPontuacao() { return pontuacao; }
    public void adicionarPontos(int pontos) { pontuacao += pontos; }
    public boolean venceu() { return pontuacao >= Constants.PONTOS_VITORIA; }
}