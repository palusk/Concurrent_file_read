import java.io.*;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CallableCalculator implements Callable {

    static HashMap<String,Integer> reservationLine = new HashMap<String,Integer>();

    Condition condition1;
    Condition condition2;
    Lock lock;

    InputStream f = getInputStream();
    DataInputStream in = new DataInputStream(f);
    BufferedReader r = new BufferedReader(new InputStreamReader(in));

    CallableCalculator(Condition condition1, Condition condition2, Lock lock){
        this.condition1 = condition1;
        this.condition2 = condition2;
        this.lock = lock;
    }

    public Object call() throws Exception {

        int lineNumber = 0;

        String strLine = new String();
        String output = new String();

        boolean isCalculated = false;
        boolean notReserved;
        boolean notSolved;

        lock.lock();
        try {
            while ((strLine = r.readLine()) != null && !isCalculated) {
                condition1.await();
                notSolved = isNotSolved(strLine);


                notReserved = isNotReserved(lineNumber);
                if (notReserved && notSolved) {
                    reserveLine(lineNumber);
                }



                if (notReserved && notSolved) {

                    strLine = strLine.replaceAll("=", "");
                    ONP calculator = new ONP(strLine);
                    output += calculator.oblicz();
                    printResult(strLine, output);

                    isCalculated = true;
                } else lineNumber++;
            }
            in.close();
            condition2.signal();
        }finally {
            if(((ReentrantLock)lock).isHeldByCurrentThread())
            lock.unlock();
        }

            return output;

        }

    public FileInputStream getInputStream(){
        FileInputStream f;
        {
            try {
                f = new FileInputStream("C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read1\\src\\file.txt");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return f;
    }

    public boolean isNotReserved(int lineNumber){
        for(HashMap.Entry<String, Integer> entry : reservationLine.entrySet()) {
            if (lineNumber == entry.getValue())
                return false;
        }
        return true;
    }

    public boolean isNotSolved(String strLine){
        strLine.trim();
        int indexOfEquals = strLine.indexOf('=');
        if(strLine.length() == indexOfEquals + 1)
            return true;
        else return false;
    }

    public void reserveLine(int lineNumber){
        reservationLine.put(Thread.currentThread().getName(), lineNumber);
    }

    public void printResult(String strLine, String output){
        System.out.println(Thread.currentThread().getName() + "  -  " + strLine + " " + output);
    }

}
