/*******************************************************************************
 * Repeid, Home of Professional Open Source
 * <p>
 * Copyright 2015 Sistcoop, Inc. and/or its affiliates.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.repeid.manager.api.beans.representations;

import java.io.Serializable;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class StoreConfigurationRepresentation implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String id;
    private String denominacion;
    private String provider;
    private String carpetaRaiz;
    private String carpetaFoto;
    private String carpetaFirma;
    private String carpetaTemporal;
    private String appKey;
    private String appSecret;
    private String token;
    private boolean isDefault;

    public StoreConfigurationRepresentation() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getCarpetaRaiz() {
        return carpetaRaiz;
    }

    public void setCarpetaRaiz(String carpetaRaiz) {
        this.carpetaRaiz = carpetaRaiz;
    }

    public String getCarpetaFoto() {
        return carpetaFoto;
    }

    public void setCarpetaFoto(String carpetaFoto) {
        this.carpetaFoto = carpetaFoto;
    }

    public String getCarpetaFirma() {
        return carpetaFirma;
    }

    public void setCarpetaFirma(String carpetaFirma) {
        this.carpetaFirma = carpetaFirma;
    }

    public String getCarpetaTemporal() {
        return carpetaTemporal;
    }

    public void setCarpetaTemporal(String carpetaTemporal) {
        this.carpetaTemporal = carpetaTemporal;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

}
