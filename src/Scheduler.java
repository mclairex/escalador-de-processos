public class Scheduler {
    // Atributos obrigatórios conforme requisitos
    private ListaDeProcessos listaAltaPrioridade;
    private ListaDeProcessos listaMediaPrioridade;
    private ListaDeProcessos listaBaixaPrioridade;
    private ListaDeProcessos listaBloqueados;
    private int contadorCiclosAltaPrioridade;
    private int cicloAtual;

    public Scheduler() {
        this.listaAltaPrioridade = new ListaDeProcessos();
        this.listaMediaPrioridade = new ListaDeProcessos();
        this.listaBaixaPrioridade = new ListaDeProcessos();
        this.listaBloqueados = new ListaDeProcessos();
        this.contadorCiclosAltaPrioridade = 0;
        this.cicloAtual = 0;
    }

    public void adicionarProcesso(Processo processo) {
        switch(processo.prioridade) {
            case 1: // Alta prioridade
                listaAltaPrioridade.adicionarNoFinal(processo);
                break;
            case 2: // Média prioridade
                listaMediaPrioridade.adicionarNoFinal(processo);
                break;
            case 3: // Baixa prioridade
                listaBaixaPrioridade.adicionarNoFinal(processo);
                break;
            default:
                System.out.println("⚠️ Prioridade inválida para processo P" + processo.id);
        }
    }

    public void executarCicloDeCPU() {
        cicloAtual++;
        System.out.println("\n=== CICLO " + cicloAtual + " ===");

        // 1. No início de cada ciclo, desbloqueie o processo mais antigo
        desbloquearProcessoMaisAntigo();

        // 2. Mostrar estado atual das listas
        mostrarEstadoDasListas();

        // 3. Verificar regra de prevenção de inanição
        if (contadorCiclosAltaPrioridade >= 5) {
            System.out.println("🚨 PREVENÇÃO DE INANIÇÃO ATIVADA!");
            executarProcessoMediaOuBaixa();
            contadorCiclosAltaPrioridade = 0;
            return;
        }

        //Execução Padrão: A -> M -> B
        executarProximoProcessoDisponivel();

    }

    private void desbloquearProcessoMaisAntigo() {
        if (!listaBloqueados.isEmpty()) {
            Processo processo = listaBloqueados.removerDoInicio();

            // Adiciona de volta ao final da sua lista de prioridade original
            switch(processo.prioridade) {
                case 1:
                    listaAltaPrioridade.adicionarNoFinal(processo);
                    break;
                case 2:
                    listaMediaPrioridade.adicionarNoFinal(processo);
                    break;
                case 3:
                    listaBaixaPrioridade.adicionarNoFinal(processo);
                    break;
            }

            System.out.println("🔓 Processo desbloqueado: " + processo);
        }
    }
}
