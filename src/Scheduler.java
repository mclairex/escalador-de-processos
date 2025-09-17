public class Scheduler {
    private ListaDeProcessos listaAltaPrioridade;
    private ListaDeProcessos listaMediaPrioridade;
    private ListaDeProcessos listaBaixaPrioridade;
    private ListaDeProcessos listaBloqueados;
    private int contadorCiclosAltaPrioridade;
    private int cicloAtual;
    private boolean verbose = true;

    public Scheduler() {
        listaAltaPrioridade = new ListaDeProcessos();
        listaMediaPrioridade = new ListaDeProcessos();
        listaBaixaPrioridade = new ListaDeProcessos();
        listaBloqueados = new ListaDeProcessos();
        contadorCiclosAltaPrioridade = 0;
        cicloAtual = 0;
    }

    public void setVerbose(boolean v) { this.verbose = v; }

    public void adicionarProcesso(Processo p) {
        switch(p.getPrioridade()) {
            case 1 -> listaAltaPrioridade.adicionarNoFinal(p);
            case 2 -> listaMediaPrioridade.adicionarNoFinal(p);
            case 3 -> listaBaixaPrioridade.adicionarNoFinal(p);
            default -> { if (verbose) System.out.println("Prioridade inválida: " + p); }
        }
    }

    public void executarSimulacao() {
        System.out.println("Iniciando simulação do escalonador...");
        StringBuilder log = new StringBuilder(); // acumula saída em memória

        while (temProcessos()) {
            executarCicloDeCPU(log);
            // 🔹 REMOVIDO o sleep(50) para ganhar velocidade
        }

        System.out.println("\nESCALONAMENTO FINALIZADO! Total de ciclos: " + cicloAtual);
        if (verbose) {
            // 🔹 Imprime tudo de uma vez só → muito mais rápido
            System.out.println(log);
        }
    }

    private boolean temProcessos() {
        return !listaAltaPrioridade.estaVazia()
            || !listaMediaPrioridade.estaVazia()
            || !listaBaixaPrioridade.estaVazia()
            || !listaBloqueados.estaVazia();
    }

    private void executarCicloDeCPU(StringBuilder log) {
        cicloAtual++;
        if (verbose) log.append("\n=== CICLO ").append(cicloAtual).append(" ===\n");

        desbloquearProcessoMaisAntigo(log);
        mostrarEstadoDasListas(log);

        if (contadorCiclosAltaPrioridade >= 5) {
            if (verbose) log.append("⚠ PREVENÇÃO DE INANIÇÃO ATIVADA!\n");
            executarProcessoMediaOuBaixa(log);
            contadorCiclosAltaPrioridade = 0;
            return;
        }

        executarProximoProcessoDisponivel(log);
    }

    private void desbloquearProcessoMaisAntigo(StringBuilder log) {
        if (!listaBloqueados.estaVazia()) {
            Processo p = listaBloqueados.removerDoInicio();
            switch(p.getPrioridade()) {
                case 1 -> listaAltaPrioridade.adicionarNoFinal(p);
                case 2 -> listaMediaPrioridade.adicionarNoFinal(p);
                case 3 -> listaBaixaPrioridade.adicionarNoFinal(p);
            }
            if (verbose) log.append("🔓 Desbloqueado: P").append(p.getId()).append("\n");
        }
    }

    private void executarProximoProcessoDisponivel(StringBuilder log) {
        Processo p = null;
        String origem = "";

        if (!listaAltaPrioridade.estaVazia()) {
            p = listaAltaPrioridade.removerDoInicio();
            origem = "ALTA";
            contadorCiclosAltaPrioridade++;
        } else if (!listaMediaPrioridade.estaVazia()) {
            p = listaMediaPrioridade.removerDoInicio();
            origem = "MÉDIA";
        } else if (!listaBaixaPrioridade.estaVazia()) {
            p = listaBaixaPrioridade.removerDoInicio();
            origem = "BAIXA";
        }

        if (p == null) {
            if (verbose) log.append("Nenhum processo disponível.\n");
            return;
        }

        if (verbose) log.append("Executando da ").append(origem).append(": P").append(p.getId()).append("\n");
        executarProcesso(p, log);
    }

    private void executarProcesso(Processo p, StringBuilder log) {
        if (p.getRecursoNecessario() != null && !p.isJaUsouRecurso()) {
            p.setJaUsouRecurso(true);
            listaBloqueados.adicionarNoFinal(p);
            if (verbose) log.append("⛔ Bloqueado: P").append(p.getId())
                           .append(" por ").append(p.getRecursoNecessario()).append("\n");
            return;
        }

        p.setCiclosNecessarios(p.getCiclosNecessarios() - 1);
        if (verbose) log.append("⚡ Executado: P").append(p.getId())
                       .append(" | ciclos restantes: ").append(p.getCiclosNecessarios()).append("\n");

        if (p.getCiclosNecessarios() == 0) {
            if (verbose) log.append("✅ Finalizado: P").append(p.getId()).append("\n");
        } else {
            switch(p.getPrioridade()) {
                case 1 -> listaAltaPrioridade.adicionarNoFinal(p);
                case 2 -> listaMediaPrioridade.adicionarNoFinal(p);
                case 3 -> listaBaixaPrioridade.adicionarNoFinal(p);
            }
        }
    }

    private void executarProcessoMediaOuBaixa(StringBuilder log) {
        Processo p = null;
        String origem = "";

        if (!listaMediaPrioridade.estaVazia()) {
            p = listaMediaPrioridade.removerDoInicio();
            origem = "MÉDIA";
        } else if (!listaBaixaPrioridade.estaVazia()) {
            p = listaBaixaPrioridade.removerDoInicio();
            origem = "BAIXA";
        }

        if (p == null) {
            if (verbose) log.append("Nenhum processo média/baixa disponível para anti-inanição.\n");
            return;
        }

        if (verbose) log.append("Executando (anti-inanição) da ").append(origem)
                       .append(": P").append(p.getId()).append("\n");
        executarProcesso(p, log);
    }

    private void mostrarEstadoDasListas(StringBuilder log) {
        if (verbose) {
            log.append("Resumo: Alta=").append(listaAltaPrioridade.getTamanho())
               .append(" | Média=").append(listaMediaPrioridade.getTamanho())
               .append(" | Baixa=").append(listaBaixaPrioridade.getTamanho())
               .append(" | Bloqueados=").append(listaBloqueados.getTamanho())
               .append(" | ContadorAlta=").append(contadorCiclosAltaPrioridade)
               .append("\n");
        }
    }

    // Getters
    public int getCicloAtual() { return cicloAtual; }
}
