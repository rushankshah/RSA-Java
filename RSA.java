import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSA {
	private BigInteger p;
	private BigInteger q;
	private BigInteger n;
	private BigInteger phi;
	private BigInteger e;
	private BigInteger d;
	private int maxLength = 1024;
	private Random R;

	public RSA() {
		R = new Random();
		p = BigInteger.probablePrime(maxLength, R);
		q = BigInteger.probablePrime(maxLength, R);
		n = p.multiply(q);
		phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		e = BigInteger.probablePrime(maxLength / 2, R);
		while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
			e.add(BigInteger.ONE);
		}
		d = e.modInverse(phi);
	}
	public byte[] encryptMessage(byte[] plainText)
    {
        return (new BigInteger(plainText)).modPow(e, n).toByteArray();
    }
	public byte[] decryptMessage(byte[] cipherText)
    {
        return (new BigInteger(cipherText)).modPow(d, n).toByteArray();
    }
	public static void main(String[] args) {
		RSA rsa = new RSA();
		Scanner sc = new Scanner(System.in);
		String input;
		System.out.println("Enter plainText");
		input = sc.next();
		System.out.println("Encrypted message: "+bytesToString((rsa.encryptMessage(input.getBytes()))));
		System.out.println("Decrypted message: "+new String(rsa.decryptMessage(rsa.encryptMessage(input.getBytes()))));
		sc.close();
	}
	private static String bytesToString(byte[] givenText)
    {
        String temp = "";
        for (byte b : givenText)
        {
            temp += Byte.toString(b);
        }
        return temp;
    }
}
