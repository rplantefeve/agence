package vol.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vol.model.Escale;


public class EscaleDaoSql implements EscaleDao {

	public EscaleDaoSql() {
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
	
	public List<Escale> findAll() {
		// Liste des escales que l'on va retourner
		List<Escale> escales = new ArrayList<Escale>();
		AeroportDaoSQL aeroportDAO = new AeroportDaoSQL();
		VolDaoSql volDAO = new VolDaoSql();
		try {
			// connexion
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM escale");
			// 4. Execution de la requ�te
			ResultSet tuple = ps.executeQuery();
			// 5. Parcoutuple de l'ensemble des r�sultats (ResultSet) pour
			// r�cup�rer les valeutuple des colonnes du tuple qui correspondent aux
			// valeur des attributs de l'objet
			while (tuple.next()) {
				// Creation d'un objet escale
				Escale escale = new Escale(tuple.getInt("idEscale"));
				escale.setDateArrivee(tuple.getDate("dateArrivee"));
				escale.setDateDepart(tuple.getDate("dateDepart"));
				escale.setHeureArrivee(tuple.getTime("heureArrivee"));
				escale.setHeureDepart(tuple.getTime("heureDepart"));
				// ajout des id Adress
				escale.setVol(volDAO.findById(tuple.getInt("idVol")));
				//ajout des aeroports
				escale.setAeoroport(aeroportDAO.findById(tuple.getInt("idAeroport")));
				// Ajout du nouvel objet Aeroport cr�� � la liste des a�roports
				escales.add(escale);
			} // fin de la boucle de parcoutuple de l'ensemble des r�sultats
			//ajout des vols;
			
			
			
			volDAO.fermetureConnexion();
			aeroportDAO.fermetureConnexion();

		}  catch (SQLException e) {
			e.printStackTrace();
		}
		// Retourne la liste de tous les a�roports
		return escales;
	}

	
	public Escale findById(Integer idEscale){
		Escale escale = new Escale();
		AeroportDaoSQL aeroport = new AeroportDaoSQL();
		VolDaoSql vol = new VolDaoSql();
		
		try {

			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM escale where idEscale=?");
			//Cherche l'idVol voulu dans la BDD
			ps.setInt(1, idEscale);

			//R�cup�ration des r�sultats de la requ�te
			ResultSet tuple = ps.executeQuery();

			if (tuple.next()) {
				escale.setIdEscale(tuple.getInt("idEscale"));
				escale.setDateArrivee(tuple.getDate("dateArrivee"));
				escale.setDateDepart(tuple.getDate("dateDepart"));
				escale.setHeureArrivee(tuple.getTime("heureArrivee"));
				escale.setHeureDepart(tuple.getTime("heureDepart"));			
				escale.setVol(vol.findById(tuple.getInt("idVol")));
				vol.fermetureConnexion();
				escale.setAeoroport(aeroport.findById(tuple.getInt("idAeroport")));
				aeroport.fermetureConnexion();
			}

		}  catch (SQLException e) {
			e.printStackTrace();
		} 

		return escale;
	}
		
	public void create(Escale escale) {
	}
	
	/* (non-Javadoc)
	 * @see formation.dao.Dao#update(java.lang.Object)
	 */
	@Override
	public Escale update(Escale escale) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see formation.dao.Dao#delete(java.lang.Object)
	 */
	@Override
	public void delete(Escale escale) {
	}

}
