package org.repeid.manager.api.model.utils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.AccionistaRepresentation;
import org.repeid.manager.api.beans.representations.PersonaJuridicaRepresentation;
import org.repeid.manager.api.beans.representations.PersonaNaturalRepresentation;
import org.repeid.manager.api.beans.representations.TipoDocumentoRepresentation;
import org.repeid.manager.api.model.AccionistaModel;
import org.repeid.manager.api.model.AccionistaProvider;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.enums.EstadoCivil;
import org.repeid.manager.api.model.enums.Sexo;
import org.repeid.manager.api.model.enums.TipoEmpresa;
import org.repeid.manager.api.model.enums.TipoPersona;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RepresentationToModel {

	public TipoDocumentoModel createTipoDocumento(TipoDocumentoRepresentation rep, TipoDocumentoProvider provider) throws StorageException {
		TipoDocumentoModel model = provider.create(rep.getAbreviatura(), rep.getDenominacion(),
				rep.getCantidadCaracteres(), TipoPersona.valueOf(rep.getTipoPersona()));
		return model;
	}

	public PersonaNaturalModel createPersonaNatural(PersonaNaturalRepresentation rep,
			TipoDocumentoModel tipoDocumentoModel, PersonaNaturalProvider personaNaturalProvider)
					throws StorageException {

		PersonaNaturalModel model = personaNaturalProvider.create(rep.getCodigoPais(), tipoDocumentoModel,
				rep.getNumeroDocumento(), rep.getApellidoPaterno(), rep.getApellidoMaterno(), rep.getNombres(),
				rep.getFechaNacimiento(), Sexo.valueOf(rep.getSexo().toUpperCase()));

		model.setEstadoCivil(
				rep.getEstadoCivil() != null ? EstadoCivil.valueOf(rep.getEstadoCivil().toUpperCase()) : null);
		model.setOcupacion(rep.getOcupacion());
		model.setUbigeo(rep.getUbigeo());
		model.setDireccion(rep.getDireccion());
		model.setReferencia(rep.getReferencia());
		model.setTelefono(rep.getTelefono());
		model.setCelular(rep.getCelular());
		model.setEmail(rep.getEmail());

		model.commit();
		return model;
	}

	public PersonaJuridicaModel createPersonaJuridica(PersonaJuridicaRepresentation rep,
			TipoDocumentoModel tipoDocumentoModel, PersonaNaturalModel representanteLegal,
			PersonaJuridicaProvider personaJuridicaProvider) throws StorageException {

		PersonaJuridicaModel model = personaJuridicaProvider.create(representanteLegal, rep.getCodigoPais(),
				tipoDocumentoModel, rep.getNumeroDocumento(), rep.getRazonSocial(), rep.getFechaConstitucion(),
				TipoEmpresa.valueOf(rep.getTipoEmpresa().toUpperCase()), rep.isFinLucro());

		model.setActividadPrincipal(rep.getActividadPrincipal());
		model.setNombreComercial(rep.getNombreComercial());

		model.setUbigeo(rep.getUbigeo());
		model.setDireccion(rep.getDireccion());
		model.setReferencia(rep.getReferencia());
		model.setTelefono(rep.getTelefono());
		model.setCelular(rep.getCelular());
		model.setEmail(rep.getEmail());

		model.commit();
		return model;
	}

	public AccionistaModel createAccionista(AccionistaRepresentation rep, PersonaJuridicaModel personaJuridica,
			PersonaNaturalModel personaNatural, AccionistaProvider accionistaProvider) {

		AccionistaModel model = accionistaProvider.create(personaJuridica, personaNatural,
				rep.getPorcentajeParticipacion());
		return model;
	}

}
