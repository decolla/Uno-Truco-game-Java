package cartas.framework;

/*
    PP: Observer

    permite que o núcleo funcional do jogo se comunique
    com a interface sem depender dela.
    0 jogo é o "Subject" e a interface gráfica será o "Observer"
*/
public interface JogoListener {

    // é chamado sempre que algo visual deve ser atualizado
    void onEstadoAlterado();

    // exibe mensagens genéricas
    void onMensagem(String mensagem);

    // quando a condição de vitória é atingida
    void onJogoTerminado(String mensagemVitoria);
}