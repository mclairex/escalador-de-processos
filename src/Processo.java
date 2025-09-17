public class Processo {
    private int id;
    private String nome;
    private int prioridade;
    private int ciclosNecessarios;
    private String recursoNecessario;
    private boolean jaUsouRecurso;

    // Construtor principal usado na leitura do arquivo
    public Processo(int id, String nome, int prioridade, int ciclosNecessarios, String recursoNecessario) {

        if (prioridade < 1 || prioridade > 3) {
            throw new IllegalArgumentException("Prioridade Inválida: " + prioridade + ". Use 1 (Alta), 2 (Média) ou 3 (Baixa).");
        }

        this.id = id;
        this.nome = nome;
        this.prioridade = prioridade;
        this.ciclosNecessarios = ciclosNecessarios;
        this.recursoNecessario = (recursoNecessario == null || recursoNecessario.trim().isEmpty()) ? null : recursoNecessario.trim();
        this.jaUsouRecurso = false;
    }

    // Getters / setters usados pelo Scheduler
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getPrioridade() { return prioridade; }
    public int getCiclosNecessarios() { return ciclosNecessarios; }
    public void setCiclosNecessarios(int ciclos) { this.ciclosNecessarios = ciclos; }
    public String getRecursoNecessario() { return recursoNecessario; }
    public boolean isJaUsouRecurso() { return jaUsouRecurso; }
    public void setJaUsouRecurso(boolean jaUsouRecurso) { this.jaUsouRecurso = jaUsouRecurso; }

    public String getPrioridadeNome() {
        switch (prioridade) {
            case 1: return "ALTA";
            case 2: return "MÉDIA";
            case 3: return "BAIXA";
            default: return "DESCONHECIDA";
        }
    }

    @Override
    public String toString() {
        return "P" + id + " (" + nome + ") - Prioridade: " + getPrioridadeNome() +
                ", Ciclos: " + ciclosNecessarios +
                (recursoNecessario != null ? ", Recurso: " + recursoNecessario : "");
    }
}
