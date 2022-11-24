import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CallableCalculator implements Callable {

    static ArrayList<Integer> reservationLine = new ArrayList<Integer>();

    Lock lock2 = new ReentrantLock();
    Condition txtWritten2 = lock2.newCondition();

    FileInputStream f;
    {
        try {
            f = new FileInputStream("D:\\Program Files\\IdeaProjects\\Concurrent_file_read\\src\\file.txt");
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
            for (int x : reservationLine) {
                if (x == lineNumber) {
                    found = true;
                    break;
                }
            }
                if (strLine.length() == indexOfEquals + 1 && !found) {
                    reservationLine.add(lineNumber);

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
