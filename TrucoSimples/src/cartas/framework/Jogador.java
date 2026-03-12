package cartas.framework;

// classe de jogador
public class Jogador {
    private final int id;
    private final Mao mao;
    // Uno não contém duplas igual truco, ficará restrita ao jogo que implementará

    public Jogador(int id) {
        this.id = id;
        this.mao = new Mao();
    }

    public int getId() { return id; }
    public Mao getMao() { return mao; }
}
