package felhasznalokJDBC_AdatbazisKezeles;

import java.time.LocalDate;

/* Készítsünk egytáblás adatbázist, melyben felhasználókat tudunk tárolni:
FELHASZNÁLÓ(UID, Felhasználónév, Jelszó, Regisztráció dátuma)
Oldjuk meg programból, hogy fel tudjunk tölteni, illetve tudjunk módosítani és törölni is az adatbázisban. */

public class Felhasznalo {
	
	// még hiányzik a konstruktor

	private Integer id;
	private String felhasznaloNev;
	private String jelszo;
	private LocalDate regisztracioDatuma;
	
	
	public Felhasznalo(Integer id, String felhasznaloNev, String jelszo, LocalDate regisztracioDatuma) {
		
		setId(id);
		this.felhasznaloNev = felhasznaloNev;
		this.jelszo = jelszo;
		setRegisztracioDatuma(regisztracioDatuma);
		
	}
	
	
	public Felhasznalo(String felhasznaloNev, String jelszo) {
		
		setFelhasznaloNev(felhasznaloNev);
		setJelszo(jelszo);
		
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		
		if (this.id == null) {
		
			this.id = id;
			
		}
		else {
			
			throw new IllegalStateException("Az ID értéke csak egyszer adható meg!");
			
		}
		
	}
	
	
	public String getFelhasznaloNev() {
		return felhasznaloNev;
	}
	
	public void setFelhasznaloNev(String felhasznaloNev) {
		
		if (felhasznaloNev != null && !felhasznaloNev.isBlank()) {
			
			this.felhasznaloNev = felhasznaloNev;
			
		}
		else {
			
			throw new IllegalArgumentException("A felhasználó azonosító nem lehet üres!");
			
		}
		
	}
	
	public String getJelszo() {
		return jelszo;
	}
	
	public void setJelszo(String jelszo) {
		
		if (JelszoMuveletek.erosJelszo(jelszo)) {
		
			this.jelszo = JelszoMuveletek.titkosit(jelszo);
			
		}
		else {
			
			throw new IllegalArgumentException("A jelszó minimum 8 karakter kell legyen és tartalmaznia kell kisbetűt, nagybetűt, számjegyet! ");
			
		}
		
	}
	
	
	public LocalDate getRegisztracioDatuma() {
		return regisztracioDatuma;
	}
	public void setRegisztracioDatuma(LocalDate regisztracioDatuma) {
		
		if (this.regisztracioDatuma == null) {
		
			this.regisztracioDatuma = regisztracioDatuma;
			
		}
		else {
			
			throw new IllegalStateException("A regisztráció dátuma csak egyszer adható meg!");
			
		}
	}
	
	
	
	
	
}
