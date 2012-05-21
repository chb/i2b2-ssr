package edu.chip.carranet.overlay.persistence;

import edu.chip.carranet.overlay.persistance.JDBMResourceStore;
import edu.chip.carranet.overlay.persistance.ResourceStore;


public class JDBMStoreTest extends OverlayStoreTest {
    @Override
    public ResourceStore createResourceStore()  {

        return new JDBMResourceStore(true );

    }
}
