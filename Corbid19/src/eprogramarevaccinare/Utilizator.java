package eprogramarevaccinare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Utilizator {
	String username;
	String password;
	ArrayList<Beneficiari> bene = new ArrayList<Beneficiari>();
	ArrayList<Programari> program = new ArrayList<Programari>();
	
	Utilizator(String u , String p) throws SQLException{
		this.username=u;
		this.password=p;
		String databaseURL = "jdbc:ucanaccess://DB.accdb";
		Connection connection = DriverManager.getConnection(databaseURL);
		String sql = "SELECT * FROM Beneficiari";
		Statement state = connection.createStatement();
		ResultSet result = state.executeQuery(sql);
		while (result.next())
			if (result.getString("AttachedUser").equals(username))
				bene.add(new Beneficiari(result.getString("Nume"),result.getString("Prenume"),result.getString("CNP"),result.getString("SerieID"),result.getString("NumarID"),result.getString("Judet"),result.getString("Localitate"),Boolean.getBoolean(result.getString("Dizabilitate"))));
		sql = "SELECT * FROM Programari";
		result = state.executeQuery(sql);
		while (result.next())
			for (int i=0;i<bene.size();i++)
				if (result.getString("AttachedUsers").equals(username))
					if (bene.get(i).CNP.equals(result.getString("CNP"))) {
						boolean f;
						program.add(new Programari(bene.get(i).nume,bene.get(i).prenume,result.getString("Centru"),result.getString("Finalizata"),result.getString("Luna"),Integer.parseInt(result.getString("Data")),Integer.parseInt(result.getString("Ora")),result.getString("Tip_Vaccin"),bene.get(i).CNP));
						}
	}
	
	void revalidate_database() throws SQLException {
		bene.clear();
		program.clear();
		String databaseURL = "jdbc:ucanaccess://DB.accdb";
		Connection connection = DriverManager.getConnection(databaseURL);
		String sql = "SELECT * FROM Beneficiari";
		Statement state = connection.createStatement();
		ResultSet result = state.executeQuery(sql);
		while (result.next())
			if (result.getString("AttachedUser").equals(username))
				bene.add(new Beneficiari(result.getString("Nume"),result.getString("Prenume"),result.getString("CNP"),result.getString("SerieID"),result.getString("NumarID"),result.getString("Judet"),result.getString("Localitate"),Boolean.getBoolean(result.getString("Dizabilitate"))));
		sql = "SELECT * FROM Programari";
		result = state.executeQuery(sql);
		while (result.next())
			for (int i=0;i<bene.size();i++)
				if (result.getString("AttachedUsers").equals(username))
					if (bene.get(i).CNP.equals(result.getString("CNP"))) {
						boolean f;
						program.add(new Programari(bene.get(i).nume,bene.get(i).prenume,result.getString("Centru"),result.getString("Finalizata"),result.getString("Luna"),Integer.parseInt(result.getString("Data")),Integer.parseInt(result.getString("Ora")),result.getString("Tip_Vaccin"),bene.get(i).CNP));
						}
	}
	
	Utilizator(){
		this.username=null;
		this.password=null;
	}
	
	void set_username(String u) {this.username=u;};
	void set_password(String p) {this.password=p;};
	String get_username() { return this.username;};
	String get_password() { return this.password;};
	
	void add_bene(String nume,String prenume,String CNP,String serie_id,String numar_id,String Judet,String Localitate,boolean diza) throws SQLException {
		bene.add(new Beneficiari(nume,prenume,CNP,serie_id,numar_id,Judet,Localitate,diza));
		String databaseURL = "jdbc:ucanaccess://DB.accdb";
		Connection connection = DriverManager.getConnection(databaseURL);
		String sql = "INSERT INTO Beneficiari (Nume, Prenume, CNP, SerieID, NumarID, Judet, Localitate, Dizabilitate, AttachedUser) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";            
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, nume);
        preparedStatement.setString(2, prenume);
        preparedStatement.setString(3, CNP);
        preparedStatement.setString(4, serie_id);
        preparedStatement.setString(5, numar_id);
        preparedStatement.setString(6, Judet);
        preparedStatement.setString(7, Localitate);
        if (diza)
        	preparedStatement.setString(8, "1");
        else
        	preparedStatement.setString(8, "0");
        preparedStatement.setString(9, this.username);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
	}
	
	
	
	void Afisare () {
		System.out.println("Username = " + get_username());
		System.out.println("Password = " + get_password());
	}

}
