package edu.chip.carranet.overlay.persistence;

import edu.chip.carranet.overlay.persistance.ResourceStore;
import edu.chip.carranet.overlay.persistance.ResourceStoreInMemory;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 15, 2010
 */
public class OverlayStoreInMemoryTest extends OverlayStoreTest {

    @Override
    public ResourceStore createResourceStore() {
        return new ResourceStoreInMemory();
    }
}
