package org.repeid.manager.api.rest.bussiness.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.repeid.manager.api.model.enums.EstadoCivil;
import org.repeid.manager.api.model.enums.Sexo;
import org.repeid.manager.api.model.enums.TipoEmpresa;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.rest.bussiness.MaestroResource;

@Stateless
public class MaestroResourceImpl implements MaestroResource {

    @Override
    public List<String> getAllTipoPersonas() {
        TipoPersona[] enums = TipoPersona.values();

        List<String> representations = new ArrayList<>();
        for (int i = 0; i < enums.length; i++) {
            representations.add(enums[i].toString());
        }
        return representations;
    }

    @Override
    public List<String> getAllEstadosCiviles() {
        EstadoCivil[] enums = EstadoCivil.values();

        List<String> representations = new ArrayList<>();
        for (int i = 0; i < enums.length; i++) {
            representations.add(enums[i].toString());
        }
        return representations;
    }

    @Override
    public List<String> getAllSexos() {
        Sexo[] enums = Sexo.values();

        List<String> representations = new ArrayList<>();
        for (int i = 0; i < enums.length; i++) {
            representations.add(enums[i].toString());
        }
        return representations;
    }

    @Override
    public List<String> getAllTiposEmpresa() {
        TipoEmpresa[] enums = TipoEmpresa.values();

        List<String> representations = new ArrayList<>();
        for (int i = 0; i < enums.length; i++) {
            representations.add(enums[i].toString());
        }
        return representations;
    }

}
