package eu.hopu.activage.services;

import eu.hopu.activage.persistence.RecordStorage;
import eu.hopu.activage.services.dto.Record;
import eu.hopu.activage.services.dto.RecordStatus;
import eu.hopu.activage.services.dto.RecordType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MaintenancePanelServiceImpl implements MaintenancePanelService {

    private static final Logger LOG = LogManager.getLogger(MaintenancePanelServiceImpl.class);

    private RecordStorage storage;

    public MaintenancePanelServiceImpl(RecordStorage storage) {
        this.storage = storage;
    }

    @Override
    public Record createRecord(String elementId, String description, String statusString, String typeString) {
        LOG.info("Creating record → {}:{}:{}:{}", elementId, description, statusString, typeString);
        Record record = new Record(elementId, description, RecordStatus.valueOf(statusString.toUpperCase()), RecordType.valueOf(typeString.toUpperCase()));
        Record storageRecord = storage.createRecord(record);
        LOG.debug("Record created → {}", storageRecord.toString());
        return storageRecord;
    }

    @Override
    public Record updateRecord(long id, String elementId, String description, String statusString, String typeString) {
        LOG.info("Updating record → {}:{}:{}:{}:{}", id, elementId, description, statusString, typeString);
        Record record = new Record(id, elementId, description, RecordStatus.valueOf(statusString.toUpperCase()), RecordType.valueOf(typeString.toUpperCase()));
        Record storageRecord = storage.updateRecord(record);
        LOG.debug("Record updated → {}", storageRecord.toString());
        return storageRecord;
    }

    @Override
    public List<Record> getRecordsByElementId(String elementId) {
        LOG.info("Retrieving records → {}", elementId);
        List<Record> records = storage.getRecordsByElementId(elementId);
        LOG.debug("Records retrieved → {}", records.toString());
        return records;
    }

    @Override
    public boolean deleteRecordById(String elementId, long id) {
        LOG.info("Delete record → {}:{}", id, elementId);
        boolean result = storage.deleteRecordById(elementId, id);
        if (result) {
            LOG.debug("Record deleted → {}:{}", elementId, id);
            return true;
        } else {
            LOG.debug("Cannot delete record with → {}:{}", elementId, id);
            return false;
        }
    }

}
