package net.shrine.config;

import org.apache.log4j.Logger;
import org.spin.tools.JAXBUtils;
import org.spin.tools.config.ConfigException;
import org.spin.tools.config.ConfigTool;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.TreeMap;

@XmlRootElement(name = "ProjectMappings")
@XmlAccessorType(XmlAccessType.FIELD)

/**
 * This mapping is similar to the Adaptor mappings but is a mapping of global
 * projects to local projects.  As of 1.7, all nodes must have identically
 * named projects.  This config maps the shrine broadcast project to a local project.
 *
 * This is needed to drive CARRAnet, the Harvard deployment shouldn't need this.
 *
 * @author David Ortiz
 * @date July 19, 2010
 * @link http://cbmi.med.harvard.edu
 * @link http://chip.org
 * <p/>
 * NOTICE: This software comes with NO guarantees whatsoever and is
 * licensed as Lgpl Open Source
 * @link http://www.gnu.org/licenses/lgpl.html
 * @see ShrimpETL
 *      <p/>
 *      REFACTORED from 1.6.6
 * @see AdapterMappings
 * @see AdapterMappingTrieNode
 */
public class ProjectMappings {

    public static String DEFAULT_PROJET = "default";
    private static String DEFAULT_MAPPINGS_FILENAME = "ProjectMappings.xml";


    private TreeMap<String, String> mappings = new TreeMap<String, String>();

    private static ProjectMappings defaultInstance;
    private static final Logger log = Logger.getLogger(ProjectMappings.class);
    private static final boolean DEBUG = log.isDebugEnabled();
    private static final boolean INFO = log.isInfoEnabled();


    public static ProjectMappings getDefaultInstance() throws ConfigException {
        if (defaultInstance != null) {
            if (DEBUG) {
                log.debug("getDefaultInstance() returning cached instance");
            }
        } else {
            defaultInstance = loadFromFile(DEFAULT_MAPPINGS_FILENAME);
        }

        return defaultInstance;
    }

    public static ProjectMappings loadFromFile(String mappingsFilename)
            throws ConfigException {
        return loadfromFile(ConfigTool
                .getConfigFileWithFailover(mappingsFilename));
    }

    public static ProjectMappings loadfromFile(File mappingsFile) throws ConfigException {
        ProjectMappings mappings = null;
        try {
            if (INFO) {
                log.info("Loading AdapterMappings from disk : "
                        + mappingsFile.getAbsolutePath());
            }
            mappings = JAXBUtils.unmarshal(mappingsFile, ProjectMappings.class);
            return mappings;
        }
        catch (JAXBException jaxbe) {
            log.error("Unmarshalling error", jaxbe);
            throw new ConfigException("AdapterMappings unmarshalling error",
                    jaxbe);
        }
    }

    public TreeMap<String, String> getMappings() {
        return mappings;
    }

    public void setMappings(TreeMap<String, String> mappings) {
        this.mappings = mappings;
    }
}
