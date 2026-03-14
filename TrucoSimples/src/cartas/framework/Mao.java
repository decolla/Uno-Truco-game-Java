package cartas.framework;

import java.util.ArrayList;
import java.util.List;

// esta classe ja é genérica, pois já gerencia uma lista de cartas
public class Mao {

    // lista de cartas
    private final List<Carta> cartas;

    // cartas que estarão contidas na mão
    public Mao() {
        this.cartas = new ArrayList<>();
    }

    // adiciona uma carta a lista de cartas
    public void adicionar(Carta carta) {
        cartas.add(carta);
    }

    // remove uma carta da lista de cartas
    public Carta remover(int indice) {
        if (indice < 0 || indice >= cartas.size()) return null;
        return cartas.remove(indice);
    }

    // o indíce que caracteriza a carta
    public Carta get(int indice) {
        if (indice < 0 || indice >= cartas.size()) return null;
        return cartas.get(indice);
    }

    public int tamanho() {
        return cartas.size();
    }

    public boolean estaVazia() {
        return cartas.isEmpty();
    }

    public List<Carta> getCartas() {
        return new ArrayList<>(cartas);
    }
}
