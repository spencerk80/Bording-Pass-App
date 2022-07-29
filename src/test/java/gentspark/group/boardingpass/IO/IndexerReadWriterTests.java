package gentspark.group.boardingpass.IO;

import genspark.group.boardingpass.IO.IndexerReadWriter;
import genspark.group.boardingpass.IO.TicketIndexer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IndexerReadWriterTests {
    private UUID[] ids;

    @BeforeAll
    public void fillIndexerWithData() {
        ids = new UUID[5];
        for(byte i = 0; i < 5; i++) {
            ids[i] = UUID.randomUUID();
            TicketIndexer.getInstance().insertUUID(ids[i]);
        }
    }

    @BeforeEach
    public void deleteFile() {
        File indexerDat = new File(IndexerReadWriter.INDEXER_SAVE_FILE);

        if(indexerDat.exists())
            if( ! indexerDat.delete())
                System.err.printf("Error: Unable to delete %s\n", IndexerReadWriter.INDEXER_SAVE_FILE);
    }

    @Test
    public void saveIndexerData() {
        File file;

        IndexerReadWriter.saveIndexerData();
        file = new File(IndexerReadWriter.INDEXER_SAVE_FILE);

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    public void restoreIndexData() {
        IndexerReadWriter.saveIndexerData();
        resetIndexer();
        IndexerReadWriter.restoreIndexerData();

        for(byte i = 0; i < 5; i++) assertEquals(i, TicketIndexer.getInstance().getIndex(ids[i]));
    }

    private void resetIndexer() {
        Field indexerInstance;

        try {
            indexerInstance = TicketIndexer.class.getDeclaredField("instance");
            indexerInstance.setAccessible(true);
            indexerInstance.set(TicketIndexer.getInstance(), null);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Error: Could not reset TicketIndexer!");
        }
    }
}
