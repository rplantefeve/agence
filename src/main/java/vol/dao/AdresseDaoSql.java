package vol.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vol.model.Adresse;

public class AdresseDaoSql implements AdresseDao {

	private Connection connexion;
	
	public AdresseDaoSql() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		// 2. Créer la connexion à la base (on instancie l'objet connexion)
		try {
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/vol", "user", "");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void fermetureConnexion() {
		try {
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Adresse> findAll() {
		// Liste des clients que l'on va retourner
				List<Adresse> ListAdresse = new ArrayList<Adresse>();

				try {

					/*
					 * Connexion à la BDD
					 */
					PreparedStatement ps = connexion.prepareStatement("SELECT * FROM adresse");

					// 4. Execution de la requête
					ResultSet tuple = ps.executeQuery();
					// 5. Parcoutuple de l'ensemble des résultats (ResultSet) pour
					// récupérer les valeutuple des colonnes du tuple qui correspondent
					// aux
					// valeur des attributs de l'objet
					while (tuple.next()) {
						// Creation d'un objet Client
						Adresse objAdresse = new Adresse(tuple.getInt("idAdd"));

						objAdresse.setAdresse(tuple.getString("adresse"));
						objAdresse.setCodePostal(tuple.getString("codePostal"));
						objAdresse.setVille(tuple.getString("ville"));
						objAdresse.setPays(tuple.getString("pays"));
						
						// Ajout du nouvel objet Client créé à la liste des clients
						ListAdresse.add(objAdresse);
					} // fin de la boucle de parcoutuple de l'ensemble des résultats

				} catch (SQLException e) {
					e.printStackTrace();
				}
				// Retourne la liste de toutes les clients
				return ListAdresse;
	}

	@Override
	public Adresse findById(Integer idAdd) {
		// Déclaration d'un objet Client
				Adresse objAdresse = null;

				try {
					// Connexion à la BDD
					PreparedStatement ps = connexion.prepareStatement("SELECT * FROM adresse WHERE idAdd=?");
					// Cherche l'idVill voulu dans la BDD
					ps.setInt(1, idAdd);

					// Récupération des résultats de la requête
					ResultSet tuple = ps.executeQuery();

					if (tuple.next()) {
						objAdresse = new Adresse(tuple.getInt("idAdd"));
						objAdresse.setAdresse(tuple.getString("adresse"));
						objAdresse.setCodePostal(tuple.getString("codePostal"));
						objAdresse.setVille(tuple.getString("ville"));
						objAdresse.setPays(tuple.getString("pays"));
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

				return objAdresse;
	}

	/* (non-Javadoc)
	 * @see formation.dao.Dao#create(java.lang.Object)
	 */
	@Override
	public void create(Adresse adresse) {
		try {
			// Créer sa requête d'insertion INSERT INTO
			PreparedStatement ps;
		
			ps = connexion.prepareStatement("insert into adresse (idAdd,adresse,codePostal,ville,pays) VALUES(?,?,?,?,?)");
			ps.setInt(1, adresse.getIdAdd());
			ps.setString(2, adresse.getAdresse());
			ps.setString(3, adresse.getCodePostal());
			ps.setString(4, adresse.getVille());
			ps.setString(5, adresse.getPays());
			
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
	public Adresse update(Adresse adresse) {
		try {
			// MAJ d'une table (ici, adresse) pour laquelle je vais positionner les champs pour lesquels l'id est de ?
			PreparedStatement ps = connexion.prepareStatement("update adresse set adresse=?,codePostal=?,ville=?,pays=? where idAdd = ?");
			
			ps.setInt(5, adresse.getIdAdd());
			
			ps.setString(1, adresse.getAdresse());
			ps.setString(2, adresse.getCodePostal());
			ps.setString(3, adresse.getVille());
			ps.setString(4, adresse.getPays());
			

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} 		
		return adresse;
	}
	
	/* (non-Javadoc)
	 * @see formation.dao.Dao#delete(java.lang.Object)
	 */
	@Override
	public void delete(Adresse adresse) {
		try {

			PreparedStatement ps = connexion.prepareStatement("delete from adresse where idAdd = ?");
			ps.setLong(1, adresse.getIdAdd());

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
