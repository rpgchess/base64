package br.base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class CBase64 {
	private static void printHelp() {
		System.out.println("Base64 - Encode and Decode\r\n\r\n" +
				"USAGE:\r\n" +
				"  java -cp target/classes br.base64.CBase64 [OPTIONS] [VALUE]\r\n\r\n" +
				"OPTIONS:\r\n" +
				"  -e, --encode [string]      Codifica o texto para Base64\r\n" +
				"  -d, --decode [string]      Decodifica Base64 para texto\r\n" +
				"  -ef, --encode-file [path]  Codifica arquivo para Base64\r\n" +
				"  -df, --decode-file [path]  Decodifica Base64 de arquivo\r\n" +
				"  -h, --help                 Mostra esta mensagem\r\n" +
				"\r\n" +
				"EXEMPLOS:\r\n" +
				"  java -cp target/classes br.base64.CBase64 -e \"Claudio Almeida\"\r\n" +
				"  java -cp target/classes br.base64.CBase64 -d \"Q2xhdWRpbyBBbG1laWRh\"\r\n" +
				"  java -cp target/classes br.base64.CBase64 -ef image.png > image.txt\r\n" +
				"  java -cp target/classes br.base64.CBase64 -df image.txt > image.png\r\n" +
				"\r\n");
	}

	private static String encode(String txt) {
		try {
			return Base64.getEncoder().encodeToString(txt.getBytes("UTF-8"));
		} catch (Exception e) {
			System.err.println("Erro ao codificar: " + e.getMessage());
			return "";
		}
	}

	private static String decode(String txt) {
		try {
			byte[] decoded = Base64.getDecoder().decode(txt.replaceAll("[\\r\\n]", ""));
			return new String(decoded, "UTF-8");
		} catch (IllegalArgumentException e) {
			System.err.println("Erro: String Base64 inválida!");
			return "";
		} catch (Exception e) {
			System.err.println("Erro ao decodificar: " + e.getMessage());
			return "";
		}
	}

	private static String encodeFile(String filePath) {
		try {
			byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
			return Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			System.err.println("Erro ao ler arquivo: " + e.getMessage());
			return "";
		}
	}

	private static byte[] decodeFile(String base64Content) {
		try {
			return Base64.getDecoder().decode(base64Content.replaceAll("[\\r\\n]", ""));
		} catch (IllegalArgumentException e) {
			System.err.println("Erro: Conteúdo Base64 inválido!");
			return new byte[0];
		}
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			printHelp();
			return;
		}

		String command = args[0];

		switch (command) {
			case "-e":
			case "--encode":
				if (args.length < 2) {
					System.err.println("Erro: Texto para codificar não fornecido!");
					printHelp();
					return;
				}
				System.out.println(encode(args[1]));
				break;

			case "-d":
			case "--decode":
				if (args.length < 2) {
					System.err.println("Erro: Texto para decodificar não fornecido!");
					printHelp();
					return;
				}
				System.out.println(decode(args[1]));
				break;

			case "-ef":
			case "--encode-file":
				if (args.length < 2) {
					System.err.println("Erro: Caminho do arquivo não fornecido!");
					printHelp();
					return;
				}
				String encoded = encodeFile(args[1]);
				if (!encoded.isEmpty()) {
					System.out.println(encoded);
				}
				break;

			case "-df":
			case "--decode-file":
				if (args.length < 2) {
					System.err.println("Erro: Caminho do arquivo não fornecido!");
					printHelp();
					return;
				}
				try {
					String content = new String(Files.readAllBytes(Paths.get(args[1])), "UTF-8");
					byte[] decoded = decodeFile(content);
					if (decoded.length > 0) {
						System.out.write(decoded);
					}
				} catch (IOException e) {
					System.err.println("Erro ao ler arquivo: " + e.getMessage());
				}
				break;

			case "-h":
			case "--help":
				printHelp();
				break;

			default:
				System.err.println("Erro: Comando desconhecido: " + command);
				printHelp();
		}
	}
}
