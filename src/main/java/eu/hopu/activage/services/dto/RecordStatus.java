package eu.hopu.activage.services.dto;

public enum RecordStatus {
    PENDING("pending"),
    COMPLETED("completed");

    private String status;

    RecordStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RecordStatus{" +
                "status='" + status + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }
}
