package cartas.uno;

import cartas.framework.MesaVisual;

public class MesaUno extends MesaVisual {
    public MesaUno(JogoUno jogo){
        // passa para a superclasse
        super(jogo, "UNO");
    }

    @Override
    protected void atualizarTela(){

    }

    public static void main(String[] args){
        // inicia o jogo
        JogoUno jogo = new JogoUno();
        MesaUno tela = new MesaUno(jogo);
        jogo.iniciarJogo();
        tela.setVisible(true);
    }

}
