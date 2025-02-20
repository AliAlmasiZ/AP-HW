import java.util.*;

/* STDID : 403100043 */
public class HW0_Q1 {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();
        scanner.close();
        String[] words = inputLine.split("[- ]+");

        for (String currentWord : words) {
            if (currentWord.isEmpty()) continue;
            System.out.printf("%d ", getLeftMatchCount(currentWord, words));
        }
        System.out.println();
        for (String currentWord : words) {
            if (currentWord.isEmpty()) continue;
            System.out.printf("%d ", getRightMatchCount(currentWord, words));
        }
        System.out.println();

    }

    private static int getLeftMatchCount(String currentWord, String[] words) {
        int count = 1;
        for (int i = 0; i < words.length; i++) {
            if (count == currentWord.length()) break;
            if (count > words[i].length() || words[i].equals(currentWord)) continue;

            String sub1 = currentWord.substring(0, count);
            String sub2 = words[i].substring(0, count);

            if (sub1.equals(sub2)) {
                count++;
                i--;
            }

        }
        return count;
    }

    private static int getRightMatchCount(String currentWord, String[] words) {
        int count = 1;
        for (int i = 0; i < words.length; i++) {
            if (count == currentWord.length()) break;
            if (count > words[i].length() || words[i].equals(currentWord)) continue;

            String sub1 = currentWord.substring(currentWord.length() - count);
            String sub2 = words[i].substring(words[i].length() - count);

            if (sub1.equals(sub2)) {
                count++;
                i--;
            }
        }
        return count;
    }
}