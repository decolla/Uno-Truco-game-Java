package cartas.truco;

import cartas.framework.Carta;
import cartas.framework.Jogador;
import cartas.framework.JogoBase;

import javax.swing.*;

public class JogoTrucoRegras extends JogoBase {


    // GUI Components
    private JButton[][] botoesCartas = new JButton[4][3];
    private JButton[] btnTruco = new JButton[4];
    private JButton[] btnAceitar = new JButton[4];
    private JButton[] btnCorrer = new JButton[4];
    private JButton[] btnSeis = new JButton[4];
    private JButton[] btnNove = new JButton[4];
    private JButton[] btnDoze = new JButton[4];
    private JLabel[] labelsMesa = new JLabel[4];

    // Painel de Pontos
    private JLabel lblEstado;
    private JLabel lblPontuacao;

    // Game State
    private Jogador[] jogadores;
    private Dupla[] duplas;
    private Carta[] cartasNaMesa;
    private int jogadorAtual;
    private int estadoTruco;
    private int pontuacaoAtual;
    private boolean aguardandoRespostaTruco;
    private int jogadorQuePediuTruco;
    private int rodadaAtual;
    private int[] placarParcial = new int[2];
    private int cartasJogadasNaRodada;
    private int duplaQuePodeAumentar = -1; // -1 = ninguém; 0 ou 1 = dupla com direito de aumentar

    public JogoTrucoRegras() {}
}
