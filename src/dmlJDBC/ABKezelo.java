package dmlJDBC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ABKezelo {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	private ABKezelo() {

	}

	public static Connection csatlakozas() {

		Connection kapcsolat = null;
		try {

			String connectionString = "jdbc:mysql://localhost:3306/dml?autoReconnect=true&useSSL=false";
			kapcsolat = DriverManager.getConnection(connectionString, "root", "Attpzk78@");

		} catch (Exception e) {

			System.err.println("A csatlakozas sikertelen, a program bezarul!");
			System.exit(0);

		}

		return kapcsolat;

	}

	public static void kevesetKeresok() {

		boolean ujra;
		do {
			ujra = false;
			try (Connection kapcsolat = csatlakozas()) {

				System.out.print("Adja meg az osszeghatart:");
				int osszeg = Integer.parseInt(br.readLine());

				PreparedStatement ps = kapcsolat.prepareStatement("SELECT * from emp WHERE sal <?");
				ps.setInt(1, osszeg);

				System.out.println("A megadott osszegnel kevesebbet keresok listaja:");
				ResultSet res = ps.executeQuery();
				while (res.next()) {
					System.out.println(res.getString("ename") + " " + res.getInt("sal") + "$");
				}

			} catch (SQLException e) {

				System.err.println("DB hiba " + e.getMessage());

			} catch (NumberFormatException e) {
				System.err.println("Szamformatum hiba!");
				ujra = true;
			} catch (IOException e) {
				System.err.println("I/O hiba!");
			}
		} while (ujra);

	}

	public static void beosztottakListaja() {

		boolean ujra;
		do {

			ujra = false;
			try (Connection kapcsolat = csatlakozas()) {

				System.out.print("Adja meg a fonok azonositojat: ");
				int fonokID = Integer.parseInt(br.readLine());

				PreparedStatement ps = kapcsolat.prepareStatement("SELECT * from emp WHERE mgr=?");
				ps.setInt(1, fonokID);

				ResultSet res = ps.executeQuery();

				if (res.isBeforeFirst()) {

					System.out.println("A fonok beosztottjai:");
					while (res.next()) {
						System.out.println(res.getString("ename"));

					}

				} else {
					System.out.println("Nem letezo ID vagy nincs beosztott");
				}

			} catch (SQLException e) {

				System.err.println("DB hiba " + e.getMessage());

			} catch (NumberFormatException e) {
				System.err.println("Szamformatum hiba!");
				ujra = true;
				ujra = true;
			} catch (IOException e) {
				System.err.println("I/O hiba!");
			}
		} while (ujra);

	}

	public static void osztalyDolgozoi(String osztaly) {

		try (Connection kapcsolat = csatlakozas()) {

			PreparedStatement ps = kapcsolat
					.prepareStatement("SELECT * from emp where deptno = ANY (SELECT deptno from dept where dname=?)");
			ps.setString(1, osztaly);

			ResultSet res = ps.executeQuery();
			if (res.isBeforeFirst()) {

				System.out.println("Az " + osztaly + " dolgozoi:");
				while (res.next()) {

					System.out.println(res.getString("ename"));

				}

			} else {

				System.out.println("Nem letezo osztaly vagy nincsenek dolgozoi");

			}

		} catch (SQLException e) {

			System.err.println("DB hiba " + e.getMessage());
		}

	}

}
