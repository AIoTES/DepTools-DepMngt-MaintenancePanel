package eu.hopu.activage.servlets;


import com.google.gson.Gson;
import eu.hopu.activage.Initializer;
import eu.hopu.activage.services.MaintenancePanelService;
import eu.hopu.activage.services.dto.Record;
import eu.hopu.activage.servlets.dto.CreateRecordDto;
import eu.hopu.activage.servlets.dto.RecordDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Path("records")
public class RecordServlet {

    private static final Logger LOG = LogManager.getLogger(RecordServlet.class);

    private MaintenancePanelService service;
    private Gson gson;

    public RecordServlet() {
        this.service = Initializer.service;
        this.gson = new Gson();
    }

    @GET
    @Produces({"application/json", "plain/text"})
    @Path("/element/{elementId}")
    public Response listAllRecordsById(@Context HttpServletRequest request, @PathParam("elementId") String elementId) {
        LOG.info("List records by element with id → {}", elementId);
        List<Record> records = service.getRecordsByElementId(elementId);
        List<RecordDto> responses = new LinkedList<>();
        for (Record record : records)
            responses.add(recordToRecordDto(record));
        LOG.debug("Records retrieved → {}", responses.toString());
        return Response.ok().entity(gson.toJson(responses)).build();
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json", "plain/text"})
    public Response createRecord(@Context HttpServletRequest request, CreateRecordDto recordDto) {
        LOG.info("Creating record → {}", recordDto.toString());
        Record record = service.createRecord(recordDto.getElementId(), recordDto.getDescription(), recordDto.getStatus(), recordDto.getType());
        RecordDto response = recordToRecordDto(record);
        LOG.debug("Record created → {}", response.toString());
        return Response.ok().entity(gson.toJson(response)).build();
    }

    @DELETE
    @Produces({"application/json", "plain/text"})
    @Path("{recordId}/element/{elementId}")
    public Response deleteRecord(@Context HttpServletRequest request, @PathParam("recordId") long recordId, @PathParam("elementId") String elementId) {
        LOG.info("Removing record → {}:{}", recordId, elementId);
        boolean result = service.deleteRecordById(elementId, recordId);
        if (result) {
            LOG.debug("Record removed → {}:{}", recordId, elementId);
            return Response.noContent().build();
        } else {
            LOG.debug("Cannot find the record with → {}:{}", recordId, elementId);
            return Response.status(404).build();
        }
    }

    @PUT
    @Produces({"application/json", "plain/text"})
    public Response updateRecord(@Context HttpServletRequest request, RecordDto recordDto) {
        LOG.info("Updating record → {}", recordDto.toString());
        Record record = service.updateRecord(recordDto.getId(), recordDto.getElementId(), recordDto.getDescription(), recordDto.getStatus(), recordDto.getType());
        RecordDto response = recordToRecordDto(record);
        LOG.debug("Record updated → {}", response.toString());
        return Response.ok().entity(gson.toJson(response)).build();
    }

    private RecordDto recordToRecordDto(Record record) {
        return new RecordDto(record.getId(), record.getElementId(), record.getDescription(), record.getRecordStatus().getStatus(), record.getRecordType().getType(), record.getTimestamp().getTime());
    }

}
