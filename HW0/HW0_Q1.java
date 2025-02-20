import java.util.*;

/* STDID : 403100043 */
public class HW0_Q1 {
    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        String inputLine = sc.nextLine();
        sc.close();
        String[] words = inputLine.split("[- ]+");

        for (String word : words) {
            if (word.isEmpty()) continue;
            System.out.printf("%d ", getLeftMatchCount(word, words));
        }
        System.out.println();
        for (String word : words) {
            if (word.isEmpty()) continue;
            System.out.printf("%d ", getRightMatchCount(word, words));
        }
        System.out.println();

    }

    private static int getLeftMatchCount(String word, String[] words) {
        int count = 1;
        for (int i = 0; i < words.length; i++) {
            if (count == word.length()) break;
            if (count > words[i].length() || words[i].equals(word)) continue;

            String sub1 = word.substring(0, count);
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