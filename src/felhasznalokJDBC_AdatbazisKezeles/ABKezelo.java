package felhasznalokJDBC_AdatbazisKezeles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ABKezelo {

	private static Connection kapcsolat;
	private static PreparedStatement sqlUtasitas;

	private ABKezelo() {

	}

	public static void csatlakozas() throws SQLException {

		try {

			String connectionString = "jdbc:mysql://localhost:3306/felhasznaloDB?autoReconnect=true&useSSL=false";

			kapcsolat = DriverManager.getConnection(connectionString, "root", "Attpzk78@");

		} catch (Exception e) {

			throw new SQLException("A csatlakozás sikertelen!" + e.getMessage());

		}

	}

	public static void kapcsolatBontas() throws SQLException {

		try {

			kapcsolat.close();

		} catch (Exception e) {

			throw new SQLException("A kapcsolat bontása sikertelen!");

		}

	}

	// CRUD műveletek:
	// felhasznalo tábla oszlopai: ID, Felhasznalonev, Jelszo, RegisztracioDatuma

	// CRUD - c
	public static void ujfelhasznalo(Felhasznalo felhasznalo) throws SQLException {

		try {

			sqlUtasitas = kapcsolat.prepareStatement("INSERT INTO felhasznalo (felhasznalonev, Jelszo) VALUES (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			sqlUtasitas.setString(1, felhasznalo.getFelhasznaloNev());
			sqlUtasitas.setString(2, felhasznalo.getJelszo());

			// sql utasítás végrehajtása 3 módon történhet:
			// executeQuery() --> select esetén használatos, ResultSet típusban szolgáltatja
			// az eredményhalmazt
			// executeUpdate() --> insert/update/delete utasítást futtat és int eredményt
			// szolgáltat
			// execute() --> bármilyen utasítást futtat. logikai értékkel tér vissza, igaz
			// eredményt ad, ha ResultSet eredményünk van
			// és hamisat, ha int értéket kapunk vissz az adatbázisból.

			sqlUtasitas.executeUpdate();

			ResultSet res = sqlUtasitas.getGeneratedKeys();
			if (res.next()) {

				felhasznalo.setId(res.getInt(1));

			}

			sqlUtasitas.clearParameters();
			sqlUtasitas = kapcsolat.prepareStatement("SELECT * from felhasznalo WHERE ID=?");
			sqlUtasitas.setInt(1, felhasznalo.getId());

			res = sqlUtasitas.executeQuery();
			if (res.next()) {

				LocalDate datum = res.getTimestamp("RegisztracioDatuma").toLocalDateTime().toLocalDate();
				felhasznalo.setRegisztracioDatuma(datum);

			}
			sqlUtasitas.clearParameters();

		} catch (Exception e) {

			if (e.getMessage().toLowerCase().contains("duplicate")) {

				throw new SQLException("Létező felhasználónév!");

			} else {

				throw new SQLException("A felhasználó felvitele sikertelen! " + e.getMessage());

			}

		}

	}

	// CRUD U - update
	public static void felhasznaloModositasa(Felhasznalo felhasznalo) throws SQLException {

		try {

			sqlUtasitas = kapcsolat.prepareStatement("UPDATE felhasznalo SET Felhasznalonev=?, Jelszo=? WHERE ID=?");
			sqlUtasitas.setString(1, felhasznalo.getFelhasznaloNev());
			sqlUtasitas.setString(2, felhasznalo.getJelszo());
			sqlUtasitas.setInt(3, felhasznalo.getId());

			sqlUtasitas.executeUpdate();
			sqlUtasitas.clearParameters();

		} catch (Exception e) {

			throw new SQLException("A felhasználó módosítása sikertelen! " + e.getMessage());

		}

	}

	// CRUD - d
	public static void felhasznaloTorlese(Felhasznalo felhasznalo) throws SQLException {

		try {

			sqlUtasitas = kapcsolat.prepareStatement("delete from felhasznalo where ID=?");
			sqlUtasitas.setInt(1, felhasznalo.getId());

			sqlUtasitas.executeUpdate();
			sqlUtasitas.clearParameters();

		} catch (Exception e) {

			throw new SQLException("A felhasználó törlése sikertelen! " + e.getMessage());

		}

	}

	// CRUD - R:
	public static List<Felhasznalo> felhasznalokBeolvasasa() throws SQLException {

		try {

			List<Felhasznalo> felhasznalok = new ArrayList<Felhasznalo>();

			sqlUtasitas = kapcsolat.prepareStatement("SELECT * from felhasznalo");

			ResultSet res = sqlUtasitas.executeQuery();
			while (res.next()) {

				felhasznalok
						.add(new Felhasznalo(res.getInt("ID"), res.getString("Felhasznalonev"), res.getString("Jelszo"),
								res.getTimestamp("RegisztracioDatuma").toLocalDateTime().toLocalDate()));

			}

			res.close();
			return felhasznalok;

		} catch (Exception e) {

			throw new SQLException("Adatbázisból beolvasás sikertelen");

		}

	}

	// minden további adatbázisművelet ide kerülhet.

	public static Felhasznalo belepes(String felhasznalonev, String jelszo) throws SQLException {

		try {

			sqlUtasitas = kapcsolat.prepareStatement("select * from felhasznalo where felhasznalonev=? and Jelszo=?");
			sqlUtasitas.setString(1, felhasznalonev);
			sqlUtasitas.setString(2, jelszo);

			ResultSet res = sqlUtasitas.executeQuery();
			if (res.next()) {

				Felhasznalo felhasznalo = new Felhasznalo(res.getInt("ID"), res.getString("Felhasznalonev"),
						res.getString("Jelszo"),
						res.getTimestamp("RegisztracioDatuma").toLocalDateTime().toLocalDate());

				return felhasznalo;
			}

		} catch (Exception e) {

			throw new SQLException("Adatbázis hiba!");

		}

		return null;
	}

}
