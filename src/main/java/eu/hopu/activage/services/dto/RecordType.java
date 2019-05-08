package eu.hopu.activage.services.dto;

public enum RecordType {
    NOTE("note"),
    ISSUE("issue"),
    TODO("todo");

    private String type;

    RecordType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RecordType{" +
                "type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

}
