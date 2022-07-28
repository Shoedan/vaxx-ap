package eprogramarevaccinare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class Menu {
	static ArrayList<Utilizator> Users = new ArrayList<Utilizator>();
	static String weird_stuff;
	static ArrayList <Centre> centre = new ArrayList<Centre>();
	
	Menu() throws NumberFormatException, IOException, SQLException {
		String databaseURL = "jdbc:ucanaccess://DB.accdb";
		Connection connection = DriverManager.getConnection(databaseURL);
		String sql = "SELECT * FROM Accounts";
		Statement state = connection.createStatement();
		ResultSet result = state.executeQuery(sql);
		while (result.next()) 
			Users.add(new Utilizator(result.getString("Email"),result.getString("Pass")));
		sql = "SELECT * FROM Centre_de_Vaccinare";
		result = state.executeQuery(sql);
		while (result.next())
			centre.add(new Centre(result.getString("Locatie"),result.getString("Centru"),result.getString("Judet"),result.getString("Locuri")));
	    }
	
	static void start_app() {
		JFrame f=new JFrame("Starting Page");
		final JLabel label = new JLabel();            
	    label.setBounds(20,150,200,50); 
	    JLabel l2=new JLabel("Ce doresti sa faci ?");    
	    l2.setBounds(120,50, 300,80);
	    l2.setFont(label.getFont().deriveFont(16f));
	    JButton b = new JButton("Log-in");
	    JButton b2 = new JButton("Creeaza cont.");
	    b.setBounds(50,120, 150,30);
	    b2.setBounds(200,120, 150,30);
	    f.getContentPane().add(label);f.getContentPane().add(l2); f.getContentPane().add(b); f.getContentPane().add(b2); 
	    f.setSize(400,400);    
	    f.getContentPane().setLayout(null); 
	    f.setLocationRelativeTo(null);
	    f.setVisible(true);
	    b2.addActionListener(new ActionListener() { 
	   	 		@Override
	               public void actionPerformed(ActionEvent e) {       
	                  try {
						Creeare_cont();
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
	                  f.dispose();
	               }
	            });
	    b.addActionListener(new ActionListener() { 
   	 		@Override
               public void actionPerformed(ActionEvent e) {       
                  Login_window();
                  f.dispose();
               }
            });
		}
	
		static void Login_window() {
			JFrame f=new JFrame("Starting Page");
			final JLabel label = new JLabel();            
		    label.setBounds(20,150,200,50); 
		    JLabel l2=new JLabel("Username");
		    JLabel l3=new JLabel("Password");
		    l2.setBounds(120,50, 100,80);
		    l3.setBounds(120,100, 100,80);
		    JTextField t1 = new JTextField();
		    JTextField t2 = new JTextField();
		    JLabel error_message = new JLabel();
		    error_message.setHorizontalAlignment(SwingConstants.CENTER);
		    error_message.setBounds(100,300,200,20);
		    t1.setBounds(200,80, 100,20);
		    t2.setBounds(200,130,100,20);
		    JButton b = new JButton("Autentifica-te.");
		    b.setBounds(130,200,150,20);
		    f.getContentPane().add(label);f.getContentPane().add(l2);f.getContentPane().add(l3); f.getContentPane().add(t1); f.getContentPane().add(t2); f.getContentPane().add(b);f.getContentPane().add(error_message);
		    f.setSize(400,400);    
		    f.getContentPane().setLayout(null); 
		    f.setLocationRelativeTo(null);
		    f.setVisible(true);
		    b.addActionListener(new ActionListener() { 
	   	 		@Override
	               public void actionPerformed(ActionEvent e) {       
	                  for (int i=0;i<Users.size();i++)
	                	  if (Users.get(i).username.equals(t1.getText()) && Users.get(i).password.equals(t2.getText())) {
	                		  options_menu(Users.get(i));
	                  		  f.dispose();}
	                	  else 	                	  
	                		  error_message.setText("Email/parola gresita !");
	               }
	            });
		}
		
		
		static void options_menu(Utilizator U) {				/// "MAIN FRAME"
			JFrame f = new JFrame("Meniu Optiuni");
			JButton tabel = new JButton ("Vizualizeaza tabel Beneficiari inregistrati.");
			tabel.setBounds(82,126,400,20);
			JButton adauga = new JButton("Adauga beneficiar");
			adauga.setBounds(82,157,400,20);
			JLabel error_message = new JLabel("Numar maxim de beneficiari atins");	
			error_message.setBounds(20,400,400,20);
			f.getContentPane().add(tabel);f.getContentPane().add(adauga);
			f.setSize(600,400);
			f.getContentPane().setLayout(null);
			f.setVisible(true);
			f.setLocationRelativeTo(null);
			JLabel titlu = new JLabel("Optiunile Valabile");
		    titlu.setHorizontalAlignment(SwingConstants.CENTER);
		    titlu.setBounds(125, 25, 310, 70);
		    f.getContentPane().add(titlu);
		    JButton programari = new JButton("Programari");
		    programari.setBounds(82, 188, 400, 20);
		    f.getContentPane().add(programari);
		    JButton logout = new JButton("Logout");
		    logout.setBounds(82, 219, 400, 20);
		    f.getContentPane().add(logout);
		    programari.addActionListener(new ActionListener() { 
	   	 		@Override
	               public void actionPerformed(ActionEvent e) {       
	                  try {
						finalizare_programare(U);
						f.dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
	               }
	            });
		    logout.addActionListener(new ActionListener() { 
	   	 		@Override
	               public void actionPerformed(ActionEvent e) {       
	                  start_app();
	                  f.dispose();
	               }
	            });
		    tabel.addActionListener(new ActionListener() { 
	   	 		@Override
	               public void actionPerformed(ActionEvent e) {       
						tabel_beneficiari2(U);
						f.dispose();
					
	               }
	            });
		    adauga.addActionListener(new ActionListener() { 
	   	 		@Override
	               public void actionPerformed(ActionEvent e) {       
	                if (U.bene.size()>=10)
	                	f.getContentPane().add(error_message);
					else
						try {
							completare_profil(U);
							f.dispose();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
	                	
	               }
	            });
		    
			/// Resume Here Create option menu (Check table(beneficiari) , Modify Profile etc)
			
		}
		
		
		static void finalizare_programare(Utilizator U) throws SQLException {
			String databaseURL = "jdbc:ucanaccess://DB.accdb";
			Connection connection = DriverManager.getConnection(databaseURL);
			JFrame f = new JFrame("Tabel Programari");
			DefaultTableModel model = new DefaultTableModel();
			JTable table = new JTable(model);
			model.addColumn("Nume");
			model.addColumn("Prenume");
			model.addColumn("CNP");
			model.addColumn("Centru");
			model.addColumn("Finalizata");
			model.addColumn("Luna");
			model.addColumn("Data");
			model.addColumn("Ora");
			model.addColumn("Tip Vaccin");
			model.addColumn("");
			U.program.forEach( (n) -> { model.addRow(new Object[]{n.Nume,n.Prenume,n.CNP,n.Centru,n.fin,n.month,n.data,n.ora,n.tip_vaccin,"Finalizeaza/Sterge"}); } ); /// Lambda 
			Action move_on = new AbstractAction()
			{
			    public void actionPerformed(ActionEvent e)
			    {	
			    	int b = table.getSelectedRow();
			    	String checker = (String)table.getValueAt(b,4);
			    	if (checker.equals("NU")) {
			    		String value = (String)table.getValueAt(b,2);
			    		String cen = (String)table.getValueAt(b, 3);
			        	date_chooser(U,value,cen);
			    	}
			    	else {
			    		String sql3 = "DELETE FROM Programari WHERE CNP=?";
			    		String value = (String)table.getValueAt(b, 2);
			    		try {
							PreparedStatement st = connection.prepareStatement(sql3);
							st.setString(1, value);
							st.executeUpdate();
							U.revalidate_database();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
			    		
			    	}
			    }
			};	
			ButtonColumn buttonColumn = new ButtonColumn(table, move_on, 9);
			buttonColumn.setMnemonic(KeyEvent.VK_D);
			f.getContentPane().setLayout(null);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(0, 0, 1195, 385);
			f.getContentPane().add(scrollPane);
			JButton back = new JButton("Back");
			back.setBounds(440, 400, 90, 25);
			f.getContentPane().add(back);
			back.addActionListener(new ActionListener() { 
	   	 		@Override
	               public void actionPerformed(ActionEvent e) {       
	                  options_menu(U);
	                  f.dispose();
	               }
	            });
			f.setSize(1200,500);    
		    f.setVisible(true);
		    f.setLocationRelativeTo(null);
			}
		
		
		static void date_chooser(Utilizator U , String CNP,String cen) {
			String[] months = {"Ianuarie", "Februarie" , "Martie" , "Aprilie" , "Mai" , "Iunie" , "Iulie" , "August" , "Septembrie" , "Octombrie" , "Noiembrie" , "Decembrie"};
			JFrame f = new JFrame();
			f.getContentPane().setLayout(null);	
			JLabel luna = new JLabel("Luna");
			luna.setHorizontalAlignment(SwingConstants.CENTER);
			luna.setBounds(50, 75, 80, 15);
			f.getContentPane().add(luna);	
			JLabel data = new JLabel("Data");
			data.setHorizontalAlignment(SwingConstants.CENTER);
			data.setBounds(50, 125, 80, 15);
			f.getContentPane().add(data);	
			JLabel locuri = new JLabel("Ora");
			locuri.setHorizontalAlignment(SwingConstants.CENTER);
			locuri.setBounds(50, 170, 80, 15);
			f.getContentPane().add(locuri);	
			JLabel label = new JLabel("Alegeti data Programarii");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(80, 30, 250, 15);
			f.getContentPane().add(label);
			JComboBox month = new JComboBox(months);
			month.setBounds(165, 70, 100, 20);
			f.getContentPane().add(month);
			JLabel vaccin = new JLabel("Tipul de vaccin");
			vaccin.setHorizontalAlignment(SwingConstants.CENTER);
			vaccin.setBounds(50, 210, 80, 15);
			f.getContentPane().add(vaccin);
			month.addActionListener(new ActionListener() { 
	   	 		@Override
	               public void actionPerformed(ActionEvent e) {
	   	 			YearMonth yearMonthObject = YearMonth.of(2021, month.getSelectedIndex()+1);
	   	 			int daysInMonth = yearMonthObject.lengthOfMonth();
	   	 			ArrayList<Integer> days_in_a_month = new ArrayList<Integer>();
	   	 			for (int i=1;i<=daysInMonth;i++)
	   	 				days_in_a_month.add(i);
	   	 			JComboBox days = new JComboBox(days_in_a_month.toArray());
	   	 			days.setBounds(165, 120, 100, 20);
	   	 			f.getContentPane().add(days);
	   	 			f.revalidate();
	   	 			days.addActionListener(new ActionListener() {
	   	 				@Override
	   	 					public void actionPerformed(ActionEvent e) {
	   	 						ArrayList<Integer> hours = new ArrayList<Integer>();
	   	 						for (int i=8;i<=16;i++)
	   	 							hours.add(i);
	   	 						JComboBox ora = new JComboBox(hours.toArray());
	   	 						ora.setBounds(165, 165, 100, 20);
	   	 						f.getContentPane().add(ora);
	   	 						f.validate();
	   	 						String[] vac = {"Astra Zeneca","Pfizer","Moderna"};
	   	 						JComboBox tip_vaccin = new JComboBox(vac);
	   	 						tip_vaccin.setBounds(165, 210, 100, 20);
	   	 						f.getContentPane().add(tip_vaccin);
	   	 						tip_vaccin.addActionListener(new ActionListener() {
	   	 							@Override
	   	 							public void actionPerformed(ActionEvent e) {	 								
	   	 							JButton finnish = new JButton("Finalizeaza Programarea");
							 		 finnish.setBounds(145, 260, 185, 25);
							 		 f.getContentPane().add(finnish);
							 		 f.validate();
							 		finnish.addActionListener(new ActionListener() { 
							   	 		@Override
							               public void actionPerformed(ActionEvent e) {       
							   	 		try {
			   	 							String databaseURL = "jdbc:ucanaccess://DB.accdb";
											Connection connection = DriverManager.getConnection(databaseURL);
											String sql = "UPDATE Programari set Finalizata=? , Luna=? , Data=? , Ora=? , Tip_Vaccin=? where CNP=?";
											PreparedStatement st = connection.prepareStatement(sql);
									         st.setString(1, "DA");
									         st.setString(2, (String)month.getSelectedItem());
									         st.setString(3, String.valueOf(days.getSelectedItem()));
									         st.setString(4, String.valueOf(ora.getSelectedItem()));
									         st.setString(5, (String)tip_vaccin.getSelectedItem());
									         st.setString(6, CNP);
									         st.executeUpdate();
									         Calendar c = Calendar.getInstance();
									         c.set(Calendar.MONTH, month.getSelectedIndex());
									         c.set(Calendar.YEAR, 2021);
									         c.set(Calendar.DAY_OF_MONTH, days.getSelectedIndex()+1);
									         String sql2 = "INSERT INTO Programari (CNP, Centru, Finalizata, Luna,Data,Ora,AttachedUsers,Tip_Vaccin) VALUES (?, ?, ?, ?,?, ?, ?, ?)";
									         System.out.println(c.getTime());
									         PreparedStatement st2 = connection.prepareStatement(sql2);
									         st2.setString(1, CNP);
									         st2.setString(2, cen);
									         st2.setString(3, "DA");
									         String vacc = (String)tip_vaccin.getSelectedItem();
									         if (vacc.equals("Astra Zeneca"))
									        	 c.add(Calendar.DATE, 56);
									         else if (vacc.equals("Pfizer"))
									        	 c.add(Calendar.DATE, 21);
									         else if (vacc.equals("Moderna"))
									        	 c.add(Calendar.DATE,28);
									         st2.setString(4, months[c.get(c.MONTH)]);
									         st2.setString(5, String.valueOf(c.get(c.DAY_OF_MONTH)));
									         st2.setString(6, String.valueOf(ora.getSelectedItem()));
									         st2.setString(7, U.username);
									         st2.setString(8, vacc);
									         st2.executeUpdate();
									         JLabel finalizare = new JLabel("Programare finalizata cu succes!");
									 		 finalizare.setHorizontalAlignment(SwingConstants.CENTER);
									 		 finalizare.setBounds(40, 295, 450, 60);
									 		 f.getContentPane().add(finalizare);
									 		 f.revalidate();
									 		 U.revalidate_database();
									         
									 		 
										} catch (SQLException e1) {
											e1.printStackTrace();
										}
							               }
							            });
	   	 							}
	   	 						});
	   	 				}
	   	 			});
	               }
	            });
			f.setVisible(true);
		    f.setLocationRelativeTo(null);
		    f.setSize(600,600);
		
		}
		
		
		static void tabel_beneficiari(Utilizator U) throws SQLException { /// Deprecated leaving in case something breaks .
			String databaseURL = "jdbc:ucanaccess://DB.accdb";
			Connection connection = DriverManager.getConnection(databaseURL);
			JFrame f = new JFrame("Tabel Beneficiari");
			DefaultTableModel model = new DefaultTableModel();
			JTable table = new JTable(model);
			String sql= "SELECT * FROM Beneficiari";
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery(sql);
			model.addColumn("Nume");
			model.addColumn("Prenume");
			model.addColumn("CNP");
			model.addColumn("SerieID");
			model.addColumn("NumarID");
			model.addColumn("Judet");
			model.addColumn("Localitate");
			model.addColumn("Dizabilitate");
			model.addColumn("Grupa de Risc");
			model.addColumn("");
			while(result.next()){
				if (result.getString("AttachedUser").equals(U.username)) {
					String a = result.getString("Nume");
					String b = result.getString("Prenume");
					String c = result.getString("CNP");
					String d = result.getString("SerieID");
					String e = result.getString("NumarID");
					String g = result.getString("Judet");
					String h = result.getString("Localitate");
					String i = result.getString("Dizabilitate");
					String aux = "Altele/PH";
					double helper = 2021 - (((Float.valueOf(c)/10000000000f)%100)+1900);
					if (helper >65)
						aux = "Categoria a II-a A";
					model.addRow(new Object[]{a, b, c, d, e, g, h, i,aux,"Programeaza!"});}
			}
			Action move_on = new AbstractAction()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        try {
			        	int b = table.getSelectedRow();
			        	String value = (String)table.getValueAt(b,2);
						fereastra_programare(value,U);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			    }
			};
			ButtonColumn buttonColumn = new ButtonColumn(table, move_on, 9);
			buttonColumn.setMnemonic(KeyEvent.VK_D);
			f.getContentPane().setLayout(null);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(0, 0, 1195, 385);
			f.getContentPane().add(scrollPane);
			JButton back = new JButton("Back");
			back.setBounds(440, 400, 90, 25);
			f.getContentPane().add(back);
			back.addActionListener(new ActionListener() { 
	   	 		@Override
	               public void actionPerformed(ActionEvent e) {       
	                  options_menu(U);
	                  f.dispose();
	               }
	            });
			f.setSize(1200,500);    
		    f.setVisible(true);
		    f.setLocationRelativeTo(null);
		}
		
		
		static void tabel_beneficiari2(Utilizator U) { /// Actual good one 
			JFrame f = new JFrame("Tabel Beneficiari");
			DefaultTableModel model = new DefaultTableModel();
			JTable table = new JTable(model);
			model.addColumn("Nume");
			model.addColumn("Prenume");
			model.addColumn("CNP");
			model.addColumn("SerieID");
			model.addColumn("NumarID");
			model.addColumn("Judet");
			model.addColumn("Localitate");
			model.addColumn("Dizabilitate");
			model.addColumn("Grupa de Risc");
			model.addColumn("");
			U.bene.forEach( (n) -> {String grupa="";																						//Lambda
									if (2021 - (((Float.valueOf(n.CNP)/10000000000f)%100)+1900) >=65)
										grupa = "Categoria a II-a A";
									else
										grupa = "Altele/PH";
									model.addRow(new Object[]{n.nume, n.prenume, n.CNP, n.serie_id, n.numar_id, n.Judet, n.Localitate, n.dizabilitate,grupa,"Programeaza!"}); } );
			Action move_on = new AbstractAction()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        try {
			        	int b = table.getSelectedRow();
			        	String value = (String)table.getValueAt(b,2);
						fereastra_programare(value,U);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			    }
			};
			ButtonColumn buttonColumn = new ButtonColumn(table, move_on, 9);
			buttonColumn.setMnemonic(KeyEvent.VK_D);
			f.getContentPane().setLayout(null);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(0, 0, 1195, 385);
			f.getContentPane().add(scrollPane);
			JButton back = new JButton("Back");
			back.setBounds(440, 400, 90, 25);
			f.getContentPane().add(back);
			back.addActionListener(new ActionListener() { 
	   	 		@Override
	               public void actionPerformed(ActionEvent e) {       
	                  options_menu(U);
	                  f.dispose();
	               }
	            });
			f.setSize(1200,500);    
		    f.setVisible(true);
		    f.setLocationRelativeTo(null);
		}
		
		
		static void fereastra_programare(String CNP_INDEF , Utilizator U) throws SQLException {
			String databaseURL = "jdbc:ucanaccess://DB.accdb";
			Connection connection = DriverManager.getConnection(databaseURL);
			ArrayList<String> judet_array = new ArrayList<String>();
			judet_array.add(centre.get(0).Judet);
			for (int i=0;i<centre.size();i++) {
				if (!centre.get(i).Judet.matches(judet_array.get(judet_array.size()-1))) {
					judet_array.add(centre.get(i).Judet);
				}	
			}
			JFrame f = new JFrame();
			f.getContentPane().setLayout(null);	
			JLabel judet = new JLabel("Alege Judetul");
			judet.setHorizontalAlignment(SwingConstants.CENTER);
			judet.setBounds(45, 45, 125, 35);
			f.getContentPane().add(judet);	
			JComboBox comboBox = new JComboBox(judet_array.toArray());
			comboBox.setBounds(225, 50, 130, 20);
			f.getContentPane().add(comboBox);
			JButton filtru = new JButton("Filtreaza!");
			filtru.setBounds(155, 120, 90, 25);
			f.getContentPane().add(filtru);
			filtru.addActionListener(new ActionListener() { 
	   	 		@Override
	               public void actionPerformed(ActionEvent e){
	   	 				ArrayList<String> centru = new ArrayList<String>();
	   	 				for (int i=0;i<centre.size();i++)
	   	 					if (centre.get(i).Judet.equals((String)comboBox.getSelectedItem()))
								centru.add(centre.get(i).Nume);
	   	 				JComboBox jcombocentre = new JComboBox(centru.toArray());
	   	 				jcombocentre.setBounds(45, 175, 310, 20);
	   	 				f.getContentPane().add(jcombocentre);
	   	 			    JButton inregistrare = new JButton("Inregistreaza-te pe lista de asteptare");
	   				    inregistrare.setBounds(135, 280, 290, 23);
	   				    f.getContentPane().add(inregistrare);
	   				    JLabel message_code = new JLabel();
	   	 				message_code.setHorizontalAlignment(SwingConstants.CENTER);
	   	 				message_code.setBounds(35, 350, 550, 35);
	   				    inregistrare.addActionListener(new ActionListener() { 
	   		   	 			@Override
	   		   	 			public void actionPerformed(ActionEvent e) {       
	   		   	 			try {
	   		   	 				message_code.setText("");
	   		   	 				f.getContentPane().add(message_code);
								for (int i=0;i<centre.size();i++)
									if (centre.get(i).Nume.equals((String)jcombocentre.getSelectedItem()) && centre.get(i).locuri!=0) {
										message_code.setText("Programare completa . \nPentru a finaliza programare mergi in meniul 'Programari'");
										String sql3 = "INSERT INTO Programari (CNP, Centru, Finalizata, AttachedUsers) VALUES (?, ?, ?, ?)";
										PreparedStatement preparedStatement = connection.prepareStatement(sql3);
							            preparedStatement.setString(1, CNP_INDEF);
							            preparedStatement.setString(2, centre.get(i).Nume);
							            preparedStatement.setString(3, "NU");
							            preparedStatement.setString(4, U.username);
							            preparedStatement.executeUpdate();
							            U.revalidate_database();
										break;}
									else if (centre.get(i).Nume.equals((String)jcombocentre.getSelectedItem()) && centre.get(i).locuri==0) {
										message_code.setText("Nu sunt locuri libere la centrul : " + centre.get(i).Nume);
										break;}
							} catch (NumberFormatException | SQLException e1) {
								e1.printStackTrace();
							}
	   		   	 				f.revalidate();
	   		   	 			}
	   		            	});
	   				    f.revalidate();
	               }
	            });
			JLabel click = new JLabel("<- Click pentru a alege Centrul.");
			click.setHorizontalAlignment(SwingConstants.LEFT);
			click.setBounds(365, 175, 210, 20);
			f.getContentPane().add(click);
			f.setVisible(true);
			f.setSize(600, 600);
			f.setLocationRelativeTo(null);
		}
		
		
			
		static void Creeare_cont() throws SQLException {
			String databaseURL = "jdbc:ucanaccess://DB.accdb";
			Connection connection = DriverManager.getConnection(databaseURL);
			JFrame f=new JFrame("Creeare Cont");
			final JLabel label = new JLabel();
		    label.setBounds(20,150,200,50);
		    JLabel l2=new JLabel("Username");
		    JLabel l3=new JLabel("Password");
		    l2.setBounds(120,50, 100,80);
		    l3.setBounds(120,100, 100,80);
		    JTextField t1 = new JTextField();
		    JTextField t2 = new JTextField();
		    JLabel error_message = new JLabel();
		    error_message.setHorizontalAlignment(SwingConstants.CENTER);
		    error_message.setBounds(100,300,200,20);
		    t1.setBounds(200,80, 100,20);
		    t2.setBounds(200,130,100,20);
		    JButton b = new JButton("Creeaza cont");
		    b.setBounds(130,200,150,20);
		    f.getContentPane().add(label);f.getContentPane().add(l2);f.getContentPane().add(l3); f.getContentPane().add(t1); f.getContentPane().add(t2); f.getContentPane().add(b);f.getContentPane().add(error_message);
		    f.setSize(400,400);    
		    f.getContentPane().setLayout(null); 
		    f.setLocationRelativeTo(null);
		    f.setVisible(true);
		    b.addActionListener(new ActionListener() { 
	   	 		@Override
	            public void actionPerformed(ActionEvent e) {
	   	 			String Tester_user = "^(.+)@(.+)$";
	   	 			String Tester_pass = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
	   	 				if (t1.getText().matches(Tester_user) && t2.getText().matches(Tester_pass)) {
								try {
									Users.add(new Utilizator(t1.getText(),t2.getText()));
									String sql = "INSERT INTO Accounts (Email, Pass) VALUES (?, ?)";           
						            PreparedStatement preparedStatement = connection.prepareStatement(sql);
						            preparedStatement.setString(1, t1.getText());
						            preparedStatement.setString(2, t2.getText());
						            preparedStatement.executeUpdate();
						            send_mail(t1.getText(),t2.getText());
								} catch (SQLException e2) {
									e2.printStackTrace();
								} catch (AddressException e1) {
									e1.printStackTrace();
								} catch (MessagingException e1) {
									e1.printStackTrace();
								}
								confirmation_window(t1.getText(),Users.get(Users.size()-1));
	   	 						f.dispose();}
	   	 					else
	   	 						error_message.setText("Format email sau parola gresit");
	            }
	         });
		}
		
		static void confirmation_window(String email , Utilizator U) {
			JFrame f = new JFrame("Account Confirmation");
			JLabel l1 = new JLabel("Contul a fost creat cu succes!");
			l1.setHorizontalAlignment(SwingConstants.CENTER);
			l1.setBounds(80 , 40 , 200 , 20);
			JLabel l2 = new JLabel ("Email-ul folosit este : ");
			l2.setHorizontalAlignment(SwingConstants.CENTER);
			l2.setBounds(80 , 60 , 200 , 20);
			JLabel l3 = new JLabel (email);
			l3.setHorizontalAlignment(SwingConstants.CENTER);
			l3.setBounds(80 , 80 , 200 , 20);
			JLabel l4 = new JLabel ("Pentru a te putea programa la vaccinare, completeaza profilul!");
			l4.setHorizontalAlignment(SwingConstants.CENTER);
			l4.setBounds(0,120,380,20);
			JButton b = new JButton("Completeaza profil!");
			b.setBounds(120,200,150,50);
			f.getContentPane().add(l1);f.getContentPane().add(l2);f.getContentPane().add(l3);f.getContentPane().add(l4);f.getContentPane().add(b);
			f.setSize(400,400);
			f.getContentPane().setLayout(null); 
		    f.setLocationRelativeTo(null);
		    f.setVisible(true);
		    b.addActionListener(new ActionListener() { 
	   	 		@Override
	               public void actionPerformed(ActionEvent e) {       
	                  try {
						completare_profil(U);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
	                  f.dispose();
	   	 			}
	            });
		    
		}
		
		
		static void completare_profil(Utilizator U) throws SQLException {
			String databaseURL = "jdbc:ucanaccess://DB.accdb";
			Connection connection = DriverManager.getConnection(databaseURL);
			JFrame f = new JFrame("Completare Profil");
			JLabel l1 = new JLabel("Toate campurile sunt obligatorii");
			l1.setBounds(120 , 20 , 200 , 20);
			JLabel nume = new JLabel("Nume : ");
			nume.setBounds(120, 50, 100, 20);
			JLabel prenume = new JLabel("Prenume : ");
			prenume.setBounds(120, 80, 100, 20);
			JLabel CNP = new JLabel("CNP : ");
			CNP.setBounds(120, 110, 100, 20);
			JLabel serie_id = new JLabel("Serie : ");
			serie_id.setBounds(120, 140, 100, 20);
			JLabel numar_id = new JLabel("Numar : ");
			numar_id.setBounds(120, 170, 100, 20);
			JLabel Judet = new JLabel("Judet : ");
			Judet.setBounds(120, 200, 100, 20);
			JLabel Localitate = new JLabel("Localitate : ");
			Localitate.setBounds(120, 230, 100, 20);
			JCheckBox dizabilitate = new JCheckBox("Sunt persoana cu dizabilitati");
			dizabilitate.setBounds(120,260,300,20);
			JTextField text_nume = new JTextField();
			text_nume.setBounds(220 , 50 , 100 ,20);
			JTextField text_prenume = new JTextField();
			text_prenume.setBounds(220,80,100,20);
			JTextField text_CNP = new JTextField();
			text_CNP.setBounds(220 , 110 , 100 , 20);
			JTextField text_serie_id = new JTextField();
			text_serie_id.setBounds(220,140,100,20);
			JTextField text_numar_id = new JTextField();
			text_numar_id.setBounds(220,170,100,20);
			JTextField text_Judet = new JTextField();
			text_Judet.setBounds(220,200,100,20);
			String sql = "SELECT * FROM Localitate";
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery(sql);
			ArrayList<String> local = new ArrayList<String>();
			text_Judet.addActionListener(new ActionListener() {
				@Override
					public void actionPerformed(ActionEvent e) {
					local.clear();
					try {
						while (result.next()) 
							if (result.getString("Judet").equals(text_Judet.getText()))
								local.add(result.getString("Localitate"));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					JComboBox drop_localitate = new JComboBox(local.toArray());
					drop_localitate.setBounds(220,230,100,20);
					weird_stuff=(String)drop_localitate.getSelectedItem();
					f.getContentPane().add(drop_localitate);
					f.revalidate();
					}
			});			
			JButton finish = new JButton ("Finalizeaza completarea profilului");
			finish.setBounds(120,300,300,50);
			f.getContentPane().add(l1);f.getContentPane().add(nume);f.getContentPane().add(prenume);f.getContentPane().add(CNP);f.getContentPane().add(serie_id);f.getContentPane().add(numar_id);f.getContentPane().add(Judet);f.getContentPane().add(Localitate);
			f.getContentPane().add(text_nume);f.getContentPane().add(text_prenume);f.getContentPane().add(text_CNP);f.getContentPane().add(text_serie_id);f.getContentPane().add(text_numar_id);f.getContentPane().add(text_Judet);f.getContentPane().add(dizabilitate);
			f.getContentPane().add(finish);
			f.setSize(600,600);
			f.getContentPane().setLayout(null); 
		    f.setLocationRelativeTo(null);
		    f.setVisible(true);
		    finish.addActionListener(new ActionListener() { 
	   	 		@Override
	               public void actionPerformed(ActionEvent e) {       
	   	 				try {
							U.add_bene(text_nume.getText(), text_prenume.getText(), text_CNP.getText(), text_serie_id.getText(), text_numar_id.getText(), text_Judet.getText(),weird_stuff,dizabilitate.isSelected());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
	   	 				options_menu(U);
	   	 				f.dispose();
	   	 		}
	            });
		    
		}
		
		static void send_mail(String to , String pass) throws AddressException, MessagingException {
	        Properties properties = System.getProperties();
	        properties.put("mail.smtp.host", "smtp.gmail.com");
	        properties.put("mail.smtp.port", "465");
	        properties.put("mail.smtp.ssl.enable", "true");
	        properties.put("mail.smtp.auth", "true");
	        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

	            protected PasswordAuthentication getPasswordAuthentication() {

	                return new PasswordAuthentication("shodanitza@gmail.com", "ddydhqyzdvklvdyz");

	            }

	        });
	        session.setDebug(true);
	        try {
	            MimeMessage message = new MimeMessage(session);
	            message.setFrom(new InternetAddress("shodanitza@gmail.com"));
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	            message.setSubject("Confirmare Inregistrare Completa");
	            message.setText("Contul cu \nUsername : " + to + "\nPassword : " + pass + "\nA fost creat cu succes!" );
	            Transport.send(message);
	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	        }

	    }
			
		
		void afisare() {
			for (int i=0;i<Users.size();i++)
				Users.get(i).Afisare();
				
		}
}
