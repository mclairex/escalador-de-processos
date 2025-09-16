import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorDeArquivo {

    /**
     * Lê o arquivo CSV (id,nome,prioridade,ciclos,recurso) e adiciona os Processos no Scheduler.
     * Retorna o número total de processos válidos carregados.
     */
    public static int carregarProcessosDeArquivo(String caminhoArquivo, Scheduler scheduler) {
        int totalLidos = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;          // ignora linhas vazias
                if (linha.startsWith("#")) continue;    // ignora comentários

                String[] partes = linha.split(",", -1); // -1 mantém campos vazios
                if (partes.length < 4) {
                    System.err.println("Linha com formato inválido (ignorando): " + linha);
                    continue;
                }

                try {
                    int id = Integer.parseInt(partes[0].trim());
                    String nome = partes[1].trim();
                    int prioridade = Integer.parseInt(partes[2].trim());
                    int ciclos = Integer.parseInt(partes[3].trim());
                    String recurso = null;
                    if (partes.length >= 5) {
                        recurso = partes[4].trim();
                        if (recurso.isEmpty()) recurso = null;
                    }

                    Processo p = new Processo(id, nome, prioridade, ciclos, recurso);
                    scheduler.adicionarProcesso(p);
                    totalLidos++;

                    // feedback periódico para arquivos muito grandes
                    if (totalLidos % 10000 == 0) {
                        System.out.println("Lidos " + totalLidos + " processos...");
                    }
                } catch (NumberFormatException nfe) {
                    System.err.println("Erro ao parsear números na linha (ignorando): " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro lendo o arquivo: " + e.getMessage());
            e.printStackTrace();
        }

        return totalLidos;
    }
}
