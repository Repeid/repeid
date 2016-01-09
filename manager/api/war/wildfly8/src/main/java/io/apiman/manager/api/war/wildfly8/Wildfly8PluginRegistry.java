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
package io.apiman.manager.api.war.wildfly8;

/**
 * A wildfly 8 version of the plugin registry.  This subclass exists in order
 * to properly configure the data directory that should be used.  In this case
 * the data directory is $WILDFLY/standalone/data/apiman/plugins
 *
 * @author eric.wittmann@redhat.com
 */
public class Wildfly8PluginRegistry {
	
}

