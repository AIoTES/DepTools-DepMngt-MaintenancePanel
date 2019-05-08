package eu.hopu.activage.persistence;

import eu.hopu.activage.services.dto.Record;

import java.util.List;

public interface RecordStorage {

    Record createRecord(Record record);

    Record updateRecord(Record record);

    List<Record> getRecordsByElementId(String elementId);

    boolean deleteRecordById(String elementId, long id);

}
