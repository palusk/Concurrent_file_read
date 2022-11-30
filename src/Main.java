import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {

        int threadsNumber = getThreadsNumber();

        MyFutureTask[] taskTab = new MyFutureTask[threadsNumber];
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);

        fillTaskTab(threadsNumber, taskTab);
        startThreads(threadsNumber, taskTab, executor);

        executor.shutdown();

    }

    static public int getThreadsNumber(){
        System.out.println("How many threads do you want to start?");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
    static public void fillTaskTab(int threadsNumber, MyFutureTask[] taskTab){
        for(int i = 0; i<threadsNumber; i++){
            taskTab[i] = new MyFutureTask (new CallableCalculator());
        }
    }
    static public void startThreads(int threadsNumber, MyFutureTask[] taskTab, ExecutorService executor){
        for(int i = 0; i<threadsNumber; i++){
            executor.submit(taskTab[i]);
        }
    }
}