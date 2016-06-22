package org.repeid.subsystem.server.extension;

import java.io.IOException;

import org.jboss.as.subsystem.test.AbstractSubsystemBaseTest;
import org.jboss.dmr.ModelNode;
import org.junit.Test;
import org.repeid.subsystem.server.extension.RepeidExtension;

/**
 * Tests all management expects for subsystem, parsing, marshaling, model definition and other
 * Here is an example that allows you a fine grained controller over what is tested and how. So it can give you ideas what can be done and tested.
 * If you have no need for advanced testing of subsystem you look at {@link AbstractSubsystemBaseTest} that testes same stuff but most of the code
 * is hidden inside of test harness
 */
public class SubsystemParsingTestCase extends AbstractSubsystemBaseTest {

    public SubsystemParsingTestCase() {
        super(RepeidExtension.SUBSYSTEM_NAME, new RepeidExtension());
    }

    @Test
    public void testJson() throws Exception {
        ModelNode node = new ModelNode();
        node.get("web-context").set("repeid");

        System.out.println("json=" + node.toJSONString(false));
    }

    @Override
    protected String getSubsystemXml() throws IOException {
        return readResource("repeid-server-1.1.xml");
    }

    @Override
    protected String getSubsystemXsdPath() throws Exception {
        return "schema/wildfly-repeid-server_1_1.xsd";
    }

    @Override
    protected String[] getSubsystemTemplatePaths() throws IOException {
        return new String[]{
            "/subsystem-templates/repeid-server.xml"
        };
    }
}
