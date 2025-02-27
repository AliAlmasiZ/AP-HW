import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class Test {
    private static final Pattern LOG_PATTERN = Pattern.compile("\\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})] \\[([A-Z]+)] (.+)");
    private static final Map<String, List<String>> logsByLevel = new HashMap<>();
    private static final List<String> allLogs = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            String log = scanner.nextLine();
            allLogs.add(log);
            Matcher matcher = LOG_PATTERN.matcher(log);
            if (matcher.find()) {
                logsByLevel.computeIfAbsent(matcher.group(2), k -> new ArrayList<>()).add(log);
            }
        }

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (command.trim().isEmpty()) continue;
            processCommand(command);
            System.out.println();
        }
    }

    private static void processCommand(String command) {
        String[] parts = command.split(" ");
        switch (parts[0]) {
            case "COUNT_LEVEL":
                countLevel(parts[1]);
                break;
            case "LEVEL":
                printLogsByLevel(parts[1]);
                break;
            case "CONTAINS":
                printLogsContaining(parts[1]);
                break;
            case "ERROR_TIMESTAMPS":
                printErrorTimestamps(parts.length > 1 && "--reverse".equals(parts[1]));
                break;
            case "TOP_K_LEVEL":
                printTopKLevel(Integer.parseInt(parts[1]), parts[2], parts.length > 3 && "--reverse".equals(parts[3]));
                break;
            case "FREQUENCY_ANALYSIS":
                int topN = (parts.length > 2) ? Integer.parseInt(parts[2]) : 5;
                frequencyAnalysis(topN);
                break;
            case "DATE_RANGE":
                printLogsInDateRange(parts[1], parts[2]);
                break;
            default:
                System.out.println("Invalid command");
                break;
        }
    }

    private static void printLogsInDateRange(String startDate, String endDate) {
        for (String log : allLogs) {
            String logDate = log.substring(1, 11); // استخراج YYYY-MM-DD
            if (logDate.compareTo(startDate) >= 0 && logDate.compareTo(endDate) <= 0) {
                System.out.println(log);
            }
        }
    }

    private static void countLevel(String level) {
        System.out.println(logsByLevel.getOrDefault(level, new ArrayList<>()).size());
    }

    private static void printLogsByLevel(String level) {
        logsByLevel.getOrDefault(level, new ArrayList<>()).forEach(System.out::println);
    }

    private static void printLogsContaining(String keyword) {
        allLogs.stream().filter(log -> log.contains(keyword)).forEach(System.out::println);
    }

    private static void printErrorTimestamps(boolean reverse) {
        List<String> timestamps = logsByLevel.getOrDefault("ERROR", new ArrayList<>()).stream().map(log -> log.substring(1, 20)) // استخراج تاریخ و زمان
                .collect(Collectors.toList());
        if (reverse) Collections.reverse(timestamps);
        timestamps.forEach(System.out::println);
    }

    private static void printTopKLevel(int k, String level, boolean reverse) {
        List<String> logs = logsByLevel.getOrDefault(level, new ArrayList<>());
        if (reverse) {
            logs.stream().limit(k).forEach(System.out::println);
        } else {
            List<String> reversed = new ArrayList<>(logs);
            Collections.reverse(reversed);
            reversed.stream().limit(k).forEach(System.out::println);
        }
    }

    //    private static void frequencyAnalysis(int topN) {
//        Map<String, Integer> wordCount = new HashMap<>();
//        for (String log : allLogs) {
//            Matcher matcher = LOG_PATTERN.matcher(log);
//            if (matcher.find()) {
//                String[] words = matcher.group(3).split("\\s+");
//                for (String word : words) {
//                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
//                }
//            }
//        }
//        wordCount.entrySet().stream()
//                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
//                .limit(topN)
//                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
//    }
    private static void frequencyAnalysis(int topN) {
        Map<String, Integer> wordCount = new HashMap<>();
        for (String log : allLogs) {
            Matcher matcher = LOG_PATTERN.matcher(log);
            if (matcher.find()) {
                String message = matcher.group(3).toLowerCase(); // پیام را به حروف کوچک تبدیل می‌کنیم
                String[] words = message.split("\\s+");
                for (String word : words) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
        }
        wordCount.entrySet().stream().sorted((a, b) -> b.getValue().compareTo(a.getValue())).limit(topN).forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}
