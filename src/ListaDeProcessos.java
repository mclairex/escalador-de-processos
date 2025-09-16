public class ListaDeProcessos {
    private No primeiro;
    private No ultimo;
    private int tamanho; // mantém o contador atualizado (O(1) para getTamanho)

    // Classe interna Nó (representa cada elemento da lista)
    private static class No {
        Processo processo;
        No proximo;

        No(Processo processo) {
            this.processo = processo;
            this.proximo = null;
        }
    }

    // Construtor
    public ListaDeProcessos() {
        this.primeiro = null;
        this.ultimo = null;
        this.tamanho = 0;
    }

    // Adiciona no final da lista (para simular fila)
    public void adicionarNoFinal(Processo processo) {
        No novo = new No(processo);
        if (estaVazia()) {
            primeiro = novo;
            ultimo = novo;
        } else {
            ultimo.proximo = novo;
            ultimo = novo;
        }
        tamanho++;
    }

    // Remove e retorna o primeiro processo (início da fila)
    public Processo removerDoInicio() {
        if (estaVazia()) return null;

        Processo processo = primeiro.processo;
        primeiro = primeiro.proximo;
        if (primeiro == null) { // lista ficou vazia
            ultimo = null;
        }
        tamanho--;
        return processo;
    }

    // Verifica se a lista está vazia
    public boolean estaVazia() {
        return primeiro == null;
    }

    // Retorna o tamanho da lista
    public int getTamanho() {
        return tamanho;
    }

    // Retorna uma representação em string da lista
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        No atual = primeiro;
        while (atual != null) {
            sb.append(atual.processo.getNome());
            if (atual.proximo != null) {
                sb.append(" -> ");
            }
            atual = atual.proximo;
        }
        sb.append("]");
        return sb.toString();
    }
}

