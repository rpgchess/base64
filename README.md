# Base64 Encoder and Decoder 🔐

## 📌 Sobre o Projeto

Utilitário Java para codificação e decodificação Base64. Ferramenta de linha de comando e interface gráfica para converter texto/arquivos entre formato binário e Base64.

## 🎯 Objetivo

Fornecer ferramenta simples para:
- Codificar texto/arquivos em Base64
- Decodificar strings Base64
- Suporte a arquivos binários
- Integração com clipboard

## 🚀 Tecnologias Utilizadas

- **Java** - Linguagem de programação
- **java.util.Base64** - API nativa de Base64 (Java 8+)
- **Swing** - Interface gráfica (opcional)

## 📁 Estrutura do Projeto

```
base64/
├── src/                    # Código-fonte Java
│   ├── Main.java           # Aplicação CLI
│   ├── Base64Tool.java     # Lógica de codificação
│   ├── GUI.java            # Interface gráfica (opcional)
│   └── ...                 # Outros arquivos
└── README.md
```

## 🔧 Funcionalidades

### Codificação (Encode)

- ✅ Texto para Base64
- ✅ Arquivo binário para Base64
- ✅ Suporte a UTF-8
- ✅ Output em arquivo ou console

### Decodificação (Decode)

- ✅ Base64 para texto
- ✅ Base64 para arquivo binário
- ✅ Validação de formato
- ✅ Tratamento de erros

### Recursos Adicionais

- ✅ Interface CLI
- ✅ Interface GUI (Swing)
- ✅ Copiar/Colar do clipboard
- ✅ Processamento em lote

## 💻 Como Usar

### Pré-requisitos

- **JDK 8+** (para `java.util.Base64` nativo)

### Compilação

```bash
# Compilar
javac -d bin src/*.java

# Executar
java -cp bin Main
```

### Linha de Comando (CLI)

```bash
# Codificar texto
java -cp bin Main encode "Hello, World!"
# Output: SGVsbG8sIFdvcmxkIQ==

# Decodificar texto
java -cp bin Main decode "SGVsbG8sIFdvcmxkIQ=="
# Output: Hello, World!

# Codificar arquivo
java -cp bin Main encode-file input.txt output.b64

# Decodificar arquivo
java -cp bin Main decode-file input.b64 output.txt
```

### Interface Gráfica (GUI)

```bash
# Executar interface gráfica
java -cp bin Main --gui
```

## 📚 Implementação

### Classe Base64Tool

```java
import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class Base64Tool {
    
    /**
     * Codifica texto em Base64
     */
    public static String encodeString(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }
    
    /**
     * Decodifica Base64 para texto
     */
    public static String decodeString(String base64) {
        byte[] bytes = Base64.getDecoder().decode(base64);
        return new String(bytes, StandardCharsets.UTF_8);
    }
    
    /**
     * Codifica arquivo em Base64
     */
    public static void encodeFile(String inputPath, String outputPath) 
            throws IOException {
        byte[] fileBytes = Files.readAllBytes(Paths.get(inputPath));
        String base64 = Base64.getEncoder().encodeToString(fileBytes);
        Files.write(Paths.get(outputPath), base64.getBytes());
    }
    
    /**
     * Decodifica arquivo Base64
     */
    public static void decodeFile(String inputPath, String outputPath) 
            throws IOException {
        String base64 = new String(Files.readAllBytes(Paths.get(inputPath)));
        byte[] decoded = Base64.getDecoder().decode(base64);
        Files.write(Paths.get(outputPath), decoded);
    }
    
    /**
     * Valida string Base64
     */
    public static boolean isValidBase64(String str) {
        try {
            Base64.getDecoder().decode(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
```

### Main CLI

```java
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }
        
        String command = args[0];
        
        switch (command) {
            case "encode":
                if (args.length < 2) {
                    System.err.println("Erro: texto não fornecido");
                    return;
                }
                String encoded = Base64Tool.encodeString(args[1]);
                System.out.println(encoded);
                break;
                
            case "decode":
                if (args.length < 2) {
                    System.err.println("Erro: Base64 não fornecido");
                    return;
                }
                try {
                    String decoded = Base64Tool.decodeString(args[1]);
                    System.out.println(decoded);
                } catch (IllegalArgumentException e) {
                    System.err.println("Erro: Base64 inválido");
                }
                break;
                
            case "encode-file":
                // ...
                break;
                
            case "decode-file":
                // ...
                break;
                
            default:
                System.err.println("Comando desconhecido: " + command);
                printUsage();
        }
    }
    
    private static void printUsage() {
        System.out.println("Uso:");
        System.out.println("  base64 encode <texto>");
        System.out.println("  base64 decode <base64>");
        System.out.println("  base64 encode-file <input> <output>");
        System.out.println("  base64 decode-file <input> <output>");
    }
}
```

## 📖 O que é Base64?

**Base64** é um esquema de codificação que converte dados binários em texto ASCII usando 64 caracteres:

- **A-Z** (26 caracteres)
- **a-z** (26 caracteres)
- **0-9** (10 caracteres)
- **+** e **/** (2 caracteres)
- **=** (padding)

### Por que usar Base64?

✅ Transmitir dados binários em texto (email, JSON, XML)
✅ Embedd imagens em HTML/CSS (data URLs)
✅ Armazenar binários em formatos de texto
✅ Codificação de credenciais (HTTP Basic Auth)

### Exemplo

```
Texto:   Hello!
Bytes:   48 65 6c 6c 6f 21
Binário: 01001000 01100101 01101100 01101100 01101111 00100001
Base64:  SGVsbG8h
```

## 📊 Casos de Uso

### 1. Imagens em HTML

```html
<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA..." />
```

### 2. HTTP Basic Authentication

```
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

### 3. JSON com binários

```json
{
  "file": "SGVsbG8sIFdvcmxkIQ==",
  "mime": "application/octet-stream"
}
```

### 4. Email (MIME)

```
Content-Transfer-Encoding: base64

SGVsbG8sIFdvcmxkIQ==
```

## ⚠️ Observações

- Base64 **NÃO É CRIPTOGRAFIA** - é apenas codificação
- Aumenta tamanho em ~33% (4 bytes Base64 = 3 bytes originais)
- Facilmente reversível - não use para segurança
- Para segurança, use AES, RSA, ou outros algoritmos criptográficos

## 🔗 Referências

- [RFC 4648 - Base64](https://tools.ietf.org/html/rfc4648)
- [Java Base64 API](https://docs.oracle.com/javase/8/docs/api/java/util/Base64.html)
- [MDN - Base64](https://developer.mozilla.org/en-US/docs/Glossary/Base64)

## 👨‍💻 Autor

Claudio Almeida

## 📝 Licença

Projeto de utilitário público.

---

> **Dica**: Para criptografia real, use bibliotecas como Bouncy Castle, JCE, ou Jasypt.


> Codificador e Decodificador de Base64 para String feito em Java
