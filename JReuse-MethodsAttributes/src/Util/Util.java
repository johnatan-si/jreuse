package Util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Util {
	
	public static void write(String fileName, String content, boolean overwrite) {
		try {
			File file = new File(fileName);

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), overwrite);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

		} catch (IOException e) {
			System.out.println("Error writting file at project "+ fileName);
		}
	}
	
	public static void writeError(String fileName, String content, boolean overwrite, Exception e) {
		try {
			File file = new File(fileName);

			if (!file.exists()) {
				file.createNewFile();
			}

			PrintWriter pw = new PrintWriter(file);
			pw.write(content);
		    e.printStackTrace(pw);
		    pw.close();

		} catch (IOException ioe) {
			System.out.println("Error writting file at project "+ fileName);
		}
	}

	public static String readFile(String filePath) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			FileInputStream is = new FileInputStream(filePath);
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}

		} catch (IOException e) {
			System.out.println("Erro ler arquivo: " + filePath);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
	/**
		Gera um inteiro randomico entre zero (inclusive) e max (exclusive).
	*/
	public static int random (int max) {
		return (int)(Math.random() * max);
	}
	
	public static String[] splitFilePath(String path) {
		String [] pathName = new String[2];
		int index =  path.lastIndexOf(File.separator);
		if(index != -1) {
			pathName[0] = path.substring(0, index);
			pathName[1] = path.substring(index + 1);
			return pathName;
		}
		return null;
	}
	
}
