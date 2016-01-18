/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.repeid.manager.test.api;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.repeid.manager.api.model.AccionistaProvider;
import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.provider.ProviderFactory;
import org.repeid.manager.api.model.provider.ProviderType;

/**
 * Attempt to create producer methods for CDI beans.
 *
 * @author eric.wittmann@redhat.com
 */

@ApplicationScoped
public class CdiFactoryTest {

    public static ProviderType PROVIDER = ProviderType.JPA;

    @Produces
    public TipoDocumentoProvider getTipoDocumentoProvider(
            @ProviderFactory(ProviderType.JPA) TipoDocumentoProvider jpa,
            @ProviderFactory(ProviderType.MONGO) TipoDocumentoProvider mongo) {
        if (PROVIDER.equals(ProviderType.JPA)) {
            return jpa;
        } else if (PROVIDER.equals(ProviderType.MONGO)) {
            return mongo;
        } else {
            throw new RuntimeException("Provider type desconocido");
        }
    }

    @Produces
    public PersonaNaturalProvider getPersonaNaturalProvider(
            @ProviderFactory(ProviderType.JPA) PersonaNaturalProvider jpa,
            @ProviderFactory(ProviderType.MONGO) PersonaNaturalProvider mongo) {
        if (PROVIDER.equals(ProviderType.JPA)) {
            return jpa;
        } else if (PROVIDER.equals(ProviderType.MONGO)) {
            return mongo;
        } else {
            throw new RuntimeException("Provider type desconocido");
        }
    }

    @Produces
    public PersonaJuridicaProvider getPersonaJuridicaProvider(
            @ProviderFactory(ProviderType.JPA) PersonaJuridicaProvider jpa,
            @ProviderFactory(ProviderType.MONGO) PersonaJuridicaProvider mongo) {
        if (PROVIDER.equals(ProviderType.JPA)) {
            return jpa;
        } else if (PROVIDER.equals(ProviderType.MONGO)) {
            return mongo;
        } else {
            throw new RuntimeException("Provider type desconocido");
        }
    }

    @Produces
    public AccionistaProvider getAccionistaProvider(@ProviderFactory(ProviderType.JPA) AccionistaProvider jpa,
            @ProviderFactory(ProviderType.MONGO) AccionistaProvider mongo) {
        if (PROVIDER.equals(ProviderType.JPA)) {
            return jpa;
        } else if (PROVIDER.equals(ProviderType.MONGO)) {
            return mongo;
        } else {
            throw new RuntimeException("Provider type desconocido");
        }
    }

}
