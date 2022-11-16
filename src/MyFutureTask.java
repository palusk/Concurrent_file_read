import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MyFutureTask extends FutureTask {

    public MyFutureTask(Callable callable) {
        super(callable);
    }

}
