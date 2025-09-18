public class ListaDeProcessos {
    private No primeiro;
    private No ultimo;
    private int tamanho;

    
    private static class No {
        Processo processo;
        No proximo;

        No(Processo processo) {
            this.processo = processo;
            this.proximo = null;
        }
    }

    //Inicializa a lista como vazia
    public ListaDeProcessos() {
        this.primeiro = null;
        this.ultimo = null;
        this.tamanho = 0;
    }

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

    public Processo removerDoInicio() {
        if (estaVazia()) return null;

        Processo processo = primeiro.processo;
        primeiro = primeiro.proximo;
        if (primeiro == null) { 
            ultimo = null;
        }
        tamanho--;
        return processo;
    }

    public boolean estaVazia() {
        return primeiro == null;
    }

    public int getTamanho() {
        return tamanho;
    }

    //Retorna uma representação em string da lista
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