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

public class CdiFactoryTest {

    @Produces
    public static TipoDocumentoProvider getTipoDocumentoProvider(
            @ProviderFactory(type = ProviderType.JPA) TipoDocumentoProvider jpa,
            @ProviderFactory(type = ProviderType.MONGO) TipoDocumentoProvider mongo) {
        return jpa;
    }

    @Produces
    public static PersonaNaturalProvider getPersonaNaturalProvider(
            @ProviderFactory(type = ProviderType.JPA) PersonaNaturalProvider jpa,
            @ProviderFactory(type = ProviderType.MONGO) PersonaNaturalProvider mongo) {
        return jpa;
    }

    @Produces
    public static PersonaJuridicaProvider getPersonaJuridicaProvider(
            @ProviderFactory(type = ProviderType.JPA) PersonaJuridicaProvider jpa,
            @ProviderFactory(type = ProviderType.MONGO) PersonaJuridicaProvider mongo) {
        return jpa;
    }

    @Produces
    public static AccionistaProvider getAccionistaProvider(
            @ProviderFactory(type = ProviderType.JPA) AccionistaProvider jpa,
            @ProviderFactory(type = ProviderType.MONGO) AccionistaProvider mongo) {
        return jpa;
    }

}
