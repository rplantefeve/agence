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
	 * Retourne la liste de tous les objets métiers de la source de données
	 * @return Liste des objets métiers de la source de données
	 */
	List<T> findAll();
	
	/**
	 * Retourne un objet métier en fonction de sa clé primaire
	 * 
	 * @param id
	 *            Clé primaire
	 * @return L'objet métier trouvé
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
	 * Supprime in objet metier de la source de donnée
	 * @param obj obj a supprimer
	 */
	void delete(T obj);
	
}