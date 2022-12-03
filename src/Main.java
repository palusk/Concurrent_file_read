import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static String STREAM = "C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read1\\src\\file.txt";
    public static void main(String[] args) throws Exception {

        int threadsNumber = calculateRequiredThreadsNumber();

        MyFutureTask[] taskTab = new MyFutureTask[threadsNumber];
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);

        fillTaskTab(threadsNumber, taskTab);
        startThreads(threadsNumber, taskTab, executor);

        executor.shutdown();

    }
    static public void fillTaskTab(int threadsNumber, MyFutureTask[] taskTab){
        for(int i = 0; i<threadsNumber; i++){
            taskTab[i] = new MyFutureTask (new CallableCalculator() );
        }
    }
    static public void startThreads(int threadsNumber, MyFutureTask[] taskTab, ExecutorService executor){
        for(int i = 0; i<threadsNumber; i++){
            executor.submit(taskTab[i]);
        }
    }
    public static int calculateRequiredThreadsNumber() {
        int lineNumber = 0;
        try {
        InputStream  f = new FileInputStream(STREAM);
        DataInputStream in = new DataInputStream(f);
        BufferedReader r = new BufferedReader(new InputStreamReader(in));

        String strLine = new String();


        boolean isCalculated = false;
        boolean notSolved;

        while ((strLine = r.readLine()) != null) {

            notSolved = isNotSolved(strLine);
            if (notSolved) {
               lineNumber++;
            }

        }
        in.close();
        } catch (Exception e){}
        return lineNumber;
    }
    public static boolean isNotSolved(String strLine){
        strLine.trim();
        int indexOfEquals = strLine.indexOf('=');
        if(strLine.length() == indexOfEquals + 1)
            return true;
        else return false;
    }
}