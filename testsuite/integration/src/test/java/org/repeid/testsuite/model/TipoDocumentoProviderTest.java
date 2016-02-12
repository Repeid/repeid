/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
package org.repeid.testsuite.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jboss.logging.Logger;
import org.junit.ClassRule;
import org.junit.Test;
import org.repeid.testsuite.RepeidServer;
import org.repeid.testsuite.rule.RepeidRule;

/**
 * @author huertas
 *
 */
public class TipoDocumentoProviderTest extends AbstractModelTest {

	private static final Logger log = Logger.getLogger(RepeidServer.class);

	@ClassRule
	public static RepeidRule kc = new RepeidRule();

	@Test
	public void test() {
		log.info("My test");
		assertThat(true, is(true));
	}

}
