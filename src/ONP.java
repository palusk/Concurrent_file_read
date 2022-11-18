import java.util.*;


public class ONP {

    private String wyrazenie;//zawiera wyrażenie w pierwotnej postaci

    private String onp;//wyrażenie w odwrotnej notacji polskiej

    private String nieLiczby="+-*/^()";


    public ONP(String wyrazenie) {

        this.wyrazenie = wyrazenie;

        onp = "";

        toONP();//wywołaj konwersję wyrażenia na oonp

    }

    private void toONP()

    {

// eliminateNakedMinus();

        Stack<String> stos = new Stack<String>();

        StringTokenizer st = new StringTokenizer(wyrazenie, "+-*/^()", true);//1

        while(st.hasMoreTokens()) //2

        {

            String s = st.nextToken();//2.1

            if( s.equals("+") || s.equals("*") || s.equals("-") || s.equals("/") || s.equals("^")) //2.2

            {

                while(!stos.empty() && priorytet(stos.peek()) >= priorytet(s))//2.2.1

                {

                    onp += stos.pop() + " ";

                }

                stos.push(s);//2.2.2

            }

            else if(s.equals("(")) //2.3

            {

                stos.push(s);//2.3.1

            }

            else if(s.equals(")"))//2.4

            {

                while(!stos.peek().equals("(")) //2.4.1

                {

                    onp += stos.pop() + " ";//2.4.2

                }

                stos.pop();//2.4.3

            }

            else //2.5

            { onp += s + " "; }

        }

        while(!stos.empty())//3

        {

            onp += stos.pop() + " ";//3.1

        }

    }

    private boolean czyNieLiczba(char z)

    {

        for(int i=0;i<nieLiczby.length();i++)

        {

            if(nieLiczby.charAt(i)==z)

            { return true; }

        }

        return false;

    }

    public static int priorytet(String operator)

    {

        if(operator.equals("+") || operator.equals("-")) {return 1;}

        else if(operator.equals("*") || operator.equals("/")) {return 2;}

        else if(operator.equals("^")){return 3;}

        else {return 0;}//pozostałe 0

    }


    public @Override String toString() //zwróć wyrażenie w postaci onp

    {

        return onp;

    }

    public double oblicz()

    {

        String wejscie = onp+" =";

        Stack<Double> stos = new Stack<Double>();//przechowuje wyniki pośrednie

        double a=0;//przechowuje dane ze stosu

        double b=0;//przechowuje dane ze stosu

        double w=0;//wynik operacji arytmetycznej

        String buduj="";

        String spacja=" ";

        char sp=' ';

        int licznik=0;

        do{//Krok 1: Czytaj el z we

            char czar=wejscie.charAt(licznik);

            if(czar=='+' || czar == '-' || czar == '*' || czar == '/' || czar == '^')//Krok 2: Jeśli el nie jest liczbą, to idź do kroku 5

            {

                if(!stos.empty()){

                    b=stos.pop();//Krok 6: Pobierz ze stosu dwie liczby a i b

                    a=stos.pop();

                    if(czar=='+'){w=a+b;}//Krok 7: Wykonaj nad liczbami a i b operację określoną przez el i umieść wynik w w

                    else if(czar=='-'){w=a-b;}

                    else if(czar=='*'){w=a*b;}

                    else if(czar=='/'){w=a/b;}

                    else if(czar=='^')

                    {

                        if(b==0)

                        { w=1;

                        } else {

                            w=a;

                            int licz=1;

                            while(licz<(int)b)

                            {

                                w*=w;

                                licz++;

                            }

                        }

                    }

                    stos.push(w);//Krok 8: Umieść w na stosie

                }

            }

            else if(czar == sp)//Krok 3: Umieść el na stosie

            {

                if(buduj.compareTo("")!=0){

                    double tmp = Double.parseDouble(buduj);

                    stos.push(tmp);

                    buduj="";

                }

            }

            else if(czar=='=')//Krok 5: Jeśli el jest znakiem '=', to idź do kroku 10

            {

                if(!stos.empty()){

                    w=stos.pop();//Krok 10: Prześlij na wyjście zawartość wierzchołka stosu

                    break;

                }

            }

            else if(czar>='0' && czar <='9')//buduj liczbe

            {

                buduj+=czar;

            }

            licznik++;

        }while(licznik<wejscie.length());//Krok 4: Idź do kroku 1

        return w;

    }

}