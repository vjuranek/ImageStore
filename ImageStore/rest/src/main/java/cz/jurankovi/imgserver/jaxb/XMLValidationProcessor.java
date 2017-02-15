package cz.jurankovi.imgserver.jaxb;

import java.lang.annotation.Annotation;
import java.net.URL;

import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.jboss.resteasy.annotations.DecorateTypes;
import org.jboss.resteasy.spi.interception.DecoratorProcessor;
import org.xml.sax.SAXException;

@DecorateTypes({ "text/*+xml", "application/*+xml" })
public class XMLValidationProcessor implements DecoratorProcessor<Unmarshaller, ValidateXML> {

    public static final String XSD_RESOURCE = "image.xsd";

    private final Schema schema;

    public XMLValidationProcessor() {
        System.out.println("!!!!!!!!!!!!!!!!!");
        Schema s = null;
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL schemaURL = XMLValidationProcessor.class.getClassLoader().getResource(XSD_RESOURCE);
            s = sf.newSchema(schemaURL);
        } catch (SAXException e) {
            // TODO log error or abort?
        }
        this.schema = s;
    }

    @Override
    public Unmarshaller decorate(Unmarshaller unmasrhaller, ValidateXML annotation, Class type,
            Annotation[] annotations, MediaType mediaType) {
        unmasrhaller.setSchema(schema);
        System.out.println("!!!!!SETTING schema");
        return unmasrhaller;
    }

}
