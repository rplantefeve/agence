package vol.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vol.model.VilleAeroport;

public class VilleAeroportDaoSql implements VilleAeroportDao {

	public VilleAeroportDaoSql() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 2. Cr�er la connexion � la base (on instancie l'objet connexion)
		try {
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/vol", "user", "");
		} catch (SQLException e) {
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

	@Override
	public List<VilleAeroport> findAll() {
		List<VilleAeroport> villeAeroports = new ArrayList<VilleAeroport>();
		AeroportDaoSQL aeroportDAO = new AeroportDaoSQL();
		VilleDaoSQL villeDAO = new VilleDaoSQL();
		try {
			/*
			 * Connexion � la BDD
			 */
			
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM ville_aeroport");
			// 4. Execution de la requ�te
			ResultSet tuple = ps.executeQuery();
			// 5. Parcoutuple de l'ensemble des r�sultats (ResultSet) pour
			// r�cup�rer les valeutuple des colonnes du tuple qui correspondent aux
			// valeur des attributs de l'objet
			while (tuple.next()) {
				// Creation d'un objet Aeroport
				VilleAeroport villeAeroport = new VilleAeroport(tuple.getInt("id"));
				villeAeroport.setVille(villeDAO.findById(tuple.getInt("idVille")));
				villeAeroport.setAeroport(aeroportDAO.findById(tuple.getInt("idAeroport")));
				// Ajout du nouvel objet Aeroport cr�� � la liste des a�roports
				villeAeroports.add(villeAeroport);
			} // fin de la boucle de parcoutuple de l'ensemble des r�sultats
			
//			for (int i=0; i<villeAeroports.size(); i++)
//			{
//				villeAeroports.get(i).setVille(villeDAO.findById(villeAeroports.get(i).getIdVille()));
//			}
			
//			for (int i=0; i<villeAeroports.size(); i++)
//			{
//				villeAeroports.get(i).setAeroport(aeroportDAO.findById(villeAeroports.get(i).getIdAeroport()));
//			}
			villeDAO.fermetureConnexion();
			aeroportDAO.fermetureConnexion();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Retourne la liste de tous les a�roports
		return villeAeroports;
	}

	@Override
	public VilleAeroport findById(Integer id) {
		// D�claration d'un objet aeroport
		VilleAeroport villeAeroport = null;
		AeroportDaoSQL aeroportDAO = new AeroportDaoSQL();
		VilleDaoSQL villeDAO = new VilleDaoSQL();
		
		try {

			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM ville_aeroport where id=?");
			//Cherche l'idAero voulu dans la BDD
			ps.setInt(1, id);

			//R�cup�ration des r�sultats de la requ�te
			ResultSet tuple = ps.executeQuery();

			if (tuple.next()) {
				villeAeroport = new VilleAeroport(tuple.getInt("id"));
				villeAeroport.setVille(villeDAO.findById(tuple.getInt("idVille")));
				villeDAO.fermetureConnexion();
				villeAeroport.setAeroport(aeroportDAO.findById(tuple.getInt("idAeroport")));
				aeroportDAO.fermetureConnexion();
				
			}

		}  catch (SQLException e) {
			e.printStackTrace();
		} 

		return villeAeroport;
	}
	
	/* (non-Javadoc)
	 * @see formation.dao.Dao#create(java.lang.Object)
	 */
	@Override
	public void create(VilleAeroport villeAeroport) {
	}
	
	/* (non-Javadoc)
	 * @see formation.dao.Dao#update(java.lang.Object)
	 */
	@Override
	public VilleAeroport update(VilleAeroport villeAeroport) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see formation.dao.Dao#delete(java.lang.Object)
	 */
	@Override
	public void delete(VilleAeroport villeAeroport) {
	}

	
	

}
