import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableCalculator implements Callable {




   static FileInputStream f;

    static {
        try {
            f = new FileInputStream("C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read3\\src\\file.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    static DataInputStream in = new DataInputStream(f);
    static BufferedReader r = new BufferedReader(new InputStreamReader(in));

    public Object call() throws Exception {
        String strLine = new String();
        String allResults = new String();
        String output = new String();
        while ((strLine = r.readLine()) != null) {

                strLine = strLine.replaceAll("=","");
                ONP calculator = new ONP(strLine);
                output = "";
                output += calculator.oblicz();
                allResults += strLine+" = "+output+System.lineSeparator();
            System.out.println(Thread.currentThread().getName() + "  -  "+ strLine);
        }
        in.close();
        return allResults;
    }

}
