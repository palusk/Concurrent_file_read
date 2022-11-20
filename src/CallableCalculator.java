import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableCalculator implements Callable {




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
        String strLine = new String();
        String allResults = new String();
        String output = new String();
        while ((strLine = r.readLine()) != null) {{
            strLine.trim();
            int indexOfEquals = strLine.indexOf('=');
            if(strLine.length() == indexOfEquals+1){


                strLine = strLine.replaceAll("=","");
                ONP calculator = new ONP(strLine);
                output += calculator.oblicz();
                allResults += strLine+" = "+output+"\t"+Thread.currentThread().getName()+System.lineSeparator();
                System.out.println(Thread.currentThread().getName() + "  -  "+ strLine);


            } else strLine = r.readLine();}
        }
        in.close();
        return allResults;
    }

}
