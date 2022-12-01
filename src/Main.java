import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws Exception {

        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();



        int threadsNumber = getThreadsNumber();

        MyFutureTask[] taskTab = new MyFutureTask[threadsNumber];
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);

        fillTaskTab(threadsNumber, taskTab,  condition1,  condition2, lock);
        startThreads(threadsNumber, taskTab, executor);

        executor.shutdown();

    }

    static public int getThreadsNumber(){
        System.out.println("How many threads do you want to start?");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
    static public void fillTaskTab(int threadsNumber, MyFutureTask[] taskTab,  Condition condition1,  Condition condition2, Lock lock){
        for(int i = 0; i<threadsNumber; i++){
            taskTab[i] = new MyFutureTask (new CallableCalculator(condition1, condition2, lock), condition1, condition2, lock);
        }
    }
    static public void startThreads(int threadsNumber, MyFutureTask[] taskTab, ExecutorService executor){
        for(int i = 0; i<threadsNumber; i++){
            executor.submit(taskTab[i]);
        }
    }
}