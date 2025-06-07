import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("passwords.txt"));
        List<String> passwords = new ArrayList<>();
        while (scanner.hasNext()) {
            passwords.add(scanner.nextLine());
        }
        scanner.close();

        for(int i = 0; i < passwords.size(); i++) {
            String current = passwords.get(i);
            int repeat = 0;
            for (int j = i + 1; j < passwords.size(); j++) {
                if(passwords.get(j).equals(current))
                    repeat++;
            }
            if(repeat > 0) {
                System.out.println(current + " repeat : " + repeat);
            }
        }
    }
}
