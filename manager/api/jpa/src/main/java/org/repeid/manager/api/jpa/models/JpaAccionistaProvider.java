package org.repeid.manager.api.jpa.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.jpa.entities.AccionistaEntity;
import org.repeid.manager.api.jpa.entities.PersonaJuridicaEntity;
import org.repeid.manager.api.jpa.entities.PersonaNaturalEntity;
import org.repeid.manager.api.model.AccionistaModel;
import org.repeid.manager.api.model.AccionistaProvider;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Named
@Stateless
@Local(AccionistaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaAccionistaProvider extends AbstractHibernateStorage implements AccionistaProvider {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public AccionistaModel create(PersonaJuridicaModel personaJuridica, PersonaNaturalModel personaNatural,
            BigDecimal porcentaje) {
        if (findByPersonaJuridicaNatural(personaJuridica, personaNatural) != null) {
            throw new ModelDuplicateException(
                    "AccionistaEntity personaNatural y personaJuridica debe ser unico, se encontro otra entidad con personaJuridica="
                            + personaJuridica + "y personaNatural=" + personaNatural);
        }

        PersonaJuridicaEntity personaJuridicaEntity = em.find(PersonaJuridicaEntity.class,
                personaJuridica.getId());
        PersonaNaturalEntity personaNaturalEntity = em.find(PersonaNaturalEntity.class,
                personaNatural.getId());

        AccionistaEntity accionistaEntity = new AccionistaEntity();
        accionistaEntity.setPersonaNatural(personaNaturalEntity);
        accionistaEntity.setPersonaJuridica(personaJuridicaEntity);
        accionistaEntity.setPorcentajeParticipacion(porcentaje);
        em.persist(accionistaEntity);
        return new AccionistaAdapter(em, accionistaEntity);
    }

    @Override
    public AccionistaModel findById(String id) {
        AccionistaEntity accionistaEntity = em.find(AccionistaEntity.class, id);
        return accionistaEntity != null ? new AccionistaAdapter(em, accionistaEntity) : null;
    }

    @Override
    public AccionistaModel findByPersonaJuridicaNatural(PersonaJuridicaModel personaJuridica,
            PersonaNaturalModel personaNatural) {
        TypedQuery<AccionistaEntity> query = em.createNamedQuery(
                "AccionistaEntity.findByIdPersonaJuridicaNatural", AccionistaEntity.class);
        query.setParameter("idPersonaJuridica", personaJuridica.getId());
        query.setParameter("idPersonaNatural", personaNatural.getId());
        List<AccionistaEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new IllegalStateException("Mas de un AccionistaEntity con personaNatural="
                    + personaNatural.getId() + " y personaJuridica=" + personaJuridica.getId() + ", results="
                    + results);
        } else {
            return new AccionistaAdapter(em, results.get(0));
        }
    }

    @Override
    public boolean remove(AccionistaModel accionistaModel) {
        AccionistaEntity accionistaEntity = em.find(AccionistaEntity.class, accionistaModel.getId());
        if (accionistaEntity == null)
            return false;
        em.remove(accionistaEntity);
        return true;
    }

    @Override
    public List<AccionistaModel> getAll(PersonaJuridicaModel personaJuridicaModel) {
        PersonaJuridicaEntity personaJuridicaEntity = em.find(PersonaJuridicaEntity.class,
                personaJuridicaModel.getId());

        Set<AccionistaEntity> entities = personaJuridicaEntity.getAccionistas();
        List<AccionistaModel> models = new ArrayList<AccionistaModel>();
        for (AccionistaEntity accionistaEntity : entities) {
            models.add(new AccionistaAdapter(em, accionistaEntity));
        }

        return models;
    }

}
