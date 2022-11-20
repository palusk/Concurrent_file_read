import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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
    @Override
    protected void done() {
        try {
            String dane = this.get().toString();
            FileWriter f = null;
            lock.lock();

            f = new FileWriter("C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read6\\src\\file.txt");
            BufferedWriter out = new BufferedWriter(f);
            out.write(dane + this.get().toString());
            dane += this.get().toString();
            out.close();

            lock.unlock();
        }catch(Exception e){}

    }
}
    }

