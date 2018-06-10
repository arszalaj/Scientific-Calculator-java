import java.util.Scanner;


class Przedmiot 
{
		public Przedmiot(){};

   		public String nazwa;
		public int wynik;
		public boolean rozszerzony;

		public Przedmiot(String nazwa, int wynik, boolean roz)
		{
			this.nazwa = nazwa;
			this.wynik = wynik;
			this.rozszerzony = roz;
		}	
}

class Projekt
{
   	

 public static void main ( String[] args )
 {
System.out.println("Asystent maturzysty");
Scanner podaj = new Scanner(System.in);
System.out.println("Podaj swoje imie:");
String imie = podaj.next();
System.out.println("Podaj swoje nazwisko:");
String nazwisko = podaj.next();

System.out.println("Podaj iloœæ zdawanych przedmiotów: ");
int rozmiar = podaj.nextInt();
Przedmiot[] przedmioty = new Przedmiot[rozmiar];

System.out.println("Podaj swoje wyniki z matury:");
System.out.println("j.polski poziom podstawowy:");
int polski  = podaj.nextInt();

System.out.println("matematyka poziom podstawowy:");
int matematyka = podaj.nextInt();

if((polski >=30) && (matematyka>=30))
{
	System.out.println("Matura zdana");
	przedmioty[0] = new Przedmiot("j. polski",polski, false);
	przedmioty[1] = new Przedmiot("matematyka",matematyka, false);
	
}
else
{
	System.out.println("Matura nie zdana");
	return;
}

//Dodawanie pozosta³ych przedmiotów

for(int i = 2; i < przedmioty.length; ++i)
{
przedmioty[i] = new Przedmiot();
System.out.println("Podaj nazwe kolejnego przedmiotu:");
przedmioty[i].nazwa = podaj.next();

System.out.println("Podaj swoj wynik z przedmiotu:");
przedmioty[i].wynik = podaj.nextInt();

System.out.println("Czy przedmiot rozszerzony [T/N]:");
String roz = "";
while(!(roz.equals("T") || roz.equals("t") || roz.equals("N") || roz.equals("n")))
{
	roz = podaj.next();
}
if(roz.equals("T") || roz.equals("t"))
{
	przedmioty[i].rozszerzony = true;
}
else
{
	przedmioty[i].rozszerzony = false;
}
}

int sumaPkt = 0;
String[] przedmiotySGH = {"matematyka","geografia","angielski"};
double wagaPodst = 0.7;
double wagaRoz = 1;

for(Przedmiot p : przedmioty)
{
	for(String s : przedmiotySGH)
	{
		if(s.equals(p.nazwa.toLowerCase()))
		{
			if(p.rozszerzony)
			{
				sumaPkt += Math.round(wagaRoz*p.wynik);
				if(s.equals("matematyka"))
					sumaPkt -= Math.round(wagaPodst *przedmioty[1].wynik);
				if(s.equals("j.polski"))
					sumaPkt -= Math.round(wagaPodst *przedmioty[0].wynik);
			}
			else
			{
				sumaPkt += Math.round(wagaPodst*p.wynik);
			}
		}
	}
}
System.out.println("Maturzysta: " + imie + " " +  nazwisko);
System.out.print("Twoj wynik:");
System.out.println(sumaPkt);
if(sumaPkt >= 200)
{
	System.out.println("Dostales sie na studia!");
}
else
{
	System.out.println("Niestety nie dostales sie na studia.");
}
for(Przedmiot p : przedmioty)
{
	System.out.print("Nazwa: " + p.nazwa + " Wynik:" + p.wynik);
	if(p.rozszerzony)
		System.out.println(" Rozszerzony");
	else
		System.out.println(" Podstawowy");
}

}
}