import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final Scanner stdinScanner = new Scanner(System.in);


        PasswordBreaker passwordBreaker =  new PasswordBreaker(stdinScanner.nextLine().trim(), stdinScanner.nextInt());
        stdinScanner.close();
        passwordBreaker.run();
//
        int specialPasswords = passwordBreaker.getSpecialPasswords();
        String correctPass = passwordBreaker.getCorrectPass();
        if(correctPass == null)
            correctPass = "NOT FOUND";

        System.out.println(specialPasswords);
        System.out.println(correctPass);

        System.exit(0);


    }
}
