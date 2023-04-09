package apache.fop.converter;

import java.io.*;
import java.util.Date;

import javax.xml.bind.JAXB;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Class description
 * author: aparfenov
 * date: 4/9/23
 */
public class Main {

    public static final String RESOURCES_DIR;
    public static final String OUTPUT_DIR;

    static {
        RESOURCES_DIR = "src//main//resources//";
        OUTPUT_DIR = "src//main//resources//output//";
    }

    public static void main(String[] args) {
        try {
            convertToPDF();
        } catch (FOPException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void convertToPDF() throws IOException, FOPException, TransformerException {
        File xsltFile = new File(RESOURCES_DIR + "//template.xsl");
        MessagePdfInputDataDAO dao = MessagePdfInputDataDAO.builder()
                .caseFileId(12)
                .date(new Date())
                .name("Ivan")
                .surname("Ivanov")
                .build();
        StringWriter sw = new StringWriter();
        JAXB.marshal(dao, sw);
        String xmlString = sw.toString();
        StreamSource xmlSource = new StreamSource(new ByteArrayInputStream(xmlString.getBytes()));

        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream out = new java.io.FileOutputStream(OUTPUT_DIR + "//output.pdf");

        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(xmlSource, res);
        } finally {
            out.close();
        }
    }
}
