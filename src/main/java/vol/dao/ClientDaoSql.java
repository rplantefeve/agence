package vol.dao;

// liste des imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vol.model.Client;

/**
 * Permet la synchronisation entre l'objet Client et la BDD
 * @author ajc
 *
 */
public class ClientDaoSql implements ClientDao {

	private Connection connexion;

	/**
	 * constructeur
	 * 
	 */
	public ClientDaoSql() {
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

	/**
	 * Permet la fermeture de la connexion
	 * 
	 */
	public void fermetureConnexion() {
		try {
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Client> findAll() {
		// Liste des clients que l'on va retourner
		List<Client> ListClients = new ArrayList<Client>();
		AdresseDaoSql adresseDAO = new AdresseDaoSql();
		LoginDaoSql loginDAO = new LoginDaoSql();

		try {

			/*
			 * Connexion à la BDD
			 */
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM client");

			// 4. Execution de la requête
			ResultSet tuple = ps.executeQuery();
			// 5. Parcoutuple de l'ensemble des résultats (ResultSet) pour
			// récupérer les valeutuple des colonnes du tuple qui correspondent
			// aux
			// valeur des attributs de l'objet
			while (tuple.next()) {
				// Creation d'un objet Client
				Client objClient = new Client(tuple.getInt("idClient"));

				objClient.setNom(tuple.getString("nom"));
				objClient.setPrenom(tuple.getString("prenom"));
				objClient.setNumeroTel(tuple.getString("numTel"));
				objClient.setNumeroFax(tuple.getString("numFax"));
				objClient.setEmail(tuple.getString("eMail"));
				objClient.setSiret(tuple.getInt("siret"));

				objClient.setAdresse(adresseDAO.findById(tuple.getInt("idAdd")));
				objClient.setLog(loginDAO.findById(tuple.getInt("idLog")));

				// Ajout du nouvel objet Client créé à la liste des clients
				ListClients.add(objClient);
			} // fin de la boucle de parcoutuple de l'ensemble des résultats
			adresseDAO.fermetureConnexion();
			loginDAO.fermetureConnexion();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Retourne la liste de toutes les clients
		return ListClients;
	}

	@Override
	public Client findById(Integer idCli) {
		// Déclaration d'un objet Client
		Client objClient = null;
		AdresseDaoSql adresseDAO = new AdresseDaoSql();
		LoginDaoSql loginDAO = new LoginDaoSql();

		try {
			// Connexion à la BDD
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM client WHERE idClient=?");
			// Cherche l'idVill voulu dans la BDD
			ps.setInt(1, idCli);

			// Récupération des résultats de la requête
			ResultSet tuple = ps.executeQuery();

			if (tuple.next()) {
				objClient = new Client(tuple.getInt("idClient"));
				objClient.setNom(tuple.getString("nom"));
				objClient.setPrenom(tuple.getString("prenom"));
				objClient.setNumeroTel(tuple.getString("numTel"));
				objClient.setNumeroFax(tuple.getString("numFax"));
				objClient.setEmail(tuple.getString("eMail"));
				objClient.setSiret(tuple.getInt("siret"));

				objClient.setAdresse(adresseDAO.findById(tuple.getInt("idAdd")));
				adresseDAO.fermetureConnexion();
				objClient.setLog(loginDAO.findById(tuple.getInt("idLog")));
				loginDAO.fermetureConnexion();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return objClient;
	}

	public void delete(Client client) {
		PreparedStatement ps;
		try {
			if (client.getAdresse() != null)
			{
				AdresseDao adresseDao = new AdresseDaoSql();
				adresseDao.delete(adresseDao.findById(client.getAdresse().getIdAdd()));
			}
			ps = connexion.prepareStatement("delete from client where idClient = ?");
			ps.setInt(1, client.getIdCli());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Client update(Client client) {
		try {
			// Syntaxe de l'update en BDD : UPDATE Table qui contient le tuple à modifier + set + Position des champs 
			PreparedStatement ps = connexion
					.prepareStatement("update client set nom=?,prenom=?,numTel=?,numFax=?,eMail=?,siret=? where idClient = ?");

			ps.setInt(7, client.getIdCli());
			// Donne une valeur au point d'interrogation
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getNumeroTel());
			ps.setString(4, client.getNumeroFax());
			ps.setString(5, client.getEmail());
			ps.setInt(6, client.getSiret());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return client;
	}
	
	public void create(Client client) {
		try {
			PreparedStatement ps;
			
			// je teste si le client est lié à une adresse
			if (client.getAdresse() == null) 
			{
				ps = connexion.prepareStatement("insert into client (idClient,nom,prenom,numTel,numFax,eMail,siret) VALUES(?,?,?,?,?,?,?)");
			}
			else 
			{
				ps = connexion.prepareStatement("insert into client (idClient,nom,prenom,numTel,numFax,eMail,siret,idAdd) VALUES(?,?,?,?,?,?,?,?)");
				ps.setInt(8,  client.getAdresse().getIdAdd());
			}
			
			// Requête dynamique : on insert les valeurs des attributs de l'objet(get) dans les propriétés de la table(set)
			ps.setInt(1, client.getIdCli());
			ps.setString(2, client.getNom());
			ps.setString(3, client.getPrenom());
			ps.setString(4, client.getNumeroTel());
			ps.setString(5, client.getNumeroFax());
			ps.setString(6, client.getEmail());
			ps.setInt(7, client.getSiret());
			// Exécution de la requête
			ps.executeUpdate(); //Pour les INSERT, UPDATE ou DELETE

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
