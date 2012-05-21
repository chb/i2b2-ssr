package edu.chip.carranet.overlay.persistance;

import edu.chip.carranet.ExternalExceptions.ResourceAlreadyExistsError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 23, 2010
 */
public class ResourceStoreInMemory implements ResourceStore {
    private ConcurrentHashMap<String,PersistedResource> store;


    public ResourceStoreInMemory() {
        this.store = new ConcurrentHashMap<String,PersistedResource>();
    }

    @Override
    public void create(String key, PersistedResource pr) throws ResourceAlreadyExistsException {
        if(store.putIfAbsent(key, pr) != null) {
            throw new ResourceAlreadyExistsError("Resource already exists");
        }
    }

    @Override
    public PersistedResource read(String key) {
        return store.get(key);
    }

    @Override
    public PersistedResource update(String key, PersistedResource pr) {
        return store.replace(key, pr);
    }

    @Override
    public void close() {
        //NO-OP
    }

    @Override
    public void delete(String key) {
        store.remove(key);
    }


    /**
     * Returns all objects with a key that contains the passed charset
     * @param keyLike
     */
    public List<PersistedResource> getAll(String keyLike){
        List<PersistedResource> returnSet = new ArrayList<PersistedResource>();
        for(String s : store.keySet()){
            if(s.startsWith(keyLike)){
                returnSet.add(store.get(s));
            }
        }
        return returnSet;
    }

}
