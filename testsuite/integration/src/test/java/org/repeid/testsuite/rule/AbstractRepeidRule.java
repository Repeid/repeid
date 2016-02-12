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

package org.repeid.testsuite.rule;

import java.io.IOException;
import java.net.Socket;

import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;
import org.repeid.testsuite.RepeidServer;
import org.repeid.testsuite.Retry;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public abstract class AbstractRepeidRule extends ExternalResource {

	protected TemporaryFolder temporaryFolder;

	protected RepeidServer server;

	protected void before() throws Throwable {
		temporaryFolder = new TemporaryFolder();
		temporaryFolder.create();
		System.setProperty("repeid.tmp.dir", temporaryFolder.newFolder().getAbsolutePath());

		server = new RepeidServer();

		server.start();
	}

	@Override
	protected void after() {
		stopServer();

		temporaryFolder.delete();
		System.getProperties().remove("repeid.tmp.dir");
	}

	public void restartServer() {
		try {
			stopServer();
			server.start();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private void stopServer() {
		server.stop();

		// Add some variable delay (Some windows envs have issues as server is
		// not stopped immediately after server.stop)
		try {
			Retry.execute(new Runnable() {

				@Override
				public void run() {
					try {
						Socket s = new Socket(server.getConfig().getHost(), server.getConfig().getPort());
						s.close();
						throw new IllegalStateException("Server still running");
					} catch (IOException expected) {
					}
				}

			}, 10, 500);
			Thread.sleep(100);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
	}

}
