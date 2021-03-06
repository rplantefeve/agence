package vol.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vol.model.Aeroport;

public class AeroportDaoSQL implements AeroportDao {

	/**
	 * Constructeur
	 */
	public AeroportDaoSQL() {
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
	
	public void fermetureConnexion(){
		try {
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Aeroport> findAll() {
		// Liste des a�roports que l'on va retourner
		List<Aeroport> aeroports = new ArrayList<Aeroport>();
		try {
			/*
			 * Connexion � la BDD
			 */
			
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM aeroport");
			// 4. Execution de la requ�te
			ResultSet tuple = ps.executeQuery();
			// 5. Parcoutuple de l'ensemble des r�sultats (ResultSet) pour
			// r�cup�rer les valeutuple des colonnes du tuple qui correspondent aux
			// valeur des attributs de l'objet
			while (tuple.next()) {
				// Creation d'un objet Aeroport
				Aeroport aeroport = new Aeroport(tuple.getInt("idAero"), tuple.getString("nom"));
				// Ajout du nouvel objet Aeroport cr�� � la liste des a�roports
				aeroports.add(aeroport);
			} // fin de la boucle de parcoutuple de l'ensemble des r�sultats


		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Retourne la liste de tous les a�roports
		return aeroports;
	}

	public Aeroport findById(Integer idAero) {
		// D�claration d'un objet aeroport
		Aeroport aeroport = null;
		
		try {

			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM aeroport where idAero=?");
			//Cherche l'idAero voulu dans la BDD
			ps.setInt(1, idAero);

			//R�cup�ration des r�sultats de la requ�te
			ResultSet tuple = ps.executeQuery();

			if (tuple.next()) {
				aeroport = new Aeroport(tuple.getInt("idAero"), tuple.getString("nom"));
			}

		}  catch (SQLException e) {
			e.printStackTrace();
		} 

		return aeroport;
	}

	
	
	public void create(Aeroport aeroport) {
		
	try {
		
		PreparedStatement ps = connexion.prepareStatement("insert into aeroport (idAero,nom) VALUES(?,?)");
		ps.setLong(1, aeroport.getIdAer());
		ps.setString(2, aeroport.getNom());
		
		// on execute 
		ps.executeUpdate();

	} catch (SQLException e) {
		e.printStackTrace();
	} 
}
		
		
		
	

	
	
	
	public Aeroport update(Aeroport aeroport) {
		
		try {
			
			PreparedStatement ps = connexion.prepareStatement("update aeroport set nom=? where idAero = ?");
			
			ps.setLong(2, aeroport.getIdAer());
			ps.setString(1, aeroport.getNom());
			
			

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		
		return aeroport;
		
		
	}

	
	public void delete(Aeroport aeroport) {
		
		try {
			PreparedStatement ps = connexion.prepareStatement("delete from aeroport where idAero = ?");
			ps.setLong(1, aeroport.getIdAer());

			ps.executeUpdate();

	
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}	
		
		
	}

