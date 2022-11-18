import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyFutureTask extends FutureTask {
    static boolean clearFile = false;
    public MyFutureTask(Callable callable) {
        super(callable);
    }
    @Override
    protected void done() {
        try {

            String dane = this.get().toString();
            FileWriter f = null;
            if(!clearFile) {
                clearFile = true;
                f = new FileWriter("C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read6\\src\\file.txt");
            } else {
                f = new FileWriter("C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read6\\src\\file.txt",true);
            }
            BufferedWriter out = new BufferedWriter(f);
            out.append(this.get().toString());
            out.close();
            }catch(Exception e){}

        }
    }

