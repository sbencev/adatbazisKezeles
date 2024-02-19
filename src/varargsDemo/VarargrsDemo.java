package varargsDemo;

public class VarargrsDemo {

	public static void main(String[] args) {

		Integer szam = 10, szam2 = 27, szam3 = 55;

		System.out.println(formazoVarargs("Helyettesitsuk ide be a %s szamot.", szam));
		System.out.println(formazoVarargs("Helyettesitsuk ide be a %s es %s szamokat.", szam, szam2));
		System.out.println(formazoVarargs("Helyettesitsuk ide be a %s es %s es %s szamokat.", szam, szam2, szam3));

	}

	public static String formazoVarargs(String szoveg, Object... formazoParameterek) {

		for (int i = 0; i < formazoParameterek.length; i++) {

			szoveg = szoveg.replaceFirst("%s", String.valueOf(formazoParameterek[i]));

		}
		return szoveg;
		// Egy metodusban max 1 Varargs lehet es utolso parameter kell legyen.
	}

}
