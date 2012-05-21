package edu.chip.carranet.overlay.persistance;


import edu.chip.carranet.ExternalExceptions.ResourceAlreadyExistsError;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.btree.BTree;
import jdbm.helper.StringComparator;
import jdbm.helper.Tuple;
import jdbm.helper.TupleBrowser;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Implementation of a resource store using  JDBM as a backend
 * DB = table in BerkleyDB land.
 */
public class JDBMResourceStore implements ResourceStore {

    private static Logger log = Logger.getLogger(JDBMResourceStore.class);
    private static String BTREE_NAME = "resources";
    private static String DATABASE_NAME = "overlay_db";


    private RecordManager recMan;
    private BTree tree;


    public JDBMResourceStore() {
        this(false);

    }

    /**
     * This constructor will clear out the BTRree if clearDatabase is set to true
     *
     * @param clearDatabase resets the database
     * @throws Exception
     */
    public JDBMResourceStore(boolean clearDatabase) {
        try {

            recMan = RecordManagerFactory.createRecordManager(DATABASE_NAME, new Properties());
            long recid = recMan.getNamedObject(BTREE_NAME);

            if (recid != 0) {

                if (clearDatabase) {
                    recMan.delete(recid);
                    tree = BTree.createInstance(recMan, new StringComparator());
                    recMan.setNamedObject(BTREE_NAME, tree.getRecid());
                }
                tree = BTree.load(recMan, recid);
            } else {
                // create a new B+Tree edu.chip.carranet.data structure and use a StringComparator
                // to order the records based on people's name.
                tree = BTree.createInstance(recMan, new StringComparator());
                recMan.setNamedObject(BTREE_NAME, tree.getRecid());
                System.out.println("Created a new empty BTree");
            }

        } catch (IOException e) {
            throw new RuntimeException("Can't open database");
        }
    }

    @Override
    public void close() {
        try {
            recMan.close();
        } catch (IOException e) {
            log.fatal("IOException while attempting to close the database.");
        }

    }


    @Override
    public void create(String key, PersistedResource pr) throws ResourceAlreadyExistsException {
        try {
            if (tree.find(key) != null) {
                throw new ResourceAlreadyExistsError("Can't create the resource, it already exists!");
            }
            tree.insert(key, pr, false);
            recMan.commit();
        } catch (IOException e) {
            throw new RuntimeException("IO Exception creating key " + key);
        }

    }

    @Override
    public PersistedResource read(String key) {

        try {
            return (PersistedResource) tree.find(key);
        } catch (IOException e) {
            throw new RuntimeException("IO Exception reading key " + key);
        }

    }

    @Override
    public PersistedResource update(String key, PersistedResource pr) {
        try {
            if (tree.find(key) != null) {
                tree.insert(key, pr, true);
                recMan.commit();
                return pr;
            }
        } catch (IOException e) {
            log.fatal("Couldn't update item with key: " + key);
        }
        return null;
    }

    @Override
    public void delete(String key) {
        try {
            tree.remove(key);
            recMan.commit();
        } catch (IOException e) {
            log.fatal("Couldn't delete item with key: " + key);
        }
    }

    @Override
    public List<PersistedResource> getAll(String keyLike) {
        List<PersistedResource> returnSet = new ArrayList<PersistedResource>();
        try {
            TupleBrowser browser = tree.browse(keyLike);
            Tuple tuple = new Tuple();
            while (browser.getNext(tuple)) {
                String key = (String) tuple.getKey();
                if (key.startsWith(keyLike)) {
                    returnSet.add((PersistedResource) tuple.getValue());
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            log.fatal("IO Exception trying to getAll " + keyLike);
        }

        return returnSet;
    }
}

   
