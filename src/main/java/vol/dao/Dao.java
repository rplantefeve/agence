package vol.dao;


import java.util.List;


/**
 * Contrat que tous les DAOs devront respecter.
 * @author ajc
 *
 * @param <T>
 * @param <PK>
 */
public interface Dao<T, PK> {
	/**
	 * Retourne la liste de tous les objets m�tiers de la source de donn�es
	 * @return Liste des objets m�tiers de la source de donn�es
	 */
	List<T> findAll();
	
	/**
	 * Retourne un objet m�tier en fonction de sa cl� primaire
	 * 
	 * @param id
	 *            Cl� primaire
	 * @return L'objet m�tier trouv�
	 */
	T findById(PK id);
	
	/**Cree un nouvel objet metier afin de le persister
	 * @param obj 
	 */
	void create(T obj);
	/**
	 * retourne un objet metier mis a jour
	 * @param obj l'ojet a mettre a jour
	 * @return objet metier maj
	 */
	T update(T obj);
	/**
	 * Supprime in objet metier de la source de donn�e
	 * @param obj obj a supprimer
	 */
	void delete(T obj);
	
}