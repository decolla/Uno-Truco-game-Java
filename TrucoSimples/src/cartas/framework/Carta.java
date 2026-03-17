package cartas.framework;

// a classe carta originalmente viola o princípio de reutilização, devido ter as regras de manilha do truco
// cravadas dento dela

import java.util.Objects;

// transformei em uma classe genérica simples
public abstract class Carta {
    private final String valor;
    private final String classe;

    public Carta(String valor, String classe) {
        this.valor = valor;
        this.classe = classe;
    }

    public String getValor() { return valor; }
    public String getClasse() { return classe; }

    // força as subclasses a implementarem como a carta aparece na tela
    @Override
    public abstract String toString();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carta carta = (Carta) o;
        return Objects.equals(valor, carta.valor) && Objects.equals(classe, carta.classe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor, classe);
    }


    // removi o getForça, as própias classes que herdarem carta devem implementar suas lógicas
}
