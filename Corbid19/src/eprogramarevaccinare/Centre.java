package eprogramarevaccinare;

public class Centre {
	String Judet;
	String Localitate;
	String Nume;
	int locuri;
	
	Centre(String loc ,String nume , String jude , String spots ){
		this.Localitate=loc;
		this.Judet=jude;
		this.Nume=nume;
		this.locuri=Integer.parseInt(spots);		
	}

}
