import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class PasswordBreaker {
    private Scanner fileScanner;

    {
        try {
            fileScanner = new Scanner(new File("passwords.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private final Queue<String> passwords;
    private final int n;
    private final String targetHash;
    private final AtomicReference<String> correctPass;
    private final AtomicInteger specialPasswords;


    private class CheckTask implements Callable<Boolean> {
        private final String password;

        public CheckTask(String password) {
            this.password = password;
        }

        private boolean isSpecial(String hex) {
            int test = 3;
            for(int i = 0; i < test; i++) {
                if(hex.length() <= i)
                    return false;
                char c = hex.charAt(i);
                if(c >= '0' && c <= '6')
                    return true;
            }
            return false;
        }

        @Override
        public Boolean call() {
            if(password == null)
                return false;
            String hashed = CryptoHash.hashString(password);
            if (isSpecial(hashed)) {
                specialPasswords.addAndGet(1);
            }
            if (Objects.equals(targetHash, hashed)) {
                return  correctPass.compareAndSet(null, password);
            }
            return false;
        }
    }


    public PasswordBreaker(String targetHash, int n){
        this.n = n;
        this.targetHash = targetHash;

        this.correctPass = new AtomicReference<>(null);
        this.specialPasswords = new AtomicInteger(0);

        passwords = new LinkedList<>();


//        try(BufferedReader reader = new BufferedReader(new FileReader("passwords.txt"))) {
//            String pass;
//            while((pass = reader.readLine()) != null) {
//                passwords.add(pass.trim());
//            }
//        } catch (IOException e) {
//            throw new RuntimeException();
//        }

        while (fileScanner.hasNext()) {
            String password = fileScanner.nextLine().trim();
            passwords.add(password);
        }
        fileScanner.close();
    }

    public void run() {

        ExecutorService executor = Executors.newFixedThreadPool(n);
        while(!passwords.isEmpty()) {
            boolean shouldBreak = false;
            List<Future<Boolean>> futures = new ArrayList<>();
//            List<CheckTask> tasks = new ArrayList<>();
            for(int i = 0; i < n && !passwords.isEmpty(); i++) {
                futures.add(executor.submit(new CheckTask(passwords.poll())));
//                tasks.add(new CheckTask(passwords.poll()));
            }
//            try {
//                futures = executor.invokeAll(tasks);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            for (int i =  0; i < n; i++) {
                try {
                    if(futures.get(i).get()) {
                        shouldBreak = true;
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            futures.clear();
//            tasks.clear();
            if(shouldBreak) {
//                executor.shutdown();
                break;
            }
        }
        executor.shutdown();

    }

    public String getCorrectPass() {
        return correctPass.get();
    }

    public int getSpecialPasswords() {
        return specialPasswords.get();
    }
}
