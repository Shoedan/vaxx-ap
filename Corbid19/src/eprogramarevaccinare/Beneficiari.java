package eprogramarevaccinare;

import java.util.Calendar;

public class Beneficiari {
	String nume;
	String prenume;
	String CNP;
	String serie_id;
	String numar_id;
	String Judet;
	String Localitate;
	boolean dizabilitate;
	int age;
	
	Beneficiari (String n , String p , String cn , String si , String ni , String j , String l , boolean d){
		this.nume=n;
		this.prenume=p;
		this.CNP=cn;
		this.serie_id=si;
		this.numar_id=ni;
		this.Judet=j;
		this.Localitate=l;
		this.dizabilitate=d;
		Calendar c = Calendar.getInstance();
		double helper = c.YEAR - (((Float.valueOf(cn)/10000000000f)%100)+1900);
		this.age = (int)helper;
	}
	
	void afisare () {
		System.out.println(nume + prenume + CNP + serie_id + numar_id + Judet + Localitate);
	}
}
