package org.repeid.subsystem.server.extension;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.operations.common.Util;
import org.jboss.as.controller.parsing.ParseUtils;
import org.jboss.as.controller.persistence.SubsystemMarshallingContext;
import org.jboss.dmr.ModelNode;
import org.jboss.staxmapper.XMLElementReader;
import org.jboss.staxmapper.XMLElementWriter;
import org.jboss.staxmapper.XMLExtendedStreamReader;
import org.jboss.staxmapper.XMLExtendedStreamWriter;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import static org.repeid.subsystem.server.extension.RepeidExtension.PATH_SUBSYSTEM;
import static org.repeid.subsystem.server.extension.RepeidSubsystemDefinition.WEB_CONTEXT;

import java.util.List;

/**
 * The subsystem parser, which uses stax to read and write to and from xml
 */
class RepeidSubsystemParser implements XMLStreamConstants, XMLElementReader<List<ModelNode>>, XMLElementWriter<SubsystemMarshallingContext> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void readElement(final XMLExtendedStreamReader reader, final List<ModelNode> list) throws XMLStreamException {
        // Require no attributes
        ParseUtils.requireNoAttributes(reader);
        ModelNode addKeycloakSub = Util.createAddOperation(PathAddress.pathAddress(PATH_SUBSYSTEM));
        list.add(addKeycloakSub);

        while (reader.hasNext() && nextTag(reader) != END_ELEMENT) {
            if (reader.getLocalName().equals(WEB_CONTEXT.getXmlName())) {
                WEB_CONTEXT.parseAndSetParameter(reader.getElementText(), addKeycloakSub, reader);
            } else {
                throw new XMLStreamException("Unknown repeid-server subsystem tag: " + reader.getLocalName());
            }
        }
    }

    // used for debugging
    private int nextTag(XMLExtendedStreamReader reader) throws XMLStreamException {
        return reader.nextTag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeContent(final XMLExtendedStreamWriter writer, final SubsystemMarshallingContext context) throws XMLStreamException {
        context.startSubsystemElement(RepeidExtension.NAMESPACE, false);
        writeWebContext(writer, context);
        writer.writeEndElement();
    }

    private void writeWebContext(XMLExtendedStreamWriter writer, SubsystemMarshallingContext context) throws XMLStreamException {
        if (!context.getModelNode().get(WEB_CONTEXT.getName()).isDefined()) {
            return;
        }

        WEB_CONTEXT.marshallAsElement(context.getModelNode(), writer);
    }
}
