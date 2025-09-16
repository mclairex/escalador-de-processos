public class ListaDeProcessos {
    private NodeProcesso cabeca; 
    private NodeProcesso cauda;  
    private int tamanho;

    public ListaDeProcessos() {
        this.cabeca = null;
        this.cauda = null;
        this.tamanho = 0;
    }

    public void adicionar(Processo processo) {
        NodeProcesso novo = new NodeProcesso(processo);
        if (cabeca == null) {
            cabeca = novo;
            cauda = novo;
            cauda.setProximo(cabeca);
        } else {
            cauda.setProximo(novo);
            cauda = novo;
            cauda.setProximo(cabeca);
        }
        tamanho++;
    }


        public boolean remover(int id) {
        if (cabeca == null) return false;

        NodeProcesso atual = cabeca;
        NodeProcesso anterior = cauda;

        do {
            if (atual.getProcesso().getId() == id) {
                if (atual == cabeca) {
                    cabeca = cabeca.getProximo();
                    cauda.setProximo(cabeca);
                } else if (atual == cauda) {
                    cauda = anterior;
                    cauda.setProximo(cabeca);
                } else {
                    anterior.setProximo(atual.getProximo());
                }
                tamanho--;
                if (tamanho == 0) {
                    cabeca = null;
                    cauda = null;
                }
                return true;
            }
            anterior = atual;
            atual = atual.getProximo();
        } while (atual != cabeca);

        return false;
    }

    public void listar() {
        if (cabeca == null) {
            System.out.println("Lista vazia!");
            return;
        }

        NodeProcesso atual = cabeca;
        do {
            System.out.println(atual.getProcesso());
            atual = atual.getProximo();
        } while (atual != cabeca);
    }

    public int getTamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public NodeProcesso getCabeca() {
        return cabeca;
    }
}
