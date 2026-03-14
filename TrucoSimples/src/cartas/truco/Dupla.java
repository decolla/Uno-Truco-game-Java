package cartas.truco;

// Dupla.java
public class Dupla {
    private final int id;
    private int pontuacao;
    // a classe de dupla deve ser responsável por guardar os Ids dos jogadores pertecentes
    private final int[] idsJogadores = new int[2];

    public Dupla(int id) {
        this.id = id;
        this.pontuacao = 0;
    }

    public int getId() { return id; }
    public int getPontuacao() { return pontuacao; }
    public void adicionarPontos(int pontos) { pontuacao += pontos; }
    public boolean venceu() { return pontuacao >= Constants.PONTOS_VITORIA; }
}