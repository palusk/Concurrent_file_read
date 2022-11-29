import java.io.*;
import java.util.HashMap;
import java.util.concurrent.Callable;
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
            f = new FileInputStream("D:\\Program Files\\IdeaProjects\\Concurrent_file_read\\src\\file.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    DataInputStream in = new DataInputStream(f);
    BufferedReader r = new BufferedReader(new InputStreamReader(in));

    public Object call() throws Exception {
        lock2.lock();
        try {
            System.out.println("Rozpoczeto");

        int lineNumber = 0;
        String strLine = new String();
        Boolean isCalculated = false;
        String output = new String();

        while ((strLine = r.readLine()) != null && !isCalculated) {
            strLine.trim();
            int indexOfEquals = strLine.indexOf('=');

                boolean found = false;
                for (HashMap.Entry<String, Integer> entry : reservationLine.entrySet()) {
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
            in.close();

            return output;
        }finally {
            lock2.unlock();
            System.out.println("zakonczono");
        }
        }
    }
