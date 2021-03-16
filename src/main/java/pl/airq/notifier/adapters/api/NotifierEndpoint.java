package pl.airq.notifier.adapters.api;

import io.smallrye.mutiny.Uni;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pl.airq.common.domain.notification.FailureNotificationDto;
import pl.airq.notifier.domain.NotificationPublisher;

@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1/notifier")
public class NotifierEndpoint {

    private final NotificationPublisher publisher;

    @Inject
    public NotifierEndpoint(NotificationPublisher publisher) {
        this.publisher = publisher;
    }

    @POST
    @Path("/failure")
    public Uni<Void> failure(FailureNotificationDto dto) {
        return publisher.publish(dto);
    }

}
