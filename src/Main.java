import java.io.FileReader;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception
    {

        System.out.println("How many threads do you want to start?");
        Scanner sc = new Scanner(System.in);
        int threadsNumber = sc.nextInt();

        ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);


            Callable callable = new CallableCalculator(1);

            Future<String> future = executorService.submit(callable);
            System.out.println(future.get());

    }
}