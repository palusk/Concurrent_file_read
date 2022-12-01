import javax.crypto.spec.PSource;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyFutureTask extends FutureTask {
    public MyFutureTask(Callable callable) {
        super(callable);
    }
    static Lock lock = new ReentrantLock();

    static ArrayList<String> File;
    static {try {   File = getFile(); } catch (Exception e) { throw new RuntimeException(e); }}

    @Override
    protected void done() {
        try {

            FileWriter f = null;
            f = new FileWriter("C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read1\\src\\file.txt");
            BufferedWriter out = new BufferedWriter(f);
            String temp = new String();

            lock.lock();
                writeToFile(out, temp);
            lock.unlock();


        }catch(Exception e){}

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
    public void writeToFile(BufferedWriter out, String temp) throws Exception{
        try {
            File.set(getIndex(), File.get(getIndex()) + this.get().toString());

            for (String e : File) {
                temp += e + System.lineSeparator();
            }
            out.write(temp);
            out.close();

        }catch (Exception e){
            System.out.println(e);
        }
    }

}

