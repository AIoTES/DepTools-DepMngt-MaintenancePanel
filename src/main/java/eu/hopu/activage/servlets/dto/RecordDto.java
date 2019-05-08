package eu.hopu.activage.servlets.dto;

public class RecordDto {

    private long id;
    private String elementId;
    private String description;
    private String status;
    private String type;
    private long timestamp;

    public RecordDto(long id, String elementId, String description, String status, String type, long timestamp) {
        this.id = id;
        this.elementId = elementId;
        this.description = description;
        this.status = status;
        this.type = type;
        this.timestamp = timestamp;
    }

    public RecordDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "RecordDto{" +
                "id='" + id + '\'' +
                ", elementId='" + elementId + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

}
