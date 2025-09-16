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
