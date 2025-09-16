public class NodeProcesso {
    private Processo processo;
    private NodeProcesso proximo;

    public NodeProcesso(Processo processo) {
        this.processo = processo;
        this.proximo = null;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public NodeProcesso getProximo() {
        return proximo;
    }

    public void setProximo(NodeProcesso proximo) {
        this.proximo = proximo;
    }
}
