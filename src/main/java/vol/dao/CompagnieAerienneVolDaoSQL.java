package vol.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vol.model.CompagnieAerienneVol;

public class CompagnieAerienneVolDaoSQL implements CompagnieAerienneVolDao {
	public CompagnieAerienneVolDaoSQL() {
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
	
	private Connection connexion;
	
	public void fermetureConnexion(){
		try {
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<CompagnieAerienneVol> findAll() {
		// Liste des CompagnieAerienneVol que l'on va retourner
		List<CompagnieAerienneVol> compagnieaeriennevols = new ArrayList<CompagnieAerienneVol>();
		VolDaoSql volDAO = new VolDaoSql();
		CompagnieAerienneDaoSQL compagnieDAO = new CompagnieAerienneDaoSQL();
		// Connexion à la BDD
		try {
			/*
			 * Connexion à la BDD
			 */
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM compagnie_aerienne_vol");
			// 4. Execution de la requête
			ResultSet tuple = ps.executeQuery();
			// 5. Parcours de l'ensemble des résultats (ResultSet) pour
			// récupérer les valeurs des colonnes du tuple qui correspondent aux
			// valeur des attributs de l'objet
			while (tuple.next()) {
				// Creation d'un objet Aeroport
				CompagnieAerienneVol compagnieaeriennevol = new CompagnieAerienneVol(tuple.getString("numero"), tuple.getShort("ouvert"));
				compagnieaeriennevol.setId(tuple.getInt("id"));
				compagnieaeriennevol.setCompagnieAerienne(compagnieDAO.findById(tuple.getInt("idCompagnie")));
				compagnieaeriennevol.setVol(volDAO.findById(tuple.getInt("idVol")));
				// Ajout du nouvel objet Aeroport créé à la liste des élèves
				compagnieaeriennevols.add(compagnieaeriennevol);
			} // fin de la boucle de parcours de l'ensemble des résultats
			
			volDAO.fermetureConnexion();
			compagnieDAO.fermetureConnexion();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Retourne la liste de tous les aéroports
		return compagnieaeriennevols;
	}
	
	public CompagnieAerienneVol findById(Integer id) {
		CompagnieAerienneVol compagnieAerienneVol = null;
		VolDaoSql volDAO = new VolDaoSql();
		CompagnieAerienneDaoSQL compagnieDAO = new CompagnieAerienneDaoSQL();
		try {
			
			
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM compagnie_aerienne_vol where id=?");
			//Cherche l'idComp recherché dans la BDD
			ps.setInt(1, id);

			//Récupération des résultats de la requête
			ResultSet tuple = ps.executeQuery();

			if (tuple.next()) {
				compagnieAerienneVol = new CompagnieAerienneVol(tuple.getString("numero"), tuple.getShort("ouvert"));
				compagnieAerienneVol.setId(tuple.getInt("id"));
				compagnieAerienneVol.setVol(volDAO.findById(tuple.getInt("idVol")));
				compagnieAerienneVol.setCompagnieAerienne(compagnieDAO.findById(tuple.getInt("idCompagnie")));
				volDAO.fermetureConnexion();
				compagnieDAO.fermetureConnexion();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return compagnieAerienneVol;
	}

	/* (non-Javadoc)
	 * @see formation.dao.Dao#create(java.lang.Object)
	 */
	@Override
	public void create(CompagnieAerienneVol compagnieAerienneVol) {
	}
	
	/* (non-Javadoc)
	 * @see formation.dao.Dao#update(java.lang.Object)
	 */
	@Override
	public CompagnieAerienneVol update(CompagnieAerienneVol compagnieAerienneVol) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see formation.dao.Dao#delete(java.lang.Object)
	 */
	@Override
	public void delete(CompagnieAerienneVol compagnieAerienneVol) {
	}

}
