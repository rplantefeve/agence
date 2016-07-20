package vol.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vol.model.Vol;

public class VolDaoSql implements VolDao{

	private Connection connexion;
	
	public VolDaoSql(){
		/*
		 * Connexion à la BDD
		 */
		// 1. Chargement du driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 2. Créer la connexion à la base (on instancie l'objet connexion)
		try {
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/vol", "user", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 3. Création d'une requête (statement) à partir de l'objet
		// connexion
	}
	
	public void fermetureConnexion(){
		try {
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Vol> findAll() {
		// Liste des vols que l'on va retourner
		List<Vol> vols = new ArrayList<Vol>();
		//Création d'un objet aeroport pour faire un findbyid;
		AeroportDaoSQL aeroportDAO = new AeroportDaoSQL();
		// Connexion à la BDD
		try {
			
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM vol");
			// 4. Execution de la requête
			ResultSet tuple = ps.executeQuery();
			// 5. Parcoutuple de l'ensemble des résultats (ResultSet) pour
			// récupérer les valeutuple des colonnes du tuple qui correspondent aux
			// valeur des attributs de l'objet
			while (tuple.next()) {
				// Creation d'un objet Vol
				Vol vol = new Vol(tuple.getInt("idVol"));
				vol.setDateArrivee(tuple.getDate("dateArrivee"));
				vol.setDateDepart(tuple.getDate("dateDepart"));
				vol.setHeureArrivee(tuple.getTime("heureArrivee"));
				vol.setHeureDepart(tuple.getTime("heureDepart"));
				vol.setAeroportArrivee(aeroportDAO.findById(tuple.getInt("idAeroportArrivee")));
				vol.setAeroportDepart(aeroportDAO.findById(tuple.getInt("idAeroportDepart")));
				// Ajout du nouvel objet vol créé à la liste des vols
				vols.add(vol);
			} // fin de la boucle de parcoutuple de l'ensemble des résultats
			
			//fermeture de la base aeroport
			aeroportDAO.fermetureConnexion();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Retourne la liste de tous les aéroports
		return vols;
	}

	public Vol findById(Integer idVol) {
		// Déclaration d'un objet vol
		Vol vol = null;
		//Création d'un objet aeroport pour faire un findbyid;
		AeroportDaoSQL aeroportDAO = new AeroportDaoSQL();
		
		try {
			

			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM vol where idVol=?");
			//Cherche l'idVol voulu dans la BDD
			ps.setInt(1, idVol);

			//Récupération des résultats de la requête
			ResultSet tuple = ps.executeQuery();

			if (tuple.next()) {
				vol = new Vol(tuple.getInt("idVol"));
				vol.setDateArrivee(tuple.getDate("dateArrivee"));
				vol.setDateDepart(tuple.getDate("dateDepart"));
				vol.setHeureArrivee(tuple.getTime("heureArrivee"));
				vol.setHeureDepart(tuple.getTime("heureDepart"));
				vol.setAeroportArrivee(aeroportDAO.findById(tuple.getInt("idAeroportArrivee")));
				vol.setAeroportDepart(aeroportDAO.findById(tuple.getInt("idAeroportDepart")));
				//fermeture de la base aeroport
				aeroportDAO.fermetureConnexion();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} 

		return vol;
	}

	
	
	
	public void create(Vol vol) {
		
		try {
			
			PreparedStatement ps = connexion.prepareStatement("insert into vol (idVol,dateDepart,dateArrivee,heureDepart,heureArrivee) VALUES(?,?,?,?,?)");
			ps.setLong(1, vol.getIdVol());
			//je copnvertit un java.util date en date SQL
			ps.setDate(2, new java.sql.Date(vol.getDateDepart().getTime()));
			ps.setDate(3, new java.sql.Date(vol.getDateArrivee().getTime()));
			ps.setTime(4, new java.sql.Time(vol.getHeureDepart().getTime()));
			ps.setTime(5, new java.sql.Time(vol.getHeureArrivee().getTime()));
			// on execute 
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public Vol update(Vol vol) {
		
		
		try {
			
			PreparedStatement ps = connexion.prepareStatement("update vol set dateDepart=?,dateArrivee=?,heureDepart=?,heureArrivee where idVol = ?");
			
			ps.setLong(5, vol.getIdVol());
			
			
			//je copnvertit un java.util date en date SQL
			ps.setDate(1, new java.sql.Date(vol.getDateDepart().getTime()));
			ps.setDate(2, new java.sql.Date(vol.getDateArrivee().getTime()));
			ps.setTime(3, new java.sql.Time(vol.getHeureDepart().getTime()));
			ps.setTime(4, new java.sql.Time(vol.getHeureArrivee().getTime()));
			

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		
		return vol;
	}
	

	
	
	
	
	public void delete(Vol vol) {
		
		
		try {
			PreparedStatement ps = connexion.prepareStatement("delete from vol where idVol = ?");
			ps.setLong(1, vol.getIdVol());

			ps.executeUpdate();

	
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
		
}




