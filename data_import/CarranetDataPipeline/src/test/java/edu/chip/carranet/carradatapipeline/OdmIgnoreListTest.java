package edu.chip.carranet.carradatapipeline;

import edu.chip.carranet.carradatapipeline.pipeline.OdmIgnoreList;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Justin
 * Date: 5/27/11
 * Time: 12:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class OdmIgnoreListTest {

    @Test
    public void testHappy() throws Exception {
        File formIgnoreList = new File(getClass().getClassLoader().getResource("testFormIgnoreList.txt").toURI());
        File itemIgnoreList = new File(getClass().getClassLoader().getResource("testItemIgnoreList.txt").toURI());
        OdmIgnoreList odmIgnoreList = new OdmIgnoreList(formIgnoreList, itemIgnoreList);
        assertEquals(13, odmIgnoreList.getFormIgnoreList().size());
        assertTrue(odmIgnoreList.getItemIgnoreList().isEmpty());
    }
}
