package gentspark.group.boardingpass.IO;

import genspark.group.boardingpass.IO.TicketIndexer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketIndexerTests {
    @BeforeEach
    public void reset() {
        Field instanceField;

        //By setting the instance to null, a new one is created next time it's accessed
        try {
            instanceField = TicketIndexer.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(TicketIndexer.getInstance(), null);
        } catch(IllegalAccessException | NoSuchFieldException e) {
            System.err.println("Error: Failed to reset indexer!");
        }
    }

    @Test
    public void getInvalidID() {
        assertEquals(-1, TicketIndexer.getInstance().getIndex(UUID.randomUUID()));
    }

    @Test
    public void insertUUID() {
        Field index;
        Field map;

        TicketIndexer.getInstance().insertUUID(UUID.randomUUID());

        try {
            index = TicketIndexer.getInstance().getClass().getDeclaredField("index");
            map = TicketIndexer.getInstance().getClass().getDeclaredField("indexMap");
            index.setAccessible(true);
            map.setAccessible(true);

            assertEquals(1, index.get(TicketIndexer.getInstance()));
            assertEquals(1, ((Map<UUID, Integer>) map.get(TicketIndexer.getInstance())).size());
        } catch(IllegalAccessException | NoSuchFieldException e) {
            System.err.println("Error: Could not verify UUID has inserted");
            fail();
        }
    }

    @Test
    public void readIndex() {
        UUID[] ids = new UUID[5];

        for(byte i = 0; i < 5; i++) {
            ids[i] = UUID.randomUUID();
            TicketIndexer.getInstance().insertUUID(ids[i]);
        }

        //Index starts at 0 in the indexer, so it'll be equal to i
        for(byte i = 0; i < 5; i++) assertEquals(i, TicketIndexer.getInstance().getIndex(ids[i]));
    }
}
