package com.github.madeindjs.feedID3Test;

import com.github.madeindjs.feedID3.DiscogRelease;
import com.github.madeindjs.feedID3.DiscogResults;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import junit.framework.Assert;
import org.json.JSONObject;
import org.junit.Test;

/**
 *
 * @author arousseau
 */
public class DiscogResultsTest {

    private static final String JSON_PATH = "src/test/resources/search.json";

    /**
     * Get a JSONObject who represent a result from Discog API
     *
     * @return
     */
    public static DiscogResults getDiscogResults() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(JSON_PATH)));
        JSONObject json = new JSONObject(content);

        return new DiscogResults(json);
    }

    @Test
    public void testLength() throws IOException {
        DiscogResults results = getDiscogResults();
        Assert.assertEquals(9, results.getLength());
    }

    @Test
    public void testIterator() throws IOException {

        Iterator<DiscogRelease> iterator = getDiscogResults().iterator();

        int count = 0;

        while (iterator.hasNext()) {

            DiscogRelease release = iterator.next();

            switch (count) {
                case 0:
                case 1:
                case 2:
                case 3:
                    Assert.assertEquals(
                            String.format("Result #%s not corresponding", count),
                            "Look What This World Did to Us",
                            release.getAlbum()
                    );
                    break;
                case 4:
                case 5:
                case 6:
                    Assert.assertEquals(
                            String.format("Result #%s not corresponding", count),
                            "Organized Blues",
                            release.getAlbum()
                    );
                    break;
                case 7:
                case 8:
                case 9:
                    Assert.assertEquals(
                            String.format("Result #%s not corresponding", count),
                            "New Brains For Everyone",
                            release.getAlbum()
                    );
                    break;
            }

            count++;
        }

        Assert.assertEquals(9, count);
    }

}
