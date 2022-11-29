import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("How many threads do you want to start?");
        Scanner sc = new Scanner(System.in);
        int threadsNumber = sc.nextInt();
        ExecutorService executor = null;
        MyFutureTask[] taskTab = new MyFutureTask[threadsNumber];
        for(int i = 0; i<threadsNumber; i++){
            executor = Executors.newFixedThreadPool(threadsNumber);
            taskTab[i] = new MyFutureTask (new CallableCalculator());
        }

        for(int i = 0; i<threadsNumber; i++){
            executor.submit(taskTab[i]);
        }
        executor.shutdown();
        }
      }