package org.repeid.manager.test.api.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import javax.inject.Inject;

import org.junit.Test;
import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.enums.Sexo;
import org.repeid.manager.api.model.enums.TipoEmpresa;
import org.repeid.manager.api.model.enums.TipoPersona;

public class PersonaJuridicaProviderTest extends AbstractTest {

    @Inject
    private TipoDocumentoProvider tipoDocumentoProvider;

    @Inject
    private PersonaNaturalProvider personaNaturalProvider;

    @Inject
    private PersonaJuridicaProvider personaJuridicaProvider;

    @Test
    public void addPersonaJuridica() throws StorageException {
        TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.create("RUC",
                "Registro unico de contribuyente", 8, TipoPersona.JURIDICA);

        PersonaNaturalModel representanteLegalModel = personaNaturalProvider.create("PER", tipoDocumentoModel,
                "12345678", "Flores", "Huertas", "Jhon wilber", Calendar.getInstance().getTime(),
                Sexo.MASCULINO);

        PersonaJuridicaModel model = personaJuridicaProvider.create(representanteLegalModel, "PER",
                tipoDocumentoModel, "10467793549", "Softgreen S.A.C.", Calendar.getInstance().getTime(),
                TipoEmpresa.PRIVADA, true);

        assertThat(model, is(notNullValue()));
        assertThat(model.getId(), is(notNullValue()));
        assertThat(model.getTipoDocumento(), is(equalTo(tipoDocumentoModel)));
        assertThat(model.getRepresentanteLegal(), is(equalTo(representanteLegalModel)));
    }

    @Test
    public void getPersonaJuridicaById() throws StorageException {
        TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.create("RUC",
                "Registro unico de contribuyente", 8, TipoPersona.JURIDICA);

        PersonaNaturalModel representanteLegalModel = personaNaturalProvider.create("PER", tipoDocumentoModel,
                "12345678", "Flores", "Huertas", "Jhon wilber", Calendar.getInstance().getTime(),
                Sexo.MASCULINO);

        PersonaJuridicaModel model1 = personaJuridicaProvider.create(representanteLegalModel, "PER",
                tipoDocumentoModel, "10467793549", "Softgreen S.A.C.", Calendar.getInstance().getTime(),
                TipoEmpresa.PRIVADA, true);

        String id = model1.getId();
        PersonaJuridicaModel model2 = personaJuridicaProvider.findById(id);

        assertThat(model1, is(equalTo(model2)));
    }

    @Test
    public void getPersonaJuridicaByTipoNumeroDoc() throws StorageException {
        TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.create("RUC",
                "Registro unico de contribuyente", 8, TipoPersona.JURIDICA);

        PersonaNaturalModel representanteLegalModel = personaNaturalProvider.create("PER", tipoDocumentoModel,
                "12345678", "Flores", "Huertas", "Jhon wilber", Calendar.getInstance().getTime(),
                Sexo.MASCULINO);

        PersonaJuridicaModel model1 = personaJuridicaProvider.create(representanteLegalModel, "PER",
                tipoDocumentoModel, "10467793549", "Softgreen S.A.C.", Calendar.getInstance().getTime(),
                TipoEmpresa.PRIVADA, true);

        TipoDocumentoModel tipoDocumento = model1.getTipoDocumento();
        String numeroDocumento = model1.getNumeroDocumento();
        PersonaJuridicaModel model2 = personaJuridicaProvider.findByTipoNumeroDocumento(tipoDocumento,
                numeroDocumento);

        assertThat(model1, is(equalTo(model2)));
    }

    @Test
    public void removePersonaJuridica() throws StorageException {
        TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.create("RUC",
                "Registro unico de contribuyente", 8, TipoPersona.JURIDICA);

        PersonaNaturalModel representanteLegalModel = personaNaturalProvider.create("PER", tipoDocumentoModel,
                "12345678", "Flores", "Huertas", "Jhon wilber", Calendar.getInstance().getTime(),
                Sexo.MASCULINO);

        PersonaJuridicaModel model1 = personaJuridicaProvider.create(representanteLegalModel, "PER",
                tipoDocumentoModel, "10467793549", "Softgreen S.A.C.", Calendar.getInstance().getTime(),
                TipoEmpresa.PRIVADA, true);

        String id = model1.getId();
        boolean result = personaJuridicaProvider.remove(model1);

        PersonaJuridicaModel model2 = personaJuridicaProvider.findById(id);

        assertThat(result, is(true));
        assertThat(model2, is(nullValue()));
    }

}
