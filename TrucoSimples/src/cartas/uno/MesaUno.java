package cartas.uno;

import cartas.framework.Carta;
import cartas.framework.Jogador;
import cartas.framework.MesaVisual;
import javax.swing.*;
import java.awt.*;

public class MesaUno extends MesaVisual {
    public MesaUno(JogoUno jogo){
        // passa para a superclasse
        super(jogo, "UNO");
    }

    @Override
    protected void atualizarTela(){
        // limpa as cartas antigas
        this.areaSul.removeAll();
        this.areaNorte.removeAll();
        this.areaLeste.removeAll();
        this.areaOeste.removeAll();
        this.campo.removeAll();

        // restaura a cor padrão para os painéis dos jogadores
        this.areaSul.setBackground(Color.darkGray);
        this.areaNorte.setBackground(Color.darkGray);
        this.areaLeste.setBackground(Color.darkGray);
        this.areaOeste.setBackground(Color.darkGray);

        // typecast do jogo genérico para o jogo de UNO
        JogoUno meuJogo = (JogoUno) this.jogo;


        // destaca o jogador atual mudando a cor do painel
        int vez = meuJogo.getJogadorAtual();
        if (vez == 0){
            this.areaSul.setBackground(Color.lightGray);
        }
        else if (vez == 1){
            this.areaOeste.setBackground(Color.lightGray);
        }

        else if (vez == 2){
            this.areaNorte.setBackground(Color.lightGray);
        }

        else if (vez == 3){
            this.areaLeste.setBackground(Color.lightGray);
        }

        CartaUno cartaCentro = (CartaUno) meuJogo.getCartaNaMesa();

        // botão que representa a última carta jogada no campo
        JButton botaoCentro = new JButton(cartaCentro.getValor());
        botaoCentro.setPreferredSize(new Dimension(80,120));
        pintarBotao(botaoCentro, cartaCentro.getClasse());
        this.campo.add(botaoCentro);

        // botão para comprar
        JButton botaoComprar = new JButton("Comprar");
        botaoComprar.setPreferredSize(new Dimension(100, 50));
        botaoComprar.setBackground(Color.white);
        botaoComprar.addActionListener(e->{
            meuJogo.comprarCarta(vez);
        });
        this.campo.add(botaoComprar);

        // laço para desenhar as mãos dos 4 jogadores
        for (int i = 0; i < 4; i++){
            Jogador jogadorAtual = meuJogo.getJogador(i);

            // criei uma variável para armazenar o id da carta
            int j = 0;

            // percorre a mão do jogador de índice "i" e desenha suas cartas
            for (Carta c : jogadorAtual.getMao().getCartas()){
                JButton botaoCarta = new JButton(c.getValor());

                int idJogador = i;
                int idCarta = j;

                // adicionar listener para cada carta da mão
                botaoCarta.addActionListener(e->{
                    this.jogo.jogarCarta(idJogador, idCarta);
                });

                pintarBotao(botaoCarta, c.getClasse());

                // jogador sul (eu)
                if (i == 0){
                    botaoCarta.setPreferredSize(new Dimension(80, 120));
                    this.areaSul.add(botaoCarta);
                }

                // jogador oeste (próximo no sentido horário
                else if (i == 1){
                    botaoCarta.setPreferredSize(new Dimension(120, 80));
                    this.areaOeste.add(botaoCarta);
                }

                else if (i == 2){
                    botaoCarta.setPreferredSize(new Dimension(80, 120));
                    this.areaNorte.add(botaoCarta);
                }

                else if (i == 3){
                    botaoCarta.setPreferredSize(new Dimension(120, 80));
                    this.areaLeste.add(botaoCarta);
                }

                j++;
            }
        }

        // redesenha a mesa atualizada
        this.revalidate();
        this.repaint();
    }

    private void pintarBotao(JButton botao, String corCarta){
        switch (corCarta){
            case "Vermelho":
                botao.setBackground(Color.red);
                break;

            case "Azul":
                botao.setBackground(Color.blue);
                break;

            case "Verde":
                botao.setBackground(Color.green);
                break;

            case "Amarelo":
                botao.setBackground(Color.yellow);
                break;

            case "Preta":
                botao.setBackground(Color.black);
                // mudei a fonte para branco para conseguir enxergar na carta preta
                botao.setForeground(Color.white);
                break;
        }
    }

    public static void main(String[] args){
        // inicia o jogo
        JogoUno jogo = new JogoUno();
        MesaUno tela = new MesaUno(jogo);
        jogo.iniciarJogo();
        tela.setVisible(true);
    }

}