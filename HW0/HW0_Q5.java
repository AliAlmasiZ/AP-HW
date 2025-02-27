import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* STDID : 403100043 */

public class HW0_Q5 {
    static List<Log> logList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] lines = new String[n];
        for (int i = 0; i < n; i++) {
            while ((lines[i] = sc.nextLine()).isEmpty()) { // checks to not be empty line
                continue;
            }
            logList.add(makeLog(lines[i]));
        }
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] words = line.split("\\s+");
            switch (words[0]) {
                case "LEVEL" -> {
                    handleLevel(words[1]);
                    System.out.println();
                }
                case "DATE_RANGE" -> {
                    handleDateRange(words[1], words[2]);
                    System.out.println();
                }
                case "ERROR_TIMESTAMPS" -> {
                    boolean flag = words.length > 1 && words[1].equals("--reverse");
                    handleErrorTimeStamps(flag);
                    System.out.println();

                }
                case "CONTAINS" -> {
                    handleContains(words[1]);
                    System.out.println();
                }
                case "COUNT_LEVEL" -> {
                    handleCountLevel(words[1]);
                    System.out.println();
                }
                case "FREQUENCY_ANALYSIS" -> {
                    int m = 5;
                    if (words.length > 2 && words[1].equals("--top")) m = Integer.parseInt(words[2]);
                    handleFrequencyAnalysis(m);
                    System.out.println();
                }
                case "TOP_K_LEVEL" -> {
                    boolean isReverse = words.length > 3 && words[3].equals("--reverse");

                    handleTop_K_Level(words[2], isReverse, Integer.parseInt(words[1]));
                    System.out.println();

                }
                default -> {
                }
//                while (false) {
//                    System.out.println("ERROR");
//                    System.out.println();
            }

        }
    }

    private static Log makeLog(String message) {
        Log log = new Log();
        log.all = message;
        String pattern = "\\[(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})] \\[(\\w+)] (.+)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(message);
        if (matcher.matches()) {
            log.year = Integer.parseInt(matcher.group(1));
            log.month = Integer.parseInt(matcher.group(2));
            log.day = Integer.parseInt(matcher.group(3));
            log.hour = Integer.parseInt(matcher.group(4));
            log.minute = Integer.parseInt(matcher.group(5));
            log.second = Integer.parseInt(matcher.group(6));
            log.type = matcher.group(7);
            log.content = matcher.group(8);
        }
        return log;
    }

    private static void handleLevel(String type) {
        for (Log log : logList) {
            if (log.type.equals(type)) {
                System.out.println(log.all);
            }
        }
    }

    private static void handleDateRange(String date1, String date2) {
        for (Log log : logList) {
            if (isBetweenDateRange(log, date1, date2)) {
                System.out.println(log.all);
            }
        }
    }

    private static void handleErrorTimeStamps(boolean isReverse) {
        if (isReverse) {
            for (int i = logList.size() - 1; i >= 0; i--) {
                if (logList.get(i).type.equals("ERROR")) {
                    Log log = logList.get(i);
                    System.out.format("%04d-%02d-%02d %02d:%02d:%02d\n", log.year, log.month, log.day, log.hour, log.minute, log.second);
                }
            }
        } else {
            for (Log log : logList) {
                if (log.type.equals("ERROR")) {
                    System.out.format("%04d-%02d-%02d %02d:%02d:%02d\n", log.year, log.month, log.day, log.hour, log.minute, log.second);
                }
            }
        }
    }

    private static void handleContains(String keyWord) {
        for (Log log : logList) {
            String temp = log.content;
//            temp = temp.toLowerCase();
            if (temp.contains(keyWord)) {
                System.out.println(log.all);
            }
        }
    }

    private static void handleCountLevel(String type) {
        int result = 0;
        for (Log log : logList) {
            if (log.type.equals(type)) result++;
        }
        System.out.println(result);
    }

    private static void handleFrequencyAnalysis(int n) {
        Map<String, Integer> map = new HashMap<>();
        for (Log log : logList) {
//            log.content = log.content.toLowerCase();
            for (String word : log.content.split("\\s+")) {
                word = word.toLowerCase();
                map.put(word, map.getOrDefault(word, 0) + 1);
            }
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
//        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        list.sort(HW0_Q5::frequencyCompare);
        for (int i = 0; i < n; i++) {
            System.out.format("%s: %d\n", list.get(i).getKey(), list.get(i).getValue());
        }
    }

    private static int frequencyCompare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
        int compare = entry2.getValue().compareTo(entry1.getValue());
        if(compare != 0) return compare;
        return entry1.getKey().compareTo(entry2.getKey());
    }

    private static void handleTop_K_Level(String type, boolean isReverse, int num) {
        List<Log> list = new ArrayList<>(logList);

        list.sort(Log::compareDate);
        int printed = 0;
        if (isReverse) {
            for (int i = 0; i < list.size(); i++) {
                if (printed == num) break;
                if (list.get(i).type.equals(type)) {
                    System.out.println(list.get(i).all);
                    printed++;
                }
            }
        } else {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (printed == num) break;
                if (list.get(i).type.equals(type)) {
                    System.out.println(list.get(i).all);
                    printed++;
                }
            }
        }
    }

    private static class Log {
        String type;
        int year;
        int month;
        int day;
        int hour;
        int minute;
        int second;
        String content;
        String all;

        public static int compareDate(Log log1, Log log2) {
            if (log1.year > log2.year) return 1;
            if (log1.year < log2.year) return -1;
            if (log1.month > log2.month) return 1;
            if (log1.month < log2.month) return -1;
            if (log1.day > log2.day) return 1;
            if (log1.day < log2.day) return -1;
            if (log1.hour > log2.hour) return 1;
            if (log1.hour < log2.hour) return -1;
            if (log1.minute > log2.minute) return 1;
            if (log1.minute < log2.minute) return -1;
            return Integer.compare(log1.second, log2.second);
        }
    }


    private static boolean isBetweenDateRange(Log log, String date1, String date2) {
        String pattern = "(\\d{4})-(\\d{2})-(\\d{2})";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher1 = regex.matcher(date1);
        Matcher matcher2 = regex.matcher(date2);
//        System.out.println(date1 + "-" + date2);
        int year1 = 0, month1 = 0, day1 = 0;
        int year2 = 0, month2 = 0, day2 = 0;
        if (matcher1.matches() && matcher2.matches()) {
            year1 = Integer.parseInt(matcher1.group(1));
            month1 = Integer.parseInt(matcher1.group(2));
            day1 = Integer.parseInt(matcher1.group(3));
            year2 = Integer.parseInt(matcher2.group(1));
            month2 = Integer.parseInt(matcher2.group(2));
            day2 = Integer.parseInt(matcher2.group(3));
        }

//        if (matcher1.matches() && matcher2.matches()) {
        if (isBetween(log.year % 100, year1, year2)) {
            return true;
        } else if (log.year % 100 == year1 || log.year == year2) {
            if (isBetween(log.month, month1, month2)) {
                return true;
            } else if (log.month == month1 || log.month == month2) {
                return isBetween(log.day, day1, day2) || log.day == day1 || log.day == day2;
            }
        }
        return false;
//        }
//        System.exit(400);

//        return true;
    }

    private static boolean isBetween(int x, int a, int b) {
        return Math.min(a, b) < x && x < Math.max(a, b);
    }

}