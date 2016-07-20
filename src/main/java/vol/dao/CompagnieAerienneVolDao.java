package vol.dao;

import vol.model.CompagnieAerienneVol;

/**
 * permet de synchroniser l'objet métier et la BDD
 * @author ajc
 *
 */
public interface CompagnieAerienneVolDao extends Dao<CompagnieAerienneVol, Integer> {
	
	CompagnieAerienneVol findById (Integer id);

}
