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
                System.out.println(" Prioridade inválida para processo P" + processo.id);
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
            System.out.println(" PREVENÇÃO DE INANIÇÃO ATIVADA!");
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

            System.out.println(" Processo desbloqueado: " + processo);
        }
    }

    // Executa processo de média ou baixa prioridade (regra anti-inanição)
    private void executarProcessoMediaOuBaixa() {
        if (!listaMediaPrioridade.isEmpty()) {
            Processo processo = listaMediaPrioridade.removerDoInicio();
            System.out.println(" Executando (anti-inanição) da MÉDIA: " + processo);
            executarProcesso(processo);
        } else if (!listaBaixaPrioridade.isEmpty()) {
            Processo processo = listaBaixaPrioridade.removerDoInicio();
            System.out.println(" Executando (anti-inanição) da BAIXA: " + processo);
            executarProcesso(processo);
        } else {
            System.out.println(" Nenhum processo de média/baixa prioridade disponível");
        }
    }

    // Execução padrão: procura na ordem Alta → Média → Baixa
    private void executarProximoProcessoDisponivel() {
        // Tentar Alta prioridade primeiro
        if (!listaAltaPrioridade.isEmpty()) {
            Processo processo = listaAltaPrioridade.removerDoInicio();
            System.out.println(" Executando da ALTA: " + processo);
            executarProcesso(processo);
            contadorCiclosAltaPrioridade++;
            return;
        }

        // Se não há alta, tentar Média
        if (!listaMediaPrioridade.isEmpty()) {
            Processo processo = listaMediaPrioridade.removerDoInicio();
            System.out.println(" Executando da MÉDIA: " + processo);
            executarProcesso(processo);
            return;
        }

        // Se não há baixa, tentar Baixa
        if (!listaBaixaPrioridade.isEmpty()) {
            Processo processo = listaBaixaPrioridade.removerDoInicio();
            System.out.println(" Executando da BAIXA: " + processo);
            executarProcesso(processo);
            return;
        }

        System.out.println(" Nenhum processo disponível para execução no escalonador");
    }

    private void executarProcesso(Processo processo) {
        // Verificar se precisa de recurso pela primeira vez
        if (processo.recursoNecessario != null && !processo.jaUsouRecurso) {
            // Primeira vez que solicita o recurso - bloquear
            processo.jaUsouRecurso = true;
            listaBloqueados.adicionarNoFinal(processo);
            System.out.println("🔒 Processo bloqueado por recurso " + processo.recursoNecessario + ": " + processo);
            return;
        }

        // Executar processo (diminui ciclos necessários)
        processo.ciclosNecessarios--;
        System.out.println("⚡ Processo executado: " + processo);

        // Verificar se o processo terminou
        if (processo.ciclosNecessarios == 0) {
            System.out.println("✅ Processo finalizado: P" + processo.id + " (" + processo.nome + ")");
        } else {
            // Processo não terminou - reinserir no final da lista original
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
            System.out.println("🔄 Processo reinserido na lista " + processo.getPrioridadeNome());
        }
    }
}
