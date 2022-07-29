package genspark.group.boardingpass.IO;

import java.io.*;
import java.util.Map;
import java.util.UUID;

public class IndexerReadWriter {
    public static final String INDEXER_SAVE_FILE = "indexer.dat";

    public static void saveIndexerData() {
        ObjectOutputStream writer;

        try {
            writer = new ObjectOutputStream(new FileOutputStream(INDEXER_SAVE_FILE));
            writer.writeObject(TicketIndexer.getInstance().getIndexMap());
        } catch(IOException e) {
            System.err.println("Error! Failed to write indexer to file. Data not saved!");
        }
    }

    public static void restoreIndexerData() {
        ObjectInputStream reader;

        try {
            reader = new ObjectInputStream(new FileInputStream(INDEXER_SAVE_FILE));
            TicketIndexer.getInstance().setIndexMap(((Map<UUID, Integer>) reader.readObject()));
        } catch(IOException | ClassNotFoundException e) {
            System.err.println("Error: Failed to read indexer from file! Data not imported!");
        }
    }
}
