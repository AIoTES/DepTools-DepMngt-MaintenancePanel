package eu.hopu.activage.persistence;

import eu.hopu.activage.services.dto.Record;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InMemoryRecordStorageImpl implements RecordStorage {

    private Map<Long, Record> recordById;
    private Map<String, List<Record>> recordsByElementId;
    private long recordId = 0;

    public InMemoryRecordStorageImpl(Map<Long, Record> recordById) {
        this.recordById = recordById;
        this.recordsByElementId = new HashMap<>();
    }

    public InMemoryRecordStorageImpl() {
        this.recordById = new HashMap<>();
        this.recordsByElementId = new HashMap<>();
    }

    @Override
    public Record createRecord(Record record) {
        List<Record> records = recordsByElementId.get(record.getElementId());
        if (records == null)
            records = new LinkedList<>();
        record.setId(recordId);
        records.add(record);
        recordsByElementId.put(record.getElementId(), records);

        recordById.put(recordId, record);
        recordId++;
        return record;
    }

    @Override
    public Record updateRecord(Record record) {
        deleteRecordById(record.getElementId(), record.getId());
        List<Record> records = recordsByElementId.get(record.getElementId());
        if (records == null)
            records = new LinkedList<>();
        records.add(record);
        recordsByElementId.put(record.getElementId(), records);

        recordById.put(record.getId(), record);
        return record;
    }

    @Override
    public List<Record> getRecordsByElementId(String elementId) {
        return recordsByElementId.getOrDefault(elementId, new LinkedList<>());
    }

    @Override
    public boolean deleteRecordById(String elementId, long id) {
        recordById.remove(id);
        List<Record> records = recordsByElementId.get(elementId);
        records.removeIf(rec -> rec != null && rec.getId() == id);
        return true;
    }

}
