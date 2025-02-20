import java.util.*;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        scanner.close();
        // حذف فاصله‌های اضافی اطراف "-"
        String[] words = Arrays.stream(input.split("-")).map(String::trim).toArray(String[]::new);
        int n = words.length;
        int[] prefixLengths = new int[n];
        int[] suffixLengths = new int[n];
        // محاسبه کمترین تعداد حروف برای تشخیص از ابتدا
        for (int i = 0; i < n; i++) {
            prefixLengths[i] = getMinUniquePrefixLength(words, words[i]);
        }

        // محاسبه کمترین تعداد حروف برای تشخیص از انتها
        for (int i = 0; i < n; i++) {
            suffixLengths[i] = getMinUniqueSuffixLength(words, words[i]);
        }

        // چاپ خروجی
        System.out.println(arrayToString(prefixLengths));
        System.out.println(arrayToString(suffixLengths));
    }

    private static int getMinUniquePrefixLength(String[] words, String word) {
        for (int len = 1; len <= word.length(); len++) {
            String prefix = word.substring(0, len);
            if (isUniquePrefix(words, prefix, word)) {
                return len;
            }
        }
        return word.length();
    }

    private static boolean isUniquePrefix(String[] words, String prefix, String word) {
        int count = 0;
        for (String w : words) {
            if (w.startsWith(prefix)) {
                count++;
                if (count > 1) return false;
            }
        }
        return true;
    }

    private static int getMinUniqueSuffixLength(String[] words, String word) {
        for (int len = 1; len <= word.length(); len++) {
            String suffix = word.substring(word.length() - len);
            if (isUniqueSuffix(words, suffix, word)) {
                return len;
            }
        }
        return word.length();
    }

    private static boolean isUniqueSuffix(String[] words, String suffix, String word) {
        int count = 0;
        for (String w : words) {
            if (w.endsWith(suffix)) {
                count++;
                if (count > 1) return false;
            }
        }
        return true;
    }

    private static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int num : arr) {
            sb.append(num).append(" ");
        }
        return sb.toString().trim();
    }
}
