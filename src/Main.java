import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception
    {
        System.out.println("How many threads do you want to start?");
        Scanner sc = new Scanner(System.in);
        int threadsNumber = sc.nextInt();

        Callable[] callableTab = new CallableCalculator[threadsNumber];
        FutureTask[] futureTab = new FutureTask[threadsNumber];
        for(int i = 0; i<threadsNumber; i++){
            callableTab[i] = new CallableCalculator(i);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);


        for(int i = 0; i<threadsNumber; i++) {
            futureTab[i] = (FutureTask) executorService.submit(callableTab[i]);
            System.out.println(futureTab[i].get());
        }

        //@@@@@@@@@@@@@@@@ UWAGA NIŻEJ WIRUS

//        FileWriter f = new FileWriter("D:\\Program Files\\IdeaProjects\\Concurrent_file_read\\src\\file.txt");
//        BufferedWriter out = new BufferedWriter(f);
//        String dane = new String();
//
//        boolean[] tab = new boolean[threadsNumber];
//
//        for(int i = 0; i<threadsNumber; i++){
//            tab[i] = false;
//        }
//
//        boolean help = true;
//
//        while(help){
//            try {
//                for(int i = 0; i<threadsNumber; i++) {
//                    if(futureTab[i].isDone() && tab[i] == false){
//                        dane = futureTab[i].get().toString();
//                        out.write(dane);
//                        tab[i] = true;
//                    }
//                }
//                help = false;
        for(boolean x : tab){
//                    if(x == false) help = true;
//                }
//            }catch (Exception e){
//                System.out.println("Jest zle");
//            }
//        }

        }
    }