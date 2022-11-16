import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableCalculator implements Callable {

    String strLine = new String();
    int lineNumber = 0;

    int whichLine = 0;
    public CallableCalculator(int whichLine){
        this.whichLine = whichLine;
    }

    public Object call() throws Exception
    {
        String output = "1";
        FileInputStream f = new FileInputStream("D:\\Program Files\\IdeaProjects\\Concurrent_file_read\\src\\file.txt");
        DataInputStream in = new DataInputStream(f);
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        while ((strLine = r.readLine()) != null) {
            if(lineNumber == whichLine) {
                output = String.valueOf(ONP.calculate(strLine));
                break;
            }
            lineNumber++;
        }
        in.close();
        return output+Thread.currentThread().getName();
    }

}