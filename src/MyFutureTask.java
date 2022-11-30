import javax.crypto.spec.PSource;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyFutureTask extends FutureTask {
    static boolean clearFile = false;
    public MyFutureTask(Callable callable) {
        super(callable);
    }
     static Lock lock = new ReentrantLock();
     //Condition txtWritten = lock.newCondition();

    static ArrayList<String> File;
    static {
        try {

            File = getFile();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected synchronized void done() {
        lock.lock();
        try {
        try {
            File.set(getIndex(), File.get(getIndex())+this.get().toString());


//for(String e:File){
//    System.out.println(e);
//}

            FileWriter f = null;

                f = new FileWriter("C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read1\\src\\file.txt");
                BufferedWriter out = new BufferedWriter(f);
                String temp = new String();
                for (String e : File) {

                    temp += e + System.lineSeparator();
                }
                out.write(temp);

                out.close();



        }catch(Exception e){}

    }finally {
        if (((ReentrantLock)lock).isHeldByCurrentThread())
            lock.unlock();
    }
    }



    public static ArrayList<String> getFile() throws Exception{
        ArrayList<String> contextFile = new ArrayList<String>();
        FileInputStream fin;
        {
            try {
                fin = new FileInputStream("C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read1\\src\\file.txt");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        DataInputStream in = new DataInputStream(fin);
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        String strLine = new String();
        while((strLine = r.readLine()) != null){
            contextFile.add(strLine);
        }
        return contextFile;
    }

    public int getIndex() throws Exception {

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

        String strLine = new String();
        Boolean founded = false;
        int foundedIndex = 0;
        while ((strLine = r.readLine()) != null && !founded) {
            strLine.trim();
            int indexOfEquals = strLine.indexOf('=');
            if(strLine.length() == indexOfEquals+1 && CallableCalculator.reservationLine.get(Thread.currentThread().getName()) == foundedIndex) {
                founded = true;
            }else foundedIndex++;
        }
        in.close();
        return foundedIndex;
    }

}

