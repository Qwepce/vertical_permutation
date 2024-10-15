import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Decrypt {
    public static void main(String[] args) throws IOException {
        String cryptoFilePath = "crypto.txt";
        String keyFile = "key.txt";
        String decryptFilePath = "decrypt.txt";

        String solutionCryptoFilePath = "solutionMessage.txt";
        String solutionKeyFile = "solutionKey.txt";
        String solutionDecryptFilePath = "solutionDecrypt.txt";

        String encryptedMessage = encryptMessage(solutionCryptoFilePath);
        List<Integer> orderOfReading = Encrypt.readOrderOfReadingCols(solutionKeyFile);

        System.out.println(encryptedMessage.length());
        System.out.println(orderOfReading.size());

        List<String> result = formTable(encryptedMessage, orderOfReading);

        decryptMessage(solutionDecryptFilePath, result);

    }

    private static void decryptMessage(String decryptFilePath, List<String> result) throws IOException {
        try {
            Files.write(Paths.get(decryptFilePath), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String encryptMessage(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }

        return sb.toString();
    }

    private static List<String> formTable(String encryptedMessage, List<Integer> orderOfReading) {
        int rowNum = (double) (encryptedMessage.length() / orderOfReading.size()) % 1.0 > 0 ?
                encryptedMessage.length() / orderOfReading.size() + 1 : encryptedMessage.length() / orderOfReading.size();
        int colNum = (double) (encryptedMessage.length() / rowNum) % 1.0 > 0 ?
                encryptedMessage.length() / rowNum : encryptedMessage.length() / rowNum + 1;

        List<String> resultedString = new ArrayList<>();

        char[][] symbols = new char[rowNum][colNum];
        int idx = 0;

        for (int col : orderOfReading) {
            for (int row = 0; row < rowNum; row++) {
                if (row * orderOfReading.size() + col < encryptedMessage.length()) {
                    symbols[row][col] = encryptedMessage.charAt(idx++);
                }
            }
        }
        int counter = 0;

        for (char[] chars : symbols) {
            StringBuilder sb = new StringBuilder();
            for (char c : chars) {
                sb.append(c);
            }
            if (counter % 2 == 0) {
                resultedString.add(sb.reverse().toString());
            } else {
                resultedString.add(sb.toString());
            }
            counter++;
        }

        return resultedString;
    }

}
