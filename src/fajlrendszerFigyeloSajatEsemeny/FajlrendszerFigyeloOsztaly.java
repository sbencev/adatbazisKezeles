package fajlrendszerFigyeloSajatEsemeny;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FajlrendszerFigyeloOsztaly {

	private List<IMappaFigyelesEsemeny> feliratkozottObjektumok;

	private String mappaEleres;

	long utolsoModositas;

	Timer idozito;

	public FajlrendszerFigyeloOsztaly(String mappaEleres) {

		setMappaEleres(mappaEleres);
		feliratkozottObjektumok = new ArrayList<IMappaFigyelesEsemeny>();
		idozito = new Timer();

		TimerTask feladat = new TimerTask() {

			@Override
			public void run() {

				mappaEllenorzes();

			}
		};

		idozito.schedule(feladat, 0, 3000);

	}

	public List<IMappaFigyelesEsemeny> getFeliratkozottObjektumok() {
		return feliratkozottObjektumok;
	}

	private void mappaEllenorzes() {

		File fajl = new File(mappaEleres);
		long aktualisModositasIdeje = fajl.lastModified();
		if (aktualisModositasIdeje > utolsoModositas) {
			setUtolsoModositas(aktualisModositasIdeje);
		}

	}

	public void setMappaEleres(String mappaEleres) {

		File mappa = new File(mappaEleres);
		if (mappa.exists() && mappa.isDirectory()) {
			this.mappaEleres = mappaEleres;
			utolsoModositas = mappa.lastModified();
		} else {

			throw new IllegalArgumentException("A mappa nem talalhato!");

		}
	}

	public void setUtolsoModositas(long utolsoModositas) {
		this.utolsoModositas = utolsoModositas;

		// esemeny kivaltas
		for (IMappaFigyelesEsemeny object : feliratkozottObjektumok) {

			object.mappaMegvaltozott(mappaEleres);

		}
	}

}
