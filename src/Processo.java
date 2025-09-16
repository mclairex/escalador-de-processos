public class Processo {
   private int id;
   private String nome;
   private int prioridade;
   private int ciclosNecessarios;
   private String recursoNecessario;
   private boolean jaUsouRecurso;

   public Processo(int id, String nome, int prioridade, int ciclosNecessarios, boolean jaUsouRecurso) {
       this.id = id;
       this.nome = nome;
       this.prioridade = prioridade;
       this.ciclosNecessarios = ciclosNecessarios;
       this.recursoNecessario = null; 
       this.jaUsouRecurso = jaUsouRecurso; 
   }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getCiclosNecessarios() {
        return ciclosNecessarios;
    }

    public void setCiclosNecessarios(int ciclosNecessarios) {
        this.ciclosNecessarios = ciclosNecessarios;
    }

    public String getRecursoNecessario() {
        return recursoNecessario;
    }

    public void setRecursoNecessario(String recursoNecessario) {
        this.recursoNecessario = recursoNecessario;
    }

    public boolean isJaUsouRecurso() {
        return jaUsouRecurso;
    }

    public void setJaUsouRecurso(boolean jaUsouRecurso) {
        this.jaUsouRecurso = jaUsouRecurso;
    }
}

