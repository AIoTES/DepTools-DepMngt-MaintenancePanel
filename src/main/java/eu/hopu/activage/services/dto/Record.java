package eu.hopu.activage.services.dto;

import eu.hopu.activage.utils.DateProvider;

import java.util.Date;

public class Record {

    private long id;
    private String elementId;
    private String description;
    private RecordStatus recordStatus;
    private RecordType recordType;
    private Date timestamp;

    public Record(long id, String elementId, String description, RecordStatus recordStatus, RecordType recordType) {
        this.id = id;
        this.elementId = elementId;
        this.description = description;
        this.recordStatus = recordStatus;
        this.recordType = recordType;
        this.timestamp = new DateProvider().getDate();
    }

    public Record(long id, String elementId, String description, RecordStatus status, RecordType type, long timestamp) {
        this.id = id;
        this.elementId = elementId;
        this.description = description;
        this.recordStatus = status;
        this.recordType = type;
        this.timestamp = new Date(timestamp);
    }

    public Record(String elementId, String description, RecordStatus recordStatus, RecordType recordType) {
        this.elementId = elementId;
        this.description = description;
        this.recordStatus = recordStatus;
        this.recordType = recordType;
        this.timestamp = new DateProvider().getDate();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", elementId='" + elementId + '\'' +
                ", description='" + description + '\'' +
                ", recordStatus=" + recordStatus +
                ", recordType=" + recordType +
                ", timestamp=" + timestamp +
                '}';
    }

}
