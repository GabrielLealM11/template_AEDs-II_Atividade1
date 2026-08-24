import java.util.Random;

/** 
 * MIT License
 *
 * Copyright(c) 2024-255 João Caram <caram@pucminas.br>
 *                       Eveline Alonso Veloso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.util.Random;

/**
 * AEDs II - Oficina 1 - Contagem de Operações e Medição do Tempo.
 *
 * Os quatro algoritmos fornecidos no projeto foram instrumentados para:
 * 1) contar operações relevantes;
 * 2) medir o tempo de execução em milissegundos.
 *
 * A contagem considera:
 * codigo1: uma operação para cada resto (%) e uma para cada soma (+=);
 * codigo2: uma operação para cada incremento do contador (++);
 * codigo3: uma comparação por teste vetor[j] < vetor[menor], uma operação
 *           para cada atribuição a menor e três atribuições da troca;
 * codigo4: uma operação para cada chamada/avaliação da função.
 */
public class App {

    static final int[] TAMANHOS_TESTE_GRANDE =
            {31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000};

    static final int[] TAMANHOS_TESTE_MEDIO =
            {12_500, 25_000, 50_000, 100_000, 200_000};

    static final int[] TAMANHOS_TESTE_PEQUENO =
            {3, 6, 12, 24, 48};

    static final double NANO_TO_MILLI = 1.0 / 1_000_000;

    static final Random aleatorio = new Random(42);

    static long operacoes;

    
    // CÓDIGO 1
    static int codigo1(int[] vetor) {

        int resposta = 0;

        for (int i = 0; i < vetor.length; i += 2) {

            operacoes++; // operação %

            int resto = vetor[i] % 2;

            operacoes++; // operação +=

            resposta += resto;
        }

        return resposta;
    }

    // CÓDIGO 2
    static int codigo2(int[] vetor) {

        int contador = 0;

        for (int k = vetor.length - 1; k > 0; k /= 2) {

            for (int i = 0; i <= k; i++) {

                operacoes++; // contador++

                contador++;
            }
        }

        return contador;
    }

    // CÓDIGO 3
    static void codigo3(int[] vetor) {

        for (int i = 0; i < vetor.length - 1; i++) {

            int menor = i;

            for (int j = i + 1; j < vetor.length; j++) {

                operacoes++; // comparação

                if (vetor[j] < vetor[menor]) {

                    operacoes++; // menor = j

                    menor = j;
                }
            }

            int temp = vetor[i];

            operacoes++;

            vetor[i] = vetor[menor];

            operacoes++;

            vetor[menor] = temp;

            operacoes++;
        }
    }

    // CÓDIGO 4
    static int codigo4(int n) {

        operacoes++; // chamada/avaliação da função

        if (n <= 2) {
            return 1;
        }

        return codigo4(n - 1) + codigo4(n - 2);
    }

    // GERADOR DE VETORES
    static int[] gerarVetor(int tamanho) {

        int[] vetor = new int[tamanho];

        for (int i = 0; i < tamanho; i++) {

            vetor[i] = aleatorio.nextInt(1, tamanho / 2);
        }

        return vetor;
    }

    // EXECUTAR CÓDIGO 1
    static void executarCodigo1() {

        System.out.println("\n===== CODIGO 1 =====");
        System.out.println("n;operacoes;tempo_ms");

        for (int n : TAMANHOS_TESTE_GRANDE) {

            System.out.println("gerando vetor n = " + n + "...");

            int[] vetor = gerarVetor(n);

            operacoes = 0;

            long inicio = System.nanoTime();

            codigo1(vetor);

            long fim = System.nanoTime();

            double tempo =
                    (fim - inicio) * NANO_TO_MILLI;

            System.out.printf(
                    "%d;%d;%.3f%n",
                    n,
                    operacoes,
                    tempo
            );

            vetor = null;

            System.gc();
        }
    }

    // EXECUTAR CÓDIGO 2
    static void executarCodigo2() {

        System.out.println("\n===== CODIGO 2 =====");
        System.out.println("n;operacoes;tempo_ms");

        for (int n : TAMANHOS_TESTE_GRANDE) {

            System.out.println("Gerando vetor n = " + n + "...");

            int[] vetor = gerarVetor(n);

            operacoes = 0;

            long inicio = System.nanoTime();

            codigo2(vetor);

            long fim = System.nanoTime();

            double tempo =
                    (fim - inicio) * NANO_TO_MILLI;

            System.out.printf(
                    "%d;%d;%.3f%n",
                    n,
                    operacoes,
                    tempo
            );

            vetor = null;

            System.gc();
        }
    }

    // EXECUTAR CÓDIGO 3
    static void executarCodigo3() {

        System.out.println("\n===== CODIGO 3 =====");
        System.out.println("n;operacoes;tempo_ms");

        for (int n : TAMANHOS_TESTE_MEDIO) {

            System.out.println("Gerando vetor n = " + n + "...");

            int[] vetor = gerarVetor(n);

            operacoes = 0;

            long inicio = System.nanoTime();

            codigo3(vetor);

            long fim = System.nanoTime();

            double tempo =
                    (fim - inicio) * NANO_TO_MILLI;

            System.out.printf(
                    "%d;%d;%.3f%n",
                    n,
                    operacoes,
                    tempo
            );

            vetor = null;

            System.gc();
        }
    }

    // EXECUTAR CÓDIGO 4
    static void executarCodigo4() {

        System.out.println("\n===== CODIGO 4 =====");
        System.out.println("n;operacoes;tempo_ms");

        for (int n : TAMANHOS_TESTE_PEQUENO) {

            operacoes = 0;

            long inicio = System.nanoTime();

            codigo4(n);

            long fim = System.nanoTime();

            double tempo =
                    (fim - inicio) * NANO_TO_MILLI;

            System.out.printf(
                    "%d;%d;%.3f%n",
                    n,
                    operacoes,
                    tempo
            );
        }
    }

    public static void main(String[] args) {
        if (args.length == 0 ||
                args[0].equalsIgnoreCase("todos")) {

            executarCodigo1();
            executarCodigo2();
            executarCodigo3();
            executarCodigo4();

            return;
        }

        switch (args[0]) {

            case "1" -> executarCodigo1();

            case "2" -> executarCodigo2();

            case "3" -> executarCodigo3();

            case "4" -> executarCodigo4();

            default -> System.out.println(
                    "uso: [1|2|3|4|todos]"
            );
        }
    }
}