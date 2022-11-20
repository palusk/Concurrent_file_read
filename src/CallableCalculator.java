import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableCalculator implements Callable {

    static ArrayList<Boolean> reservationLine = new ArrayList<Boolean>();


   FileInputStream f;

    {
        try {
            f = new FileInputStream("C:\\Users\\mateu\\IdeaProjects\\Concurrent_file_read6\\src\\file.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    DataInputStream in = new DataInputStream(f);
   BufferedReader r = new BufferedReader(new InputStreamReader(in));

    public Object call() throws Exception {
        int lineNumber = 0;
        String strLine = new String();
        Boolean isCalculated = false;
        String output = new String();
        while ((strLine = r.readLine()) != null && !isCalculated) {
            strLine.trim();
            int indexOfEquals = strLine.indexOf('=');
            if(strLine.length() == indexOfEquals+1){

                reservationLine
                strLine = strLine.replaceAll("=","");
                ONP calculator = new ONP(strLine);
                output += calculator.oblicz();
                System.out.println(Thread.currentThread().getName() + "  -  "+ strLine);
                isCalculated = true;


            }else lineNumber++;
        }
        in.close();
        return output;
    }

}
