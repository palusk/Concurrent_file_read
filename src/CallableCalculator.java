import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CallableCalculator implements Callable {

    static HashMap<String,Integer> reservationLine = new HashMap<String,Integer>();

    Lock lock2 = new ReentrantLock();
    Condition txtWritten2 = lock2.newCondition();

    FileInputStream f;
    {
        try {
            f = new FileInputStream("C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read1\\src\\file.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    DataInputStream in = new DataInputStream(f);
    BufferedReader r = new BufferedReader(new InputStreamReader(in));

    public Object call() throws Exception {
        int lineNumber = 0;
        String strLine = new String();
        Boolean isCalculated = false;
        String output = new String();

        while ((strLine = r.readLine()) != null && !isCalculated) {
            strLine.trim();
            int indexOfEquals = strLine.indexOf('=');

            lock2.lock();
            boolean found = false;
            for(HashMap.Entry<String, Integer> entry : reservationLine.entrySet()) {
                if (lineNumber == entry.getValue())
                    found = true;
            }
                if (strLine.length() == indexOfEquals + 1 && !found) {
                    reservationLine.put(Thread.currentThread().getName(), lineNumber);
                    strLine = strLine.replaceAll("=", "");
                    ONP calculator = new ONP(strLine);
                    output += calculator.oblicz();
                    System.out.println(Thread.currentThread().getName() + "  -  " + strLine + " " + output);
                    isCalculated = true;


                } else lineNumber++;


            }
            lock2.unlock();
            in.close();
            return output;
        }
    }
