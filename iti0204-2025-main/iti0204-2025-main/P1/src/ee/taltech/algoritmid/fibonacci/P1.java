package ee.taltech.algoritmid.fibonacci;

import java.math.BigInteger;

public class P1 {

    /**
     * Compute the Fibonacci sequence number.
     * @param n The number of the sequence to compute.
     * @return The n-th number in Fibonacci series.
     */
    public String iterativeF(int n) {
        if (n == 0) return "0";
        if (n == 1) return "1";

        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        BigInteger c;

        for (int i = 2; i <= n; i++) {
            c = a.add(b);
            a = b;
            b = c;
        }

        return b.toString();
    }

    public static void main(String[] args) throws Exception {
        P1 p1 = new P1();

        if (!p1.iterativeF(3).equals("2")) {
            throw new Exception("There is a mistake in your solution.");
        }
    }

}
