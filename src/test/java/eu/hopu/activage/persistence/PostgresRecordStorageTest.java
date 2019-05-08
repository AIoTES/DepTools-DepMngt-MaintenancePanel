package eu.hopu.activage.persistence;

import eu.hopu.activage.services.dto.Record;
import eu.hopu.activage.services.dto.RecordStatus;
import eu.hopu.activage.services.dto.RecordType;
import eu.hopu.activage.utils.DateProvider;
import eu.hopu.activage.utils.HopAsserts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// These tests must be runned with a postgres database instance launched
@Ignore
public class PostgresRecordStorageTest {

    private RecordStorage storage;

    @Before
    public void setUp() throws Exception {
        storage = new PostgresStorage("localhost", "5432", "activage-maintenance-panel", "activage", "activage");

        DateProvider dateProvider = mock(DateProvider.class);
        when(dateProvider.getDate()).thenReturn(new Date(100));
        DateProvider._setInstance(dateProvider);
    }

    @Test
    public void createRecord() {
        Record expected = new Record(1, "device1", "description", RecordStatus.PENDING, RecordType.NOTE, 100);
        Record actual = storage.createRecord(expected);

        HopAsserts.assertEqualsJson(expected, actual);
    }

    @Test
    public void updateRecord() {
        Record expected = new Record(1, "device1", "description2", RecordStatus.PENDING, RecordType.NOTE, 100);
        Record actual = storage.updateRecord(expected);

        HopAsserts.assertEqualsJson(expected, actual);
    }

    @Test
    public void getRecordsByElementId() {
        List<Record> actual = storage.getRecordsByElementId("device1");
        List<Record> expected = new LinkedList<>();
        expected.add(new Record(1, "device1", "description2", RecordStatus.PENDING, RecordType.NOTE, 100));

        HopAsserts.assertEqualsJson(expected, actual);
    }

    @Test
    public void deleteRecordById() {
        boolean result = storage.deleteRecordById("device1", 1);

        Assert.assertTrue(result);

        List<Record> actual = storage.getRecordsByElementId("device1");

        Assert.assertTrue(actual.isEmpty());
    }

}
