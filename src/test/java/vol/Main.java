package vol;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import vol.dao.AdresseDao;
import vol.dao.AdresseDaoSql;
import vol.dao.AeroportDao;
import vol.dao.AeroportDaoSQL;
import vol.dao.ClientDao;
import vol.dao.ClientDaoSql;
import vol.dao.CompagnieAerienneDao;
import vol.dao.CompagnieAerienneDaoSQL;
import vol.dao.LoginDao;
import vol.dao.LoginDaoSql;
import vol.dao.PassagerDao;
import vol.dao.PassagerDaoSQL;
import vol.dao.ReservationDao;
import vol.dao.ReservationDaoSQL;
import vol.dao.VilleDao;
import vol.dao.VilleDaoSQL;
import vol.dao.VolDao;
import vol.dao.VolDaoSql;
import vol.model.Adresse;
import vol.model.Aeroport;
import vol.model.Client;
import vol.model.CompagnieAerienne;
import vol.model.Login;
import vol.model.Passager;
import vol.model.Reservation;
import vol.model.Ville;
import vol.model.Vol;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		AeroportDao aeroDao = new AeroportDaoSQL();
		AdresseDao addDao = new AdresseDaoSql();
		VolDao volDao = new VolDaoSql();
		CompagnieAerienneDao caDao = new CompagnieAerienneDaoSQL();
		LoginDao logdao = new LoginDaoSql();
		ReservationDao resadao = new ReservationDaoSQL();
		PassagerDao paxdao = new PassagerDaoSQL();
		ClientDao clientdao = new ClientDaoSql();
		VilleDao villedao = new VilleDaoSQL();

		Vol AF445 = new Vol(101);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dt1;
		try {
			dt1 = simpleDateFormat.parse("15/08/2016");
			Date dt2 = simpleDateFormat.parse("16/08/2016");
			AF445.setDateDepart(dt1);
			AF445.setDateArrivee(dt2);
			AF445.setHeureDepart(null);
			AF445.setHeureArrivee(null);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		Aeroport aimeeCesaire = new Aeroport(100, "Aimée Cesaire");

		CompagnieAerienne XLairways = new CompagnieAerienne(100, "XLairways");

		Ville fortDeFrance = new Ville(100, "Fort Da France");

		Login log1 = testLogin();

		Adresse add1 = new Adresse(100);
		add1.setAdresse("15 residence du camion");
		add1.setCodePostal("33158");
		add1.setVille("Saint-Sulpice");
		add1.setPays("France");
		
		Adresse add2 = new Adresse(100);
		add1.setAdresse("25 residence du truc bleu");
		add1.setCodePostal("33157");
		add1.setVille("bourg-Sulpice");
		add1.setPays("France");

		Passager pax1 = new Passager(100);
		pax1.setNom("Doré");
		pax1.setPrenom("Garcin");
		pax1.setAdresse(add1);

		Client cl1 = new Client(100);

		Reservation resa1 = new Reservation(100);
		resa1.setClient(cl1);

		
		Date dt;
		try {
			dt = simpleDateFormat.parse("15/08/2016");
			resa1.setDate(dt);
			//resa1.setEtat(null);
			resa1.setIdCli(100);
			resa1.setIdPas(100);
			resa1.setNumero(15749);
			resa1.setPassager(pax1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cl1.setNom("Constantin");
		cl1.setPrenom("Luis");
		cl1.setNumeroTel("0556987456");
		cl1.setNumeroFax("0447896523");
		cl1.setLog(log1);
		cl1.setListReservations(null);
		cl1.setAdresse(add1);
		cl1.setEmail("constlui@gmail.com");

		aeroDao.create(aimeeCesaire);
		aimeeCesaire.setNom("FDF");
		// je MAJ niveau bdd
		aeroDao.update(aimeeCesaire);
		aeroDao.delete(aimeeCesaire);

		caDao.create(XLairways);
		XLairways.setNom("XLA");
		caDao.update(XLairways);
		caDao.delete(XLairways);

//		volDao.create(AF445);
//		Date dt3;
//		try {
//			dt3 = simpleDateFormat.parse("17/08/2016");
//			AF445.setDateDepart(dt3);
//			volDao.update(AF445);
//			volDao.delete(AF445);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		

		logdao.create(log1);
		log1.setMotDePasse("rootine");
		logdao.update(log1);
		logdao.delete(log1);

		villedao.create(fortDeFrance);
		fortDeFrance.setNom("Fort de France");
		villedao.update(fortDeFrance);
		villedao.delete(fortDeFrance);
		
		//resadao.create(resa1);
		//resadao.findById(10).setNumero(20154);
		//resadao.update(resadao.findById(10));
		//resa1.setClient(null);
		//resadao.update(resa1);
		//resadao.delete(resa1);
//		
//		paxdao.create(pax1);
//		pax1.setAdresse(add2);
//		paxdao.update(pax1);
//		paxdao.delete(pax1);
		
//		clientdao.create(cl1);
//		cl1.setEmail("juujuu@gmail.com");
//		clientdao.update(cl1);
//		clientdao.delete(cl1);
		
		
		
		
		
		

	}

    /**
     * @return
     */
    private static Login testLogin()
    {
        Login log1 = new Login(100);
		log1.setLogin("preview");
		log1.setMotDePasse("user");
		log1.setAdmin(0);
        return log1;
    }

}