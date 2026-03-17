package cartas.framework;

import java.util.*;

// um baralho genérico não deve saber quais cartas sua implementação utiliza
public class Baralho {
    private final List<Carta> cartas;

    // baralho vazio
    public Baralho() {
        this.cartas = new ArrayList<>();
    }

    // a responsabilidade de preencher a lista de cartas será passada por uma Factory de cada jogo

    // embaralhar as cartas do baralho
    public void embaralhar() {
        Collections.shuffle(cartas, new Random());
    }

    // comprar uma carta do baralho
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
