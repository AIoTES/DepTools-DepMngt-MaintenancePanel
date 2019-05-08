package eu.hopu.activage.services;

import eu.hopu.activage.persistence.InMemoryRecordStorageImpl;
import eu.hopu.activage.persistence.RecordStorage;
import eu.hopu.activage.services.dto.Record;
import eu.hopu.activage.services.dto.RecordStatus;
import eu.hopu.activage.services.dto.RecordType;
import eu.hopu.activage.utils.DateProvider;
import eu.hopu.activage.utils.HopAsserts;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MaintenancePanelServiceTest {

    private MaintenancePanelService service;

    @Before
    public void setUp() throws Exception {
        RecordStorage storage = new InMemoryRecordStorageImpl();
        service = new MaintenancePanelServiceImpl(storage);

//        Mockea la hora para que siempre de un valor esperado
        DateProvider provider = mock(DateProvider.class);
        when(provider.getDate()).thenReturn(new Date(1000));
        DateProvider._setInstance(provider);
    }

    @Test
    public void createRecord() {
        long id = 0;
        String elementId = "elementId";
        String description = "description";
        String statusString = "pending";
        String typeString = "note";

        Record actual = service.createRecord(elementId, description, statusString, typeString);
        Record expected = new Record(id, elementId, description, RecordStatus.PENDING, RecordType.NOTE);

        HopAsserts.assertEqualsJson(expected, actual);
    }

    @Test
    public void updateRecord() {
        String elementId = "id";
        String description = "description";
        String statusString = "completed";
        String typeString = "note";

        Record created = service.createRecord(elementId, description, "pending", typeString);
        Record actual = service.updateRecord(created.getId(), elementId, description, statusString, typeString);
        Record expected = new Record(created.getId(), elementId, description, RecordStatus.COMPLETED, RecordType.NOTE);

        HopAsserts.assertEqualsJson(expected, actual);
    }

    @Test
    public void retrieveHistoricRecordsById() {
        String elementId = "id";
        String description = "description";

        Record created1 = service.createRecord(elementId, description, "pending", "note");
        Record created2 = service.createRecord(elementId, description, "completed", "todo");
        Record created3 = service.createRecord(elementId, description, "pending", "issue");

        List<Record> actual = service.getRecordsByElementId(elementId);
        List<Record> expected = new LinkedList<>();
        expected.add(created1);
        expected.add(created2);
        expected.add(created3);

        HopAsserts.assertEqualsJson(expected, actual);
    }

    @Test
    public void retrieveHistoricRecordsById_when_Id_Not_Exists() {
        String elementId = "id";

        List<Record> actual = service.getRecordsByElementId(elementId);
        List<Record> expected = new LinkedList<>();

        HopAsserts.assertEqualsJson(expected, actual);
    }

    @Test
    public void deleteRecord() {
        String elementId = "id";
        String description = "description";

        Record created1 = service.createRecord(elementId, description, "pending", "note");
        Record created2 = service.createRecord(elementId, description, "completed", "todo");
        Record created3 = service.createRecord(elementId, description, "pending", "issue");

        service.deleteRecordById(created3.getElementId(), created3.getId());

        List<Record> actual = service.getRecordsByElementId(elementId);
        List<Record> expected = new LinkedList<>();
        expected.add(created1);
        expected.add(created2);

        HopAsserts.assertEqualsJson(expected, actual);
    }

}
