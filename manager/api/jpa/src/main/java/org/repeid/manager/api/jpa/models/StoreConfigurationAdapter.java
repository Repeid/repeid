/*******************************************************************************
 * Repeid, Home of Professional Open Source
 *
 * Copyright 2015 Sistcoop, Inc. and/or its affiliates.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.repeid.manager.api.jpa.models;

import javax.persistence.EntityManager;

import org.repeid.manager.api.jpa.entities.StoreConfigurationEntity;
import org.repeid.manager.api.model.StoreConfigurationModel;
import org.repeid.manager.api.model.enums.StoreConfigurationType;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

public class StoreConfigurationAdapter implements StoreConfigurationModel {

    private StoreConfigurationEntity storeConfigurationEntity;
    private EntityManager em;

    public StoreConfigurationAdapter(EntityManager em, StoreConfigurationEntity storeConfigurationEntity) {
        this.em = em;
        this.storeConfigurationEntity = storeConfigurationEntity;
    }

    public static StoreConfigurationEntity toStoreConfigurationEntity(StoreConfigurationModel model,
            EntityManager em) {
        if (model instanceof StoreConfigurationAdapter) {
            return ((StoreConfigurationAdapter) model).getStoreConfigurationEntity();
        }
        return em.getReference(StoreConfigurationEntity.class, model.getId());
    }

    public StoreConfigurationEntity getStoreConfigurationEntity() {
        return storeConfigurationEntity;
    }

    @Override
    public void commit() {
        em.merge(storeConfigurationEntity);
    }

    @Override
    public String getId() {
        return storeConfigurationEntity.getId();
    }

    @Override
    public StoreConfigurationType getProviderName() {
        return StoreConfigurationType.valueOf(storeConfigurationEntity.getProviderName());
    }

    @Override
    public String getDenominacion() {
        return storeConfigurationEntity.getDenominacion();
    }

    @Override
    public String getCarpetaRaiz() {
        return storeConfigurationEntity.getCarpetaRaiz();
    }

    @Override
    public void setCarpetaRaiz(String carpetaRaiz) {
        storeConfigurationEntity.setCarpetaRaiz(carpetaRaiz);
    }

    @Override
    public String getCarpetaFoto() {
        return storeConfigurationEntity.getCarpetaFoto();
    }

    @Override
    public void setCarpetaFoto(String carpetaFoto) {
        storeConfigurationEntity.setCarpetaFoto(carpetaFoto);
    }

    @Override
    public String getCarpetaFirma() {
        return storeConfigurationEntity.getCarpetaFirma();
    }

    @Override
    public void setCarpetaFirma(String carpetaFirma) {
        storeConfigurationEntity.setCarpetaFirma(carpetaFirma);
    }

    @Override
    public String getCarpetaTemporal() {
        return storeConfigurationEntity.getCarpetaTemporal();
    }

    @Override
    public void setCarpetaTemporal(String carpetaTemporal) {
        storeConfigurationEntity.setCarpetaTemporal(carpetaTemporal);
    }

    @Override
    public String getAppKey() {
        return storeConfigurationEntity.getAppKey();
    }

    @Override
    public void setAppKey(String appKey) {
        storeConfigurationEntity.setAppKey(appKey);
    }

    @Override
    public String getAppSecret() {
        return storeConfigurationEntity.getAppSecret();
    }

    @Override
    public void setAppSecret(String appSecret) {
        storeConfigurationEntity.setAppSecret(appSecret);
    }

    @Override
    public String getToken() {
        return storeConfigurationEntity.getToken();
    }

    @Override
    public void setToken(String token) {
        storeConfigurationEntity.setToken(token);
    }

    @Override
    public boolean isDefault() {
        return storeConfigurationEntity.isDefault();
    }

    @Override
    public void setDefault(boolean isDefault) {
        storeConfigurationEntity.setDefault(isDefault);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDenominacion() == null) ? 0 : getDenominacion().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof StoreConfigurationModel))
            return false;
        StoreConfigurationModel other = (StoreConfigurationModel) obj;
        if (getDenominacion() == null) {
            if (other.getDenominacion() != null)
                return false;
        } else if (!getDenominacion().equals(other.getDenominacion()))
            return false;
        return true;
    }

}
