package vol.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vol.model.Login;

/**
 * Fait la liaison entre la classe login et la base de données
 * @author ajc
 *
 */
public class LoginDaoSql implements LoginDao{

	/**
	 * l'attribut qui stock la connection à la base de données
	 */
	private Connection connexion;
	
	public LoginDaoSql() {
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
	public List<Login> findAll() {
		// Liste des clients que l'on va retourner
				List<Login> ListLogin = new ArrayList<Login>();
				
				
				try {

					/*
					 * Connexion à la BDD
					 */
					PreparedStatement ps = connexion.prepareStatement("SELECT * FROM login");

					// 4. Execution de la requête
					ResultSet tuple = ps.executeQuery();
					// 5. Parcoutuple de l'ensemble des résultats (ResultSet) pour
					// récupérer les valeutuple des colonnes du tuple qui correspondent
					// aux
					// valeur des attributs de l'objet
					while (tuple.next()) {
						// Creation d'un objet Client
						Login objLogin = new Login(tuple.getInt("id"));

						
						objLogin.setLogin(tuple.getString("login"));
						objLogin.setMotDePasse(tuple.getString("motDePasse"));
						objLogin.setAdmin(tuple.getInt("admin"));

						// Ajout du nouvel objet Client créé à la liste des clients
						ListLogin.add(objLogin);
					} // fin de la boucle de parcoutuple de l'ensemble des résultats

				} catch (SQLException e) {
					e.printStackTrace();
				}
				// Retourne la liste de toutes les clients
				return ListLogin;
	}

	@Override
	public Login findById(Integer id) {
		// Déclaration d'un objet Client
				Login objLogin = null;

				try {
					// Connexion à la BDD
					PreparedStatement ps = connexion.prepareStatement("SELECT * FROM login WHERE id=?");
					// Cherche l'idVill voulu dans la BDD
					ps.setInt(1, id);

					// Récupération des résultats de la requête
					ResultSet tuple = ps.executeQuery();

					if (tuple.next()) {
						objLogin = new Login(tuple.getInt("id"));
						objLogin.setLogin(tuple.getString("login"));
						objLogin.setMotDePasse(tuple.getString("motDePasse"));
						objLogin.setAdmin(tuple.getInt("admin"));
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

				return objLogin;
	}

	public void delete(Login login) {
		try {
			PreparedStatement ps = connexion.prepareStatement("delete from login where id = ?");
			ps.setInt(1, login.getIdLog());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Login update(Login login) {
		try {
			// Syntaxe de l'update en BDD : UPDATE Table qui contient le tuple à modifier + set + Position des champs 
			PreparedStatement ps = connexion
					.prepareStatement("update login set login=?,motDePasse=?,admin=? where id = ?");

			ps.setInt(4, login.getIdLog());
			// Donne une valeur au point d'interrogation
			ps.setString(1, login.getLogin());
			ps.setString(2, login.getMotDePasse());
			ps.setInt(3, login.getAdmin());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return login;
	}
	
	public void create(Login login) {
		try {
			PreparedStatement ps;
				ps = connexion
						.prepareStatement("insert into login (id,login,motDePasse,admin) VALUES(?,?,?,?)");
			// Requête dynamique : on insert les valeurs des attributs de l'objet(get) dans les propriétés de la table(set)
			ps.setInt(1, login.getIdLog());
			ps.setString(2, login.getLogin());
			ps.setString(3, login.getMotDePasse());
			ps.setInt(4, login.getAdmin());
			// Exécution de la requête
			ps.executeUpdate(); //Pour les INSERT, UPDATE ou DELETE

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
