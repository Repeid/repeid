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
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.enums.Sexo;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.test.api.AbstractTest;

public class PersonaNaturalProviderTest extends AbstractTest {

    @Inject
    private PersonaNaturalProvider personaNaturalProvider;

    @Inject
    private TipoDocumentoProvider tipoDocumentoProvider;

    @Test
    public void create() throws StorageException {
        TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.create("DNI",
                "Documento nacional de identidad", 8, TipoPersona.NATURAL);

        PersonaNaturalModel model = personaNaturalProvider.create("PER", tipoDocumentoModel, "12345678",
                "Flores", "Huertas", "Jhon wilber", Calendar.getInstance().getTime(), Sexo.MASCULINO);

        assertThat("model no debe ser null", model, is(notNullValue()));
        assertThat("id no debe ser null", model.getId(), is(notNullValue()));
        assertThat("tipoDocumento no debe ser null", model.getTipoDocumento(), is(notNullValue()));
        assertThat("tipoDocumento debe ser igual al insertado", model.getTipoDocumento(),
                is(equalTo(tipoDocumentoModel)));
    }

    @Test
    public void findById() throws StorageException {
        TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.create("DNI",
                "Documento nacional de identidad", 8, TipoPersona.NATURAL);

        PersonaNaturalModel model1 = personaNaturalProvider.create("PER", tipoDocumentoModel, "12345678",
                "Flores", "Huertas", "Jhon wilber", Calendar.getInstance().getTime(), Sexo.MASCULINO);

        String id = model1.getId();
        PersonaNaturalModel model2 = personaNaturalProvider.findById(id);

        assertThat(model1, is(equalTo(model2)));
    }

    @Test
    public void findByTipoNumeroDocumento() throws StorageException {
        TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.create("DNI",
                "Documento nacional de identidad", 8, TipoPersona.NATURAL);

        PersonaNaturalModel model1 = personaNaturalProvider.create("PER", tipoDocumentoModel, "12345678",
                "Flores", "Huertas", "Jhon wilber", Calendar.getInstance().getTime(), Sexo.MASCULINO);

        TipoDocumentoModel tipoDocumento = model1.getTipoDocumento();
        String numeroDocumento = model1.getNumeroDocumento();
        PersonaNaturalModel model2 = personaNaturalProvider.findByTipoNumeroDocumento(tipoDocumento,
                numeroDocumento);

        assertThat(model1, is(equalTo(model2)));
    }

    @Test
    public void remove() throws StorageException {
        TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.create("DNI",
                "Documento nacional de identidad", 8, TipoPersona.NATURAL);

        PersonaNaturalModel model1 = personaNaturalProvider.create("PER", tipoDocumentoModel, "12345678",
                "Flores", "Huertas", "Jhon wilber", Calendar.getInstance().getTime(), Sexo.MASCULINO);

        String id = model1.getId();
        boolean result = personaNaturalProvider.remove(model1);

        PersonaNaturalModel model2 = personaNaturalProvider.findById(id);

        assertThat(result, is(true));
        assertThat(model2, is(nullValue()));
    }

}
