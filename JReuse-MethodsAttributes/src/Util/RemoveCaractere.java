package Util;

public class RemoveCaractere {

	public static String removeCaracteres(String str, String charsRemove, String delimiter) {

		if (charsRemove != null && charsRemove.length() > 0 && str != null) {

			String[] remover = charsRemove.split(delimiter);

			for (int i = 0; i < remover.length; i++) {
				// System.out.println("i: " + i + " ["+ remover[i]+"]");
				if (str.indexOf(remover[i]) != -1) {
					str = str.replace(remover[i], "");
				}
			}
		}
		return str;
	}

}
