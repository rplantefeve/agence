package vol.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vol.model.Passager;

public class PassagerDaoSQL implements PassagerDao {
	
	/*
	 * Connexion BDD
	 */
	private Connection connexion;

	public PassagerDaoSQL() {
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
	}
	
	public void fermetureConnexion(){
		try {
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Passager> findAll() {
		// Liste des passagers que l'on va retourner
		List<Passager> passagers = new ArrayList<Passager>();
		AdresseDaoSql adresseDAO = new AdresseDaoSql();
		try {
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM Passager");
			// 4. Execution de la requête
			ResultSet tuple = ps.executeQuery();
			// 5. Parcoutuple de l'ensemble des résultats (ResultSet) pour
			// récupérer les valeutuple des colonnes du tuple qui correspondent aux
			// valeur des attributs de l'objet
			while (tuple.next()) {
				// Creation d'un objet passager
				Passager passager = new Passager(tuple.getInt("idPassager"));
				// MAJ des autres attributs de Passager
				passager.setNom(tuple.getString("nom"));
				passager.setPrenom(tuple.getString("prenom"));
				passager.setAdresse(adresseDAO.findById(tuple.getInt("idAdd")));
				// Ajout du nouvel objet réservation créé à la liste des passagers
				passagers.add(passager);
			} // fin de la boucle de parcours de l'ensemble des résultats
			adresseDAO.fermetureConnexion();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Retourne la liste de toutes les réservations
		return passagers;
	}

	public Passager findById(Integer idPas) {
		// Déclaration d'un objet reservation
		Passager passager = null;
		AdresseDaoSql adresseDAO = new AdresseDaoSql();
		
		try {
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM passager where idPassager=?");
			//Cherche l'idPas recherché dans la BDD
			ps.setInt(1, idPas);

			//Récupération des résultats de la requête
			ResultSet tuple = ps.executeQuery();

			if (tuple.next()) {
				passager = new Passager(tuple.getInt("idPassager"));
				passager.setNom(tuple.getString("nom"));
				passager.setPrenom(tuple.getString("prenom"));
				passager.setAdresse(adresseDAO.findById(tuple.getInt("idAdd")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return passager;
	}

	/* (non-Javadoc)
	 * @see formation.dao.Dao#create(java.lang.Object)
	 */
	@Override
	public void create(Passager passager) {
		try {
			// Créer sa requête d'insertion INSERT INTO
			PreparedStatement ps;
			
			// je teste si le passager est lié à une adresse
			if (passager.getAdresse() == null) 
			{
				ps = connexion.prepareStatement("insert into passager (idPassager,nom,prenom) VALUES(?,?,?)");
			}
			else 
			{
				ps = connexion.prepareStatement("insert into passager (idPassager,nom,prenom,idAdd) VALUES(?,?,?,?)");
				ps.setInt(4,  passager.getAdresse().getIdAdd());
			}
						
			ps.setInt(1, passager.getIdPas());
			ps.setString(2, passager.getNom());
			ps.setString(3, passager.getPrenom());
						
			// une fois la requête créée, je l'exécute
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see formation.dao.Dao#update(java.lang.Object)
	 */
	@Override
	public Passager update(Passager passager) {
		try {
			// MAJ d'une table (ici, passager) pour laquelle je vais positionner les champs pour lesquels l'id est de ?
			PreparedStatement ps = connexion.prepareStatement("update passager set nom=?,prenom=? where idPassager = ?");
			
			ps.setInt(3, passager.getIdPas());
			
			ps.setString(1, passager.getNom());
			ps.setString(2, passager.getPrenom());
		
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} 		
		return passager;
	}
	
	/* (non-Javadoc)
	 * @see formation.dao.Dao#delete(java.lang.Object)
	 */
	@Override
	public void delete(Passager passager) {
		try {
			PreparedStatement ps;
			if (passager.getAdresse() != null)
			{
				AdresseDao adresseDao = new AdresseDaoSql();
				adresseDao.delete(adresseDao.findById(passager.getAdresse().getIdAdd()));
			}
			ps = connexion.prepareStatement("delete from passager where idPassager = ?");
			ps.setLong(1, passager.getIdPas());

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
