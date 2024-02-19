package fajlrendszerFigyeloSajatEsemeny;

public class FoProgramFajlrendszerFigyelo implements IMappaFigyelesEsemeny {

	public static void main(String[] args) {

		FoProgramFajlrendszerFigyelo fff = new FoProgramFajlrendszerFigyelo();

		String mappaNev1 = System.getProperty("user.home") + "/desktop";

		FajlrendszerFigyeloOsztaly mappaObj1 = new FajlrendszerFigyeloOsztaly(mappaNev1);
		System.out.println(mappaNev1 + " mappa figyelese megkezdodott");
		mappaObj1.getFeliratkozottObjektumok().add(fff);
		System.out.println(mappaNev1 + " mappa valtozasaira sikeres feliratkozas tortent (fff)");
		System.out.println();

		String mappaNev2 = "Sajatmappa";
		FajlrendszerFigyeloOsztaly mappaObj2 = new FajlrendszerFigyeloOsztaly(mappaNev2);
		System.out.println(mappaNev1 + " mappa figyelese megkezdodott");
		mappaObj2.getFeliratkozottObjektumok().add(fff);
		System.out.println(mappaNev2 + " mappa valtozasaira sikeres feliratkozas tortent (fff)");
		System.out.println();
		
		MegegyFeliratkozoOsztaly megegyObj = new MegegyFeliratkozoOsztaly();
		mappaObj1.getFeliratkozottObjektumok().add(megegyObj);
		System.out.println(mappaNev1 + " mappa valtozasaira sikeres feliratkozas tortent (megegyObj)");
		
	}

	@Override
	public void mappaMegvaltozott(String mappaElerese) {

		System.out.println(mappaElerese + " mappa megvaltozott");

	}

}
