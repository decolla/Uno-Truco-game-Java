package cartas.framework;

import java.util.*;

// um baralho genérico não deve saber quais cartas sua implementação utiliza
public class Baralho {
    private final List<Carta> cartas;

    public Baralho() {
        this.cartas = new ArrayList<>();
    }

    // a responsabilidade de preencher a listaq de cartas será passada por uma Factory de cada jogo

    public void embaralhar() {
        Collections.shuffle(cartas, new Random());
    }

    public Carta comprarCarta() {
        if (cartas.isEmpty()) return null;
        return cartas.remove(cartas.size() - 1);
    }

    // metodo para inserir cartas
    public void adicionarCarta(Carta carta) {
        this.cartas.add(carta);
    }

    public boolean isEmpty() {
        return cartas.isEmpty();
    }

    public List<Carta> getCartas() {
        return new ArrayList<>(cartas);
    }
}
