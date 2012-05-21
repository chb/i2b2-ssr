package edu.chip.carranet.carradatapipeline;

import com.phaseforward.informadapter.odm._1.GetTransactionsResult;
import com.phaseforward.informadapter.odm._2.ResponseODM;
import edu.chip.carranet.inform.InformClientAPI;
import edu.chip.carranet.inform.InformJAXBContext;
import org.apache.log4j.Logger;
import org.cdisc.ns.odm.v1.ODM;
import org.spin.tools.JAXBUtils;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Justin Quan
 * @link http://chip.org Date: 3/3/11
 */
public class MockInformClient implements InformClientAPI {
    private static final Logger log = Logger.getLogger(MockInformClient.class);

    private int fileIndex;
    private List<File> files;

    public MockInformClient(List<File> files) {
        fileIndex = 0;
        this.files = files;
    }

    public MockInformClient() {
        File f = new File(getClass().getClassLoader().getResource("getTrans1.xml").getFile());
        this.files = Arrays.asList(new File[]{f});
    }

    @Override
    public String getSiteList() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPatientList(String site) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPatientForms(String patient) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getMetadata() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAdminMetadata() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseODM getTransaction(String bookmark) throws IOException {
        try {
            if(fileIndex >= files.size()) {
                ResponseODM response = new ResponseODM();
                response.setBookmark(bookmark);
                return response;
            }

            log.info("now serving index " + fileIndex);
            File f = files.get(fileIndex++);
            GetTransactionsResult result = (GetTransactionsResult)JAXBUtils.unmarshal(f, InformJAXBContext.getInstance().getPackageList());
            return ((JAXBElement<ResponseODM>)result.getAny()).getValue();
        } catch (JAXBException e) {
            throw new IOException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        MockInformClient mock = new MockInformClient();
        ResponseODM response = mock.getTransaction("asdf");
        ODM odm = response.getODM();
    }
}
