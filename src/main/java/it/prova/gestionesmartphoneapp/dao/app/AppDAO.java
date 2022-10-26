package it.prova.gestionesmartphoneapp.dao.app;

import it.prova.gestionesmartphoneapp.dao.IBaseDAO;
import it.prova.gestionesmartphoneapp.model.App;

public interface AppDAO extends IBaseDAO<App>{
	public void deleteAppFromThirdTable (Long idApp) throws Exception;

}
