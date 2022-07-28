package eprogramarevaccinare;


public class Programari {
	String Nume;
	String Prenume;
	String Centru;
	String CNP;
	String fin;
	String month;
	int data;
	int ora;
	String tip_vaccin;
	
	Programari(String n , String p , String c , String f , String m , int d , int o , String tip , String cn){
		this.Nume=n;
		this.Prenume=p;
		this.Centru=c;
		this.fin=f;
		this.month=m;
		this.data=d;
		this.ora=o;
		this.tip_vaccin=tip;
		this.CNP=cn;
		
		
	}

}
