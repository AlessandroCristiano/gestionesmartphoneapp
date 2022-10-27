package it.prova.gestionesmartphoneapp.test;

import java.text.SimpleDateFormat;
import java.util.Date;


import it.prova.gestionesmartphoneapp.dao.EntityManagerUtil;
import it.prova.gestionesmartphoneapp.model.App;
import it.prova.gestionesmartphoneapp.model.Smartphone;
import it.prova.gestionesmartphoneapp.service.AppService;
import it.prova.gestionesmartphoneapp.service.MyServiceFactory;
import it.prova.gestionesmartphoneapp.service.SmartphoneService;

public class MyTest {

	public static void main(String[] args) {
		AppService appServiceInstance= MyServiceFactory.getappServiceInstance();
		SmartphoneService smartphoneServiceInstance = MyServiceFactory.getsmartphoneServiceInstance();
		
		try {

			System.out.println("In tabella App ci sono " + appServiceInstance.listAll().size() + " elementi.");
			System.out.println("In tabella Smarphone ci sono " + smartphoneServiceInstance.listAll().size() + " elementi.");
			System.out.println(
					"**************************** inizio batteria di test ********************************************");
			testInserimentoNuovoSmarphone(smartphoneServiceInstance);
			testAggiornamentoSmartphoneEsistente(smartphoneServiceInstance);
			testInserimentoNuovaApp(appServiceInstance);
			testAggiornamentoversioneAppConData(appServiceInstance);
			testInstallazioneAppSuSmartphone(smartphoneServiceInstance, appServiceInstance);
			testDisinstallaUnAppDaUnoSmartphone(smartphoneServiceInstance, appServiceInstance);
			testRimozioneCompletaSmartphoneDaDueApp(smartphoneServiceInstance, appServiceInstance);

			System.out.println(
					"****************************** fine batteria di test ********************************************");

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}
	}
	
	private static void testInserimentoNuovoSmarphone(SmartphoneService smartphoneServiceInstance)throws Exception{
		System.out.println(".......testInserimentoNuovoSmarphone inizio.............");

		Smartphone smartphoneInstance = new Smartphone("Samsung", "s20", 500, "1.0.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoSmarphone fallito ");
		
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testRimozioneSmarphone fallito ");

		System.out.println(".......testInserimentoNuovoSmarphone fine: PASSED.............");
	}
	
	private static void testAggiornamentoSmartphoneEsistente(SmartphoneService smartphoneServiceInstance)throws Exception{
		System.out.println(".......testAggiornamentoSmartphoneEsistente inizio.............");

		Smartphone smartphoneInstance = new Smartphone("Xiaomi", "p20", 450, "1.2.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoSmarphone fallito ");
		String versionePrecedente=smartphoneInstance.getVersioneOS();
		
		smartphoneInstance.setVersioneOS("3.0.0");
		
		smartphoneServiceInstance.aggiorna(smartphoneInstance);
		if(smartphoneInstance.getVersioneOS().equals(versionePrecedente))
			throw new RuntimeException("testAggiornaSmarphone fallito ");
		
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testRimozioneSmarphone fallito ");

		System.out.println(".......testAggiornamentoSmartphoneEsistente fine: PASSED.............");
	}
	
	private static void testInserimentoNuovaApp(AppService appServiceInstance)throws Exception{
		System.out.println(".......testInserimentoNuovaApp inizio.............");
		
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataUltimoAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		
		App appInstance = new App("Instagram", dataInstallazione, dataUltimoAggiornamento, "3.0.1");
		
		appServiceInstance.inserisciNuovo(appInstance);
		if(appInstance.getId()==null)
			throw new RuntimeException("testInserimentoNuovaApp fallito ");
		
		appServiceInstance.rimuovi(appInstance.getId());
		if (appInstance.getId() == null)
			throw new RuntimeException("testRimozioneNuovaApp fallito ");
			
		System.out.println(".......testInserimentoNuovaApp fine: PASSED.............");
	}
	
	private static void testAggiornamentoversioneAppConData(AppService appServiceInstance)throws Exception{
		System.out.println(".......testAggiornamentoversioneAppConData inizio.............");
		
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataUltimoAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		
		App appInstance = new App("Instagram", dataInstallazione, dataUltimoAggiornamento, "3.0.1");
		
		appServiceInstance.inserisciNuovo(appInstance);
		if(appInstance.getId()==null)
			throw new RuntimeException("testInserimentoNuovaApp fallito ");
		
		String versionePrecedente=appInstance.getVersione();
		Date dataPrimaDiAggiornare=appInstance.getDataUltimoAggiornamento();
		
		appInstance.setVersione("3.2.1");
		appInstance.setDataUltimoAggiornamento(new Date());
		
		appServiceInstance.aggiorna(appInstance);
		if(appInstance.getVersione().equals(versionePrecedente) && appInstance.getDataUltimoAggiornamento().equals(dataPrimaDiAggiornare))
			throw new Exception("testAggiornamento Fallito");
		
		appServiceInstance.rimuovi(appInstance.getId());
		if (appInstance.getId() == null)
			throw new RuntimeException("testRimozioneNuovaApp fallito ");
			
		System.out.println(".......testAggiornamentoversioneAppConData fine: PASSED.............");	
	}
	
