package cartas.truco;

import cartas.framework.Carta;
import cartas.framework.Jogador;
import cartas.framework.JogoListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class JogoTrucoGUI extends JFrame implements JogoListener {

    private JButton[][] botoesCartas = new JButton[4][3];
    private JButton[] btnTruco = new JButton[4];
    private JButton[] btnAceitar = new JButton[4];
    private JButton[] btnCorrer = new JButton[4];
    private JButton[] btnSeis = new JButton[4];
    private JButton[] btnNove = new JButton[4];
    private JButton[] btnDoze = new JButton[4];
    private JButton[] btnBaterMesa = new JButton[4];
    private JLabel[] labelsMesa = new JLabel[4];
    private JLabel lblEstado;
    private JLabel lblPontuacao;

    // referência para as regras do jogo (framework)
    private JogoTrucoRegras jogo;

    public JogoTrucoGUI() {
        setTitle("Truco");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        jogo = new JogoTrucoRegras();

        // a interface é notificada se algo for mudado (padrão observer)
        jogo.adicionarListener(this);

        criarPainelPontos();
        criarPainelJogo();

        // o motor começa a rodar aqui (Template Method)
        jogo.iniciarJogo();

        setSize(1000, 800);
        setLocationRelativeTo(null);
    }

    private void criarPainelPontos() {
        JPanel painel = new JPanel(new GridLayout(2, 1, 5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painel.setBackground(new Color(240, 240, 240));

        lblEstado = new JLabel("Iniciando jogo...", JLabel.CENTER);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 18));
        lblEstado.setOpaque(true);
        lblEstado.setBackground(Color.CYAN);
        lblEstado.setBorder(BorderFactory.createRaisedBevelBorder());

        lblPontuacao = new JLabel("Dupla 0: 0   |   Dupla 1: 0", JLabel.CENTER);
        lblPontuacao.setFont(new Font("Arial", Font.BOLD, 16));
        lblPontuacao.setOpaque(true);
        lblPontuacao.setBackground(Color.YELLOW);
        lblPontuacao.setBorder(BorderFactory.createRaisedBevelBorder());

        painel.add(lblEstado);
        painel.add(lblPontuacao);
        add(painel, BorderLayout.NORTH);
    }

    private void criarPainelJogo() {
        JPanel painelCentral = new JPanel(new BorderLayout());

        JPanel painelMesa = new JPanel(new GridLayout(2, 2, 15, 15));
        painelMesa.setBorder(BorderFactory.createTitledBorder("Mesa de Jogo"));
        for (int i = 0; i < 4; i++) {
            labelsMesa[i] = new JLabel("Jogador " + i, JLabel.CENTER);
            labelsMesa[i].setOpaque(true);
            labelsMesa[i].setBackground(Color.WHITE);
            labelsMesa[i].setBorder(BorderFactory.createEtchedBorder());
            labelsMesa[i].setFont(new Font("Arial", Font.BOLD, 16));
            painelMesa.add(labelsMesa[i]);
        }
        painelCentral.add(painelMesa, BorderLayout.CENTER);

        painelCentral.add(criarPainelJogador(0), BorderLayout.SOUTH);
        painelCentral.add(criarPainelJogador(2), BorderLayout.NORTH);
        painelCentral.add(criarPainelJogador(1), BorderLayout.EAST);
        painelCentral.add(criarPainelJogador(3), BorderLayout.WEST);

        add(painelCentral, BorderLayout.CENTER);
    }

    private JPanel criarPainelJogador(int jogadorId) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Jogador " + jogadorId));

        JPanel painelCartas = new JPanel(new FlowLayout());
        for (int i = 0; i < 3; i++) {
            JButton btn = new JButton("Carta " + (i + 1));
            btn.setPreferredSize(new Dimension(110, 100)); // Um pouco maior para caber o texto
            btn.setFont(new Font("Arial", Font.BOLD, 11));
            int idx = i;

            // Aqui a GUI repassa a ação do clique para o núcleo do jogo
            btn.addActionListener(e -> jogo.jogarCarta(jogadorId, idx));
            botoesCartas[jogadorId][i] = btn;
            painelCartas.add(btn);
        }

        JPanel painelTruco = new JPanel(new FlowLayout());
        btnTruco[jogadorId] = criarBotao("Truco", e -> jogo.pedirTruco(jogadorId));
        btnAceitar[jogadorId] = criarBotao("Aceitar", e -> jogo.aceitarTruco(jogadorId));
        btnCorrer[jogadorId] = criarBotao("Correr", e -> jogo.correrTruco(jogadorId));
        btnSeis[jogadorId] = criarBotao("Seis", e -> jogo.aumentarTruco(jogadorId, Constants.TRUCO_SEIS, 6));
        btnNove[jogadorId] = criarBotao("Nove", e -> jogo.aumentarTruco(jogadorId, Constants.TRUCO_NOVE, 9));
        btnDoze[jogadorId] = criarBotao("Doze", e -> jogo.aumentarTruco(jogadorId, Constants.TRUCO_DOZE, 12));
        // botão secreto
        btnBaterMesa[jogadorId] = criarBotao("Bater na mesa", e -> {
            JOptionPane.showMessageDialog(this, "Jogador " + jogadorId + " bateu na mesa! POW! 💥");
        });
        btnBaterMesa[jogadorId].setPreferredSize(new Dimension(95, 25));

        painelTruco.add(btnTruco[jogadorId]);
        painelTruco.add(btnAceitar[jogadorId]);
        painelTruco.add(btnCorrer[jogadorId]);
        painelTruco.add(btnSeis[jogadorId]);
        painelTruco.add(btnNove[jogadorId]);
        painelTruco.add(btnDoze[jogadorId]);
        painelTruco.add(btnBaterMesa[jogadorId]);

        painel.add(painelCartas, BorderLayout.NORTH);
        painel.add(painelTruco, BorderLayout.SOUTH);
        return painel;
    }

    private JButton criarBotao(String texto, ActionListener acao) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.PLAIN, 9));
        btn.setPreferredSize(new Dimension(60, 25));
        btn.addActionListener(acao);
        return btn;
    }

    // LISTENER

    @Override
    public void onEstadoAlterado() {
        atualizarInterface();
    }

    @Override
    public void onMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    @Override
    public void onJogoTerminado(String mensagemVitoria) {
        JOptionPane.showMessageDialog(this, mensagemVitoria);
        System.exit(0);
    }

    // ATUALIAÇÃO VISUAL

    private void atualizarInterface() {
        Carta[] mesa = jogo.getCartasNaMesa();
        for (int i = 0; i < 4; i++) {
            if (mesa[i] != null) {
                labelsMesa[i].setText("J" + i + ": " + mesa[i].toString());
                labelsMesa[i].setBackground(new Color(220, 220, 255));
            } else {
                labelsMesa[i].setText("Jogador " + i);
                labelsMesa[i].setBackground(Color.WHITE);
            }
        }

        for (int j = 0; j < 4; j++) {
            Jogador jogador = jogo.getJogador(j);
            for (int i = 0; i < 3; i++) {
                if (i < jogador.getMao().tamanho()) {
                    botoesCartas[j][i].setText(jogador.getMao().get(i).toString());
                    botoesCartas[j][i].setEnabled(j == jogo.getJogadorAtual() && !jogo.isAguardandoRespostaTruco());
                } else {
                    botoesCartas[j][i].setText("");
                    botoesCartas[j][i].setEnabled(false);
                }
            }
            atualizarBotoesTruco(j);
        }

        String estado = "Rodada " + (jogo.getRodadaAtual() + 1) + " — Vez do Jogador " + jogo.getJogadorAtual();
        if (jogo.isAguardandoRespostaTruco()) {
            int duplaResponde = jogo.getDuplas()[0].contemJogador(jogo.getJogadorQuePediuTruco()) ? 1 : 0;
            estado += " — Dupla " + duplaResponde + " responde ao TRUCO!";
        }
        lblEstado.setText(estado);

        Dupla[] duplas = jogo.getDuplas();
        lblPontuacao.setText(String.format("Dupla 0: %d   |   Dupla 1: %d",
                duplas[0].getPontuacao(), duplas[1].getPontuacao()));
    }

    // atualiza os botões com base no estado atual do jogo
    private void atualizarBotoesTruco(int jogadorId) {
        int minhaDupla = jogo.getDuplas()[0].contemJogador(jogadorId) ? 0 : 1;
        int duplaPediu = jogo.getDuplas()[0].contemJogador(jogo.getJogadorQuePediuTruco()) ? 0 : 1;

        boolean possoTrucarPrimeiraVez = (jogo.getEstadoTruco() == Constants.TRUCO_NAO_TRUCADO) &&
                !jogo.isAguardandoRespostaTruco() &&
                jogo.getCartasJogadasNaRodada() < 2;
        boolean souDuplaAdversaria = jogo.isAguardandoRespostaTruco() && minhaDupla != duplaPediu;
        boolean possoAumentar = !jogo.isAguardandoRespostaTruco() && minhaDupla == jogo.getDuplaQuePodeAumentar();

        btnTruco[jogadorId].setEnabled(possoTrucarPrimeiraVez);
        btnAceitar[jogadorId].setEnabled(souDuplaAdversaria);
        btnCorrer[jogadorId].setEnabled(souDuplaAdversaria);
        btnSeis[jogadorId].setEnabled(possoAumentar && jogo.getEstadoTruco() == Constants.TRUCO_TRUCADO);
        btnNove[jogadorId].setEnabled(possoAumentar && jogo.getEstadoTruco() == Constants.TRUCO_SEIS);
        btnDoze[jogadorId].setEnabled(possoAumentar && jogo.getEstadoTruco() == Constants.TRUCO_NOVE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JogoTrucoGUI().setVisible(true));
    }
}