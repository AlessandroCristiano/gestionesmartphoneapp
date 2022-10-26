package it.prova.gestionesmartphoneapp.dao;

import it.prova.gestionesmartphoneapp.dao.app.AppDAO;
import it.prova.gestionesmartphoneapp.dao.app.AppDAOImpl;
import it.prova.gestionesmartphoneapp.dao.smartphone.SmartphoneDAO;
import it.prova.gestionesmartphoneapp.dao.smartphone.SmartphoneDAOImpl;

public class MyDaoFactory {

	private static AppDAO appDaoInstance = null;
	private static SmartphoneDAO SmartphoneDaoInstance = null;

	public static AppDAO getAppDAOInstance() {
		if (appDaoInstance == null)
			appDaoInstance = new AppDAOImpl();

		return appDaoInstance;
	}

	public static SmartphoneDAO getSmartphoneDAOInstance() {
		if (SmartphoneDaoInstance == null)
			SmartphoneDaoInstance = new SmartphoneDAOImpl();

		return SmartphoneDaoInstance;
	}

}
