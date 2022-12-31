import java.util.Base64;

public class CBase64 {
	private static void printHelp() {
			System.out.println("Base64 - Encode and Decode\r\n\r\n" +
							   "-e [string] --encode [string]: \t Codifica o texto para Base64\r\n" +
							   "-d [string] --decode [string]: \t Decodifica Base64 para texto\r\n" +
							   "\r\n" +
							   "Exemplo:\r\n" +
							   "\t java cbase64.java -e 'Claudio Almeida Martins'" +
							   "\r\n");
	}

	private static String encode(String txt) {
		try {
			return Base64.getEncoder().encodeToString(txt.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String decode(String txt) {
		try {
			byte[] arg = Base64.getDecoder().decode(txt.replaceAll("[\\r\\n]", ""));
			return new String(arg, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		if (args.length == 2) {
			switch (args[0]) {
				case "-e":
				case "--encode":	
					System.out.println(CBase64.encode(args[1]));
					break;
				case "-d":
				case "--decode":
					System.out.println(CBase64.decode(args[1]));
					break;
				default:
					CBase64.printHelp();				
			}
		} else {
			CBase64.printHelp();
		}
	}
}
