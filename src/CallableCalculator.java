import java.io.*;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CallableCalculator implements Callable {

    static HashMap<String,Integer> reservationLine = new HashMap<String,Integer>();

    static Lock lock = new ReentrantLock();
    Condition txtWritten2 = lock.newCondition();

    InputStream f = getInputStream();
    DataInputStream in = new DataInputStream(f);
    BufferedReader r = new BufferedReader(new InputStreamReader(in));


    public Object call() throws Exception {

        int lineNumber = 0;

        String strLine = new String();
        String output = new String();

        boolean isCalculated = false;
        boolean notReserved;
        boolean notSolved;

            while ((strLine = r.readLine()) != null && !isCalculated) {

                notSolved = isNotSolved(strLine);
                lock.lock();
                System.out.println("rozpoczeto");
                notReserved = isNotReserved(lineNumber);
                if (notReserved && notSolved) {
                    reserveLine(lineNumber);
                }
                System.out.println("zakonczono");
                lock.unlock();
                if (notReserved && notSolved) {
                    strLine = strLine.replaceAll("=", "");
                    ONP calculator = new ONP(strLine);
                    output += calculator.oblicz();
                    printResult(strLine, output);

                    isCalculated = true;
                }else lineNumber++;

            }
            in.close();
            return output;
        }

    public FileInputStream getInputStream(){
        FileInputStream f;
        {
            try {
                f = new FileInputStream("D:\\Program Files\\IdeaProjects\\Concurrent_file_read\\src\\file.txt");
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
