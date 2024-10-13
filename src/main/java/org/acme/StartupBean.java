package org.acme;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class StartupBean {

    public void onStart(@Observes StartupEvent ev, @ConfigProperty(name = "MONGO_DB_CONNECTION_STRING") String varA) {
        System.out.println("MONGO_DB_CONNECTION_STRING = " + varA);
    }
}