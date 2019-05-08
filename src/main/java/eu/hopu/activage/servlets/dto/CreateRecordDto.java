package eu.hopu.activage.servlets.dto;

public class CreateRecordDto {

    private String elementId;
    private String description;
    private String status;
    private String type;

    public CreateRecordDto(String elementId, String description, String status, String type) {
        this.elementId = elementId;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    public CreateRecordDto() {
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

    @Override
    public String toString() {
        return "CreateRecordDto{" +
                "elementId='" + elementId + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
