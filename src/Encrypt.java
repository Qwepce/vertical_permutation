import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class   Encrypt {

    public static void main(String[] args) throws IOException {

        // Формирование таблицы с учетом задания
        List<String> orderOfStrings = formArray("message.txt");
        // Получение порядка считывания столбцов
        List<Integer> orderOfReadingColumns = readOrderOfReadingCols("key.txt");

        encryptText(orderOfStrings, orderOfReadingColumns);
    }

    private static void encryptText(List<String> orderOfStrings, List<Integer> orderOfReading){
        StringBuilder sb = new StringBuilder();
        String filePath = "crypto.txt";

        for(int col : orderOfReading){
            for(String row : orderOfStrings){
                if( col < row.length()) {
                    sb.append(row.charAt(col));
                }
            }
        }

        try(FileWriter writer = new FileWriter(filePath)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> formArray(String filePath) throws IOException {
        List<String> fileStrings = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            int i = 0;

            while((line = br.readLine()) != null){
                StringBuilder sb = new StringBuilder(line);
                if(i % 2 == 0){
                    fileStrings.add(sb.reverse().toString());
                } else {
                    fileStrings.add(line);
                }
                i++;
            }
        }

        return fileStrings;
    }

    protected static List<Integer> readOrderOfReadingCols(String filePath) throws IOException {
        List<Integer> orderOfReading = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;

            while((line = br.readLine()) != null){
                String[] chars = line.split(", ");
                for(String stringNumber : chars){
                    orderOfReading.add(Integer.parseInt(stringNumber));
                }
            }
        }

        return orderOfReading;
    }

}
