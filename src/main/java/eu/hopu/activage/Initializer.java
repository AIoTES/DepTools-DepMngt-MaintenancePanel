package eu.hopu.activage;

import eu.hopu.activage.persistence.PostgresStorage;
import eu.hopu.activage.persistence.RecordStorage;
import eu.hopu.activage.services.MaintenancePanelService;
import eu.hopu.activage.services.MaintenancePanelServiceImpl;
import eu.hopu.activage.utils.GetEnvOrProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    private static final Logger LOG = LogManager.getLogger(Initializer.class);


    public static MaintenancePanelService service;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("Initializing maintenance panel");
        GetEnvOrProperty.getInstance().loadProperties();
        RecordStorage storage = new PostgresStorage(GetEnvOrProperty.getInstance().get("POSTGRES_HOST"), GetEnvOrProperty.getInstance().get("POSTGRES_PORT"),
                GetEnvOrProperty.getInstance().get("POSTGRES_DATABASE"), GetEnvOrProperty.getInstance().get("POSTGRES_USER"), GetEnvOrProperty.getInstance().get("POSTGRES_PASSWORD"));
        service = new MaintenancePanelServiceImpl(storage);

        LOG.info("Maintenance panel initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("Stopping maintenance panel");
        LOG.info("Maintenance panel stopped");
    }

}
