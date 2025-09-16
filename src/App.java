public class App {
    // Threshold para decidir modo verboso
    private static final int VERBOSE_THRESHOLD = 5000;

    public static void main(String[] args) {
        String caminhoArquivo = "processos.txt";
        boolean forceVerbose = false;

        if (args.length >= 1) caminhoArquivo = args[0];
        if (args.length >= 2 && args[1].equalsIgnoreCase("verbose")) forceVerbose = true;

        Scheduler scheduler = new Scheduler();

        System.out.println("Lendo arquivo: " + caminhoArquivo);
        int totalLidos = LeitorDeArquivo.carregarProcessosDeArquivo(caminhoArquivo, scheduler);

        System.out.println("Total de processos lidos: " + totalLidos);

        // Decide sobre verbosidade (força se especificado; caso contrário, decide por threshold)
        if (forceVerbose) {
            scheduler.setVerbose(true);
            System.out.println("Modo verboso forçado (verbose).");
        } else if (totalLidos > VERBOSE_THRESHOLD) {
            scheduler.setVerbose(false);
            System.out.println("Arquivo grande detectado (> " + VERBOSE_THRESHOLD + "). Modo compacto ativado (menos logs).");
        } else {
            scheduler.setVerbose(true);
            System.out.println("Modo verboso ativado (arquivo pequeno).");
        }

        // Se não houve processos lidos, avisar e sair
        if (totalLidos == 0) {
            System.out.println("Nenhum processo lido. Verifique o arquivo e tente novamente.");
            return;
        }

        // Executa a simulação
        scheduler.executarSimulacao();

        // (Opcional) resumo final simples — você pode adicionar métodos no Scheduler para estatísticas mais detalhadas
        System.out.println("\nSimulação finalizada. Total de ciclos executados: " + scheduler.getCicloAtual());
    }
}
