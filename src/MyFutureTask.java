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
     Lock lock = new ReentrantLock();
     Condition txtWritten = lock.newCondition();

    static ArrayList<String> File;
    static {
        try {

            File = getFile();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void done() {
        try {
          //  ArrayList<String> File = getFile();
            File.set(getIndex(), File.get(getIndex())+this.get().toString());
         //   System.out.println(File.toString());

for(String e:File){
    System.out.println(e);
}
            FileWriter f = null;
            lock.lock();
            f = new FileWriter("D:\\Program Files\\IdeaProjects\\Concurrent_file_read\\src\\file.txt");
            BufferedWriter out = new BufferedWriter(f);
            String temp = new String();
            for(String e:File){

                temp += e+System.lineSeparator();
            }
            out.write(temp);

            out.close();

            lock.unlock();
        }catch(Exception e){}

    }



    public static ArrayList<String> getFile() throws Exception{
        ArrayList<String> contextFile = new ArrayList<String>();
        FileInputStream fin;
        {
            try {
                fin = new FileInputStream("D:\\Program Files\\IdeaProjects\\Concurrent_file_read\\src\\file.txt");
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
                f = new FileInputStream("D:\\Program Files\\IdeaProjects\\Concurrent_file_read\\src\\file.txt");
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

