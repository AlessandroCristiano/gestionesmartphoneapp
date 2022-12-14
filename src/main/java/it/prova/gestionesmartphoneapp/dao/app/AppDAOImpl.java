package it.prova.gestionesmartphoneapp.dao.app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestionesmartphoneapp.model.App;


public class AppDAOImpl implements AppDAO{
	
	private EntityManager entityManager;

	@Override
	public List<App> list() throws Exception {
		return entityManager.createQuery("from App", App.class).getResultList();
	}

	@Override
	public App get(Long id) throws Exception {
		return entityManager.find(App.class, id);
	}

	@Override
	public void update(App input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
		
	}

	@Override
	public void insert(App input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
		
	}

	@Override
	public void delete(App input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));
		
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void deleteAppFromThirdTable(Long idApp) throws Exception {
		entityManager.createNativeQuery("delete from smartphone_app where app_id = ?1").setParameter(1, idApp).executeUpdate();	
	}

	@Override
	public App findByIdFetchingSmartphones(Long id) throws Exception {
		TypedQuery<App> query = entityManager
				.createQuery("select a FROM App a left join fetch a.smartphones s where a.id = :idsmartphone", App.class);
		query.setParameter("idsmartphone", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}
}
