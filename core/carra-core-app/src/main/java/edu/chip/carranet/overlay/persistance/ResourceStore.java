package edu.chip.carranet.overlay.persistance;

import java.util.List;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 23, 2010
 */
public interface ResourceStore {

    /**
     * Creates an entry, will fail if entry already exists
     * @param key
     * @param pr
     * @throws ResourceAlreadyExistsException
     */
    public void create(String key, PersistedResource pr) throws ResourceAlreadyExistsException;


    /**
     * Reads an entry
     * @param key
     * @return
     */
    public PersistedResource read(String key);

    /**
     * Updates an entry in the resource store, the resource must already exist
     * @param key
     * @param pr
     * @return
     */
    public PersistedResource update(String key, PersistedResource pr);

    /**
     * Delete an entry from the resource store
     *
     * @param key
     */
    public void delete(String key);


    /**
     * This method is probably really poorly named, it's actually
     * more like "getLike()" where the method will return every
     * entry with a key that starts with keyLike
     *
     * example
     * if we have 3 entries with keys
     * /key1/
     * /key2/
     * /key11/
     *
     * getLike("/key1") will return key1 and key11
     * getLike("/key") will return everything
     * getLike("foo") will return an empty list 
     *
     * @param keyLike
     * @return
     */
    public List<PersistedResource> getAll(String keyLike);

    public void close();
}
