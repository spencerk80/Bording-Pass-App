package genspark.group.boardingpass.IO;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TicketIndexer {
    private         Map<UUID, Integer>  indexMap;
    private         int                 index;
    private static  TicketIndexer       instance;

    //Singleton design pattern. No instantiation outside the one instance allowed
    private TicketIndexer() {
        indexMap = new HashMap<>();
        index = 0;
    }

    public static TicketIndexer getInstance() {
        if(instance == null) instance = new TicketIndexer();
        return instance;
    }

    //This info has to be saved to file, so this allows preservation
    public Map<UUID, Integer> getIndexMap() {
        return indexMap;
    }

    //This info has to be saved to file, so this allows restoration
    public void setIndexMap(Map<UUID, Integer> indexMap) {
        this.indexMap = indexMap;
    }

    public void insertUUID(UUID id) {
        if(indexMap.containsKey(id)) return;
        indexMap.put(id, index++); //Index increments after put, ready for next value
    }

    public int getIndex(UUID id) {
        return indexMap.containsKey(id) ? indexMap.get(id) : -1;
    }
}
