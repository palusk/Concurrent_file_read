import java.util.*;


public class ONP {

    private String wyrazenie;//zawiera wyrażenie w pierwotnej postaci

    private String onp;//wyrażenie w odwrotnej notacji polskiej

    private String nieLiczby="+-*/^()";

    /**

     Stwórz obiekt, który dokona konwersji wyrażenia na odwrotną notację polską.

     * @param wyrazenie

     * oznacza wyrażenie które ma zostac skonwertowane do odwrotnej notacji polskiej.

     */

    public ONP(String wyrazenie) {

        this.wyrazenie = wyrazenie;

        onp = "";

        toONP();//wywołaj konwersję wyrażenia na oonp

    }

    /**

     * Konwersja wyrażenia na odwrotną notację polską.

     * 1 - dzielimy wyrażenie infiksowe na części na podstawie separatorów

     * 2 - dopóki są elementy w wyrażeniu wejściowym

     * 2.1 - pobieramy element

     * 2.2 - jeżeli element jest operatorem

     * 2.2.1 - sprawdzemy priorytety

     * 2.2.2 - odkładamy operator na stos

     * 2.3 - jeżeli element jest nawiasem otwierającym

     * 2.3.1 - odłóż na stos nawias otwierający

     * 2.4 - jeżeli element jest nawiasem zamykającym

     * 2.4.1 - ściągamy operatory ze stosu, aż do nawiasu otwierajęcego

     * 2.4.2 - ściągnij ze stosu

     * 2.4.3 - ściągnij już niepotrzebny nawias otwierający

     * 2.5 - jeżeli element nie jest operatorem ani nawiasem dodajemy go do wyrażenia postfiksowego

     * 3 - ściągamy ze stosu pozostałe operatory i dodajemy je do wyrażenia postfiksowego

     * 3.1 - ściągnij i dopisz do wyrażenia onp

     @param wyrazenie

      * dokonuje konwersji wyrażenia na ONP.

     */

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

    /**

     * Obliczenie priorytetu operatora:

     * + lub - = 1

     * * lub / = 2

     * ^ = 3

     * pozostałe = 0

     * @param operator

     * operator, dla którego zostanie wyznaczony priorytet.

     * @return

     */

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

    /**

     * Zwróć wyrażenie w postaci Odwrotnej Notacji Polskiej

     *

     */

    public @Override String toString() //zwróć wyrażenie w postaci onp

    {

        return onp;

    }

    /**

     * Dokonaj obliczenia zapisanego w odwrotnej notacji polskiej i zwróć wynik.

     * @return

     * Zwraca wynik z zapisanego wyrażenia w postaci ONP.

     */

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