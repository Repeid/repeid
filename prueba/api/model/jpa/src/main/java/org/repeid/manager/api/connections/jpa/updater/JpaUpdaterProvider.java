/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.repeid.manager.api.connections.jpa.updater;

import java.sql.Connection;

import org.repeid.manager.api.model.provider.Provider;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public interface JpaUpdaterProvider extends Provider {

	public String FIRST_VERSION = "1.0.0.Final";

	public String LAST_VERSION = "1.9.0";

	public String getCurrentVersionSql(String defaultSchema);

	public void update(Connection connection, String defaultSchema);

	public void validate(Connection connection, String defaultSchema);

}