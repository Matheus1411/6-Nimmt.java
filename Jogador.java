import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogador {
    private String nome;
    private List<Carta> mao;
    private List<Carta> cartasColetadas;
    private Scanner scanner;

    public Jogador(String nome, Scanner scanner) {
        this.nome = nome;
        this.mao = new ArrayList<>();
        this.cartasColetadas = new ArrayList<>();
        this.scanner = scanner;
    }

    public String getNome() {
        return nome;
    }

    public void receberCarta(Carta carta) {
        mao.add(carta);
    }

    public void exibirMao() {
        System.out.println(nome + ", sua mão atual:");
        for (Carta carta : mao) {
            System.out.println("Carta: " + carta.getNumero() + " | Pontos: " + carta.getPontos());
        }
    }

    public Carta escolherCarta() {
        Carta cartaEscolhida = null;

        while (cartaEscolhida == null) {
            System.out.println(nome + ", é sua vez. Escolha o número da carta para jogar:");
            int numeroEscolhido = scanner.nextInt();

            scanner.nextLine();

            cartaEscolhida = procurarCarta(numeroEscolhido);

            if (cartaEscolhida == null) {
                System.out.println("Carta inválida. Escolha o número de uma carta que está em sua mão.");
            } else {
                break;
            }
        }

        mao.remove(cartaEscolhida);

        return cartaEscolhida;
    }

    private Carta procurarCarta(int numero) {
        for (Carta carta : mao) {
            if (carta.getNumero() == numero) {
                return carta;
            }
        }
        return null;
    }

    public int calcularPontuacao() {
        int pontuacao = 0;

        for (Carta carta : cartasColetadas) {
            pontuacao += carta.getPontos();
        }

        return pontuacao;
    }

    // Método para coletar cartas e adicionar à lista de cartas coletadas
    public void coletarCartas(List<Carta> cartas) {
        cartasColetadas.addAll(cartas);

        for (Carta cartaColetada : cartas) {
            int index = mao.indexOf(cartaColetada);
            if (index != -1) {
                Carta cartaNaMao = mao.get(index);
                cartaNaMao.calcularPontos(); 
            }
        }
    }

    public void exibirPontuacaoTotal() {
        int pontuacaoTotal = calcularPontuacao();
        System.out.println(nome + ", sua pontuação total é: " + pontuacaoTotal + " ponto(s).");
    }

    public void exibirCartasColetadas() {
        System.out.println(nome + ", suas cartas coletadas:");
        for (Carta carta : cartasColetadas) {
            System.out.println("Carta: " + carta.getNumero() + " | Pontos: " + carta.getPontos());
        }
    }

    // Método para limpar a mão ao final do jogo
    public void limparMao() {
        mao.clear();
    }
}
