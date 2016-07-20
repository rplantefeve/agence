package vol.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vol.model.Ville;

/**
 * Permet la synchronisation entre l'objet m�tier Ville et la BDD
 * @author Seme
 *
 */
public class VilleDaoSQL implements VilleDao {

	public VilleDaoSQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 2. Cr�er la connexion � la base (on instancie l'objet connexion)
		try {
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/vol", "user", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Connection connexion;

	public void fermetureConnexion() {
		try {
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Ville> findAll() {
		// Liste des villes que l'on va retourner
		List<Ville> villes = new ArrayList<Ville>();
		// Connexion � la BDD
		try {
			/*
			 * Connexion � la BDD
			 */
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM ville");
			// 4. Execution de la requ�te
			ResultSet tuple = ps.executeQuery();
			// 5. Parcoutuple de l'ensemble des r�sultats (ResultSet) pour
			// r�cup�rer les valeutuple des colonnes du tuple qui correspondent
			// aux
			// valeur des attributs de l'objet
			while (tuple.next()) {
				// Creation d'un objet Ville
				Ville ville = new Ville(tuple.getInt("id"), tuple.getString("nom"));
				// Ajout du nouvel objet Ville cr�� � la liste des villes
				villes.add(ville);
			} // fin de la boucle de parcoutuple de l'ensemble des r�sultats

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Retourne la liste de toutes les villes
		return villes;
	}

	public Ville findById(Integer id) {
		// D�claration d'un objet ville
		Ville ville = null;
		
		try {
			//Connexion � la BDD
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM ville where id=?");
			//Cherche l'idVill voulu dans la BDD
			ps.setInt(1, id);

			//R�cup�ration des r�sultats de la requ�te
			ResultSet tuple = ps.executeQuery();

			if (tuple.next()) {
				ville = new Ville(tuple.getInt("id"), tuple.getString("nom"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ville;
	}
	
	public void delete(Ville ville) {
		try {
			PreparedStatement ps = connexion.prepareStatement("delete from ville where id = ?");
			ps.setInt(1, ville.getIdVil());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Ville update(Ville ville) {
		try {
			// Syntaxe de l'update en BDD : UPDATE Table qui contient le tuple � modifier + set + Position des champs 
			PreparedStatement ps = connexion
					.prepareStatement("update ville set nom=? where id = ?");

			ps.setInt(2, ville.getIdVil());
			// Donne une valeur au point d'interrogation
			ps.setString(1, ville.getNom());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ville;
	}
	
	public void create(Ville ville) {
		try {
			// Cr�er sa requ�te d'insertion INSERT INTO
			PreparedStatement ps;
		
			ps = connexion.prepareStatement("insert into ville (id,nom) VALUES(?,?)");
			ps.setInt(1, ville.getIdVil());
			ps.setString(2, ville.getNom());
			
			// une fois la requ�te cr��e, je l'ex�cute
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
