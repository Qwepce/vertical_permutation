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

        String myCryptoFilePath = "solutionMessage.txt";
        String myKeyFile = "solutionKey.txt";
        String myDecryptFilePath = "solutionDecrypt.txt";

        String encryptedMessage = encryptMessage(myCryptoFilePath);
        List<Integer> orderOfReading = Encrypt.readOrderOfReadingCols(myKeyFile);
        List<String> result = formTable(encryptedMessage, orderOfReading);

        decryptMessage(myDecryptFilePath, result);
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
        int rowNum = (encryptedMessage.length() + orderOfReading.size() - 1) / orderOfReading.size();
        int colNum = orderOfReading.size();

        System.out.println(rowNum);
        System.out.println(colNum);

        List<String> resultedString = new ArrayList<>();

        char[][] symbols = new char[rowNum][colNum];
        int idx = 0;

        if(colNum * orderOfReading.size() > encryptedMessage.length()) {
            int diff = rowNum * orderOfReading.size() - encryptedMessage.length();

            for(int col = colNum - 1; col >= colNum - diff; col--){
                symbols[rowNum - 1][col] = '*';
            }
        }

        for (int col = 0; col < orderOfReading.size(); col++) {
            for (int row = 0; row < rowNum; row++) {
                if (idx < encryptedMessage.length()) {
                    symbols[row][col] = encryptedMessage.charAt(idx++);
                }
            }
        }

        for(int i = 0; i < rowNum; i++) {
            for(int j = 0; j < colNum; j++) {
                System.out.print(symbols[i][j]);
            }
            System.out.println();
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