package cartas.framework;

import javax.swing.*;
import java.awt.*;

// classe abstrata que serve de base para as mesas específicas
public abstract class MesaVisual extends JFrame implements JogoListener {

    protected JogoBase jogo;

    public MesaVisual(JogoBase jogo, String titulo){
        // qual jogo foi escolhido
        this.jogo = jogo;

        // configuração inicial da janela
        this.setTitle(titulo);
        this.setSize(1000,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());


        // campo onde as cartas serão jogadas
        JPanel campo = new JPanel();
        campo.setBackground(new Color(40, 100, 60));
        this.add(campo, BorderLayout.CENTER);

        // local do jogador norte
        JPanel areaNorte = new JPanel();
        areaNorte.setBackground(Color.darkGray);
        areaNorte.setPreferredSize(new Dimension(1000, 200));
        this.add(areaNorte, BorderLayout.NORTH);

        // local do jogador sul
        JPanel areaSul = new JPanel();
        areaSul.setBackground(Color.darkGray);
        areaSul.setPreferredSize(new Dimension(1000, 200));
        this.add(areaSul, BorderLayout.SOUTH);

        // local do jogador leste
        JPanel areaLeste = new JPanel();
        areaLeste.setPreferredSize(new Dimension(200, 800));
        areaLeste.setBackground(Color.darkGray);
        this.add(areaLeste, BorderLayout.EAST);

        // local do jogador oeste
        JPanel areaOeste = new JPanel();
        areaOeste.setPreferredSize(new Dimension(200, 800));
        areaOeste.setBackground(Color.darkGray);
        this.add(areaOeste, BorderLayout.WEST);

        // adiciona a instância da classe como listener do jogo
        this.jogo.adicionarListener(this);
    }

    @Override
    // sempre que uma ação ocorrer, a tela será atualizada
   public void onEstadoAlterado(){
        atualizarTela();
    }

    @Override

    public void onMensagem(String mensagem){
        JOptionPane.showMessageDialog(this,mensagem);
    }

    @Override
    public void onJogoTerminado(String mensagemVitoria){
        JOptionPane.showMessageDialog(this,"Fim de jogo! " + mensagemVitoria);

    }

    // obriga as subclasses a implementarem a atualização específica do visual
    protected abstract void atualizarTela();

}

