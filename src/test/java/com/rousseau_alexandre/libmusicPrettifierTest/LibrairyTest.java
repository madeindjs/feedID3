package com.rousseau_alexandre.libmusicPrettifierTest;

import com.rousseau_alexandre.libmusicPrettifier.Librairy;
import junit.framework.Assert;
import org.junit.Test;

public class LibrairyTest {

    @Test
    public void testLibrairyFoundFirstChildFiles() {
        Librairy librairy = new Librairy("src/test/resources");
        Assert.assertEquals(1, librairy.getMp3Files().size());
    }

    @Test
    public void testLibrairyFoundRecursivesFiles() {
        Librairy librairy = new Librairy("src/test");
        Assert.assertEquals(1, librairy.getMp3Files().size());
    }

}
