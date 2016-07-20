package vol.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vol.model.EtatReservation;
import vol.model.Reservation;

public class ReservationDaoSQL implements ReservationDao {
	
	private Connection connexion;

	public ReservationDaoSQL() {
		/*
		 * Connexion � la BDD
		 */
		// 1. Chargement du driver
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
	
	public void fermetureConnexion(){
		try {
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Reservation> findAll() {
		// Liste des r�servations que l'on va retourner
		List<Reservation> reservations = new ArrayList<Reservation>();
		PassagerDaoSQL passagerDAO = new PassagerDaoSQL();
		ClientDaoSql clientDAO = new ClientDaoSql();
		VolDaoSql volDAO = new VolDaoSql();
		try {
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM Reservation");
			// 4. Execution de la requ�te
			ResultSet tuple = ps.executeQuery();
			// 5. Parcoutuple de l'ensemble des r�sultats (ResultSet) pour
			// r�cup�rer les valeutuple des colonnes du tuple qui correspondent aux
			// valeur des attributs de l'objet
			while (tuple.next()) {
				// Creation d'un objet compagnieAerienne
				Reservation reservation = new Reservation(tuple.getInt("idResa"));
				// MAJ des autres attributs de Eleve
				reservation.setDate(tuple.getDate("dateReservation"));
				reservation.setNumero(tuple.getInt("numero"));
				reservation.setEtat(EtatReservation.valueOf(tuple.getString("etat")));
				reservation.setVol(volDAO.findById(tuple.getInt("idVol")));
				reservation.setPassager(passagerDAO.findById(tuple.getInt("idPassager")));
				reservation.setClient(clientDAO.findById(tuple.getInt("idClient")));
				// Ajout du nouvel objet r�servation cr�� � la liste des r�servations
				reservations.add(reservation);
			} // fin de la boucle de parcours de l'ensemble des r�sultats
			//insertion de l'objet vol dans r�servation
			
			volDAO.fermetureConnexion();
			clientDAO.fermetureConnexion();
			passagerDAO.fermetureConnexion();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Retourne la liste de toutes les r�servations
		return reservations;
	}

	public Reservation findById(Integer idRes) {
		// D�claration d'un objet reservation
		Reservation reservation = null;
		PassagerDaoSQL passagerDAO = new PassagerDaoSQL();
		ClientDaoSql clientDAO = new ClientDaoSql();
		VolDaoSql volDAO = new VolDaoSql();
		
		try {
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM reservation where idResa=?");
			//Cherche l'idResa recherch� dans la BDD
			ps.setInt(1, idRes);

			//R�cup�ration des r�sultats de la requ�te
			ResultSet tuple = ps.executeQuery();

			if (tuple.next()) {
				reservation = new Reservation(tuple.getInt("idResa"));
				reservation.setDate(tuple.getDate("dateReservation"));
				reservation.setNumero(tuple.getInt("numero"));
				reservation.setEtat(EtatReservation.valueOf(tuple.getString("etat")));
				reservation.setVol(volDAO.findById(tuple.getInt("idVol")));
				reservation.setPassager(passagerDAO.findById(tuple.getInt("idPassager")));
				reservation.setClient(clientDAO.findById(tuple.getInt("idClient")));
				volDAO.fermetureConnexion();
				clientDAO.fermetureConnexion();
				passagerDAO.fermetureConnexion();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reservation;
	}

	/* (non-Javadoc)
	 * @see formation.dao.Dao#create(java.lang.Object)
	 */
	@Override
	public void create(Reservation reservation) {
		try {
			// Cr�er sa requ�te d'insertion INSERT INTO
			PreparedStatement ps;
			//Je teste si la r�servation est li�e � un passager
			if (reservation.getPassager()!=null) {
				if (reservation.getClient()!=null){
					ps = connexion.prepareStatement("insert into reservation (idResa,date,numero,idPassager,idClient) VALUES(?,?,?,?,?)");
					ps.setInt(5, reservation.getIdCli());
				}
				else {
					ps = connexion.prepareStatement("insert into reservation (idResa,date,numero,idPassager) VALUES(?,?,?,?)");
				}
				ps.setInt(4, reservation.getIdPas());
			}
			else {
				if (reservation.getClient()!=null){
					ps = connexion.prepareStatement("insert into reservation (idResa,date,numero,idClient) VALUES(?,?,?,?)");
					ps.setInt(4, reservation.getIdCli());
				}
				else {
					ps = connexion.prepareStatement("insert into reservation (idResa,date,numero,idPassager) VALUES(?,?,?,?)");
				}
			}
			ps.setInt(1, reservation.getIdRes());
			ps.setDate(2, new java.sql.Date(reservation.getDate().getTime()));
			ps.setInt(3, reservation.getNumero());
			//ps.setString(4, reservation.getEtat().getLabel());
						
			// une fois la requ�te cr��e, je l'ex�cute
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see formation.dao.Dao#update(java.lang.Object)
	 */
	@Override
	public Reservation update(Reservation reservation) {
		try {
			// MAJ d'une table (ici, reservation) pour laquelle je vais positionner les champs pour lesquels l'id est de ?
			PreparedStatement ps = connexion.prepareStatement("update reservation set date=?,numero=? where idResa = ?");
			
			ps.setInt(3, reservation.getIdRes());
			
			ps.setDate(1, new java.sql.Date(reservation.getDate().getTime()));
			ps.setInt(2, reservation.getNumero());
			//ps.setString(3, reservation.getEtat().getLabel());
		
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} 		
		return reservation;
	}
	
	/* (non-Javadoc)
	 * @see formation.dao.Dao#delete(java.lang.Object)
	 */
	@Override
	public void delete(Reservation reservation) {
		try {
			PreparedStatement ps;
			// Suppression du passager associ� � la r�servation
			ps = connexion.prepareStatement("delete from reservation where idResas = ?");
			ps.setLong(1, reservation.getIdRes());

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
