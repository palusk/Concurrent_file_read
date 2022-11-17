import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableCalculator implements Callable {

    String strLine = new String();


   static FileInputStream f;

    static {
        try {
            f = new FileInputStream("D:\\Program Files\\IdeaProjects\\Concurrent_file_read2\\src\\file.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static DataInputStream in = new DataInputStream(f);
   static BufferedReader r = new BufferedReader(new InputStreamReader(in));
    public Object call() throws Exception {
        String allResults = new String();
        String output = new String();
        while ((strLine = r.readLine()) != null) {

                strLine = strLine.replaceAll("=","");
                ONP calculator = new ONP(strLine);
                output = "";
                output += calculator.oblicz();
                allResults += strLine+" = "+output+System.lineSeparator();
        }
        in.close();
        return allResults;
    }

}