	private static void testInstallazioneAppSuSmartphone(SmartphoneService smartphoneServiceInstance, AppService appServiceInstance)throws Exception{
		System.out.println(".......testInstallazioneAppSuSmartphone inizio.............");
		Smartphone smartphoneInstance = new Smartphone("Samsung", "s20", 500, "1.0.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoSmarphone fallito ");
		
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataUltimoAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		
		App appInstance = new App("Instagram", dataInstallazione, dataUltimoAggiornamento, "3.0.1");
		
		appServiceInstance.inserisciNuovo(appInstance);
		if(appInstance.getId()==null)
			throw new RuntimeException("testInserimentoNuovaApp fallito ");		
		
		smartphoneServiceInstance.aggiungiApp(smartphoneInstance, appInstance);
		
		// ricarico eager per forzare il test
		Smartphone SmartphoneReloaded = smartphoneServiceInstance.caricaSingoloElementoEagerApps(smartphoneInstance.getId());
		if (SmartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testInstallazioneAppSuSmartphone fallito: app non collegata ");
		System.out.println(".......testInstallazioneAppSuSmartphone fine: PASSED.............");	
	}
	
	private static void testDisinstallaUnAppDaUnoSmartphone(SmartphoneService smartphoneServiceInstance, AppService appServiceInstance)throws Exception{
		System.out.println(".......testDisinstallaUnAppDaUnoSmartphone inizio.............");
		Smartphone smartphoneInstance = new Smartphone("iphone", "13 Pro", 1500, "4.0.0");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoSmarphone fallito ");
		
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataUltimoAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		
		App appInstance = new App("Whatsapp", dataInstallazione, dataUltimoAggiornamento, "3.0.1");
		
		appServiceInstance.inserisciNuovo(appInstance);
		if(appInstance.getId()==null)
			throw new RuntimeException("testInserimentoNuovaApp fallito ");		
		
		smartphoneServiceInstance.aggiungiApp(smartphoneInstance, appInstance);
		
		Smartphone SmartphoneReloaded = smartphoneServiceInstance.caricaSingoloElementoEagerApps(smartphoneInstance.getId());
		if (SmartphoneReloaded.getApps().size()!=1)
			throw new RuntimeException("testInstallazioneAppSuSmartphone fallito: app non collegata ");

		appServiceInstance.rimuoviAppDallaTerzaTabella(appInstance.getId());
		
		SmartphoneReloaded = smartphoneServiceInstance.caricaSingoloElementoEagerApps(smartphoneInstance.getId());
		if (!SmartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testDisinstallazioneAppSuSmartphone fallito: app scollegata ");
		
		appServiceInstance.rimuovi(appInstance.getId());
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());
		
		System.out.println(".......testDisinstallaUnAppDaUnoSmartphone fine: PASSED.............");	
	}
	
	private static void testRimozioneCompletaSmartphoneDaDueApp(SmartphoneService smartphoneServiceInstance, AppService appServiceInstance)throws Exception{
		System.out.println(".......testRimozioneCompletaSmartphoneDaDueApp inizio.............");
		
		Smartphone smartphoneInstance = new Smartphone("asus", "rogue 15", 2500, "5.0.0");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoSmarphone fallito ");
		
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2012");
		Date dataUltimoAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		
		App appInstance = new App("Facebook", dataInstallazione, dataUltimoAggiornamento, "4.9.1");
		
		appServiceInstance.inserisciNuovo(appInstance);
		if(appInstance.getId()==null)
			throw new RuntimeException("testInserimentoNuovaApp fallito ");		
		
		Date dataInstallazioneSecondaApp = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2013");
		Date dataUltimoAggiornamentoSecondaApp = new SimpleDateFormat("dd-MM-yyyy").parse("27-10-2022");
		
		App appInstanceSeconda = new App("buddy bank", dataInstallazioneSecondaApp, dataUltimoAggiornamentoSecondaApp, "3.0.1");
		
		appServiceInstance.inserisciNuovo(appInstanceSeconda);
		if(appInstance.getId()==null)
			throw new RuntimeException("testInserimentoNuovaApp fallito ");		
		
		smartphoneServiceInstance.aggiungiApp(smartphoneInstance, appInstance);
		smartphoneServiceInstance.aggiungiApp(smartphoneInstance, appInstanceSeconda);
		
		Smartphone SmartphoneReloaded = smartphoneServiceInstance.caricaSingoloElementoEagerApps(smartphoneInstance.getId());
		if (SmartphoneReloaded.getApps().size()!=2)
			throw new RuntimeException("testInstallazioneAppSuSmartphone fallito: app non collegata ");

		smartphoneServiceInstance.rimuoviSmartphoneTerzatabella(smartphoneInstance.getId());
		
		App AppReloaded = appServiceInstance.caricaSingoloElementoEagerSmartphones(appInstance.getId());
		if (!AppReloaded.getSmartphones().isEmpty())
			throw new RuntimeException("testDisinstallazioneSmartphone fallito: smartphone collegato ");
		
		//controllo se la seconda app ha uno smartphone
		AppReloaded = appServiceInstance.caricaSingoloElementoEagerSmartphones(appInstanceSeconda.getId());
		if (!AppReloaded.getSmartphones().isEmpty())
			throw new RuntimeException("testDisinstallazioneSmartphone fallito: smartphone collegato ");
		
		appServiceInstance.rimuovi(appInstance.getId());
		appServiceInstance.rimuovi(appInstanceSeconda.getId());
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());
		
		System.out.println(".......testRimozioneCompletaSmartphoneDaDueApp fine: PASSED.............");
	}
}
