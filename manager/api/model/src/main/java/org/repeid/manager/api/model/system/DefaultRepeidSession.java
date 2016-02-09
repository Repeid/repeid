package org.repeid.manager.api.model.system;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.repeid.manager.api.core.config.RepeidApplication;
import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */

@Startup
@DependsOn("RepeidApplication")
@Singleton
public class DefaultRepeidSession implements RepeidSession {

	private static final Logger log = LoggerFactory.getLogger(RepeidApplication.class);

	private RepeidTransactionManager transactionManager;

	@Inject
	private TipoDocumentoProvider tipoDocumentoProvider;

	// Constructor
	public DefaultRepeidSession() {
		this.transactionManager = new DefaultRepeidTransactionManager();
	}

	@PostConstruct
	public void init() {
		log.info("RepeidSession started");
	}

	@PreDestroy
	public void close() {
		log.info("Stopping RepeidSession");
	}

	@Override
	public RepeidTransactionManager getTransaction() {
		return transactionManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.repeid.manager.api.model.system.RepeidSession#tipoDocumentos()
	 */
	@Override
	public TipoDocumentoProvider tipoDocumentos() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.repeid.manager.api.model.system.RepeidSession#personasNaturales()
	 */
	@Override
	public PersonaNaturalProvider personasNaturales() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.repeid.manager.api.model.system.RepeidSession#personasJuridicas()
	 */
	@Override
	public PersonaJuridicaProvider personasJuridicas() {
		// TODO Auto-generated method stub
		return null;
	}

}
