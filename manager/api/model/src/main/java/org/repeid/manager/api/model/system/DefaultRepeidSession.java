package org.repeid.manager.api.model.system;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.TipoDocumentoProvider;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */

@ApplicationScoped
public class DefaultRepeidSession implements RepeidSession {

	@Inject
	private RepeidTransaction repeidTransaction;

	@Inject
	private RepeidTransactionManager transactionManager;

	@Inject
	private TipoDocumentoProvider tipoDocumentoProvider;

	@Override
	public TipoDocumentoProvider tipoDocumentos() {
		transactionManager.enlist(repeidTransaction);
		return tipoDocumentoProvider;
	}

	@Override
	public RepeidTransactionManager getTransaction() {
		return transactionManager;
	}

	@Override
	public PersonaNaturalProvider personasNaturales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonaJuridicaProvider personasJuridicas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

}
