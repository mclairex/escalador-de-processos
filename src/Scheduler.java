public class Scheduler {
    private ListaDeProcessos listaAltaPrioridade;
    private ListaDeProcessos listaMediaPrioridade;
    private ListaDeProcessos listaBaixaPrioridade;
    private ListaDeProcessos listaBloqueados;
    private int contadorCiclosAltaPrioridade;
    private int cicloAtual;
    private boolean verbose = true; // se false -> imprime apenas resumos por ciclo

    public Scheduler() {
        listaAltaPrioridade = new ListaDeProcessos();
        listaMediaPrioridade = new ListaDeProcessos();
        listaBaixaPrioridade = new ListaDeProcessos();
        listaBloqueados = new ListaDeProcessos();
        contadorCiclosAltaPrioridade = 0;
        cicloAtual = 0;
    }

    // Permite controlar a verbosidade externamente (App escolhe conforme tamanho)
    public void setVerbose(boolean v) { this.verbose = v; }

    public void adicionarProcesso(Processo p) {
        switch(p.getPrioridade()) {
            case 1 -> listaAltaPrioridade.adicionarNoFinal(p);
            case 2 -> listaMediaPrioridade.adicionarNoFinal(p);
            case 3 -> listaBaixaPrioridade.adicionarNoFinal(p);
            default -> System.out.println("Prioridade inválida: " + p);
        }
    }

    public void executarSimulacao() {
        System.out.println("Iniciando simulação do escalonador...");
        while (temProcessos()) {
            executarCicloDeCPU();
            // pausa pequena opcional: se estiver em testes de desempenho, comente esta linha
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        System.out.println("\nESCALONAMENTO FINALIZADO! Total de ciclos: " + cicloAtual);
    }

    private boolean temProcessos() {
        return !listaAltaPrioridade.estaVazia() || !listaMediaPrioridade.estaVazia() ||
                !listaBaixaPrioridade.estaVazia() || !listaBloqueados.estaVazia();
    }

    private void executarCicloDeCPU() {
        cicloAtual++;
        System.out.println("\n=== CICLO " + cicloAtual + " ===");

        desbloquearProcessoMaisAntigo();
        mostrarEstadoDasListas();

        if (contadorCiclosAltaPrioridade >= 5) {
            System.out.println("⚠ PREVENÇÃO DE INANIÇÃO ATIVADA!");
            executarProcessoMediaOuBaixa();
            contadorCiclosAltaPrioridade = 0;
            return;
        }

        executarProximoProcessoDisponivel();
    }

    private void desbloquearProcessoMaisAntigo() {
        if (!listaBloqueados.estaVazia()) {
            Processo p = listaBloqueados.removerDoInicio();
            // devolve para lista original
            switch(p.getPrioridade()) {
                case 1 -> listaAltaPrioridade.adicionarNoFinal(p);
                case 2 -> listaMediaPrioridade.adicionarNoFinal(p);
                case 3 -> listaBaixaPrioridade.adicionarNoFinal(p);
            }
            if (verbose) System.out.println("🔓 Processo desbloqueado: " + p);
            else System.out.println("🔓 Desbloqueado: P" + p.getId());
        }
    }

    private void executarProximoProcessoDisponivel() {
        if (!listaAltaPrioridade.estaVazia()) {
            Processo p = listaAltaPrioridade.removerDoInicio();
            if (verbose) System.out.println("Executando da ALTA: " + p);
            executarProcesso(p);
            contadorCiclosAltaPrioridade++;
        } else if (!listaMediaPrioridade.estaVazia()) {
            Processo p = listaMediaPrioridade.removerDoInicio();
            if (verbose) System.out.println("Executando da MÉDIA: " + p);
            executarProcesso(p);
        } else if (!listaBaixaPrioridade.estaVazia()) {
            Processo p = listaBaixaPrioridade.removerDoInicio();
            if (verbose) System.out.println("Executando da BAIXA: " + p);
            executarProcesso(p);
        } else {
            System.out.println("Nenhum processo disponível.");
        }
    }

    private void executarProcesso(Processo p) {
        if (p.getRecursoNecessario() != null && !p.isJaUsouRecurso()) {
            p.setJaUsouRecurso(true);
            listaBloqueados.adicionarNoFinal(p);
            if (verbose) System.out.println("⛔ Processo bloqueado por recurso " + p.getRecursoNecessario() + ": " + p);
            else System.out.println("⛔ Bloqueado: P" + p.getId() + " por " + p.getRecursoNecessario());
            return;
        }

        p.setCiclosNecessarios(p.getCiclosNecessarios() - 1);
        if (verbose) System.out.println("⚡ Processo executado: " + p);
        else System.out.println("⚡ Executado: P" + p.getId() + " | ciclos restantes: " + p.getCiclosNecessarios());

        if (p.getCiclosNecessarios() == 0) {
            if (verbose) System.out.println("✅ Processo finalizado: " + p);
            else System.out.println("✅ Finalizado: P" + p.getId());
        } else {
            switch(p.getPrioridade()) {
                case 1 -> listaAltaPrioridade.adicionarNoFinal(p);
                case 2 -> listaMediaPrioridade.adicionarNoFinal(p);
                case 3 -> listaBaixaPrioridade.adicionarNoFinal(p);
            }
        }
    }

    private void executarProcessoMediaOuBaixa() {
        if (!listaMediaPrioridade.estaVazia()) {
            Processo p = listaMediaPrioridade.removerDoInicio();
            if (verbose) System.out.println("Executando (anti-inanição) da MÉDIA: " + p);
            executarProcesso(p);
        } else if (!listaBaixaPrioridade.estaVazia()) {
            Processo p = listaBaixaPrioridade.removerDoInicio();
            if (verbose) System.out.println("Executando (anti-inanição) da BAIXA: " + p);
            executarProcesso(p);
        } else {
            System.out.println("Nenhum processo média/baixa disponível para anti-inanição.");
        }
    }

    private void mostrarEstadoDasListas() {
        if (verbose) {
            System.out.println("Listas: " +
                    "Alta=" + listaAltaPrioridade +
                    ", Média=" + listaMediaPrioridade +
                    ", Baixa=" + listaBaixaPrioridade +
                    ", Bloqueados=" + listaBloqueados +
                    " | Contador Alta=" + contadorCiclosAltaPrioridade);
        } else {
            System.out.println("Resumo: Alta=" + listaAltaPrioridade.getTamanho()
                    + " | Média=" + listaMediaPrioridade.getTamanho()
                    + " | Baixa=" + listaBaixaPrioridade.getTamanho()
                    + " | Bloqueados=" + listaBloqueados.getTamanho()
                    + " | ContadorAlta=" + contadorCiclosAltaPrioridade);
        }
    }

    // Getters caso precise
    public ListaDeProcessos getListaAltaPrioridade() { return listaAltaPrioridade; }
    public ListaDeProcessos getListaMediaPrioridade() { return listaMediaPrioridade; }
    public ListaDeProcessos getListaBaixaPrioridade() { return listaBaixaPrioridade; }
    public ListaDeProcessos getListaBloqueados() { return listaBloqueados; }
    public int getCicloAtual() { return cicloAtual; }
}
