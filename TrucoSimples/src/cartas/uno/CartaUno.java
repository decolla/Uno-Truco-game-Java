package cartas.uno;

import cartas.framework.Carta;

public class CartaUno extends Carta{
    // passa os dados para a classe pai
    public CartaUno(String valor, String cor){
        super(valor, cor);
    }

    @Override
    public String toString(){
        // se for carta curinga (+4 ou troca de cor), não precisamos da cor
        if (this.getClasse().equals("Preta")){
            return this.getValor();
        }
        // para o resto, retorna o valor e a cor
        else{
            return this.getValor() + " " + this.getClasse();
        }
    }
}
