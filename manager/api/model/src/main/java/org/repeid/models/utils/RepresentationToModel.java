package org.repeid.models.utils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.repeid.models.AccionistaModel;
import org.repeid.models.AccionistaProvider;
import org.repeid.models.PersonaJuridicaModel;
import org.repeid.models.PersonaJuridicaProvider;
import org.repeid.models.PersonaNaturalModel;
import org.repeid.models.PersonaNaturalProvider;
import org.repeid.models.TipoDocumentoModel;
import org.repeid.models.TipoDocumentoProvider;
import org.repeid.models.enums.EstadoCivil;
import org.repeid.models.enums.Sexo;
import org.repeid.models.enums.TipoEmpresa;
import org.repeid.models.enums.TipoPersona;
import org.repeid.models.security.RoleModel;
import org.repeid.models.security.RoleProvider;
import org.repeid.models.security.UserModel;
import org.repeid.models.security.UserProvider;
import org.repeid.representations.idm.AccionistaRepresentation;
import org.repeid.representations.idm.PersonaJuridicaRepresentation;
import org.repeid.representations.idm.PersonaNaturalRepresentation;
import org.repeid.representations.idm.TipoDocumentoRepresentation;
import org.repeid.representations.idm.security.RoleRepresentation;
import org.repeid.representations.idm.security.UserRepresentation;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RepresentationToModel {

    public TipoDocumentoModel createTipoDocumento(TipoDocumentoRepresentation rep,
            TipoDocumentoProvider provider) {
        TipoDocumentoModel model = provider.create(rep.getAbreviatura(), rep.getDenominacion(),
                rep.getCantidadCaracteres(), TipoPersona.valueOf(rep.getTipoPersona()));
        return model;
    }

    public PersonaNaturalModel createPersonaNatural(PersonaNaturalRepresentation rep,
            TipoDocumentoModel tipoDocumentoModel, PersonaNaturalProvider personaNaturalProvider) {

        PersonaNaturalModel model = personaNaturalProvider.create(rep.getCodigoPais(), tipoDocumentoModel,
                rep.getNumeroDocumento(), rep.getApellidoPaterno(), rep.getApellidoMaterno(),
                rep.getNombres(), rep.getFechaNacimiento(), Sexo.valueOf(rep.getSexo().toUpperCase()));

        model.setEstadoCivil(rep.getEstadoCivil() != null
                ? EstadoCivil.valueOf(rep.getEstadoCivil().toUpperCase()) : null);
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
            PersonaJuridicaProvider personaJuridicaProvider) {

        PersonaJuridicaModel model = personaJuridicaProvider.create(representanteLegal, rep.getCodigoPais(),
                tipoDocumentoModel, rep.getNumeroDocumento(), rep.getRazonSocial(),
                rep.getFechaConstitucion(), TipoEmpresa.valueOf(rep.getTipoEmpresa().toUpperCase()),
                rep.isFinLucro());

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

    public AccionistaModel createAccionista(AccionistaRepresentation rep,
            PersonaJuridicaModel personaJuridica, PersonaNaturalModel personaNatural,
            AccionistaProvider accionistaProvider) {

        AccionistaModel model = accionistaProvider.create(personaJuridica, personaNatural,
                rep.getPorcentajeParticipacion());
        return model;
    }

    public RoleModel createRole(RoleRepresentation rep, RoleProvider roleProvider) {
        // TODO Auto-generated method stub
        return null;
    }

    public UserModel createUser(UserRepresentation rep, UserProvider userProvider) {
        // TODO Auto-generated method stub
        
    }
}
