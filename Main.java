public class Main {
    static Chamado[] criarChamados() {
        int[] prioridades = {3, 3, 5, 4, 5, 1, 4, 2};
        Chamado[] chamados = new Chamado[prioridades.length];
        for (int i = 0; i < prioridades.length; i++) {
            chamados[i] = new Chamado(i + 1, prioridades[i]);
        }
        return chamados;
    }

    static void insertionSort(Chamado[] v) {
        int deslocamentos = 0;
        for (int i = 1; i < v.length; i++) {
            Chamado chave = v[i];
            int j = i - 1;
            while (j >= 0 && v[j].prioridade < chave.prioridade) {
                v[j + 1] = v[j];
                deslocamentos++;
                j--;
            }
            v[j + 1] = chave;
        }
        System.out.println("Insertion Sort - deslocamentos: " + deslocamentos);
    }

    static void selectionSort(Chamado[] v) {
        int trocas = 0;
        for (int i = 0; i < v.length - 1; i++) {
            int posMaior = i;
            for (int j = i + 1; j < v.length; j++) {
                if (v[j].prioridade > v[posMaior].prioridade) {
                    posMaior = j;
                }
            }
            if (posMaior != i) {
                Chamado tmp = v[i];
                v[i] = v[posMaior];
                v[posMaior] = tmp;
                trocas++;
            }
        }
        System.out.println("Selection Sort - trocas: " + trocas);
    }

    static void imprimirIds(String titulo, Chamado[] v) {
        System.out.print(titulo + ": ");
        for (Chamado c : v) {
            System.out.print(c.id + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Chamado[] chamados = criarChamados();

        FilaPrioridade fila = new FilaPrioridade(chamados.length);
        for (Chamado c : chamados) {
            fila.inserir(c);
        }
        fila.imprimirVetor();

        Chamado[] ordemFila = new Chamado[chamados.length];
        int k = 0;
        Chamado atual;
        System.out.print("Ordem de atendimento: ");
        while ((atual = fila.removerMaior()) != null) {
            System.out.print(atual + " ");
            ordemFila[k++] = atual;
        }
        System.out.println();

        Chamado[] copiaInsertion = chamados.clone();
        insertionSort(copiaInsertion);
        Chamado[] copiaSelection = chamados.clone();
        selectionSort(copiaSelection);

        imprimirIds("Fila (heap)     ", ordemFila);
        imprimirIds("Insertion Sort  ", copiaInsertion);
        imprimirIds("Selection Sort  ", copiaSelection);
    }

    /*
     * Análise de estabilidade:
     * - Fila (heap): 3 5 4 7 1 2 8 6 -> todos os empates respeitam a ordem de
     *   chegada (P5: #3,#5; P4: #4,#7; P3: #1,#2).
     * - Insertion Sort: 3 5 4 7 1 2 8 6 -> estável, nenhum empate fora de ordem.
     * - Selection Sort: 3 5 4 7 2 1 8 6 -> instável: os chamados #2 e #1 (ambos P3)
     *   ficaram fora da ordem de chegada.
     */
}
