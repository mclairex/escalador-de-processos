# Projeto P1 – Escalonador de Processos iCEVOS

**Disciplina:** Algoritmos e Estrutura de Dados I  

**Professor:** Dimmy Magalhães  

**Aluno:** [Pedro Henrique Rodrigues Jacques Pinheiro]  **Matrícula:** [0030755]

**Aluno:** [José Airton Rodrigues Galdino Júnior]  **Matrícula:** [0030659]

**Aluno:** [Maria Clara Sousa de Oliveira]  **Matrícula:** [0030630]

**Link do repositório:** [https://github.com/mclairex/escalador-de-processos]

---

## 1. Descrição do Projeto

O projeto consiste na implementação do **escalonador de processos** do sistema operacional simulado **iCEVOS**, com suporte a:

- Múltiplos níveis de prioridade (Alta, Média, Baixa)
- Prevenção de inanição (anti-starvation)
- Gerenciamento de recursos (bloqueio/desbloqueio de processos)
- Estruturas de dados implementadas **do zero** (listas encadeadas)

O objetivo é simular o comportamento de um escalonador de CPU, executando processos de acordo com suas prioridades e regras de justiça.

---

## 2. Estrutura do Projeto

| Classe / Arquivo        | Função                                                                 |
|-------------------------|------------------------------------------------------------------------|
| `App.java`              | Classe principal; lê arquivo, configura verbose e inicia simulação    |
| `LeitorDeArquivo.java`  | Lê processos de arquivo CSV e adiciona no Scheduler                   |
| `ListaDeProcessos.java` | Lista encadeada para gerenciar processos sem usar estruturas prontas  |
| `NodeProcesso.java`     | Nó da lista, contendo referência ao Processo e próximo nó             |
| `Processo.java`         | Representa um processo, com id, prioridade, ciclos e recurso          |
| `Scheduler.java`        | Lógica do escalonador, execução de ciclos, anti-inanição e bloqueio   |
| `processos.txt`         | Exemplo de arquivo de entrada com processos                            |

---

## 3. Formato do Arquivo de Entrada

O arquivo deve estar no formato CSV, com campos separados por vírgula:


- **id:** inteiro único do processo
- **nome:** nome do processo (String)
- **prioridade:** 1 (Alta), 2 (Média), 3 (Baixa)
- **ciclos:** número de ciclos necessários para execução (int)
- **recurso:** opcional; se o processo precisar do "DISCO", colocar `DISCO`; caso contrário, deixar vazio

Exemplo:

1,ProcessoA,1,5,

2,ProcessoB,2,3,DISCO

3,ProcessoC,3,7,


Linhas vazias ou comentários (`#`) são ignorados.

---

## 4. Como Executar no IntelliJ IDEA

Siga estes passos para rodar o projeto sem usar linha de comando:

### Passo 1: Abrir o projeto
- Abra o IntelliJ IDEA.
- Vá em **File → Open** e selecione a pasta do projeto.
- Abra o arquivo **src/App.java**.

### Passo 2: Executar
- Clique no ícone **Run** (triângulo verde) ou use **Run → Run 'App'**.
- Para depuração, use **Run → Debug 'App'**.

### Passo 3: Passar parâmetros (opcional)
- Clique em **Run → Edit Configurations…**
- Em **Program arguments**, adicione:
## 4. Como Executar no IntelliJ IDEA

Siga estes passos para rodar o projeto sem usar linha de comando:

### Passo 1: Abrir o projeto
- Abra o IntelliJ IDEA.
- Vá em **File → Open** e selecione a pasta do projeto.
- Abra o arquivo **src/App.java**.

### Passo 2: Executar
- Clique no ícone **Run** (triângulo verde) ou use **Run → Run 'App'**.
- Para depuração, use **Run → Debug 'App'**.

### Passo 3: Passar parâmetros (opcional)
- Clique em **Run → Edit Configurations…**
- Em **Program arguments**, adicione:

## 5. Saída Esperada

Durante a execução, o programa exibirá:

- Lista de processos em cada nível de prioridade
- Processos bloqueados
- Processo sendo executado no ciclo atual
- Eventos de desbloqueio ou término de execução

**Resumo final: [Simulação finalizada. Total de ciclos executados: XXX]**


---

## 6. Considerações Técnicas

- **Estruturas de dados:** lista encadeada manual (`ListaDeProcessos`)
- **Anti-inanição:** após 5 execuções consecutivas de processos de alta prioridade, o scheduler executa um processo de média ou baixa prioridade
- **Bloqueio:** processos que requerem "DISCO" são bloqueados na primeira vez e desbloqueados no próximo ciclo
- **Complexidade:**
    - Inserção no final: O(1)
    - Remoção do início: O(1)
    - Remoção/busca por ID: O(n)

---

## 7. Referências e Observações

- Todo o código foi implementado do zero, sem uso de estruturas prontas do Java.
- Estruturas prontas como `ArrayList`, `LinkedList` ou `HashMap` **não foram utilizadas**.
- O projeto é modular, permitindo alterações futuras na lógica do escalonador ou nos critérios de prioridade.
- Foi utilizado o auxílio de Inteligência Artificial (IA) para organização das ideias, formatação do README e suporte no entendimento da lógica do escalonador. Todo o código e a implementação final foram desenvolvidos pelos integrantes do grupo.


