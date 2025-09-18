public class ListaDeProcessos {

<<<<<<< HEAD
    
    private static class No {
=======
    // Nó interno da lista
    private class No {
>>>>>>> de98f65 (Adicionado relatório de análise)
        Processo processo;
        No proximo;

        No(Processo processo) {
            this.processo = processo;
            this.proximo = null;
        }
    }

<<<<<<< HEAD
    //Inicializa a lista como vazia
=======
    private No primeiro;
    private No ultimo;
    private int tamanho;

>>>>>>> de98f65 (Adicionado relatório de análise)
    public ListaDeProcessos() {
        this.primeiro = null;
        this.ultimo = null;
        this.tamanho = 0;
    }

    // Adiciona no final da lista (O(1))
    public void adicionarNoFinal(Processo p) {
        No novo = new No(p);
        if (primeiro == null) {
            primeiro = novo;
            ultimo = novo;
        } else {
            ultimo.proximo = novo;
            ultimo = novo;
        }
        tamanho++;
    }

    // Remove do início da lista (O(1))
    public Processo removerDoInicio() {
        if (primeiro == null) return null;

        Processo removido = primeiro.processo;
        primeiro = primeiro.proximo;
        if (primeiro == null) ultimo = null;
        tamanho--;
        return removido;
    }

    // Remove processo por ID (O(n))
    public boolean removerPorId(int id) {
        if (primeiro == null) return false;

        if (primeiro.processo.getId() == id) {
            removerDoInicio();
            return true;
        }

        No atual = primeiro;
        while (atual.proximo != null && atual.proximo.processo.getId() != id) {
            atual = atual.proximo;
        }

        if (atual.proximo == null) return false;

        // remove o nó encontrado
        if (atual.proximo == ultimo) {
            ultimo = atual;
        }
        atual.proximo = atual.proximo.proximo;
        tamanho--;
        return true;
    }

    // Busca processo por ID (O(n))
    public Processo buscarPorId(int id) {
        No atual = primeiro;
        while (atual != null) {
            if (atual.processo.getId() == id) return atual.processo;
            atual = atual.proximo;
        }
        return null;
    }

    // Desbloquear o primeiro processo (para lista de bloqueados)
    public Processo desbloquearPrimeiro() {
        return removerDoInicio();
    }

    // Verifica se a lista está vazia
    public boolean estaVazia() {
        return primeiro == null;
    }

    // Retorna o tamanho da lista
    public int getTamanho() {
        return tamanho;
    }

<<<<<<< HEAD
    //Retorna uma representação em string da lista
=======
    // Listar todos os processos (imprime no console)
    public void listarTodos() {
        No atual = primeiro;
        while (atual != null) {
            System.out.println(atual.processo);
            atual = atual.proximo;
        }
    }

    // Representação da lista como string
>>>>>>> de98f65 (Adicionado relatório de análise)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        No atual = primeiro;
        while (atual != null) {
            sb.append(atual.processo.getNome());
            if (atual.proximo != null) sb.append(" -> ");
            atual = atual.proximo;
        }
        return sb.toString();
    }
}
