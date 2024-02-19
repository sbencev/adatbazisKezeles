package felhasznalokJDBC_AdatbazisKezeles;

import java.awt.HeadlessException;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class FelvitelmodositAblak extends JDialog {
	private JTextField txtID;
	private JTextField txtFelhasznalo;
	private JPasswordField pwJelszo;
	private JTextField textRegisztracioDatuma;
	
	private boolean dialogResult = false;
	
	private Felhasznalo felhasznalo;

	private String atmenetiJelszo = "       ";
	
	public Felhasznalo getFelhasznalo() {
		return felhasznalo;
	}




	public boolean isDialogResult() {
		return dialogResult;
	}




	public FelvitelmodositAblak() {
		setTitle("Adatlap");
		
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblID = new JLabel("ID:");
		lblID.setBounds(47, 26, 113, 14);
		getContentPane().add(lblID);
		
		txtID = new JTextField();
		txtID.setEnabled(false);
		txtID.setText("Rendszer által generált");
		txtID.setBounds(193, 23, 210, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);
		
		txtFelhasznalo = new JTextField();
		txtFelhasznalo.setBounds(193, 54, 210, 20);
		getContentPane().add(txtFelhasznalo);
		txtFelhasznalo.setColumns(10);
		
		JLabel lblFelhasznalo = new JLabel("Felhasználónév:");
		lblFelhasznalo.setBounds(47, 57, 113, 14);
		getContentPane().add(lblFelhasznalo);
		
		pwJelszo = new JPasswordField();
		pwJelszo.setBounds(193, 85, 210, 20);
		getContentPane().add(pwJelszo);
		
		JLabel lblJelszo = new JLabel("Jelszó:");
		lblJelszo.setBounds(47, 88, 113, 14);
		getContentPane().add(lblJelszo);
		
		textRegisztracioDatuma = new JTextField();
		textRegisztracioDatuma.setEnabled(false);
		textRegisztracioDatuma.setText("Rendszer által generált");
		textRegisztracioDatuma.setBounds(193, 116, 210, 20);
		getContentPane().add(textRegisztracioDatuma);
		textRegisztracioDatuma.setColumns(10);
		
		JLabel lblRegisztracio = new JLabel("Regisztráció dátuma:");
		lblRegisztracio.setBounds(47, 119, 113, 14);
		getContentPane().add(lblRegisztracio);
		
		JButton btnMentes = new JButton("Mentés");
		btnMentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ellenorzes();
				
			}
			
		});
		btnMentes.setBounds(71, 170, 89, 23);
		getContentPane().add(btnMentes);
		
		JButton btnMegsem = new JButton("Mégsem");
		btnMegsem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
			}
		});
		btnMegsem.setBounds(275, 170, 89, 23);
		getContentPane().add(btnMegsem);

	}

	// módosító konstruktor:
	public FelvitelmodositAblak(Felhasznalo felhasznalo) {
		
		this(); // meghívjuk a paraméter nélküli konstruktort, mert a frame beállításaira szükségünk van
		this.felhasznalo = felhasznalo;
		
		if (felhasznalo.getId() != null) {
		
			txtID.setText(felhasznalo.getId().toString());
			
		}
		else {
			
			txtID.setText("N/A");
			
		}
		
		
		txtFelhasznalo.setText(felhasznalo.getFelhasznaloNev());
		
		//pwJelszo.setText(felhasznalo.getJelszo());
		pwJelszo.setText(atmenetiJelszo);
		
		if (felhasznalo.getRegisztracioDatuma() != null) {
		
			textRegisztracioDatuma.setText(felhasznalo.getRegisztracioDatuma().toString());
			
		}
		else {
			
			textRegisztracioDatuma.setText("N/A");
			
		}
		
		
	}


	private void ellenorzes() {
		
		try {
			if (felhasznalo == null) {
				
				if (!txtFelhasznalo.getText().isBlank()) {
					
					felhasznalo = new Felhasznalo(txtFelhasznalo.getText(), new String(pwJelszo.getPassword()));
					
					ABKezelo.ujfelhasznalo(felhasznalo);
					dialogResult = true;
					dispose();
					
				}
				else {
					
					JOptionPane.showMessageDialog(this, "Felhasználónév kötelezően kitöltendő!","Figyelmeztetés",JOptionPane.WARNING_MESSAGE);
					
				}
				
			}
			else {
				
				if (!txtFelhasznalo.getText().isBlank()) {
					
					if (!atmenetiJelszo.equals(new String(pwJelszo.getPassword()))) {
						
						felhasznalo.setJelszo(new String(pwJelszo.getPassword()));
						
					}
					
					felhasznalo.setFelhasznaloNev(txtFelhasznalo.getText());
					
					ABKezelo.felhasznaloModositasa(felhasznalo);
					
					dialogResult = true;
					dispose();
					
				}
				else {
					
					JOptionPane.showMessageDialog(this, "Felhasználónév kötelezően kitöltendő!","Figyelmeztetés",JOptionPane.WARNING_MESSAGE);
					
				}	
					
				
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			
			JOptionPane.showMessageDialog(this, e.getMessage(),"Figyelmeztetés",JOptionPane.WARNING_MESSAGE);
			
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(this, e.getMessage(), "DB Hiba", JOptionPane.ERROR_MESSAGE);
			
			if (e.getMessage().contains("Létező")) {
				
				felhasznalo = null;
				
			}
			
		}
		
		
		
	}


	

	
	
	
	
	
	
	
	
	
	
	
	
	
}
