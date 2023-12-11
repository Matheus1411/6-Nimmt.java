import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tabuleiro {
    public List<List<Carta>> linhas;

    public Tabuleiro() {
        this.linhas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            linhas.add(new ArrayList<>());
        }
    }

    public void exibirTabuleiro() {
        System.out.println("================= 6 NIMMT GAME =======================");
        System.out.println("Tabuleiro atual:\n");

        for (List<Carta> linha : linhas) {
            StringBuilder linhaAtual = new StringBuilder();
            for (Carta carta : linha) {
                linhaAtual.append(carta.getNumero()).append(" ");
            }
            System.out.println(linhaAtual.toString().trim() + "\n---------------------------------------");
        }
        System.out.println("=================================================");
    }

    public void sortearCartasIniciais(List<Carta> baralho) {
        Random random = new Random();
        for (List<Carta> linha : linhas) {
            Carta cartaSorteada = baralho.remove(random.nextInt(baralho.size()));
            linha.add(cartaSorteada);
        }
    }

    public void posicionarCarta(Carta carta, Jogador jogador) {
        int melhorLinha = encontrarMelhorLinha(carta);

        // Verifica se a linha está cheia
        if (linhas.get(melhorLinha).size() == 5) {
            System.out.println("Linha cheia. Coletando cartas da linha " + melhorLinha);
            coletarCartasDaLinha(melhorLinha);
        }

        // Adicionar a carta ao lado direito da carta anterior se possível
        List<Carta> linhaAtual = linhas.get(melhorLinha);
        int tamanhoLinha = linhaAtual.size();
        if (tamanhoLinha > 0) {
            int indiceInsercao = encontrarPosicaoInsercao(carta, linhaAtual);
            linhaAtual.add(indiceInsercao, carta);
        } else {
            // Adiciona à direita se a linha estiver vazia
            linhaAtual.add(carta);
        }

        if (jogador != null) {
            // Adiciona à esquerda se possível
            int indiceCartaJogador = linhaAtual.indexOf(carta);
            if (indiceCartaJogador > 0) {
                Carta cartaEscolhida = jogador.escolherCarta();
                linhaAtual.add(indiceCartaJogador, cartaEscolhida);

            } else {
                // Adiciona à direita se não houver carta à esquerda
                Carta cartaEscolhida = jogador.escolherCarta();
                linhaAtual.add(indiceCartaJogador + 1, cartaEscolhida);
                coletarCartaMenorQueLinha(carta);

            }
        } else {
            // Coletar cartas e posicionar no início da linha se jogador for nulo
            coletarCartaMenorQueLinha(carta);
        }
    }

    private void coletarCartaMenorQueLinha(Carta carta) {
        int maiorUltimoNumero = -1;
        int linhaMaiorUltimoNumero = -1;

        for (int i = 0; i < linhas.size(); i++) {
            List<Carta> linhaAtual = linhas.get(i);

            if (!linhaAtual.isEmpty()) {
                int ultimoNumero = linhaAtual.get(linhaAtual.size() - 1).getNumero();

                if (carta.getNumero() < ultimoNumero && ultimoNumero > maiorUltimoNumero) {
                    maiorUltimoNumero = ultimoNumero;
                    linhaMaiorUltimoNumero = i;
                }
            }
        }

        if (linhaMaiorUltimoNumero != -1) {
            System.out.println(
                    "Carta do jogador não pode ser colocada. Coletando cartas da linha " + linhaMaiorUltimoNumero);
            linhas.get(linhaMaiorUltimoNumero).clear();
            linhas.get(linhaMaiorUltimoNumero).add(0, carta);
        } else {
            int maiorUltimoNumeroGeral = -1;
            int linhaMaiorUltimoNumeroGeral = -1;

            for (int i = 0; i < linhas.size(); i++) {
                List<Carta> linhaAtual = linhas.get(i);

                if (!linhaAtual.isEmpty()) {
                    int ultimoNumero = linhaAtual.get(linhaAtual.size() - 1).getNumero();

                    if (ultimoNumero > maiorUltimoNumeroGeral) {
                        maiorUltimoNumeroGeral = ultimoNumero;
                        linhaMaiorUltimoNumeroGeral = i;
                    }
                }
            }

            if (linhaMaiorUltimoNumeroGeral != -1) {
                System.out.println("Nenhuma linha adequada. Coletando cartas da linha " + linhaMaiorUltimoNumeroGeral);
                linhas.get(linhaMaiorUltimoNumeroGeral).clear();
                linhas.get(linhaMaiorUltimoNumeroGeral).add(0, carta); // Posicionar no início da linha
            }
        }
    }

    public List<Carta> coletarCartasDaLinha(int linha) {
        List<Carta> cartasDaLinha = linhas.get(linha);
        linhas.get(linha).clear();
        return cartasDaLinha;
    }

    private int encontrarMelhorLinha(Carta carta) {
        int melhorLinha = 0;
        int menorDiferenca = Integer.MAX_VALUE;

        for (int i = 0; i < linhas.size(); i++) {
            List<Carta> linhaAtualTeste = linhas.get(i);

            if (!linhaAtualTeste.isEmpty()) {
                int diferenca = carta.getNumero() - linhaAtualTeste.get(linhaAtualTeste.size() - 1).getNumero();

                if (diferenca >= 0 && diferenca < menorDiferenca) {
                    menorDiferenca = diferenca;
                    melhorLinha = i;
                }
            }
        }

        return melhorLinha;
    }

    private int encontrarPosicaoInsercao(Carta carta, List<Carta> linha) {
        int posicaoInsercao = linha.size();

        for (int i = 0; i < linha.size(); i++) {
            // Encontrar a primeira posição onde a nova carta tem um número menor ou igual
            if (carta.getNumero() <= linha.get(i).getNumero()) {
                posicaoInsercao = i;
                break;
            }
        }

        return posicaoInsercao;
    }
}
