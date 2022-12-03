import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyFutureTask extends FutureTask {
    static String STREAM = "C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read1\\src\\file.txt";
    public MyFutureTask(Callable callable) {
        super(callable);
    }
    static Lock lock = new ReentrantLock();

    static ArrayList<String> File;
    static {try {   File = getFile(); } catch (Exception e) { throw new RuntimeException(e); }}

    @Override
    protected  void done() {
        try {

            FileWriter f = null;
            f = new FileWriter(STREAM);
            BufferedWriter out = new BufferedWriter(f);
            String temp = new String();

            lock.lock();
            try {


                writeToFile(out, temp);
                out.close();
            }finally {
                out.close();
                    if (((ReentrantLock)lock).isHeldByCurrentThread())
                        lock.unlock();
                }


        }catch(Exception e){}

    }

    public static ArrayList<String> getFile() throws Exception{
        ArrayList<String> contextFile = new ArrayList<String>();
        FileInputStream fin;
        {
            try {
                fin = new FileInputStream(STREAM);
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

    public int getIndex() {
  return CallableCalculator.reservationLine.get(Thread.currentThread().getName());
    }

    public void writeToFile(BufferedWriter out, String temp) throws Exception{

        try{
            File.set(getIndex(), File.get(getIndex()) + this.get().toString());

            for (String e : File) {
                temp += e + System.lineSeparator();
            }
            out.write(temp);
        }catch (Exception e){
        }
    }
}
