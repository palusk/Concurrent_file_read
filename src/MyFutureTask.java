import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MyFutureTask extends FutureTask {

    public MyFutureTask(Callable callable) {
        super(callable);
    }
   public String dane = new String();
    @Override
    protected void done() {

        FileWriter f = null;
        try {
            f = new FileWriter("C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read3\\src\\file.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter out = new BufferedWriter(f);
        try {
            dane = this.get().toString();
            out.write(getFile()+dane + System.lineSeparator());
            out.close();
        }catch (Exception e) {
        }

    }

    public String getFile() throws Exception{

        FileInputStream ff = new FileInputStream("C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read3\\src\\file.txt");
        DataInputStream in = new DataInputStream(ff);
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        String strLine = new String();
        String out = new String();
        while ((strLine = r.readLine()) != null) {
            out += strLine + System.lineSeparator();
        }
        return out;
    }

    }

