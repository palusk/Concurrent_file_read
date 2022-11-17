import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MyFutureTask extends FutureTask {

    public MyFutureTask(Callable callable) {
        super(callable);
    }

   static FileWriter f;

    static {
        try {
            f = new FileWriter("D:\\Program Files\\IdeaProjects\\Concurrent_file_read2\\src\\file.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static BufferedWriter out = new BufferedWriter(f);
   public String dane = new String();
    @Override
    protected void done() {
        try {
            dane = this.get().toString();
            out.write(dane + System.lineSeparator());
            out.close();
        }catch (Exception e) {
            System.out.println("boje sie");
        }

    }

    }

