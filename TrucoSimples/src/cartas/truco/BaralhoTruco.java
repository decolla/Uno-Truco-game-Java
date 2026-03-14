package cartas.truco;

import cartas.framework.Baralho;

public class BaralhoTruco {

    public static void preencherBaralho(Baralho baralho) {
        String[] naipes = {Constants.OUROS, Constants.ESPADAS, Constants.COPAS, Constants.PAUS};
        String[] valores = {Constants.QUATRO, Constants.CINCO, Constants.SEIS, Constants.SETE,
                Constants.DAMA, Constants.VALETE, Constants.REI, Constants.AS,
                Constants.DOIS, Constants.TRES};

        // força de cada carta
        int[] forcas = {1,2,3,4,5,6,7,8,9,10};

        for (String naipe : naipes) {
            for(int i = 0; i < valores.length; i++){
                int forcaFinal = forcas[i];

                // manilhas
                if (valores[i].equals(Constants.QUATRO) && naipe.equals(Constants.PAUS)) forcaFinal = 14;
                if (valores[i].equals(Constants.SETE) && naipe.equals(Constants.COPAS)) forcaFinal = 13;
                if (valores[i].equals(Constants.AS) && naipe.equals(Constants.ESPADAS)) forcaFinal = 12;
                if (valores[i].equals(Constants.SETE) && naipe.equals(Constants.OUROS)) forcaFinal = 11;

                baralho.adicionarCarta(new CartaTruco(valores[i], naipe, forcaFinal));
            }
        }
    }
}
