public class Carta implements Comparable<Carta> {
    public int numero;
    public int pontos;

    public Carta(int numero) {
        this.numero = numero;
        calcularPontos();
    }

    public void calcularPontos() {
        pontos = 1;

        if (numero % 10 == 0) {
            pontos += 2; // Cartas múltiplos de 10 valem 2 pontos extras
        }
        if (numero % 10 == 5) {
            pontos += 1; // Cartas terminadas com o dígito 5 valem 1 ponto extra
        }
        if (temDigitosRepetidos()) {
            pontos += 4; // Cartas com dois dígitos repetidos valem 4 pontos extras
        }
    }

    public boolean temDigitosRepetidos() {
        String numeroStr = String.valueOf(numero);
        return numeroStr.length() == 2 && numeroStr.charAt(0) == numeroStr.charAt(1);
    }

    public int getNumero() {
        return numero;
    }

    public int getPontos() {
        return pontos;
    }

    @Override
    public int compareTo(Carta outraCarta) {
        return Integer.compare(this.numero, outraCarta.numero);
    }
}
