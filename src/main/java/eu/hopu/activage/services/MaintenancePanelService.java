package eu.hopu.activage.services;

import eu.hopu.activage.services.dto.Record;

import java.util.List;

public interface MaintenancePanelService {

    Record createRecord(String elementId, String description, String statusString, String typeString);

    Record updateRecord(long id, String elementId, String description, String statusString, String typeString);

    List<Record> getRecordsByElementId(String elementId);

    boolean deleteRecordById(String elementId, long id);
}
