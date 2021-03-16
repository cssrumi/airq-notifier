package pl.airq.notifier.domain;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.common.domain.notification.FailureNotificationDto;
import pl.airq.notifier.domain.port.Notifier;

@ApplicationScoped
public class NotificationPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationPublisher.class);

    private final List<Notifier> notifiers;

    @Inject
    public NotificationPublisher(Instance<Notifier> notifiers) {
        this.notifiers = notifiers.stream().collect(Collectors.toUnmodifiableList());
    }

    public Uni<Void> publish(final FailureNotificationDto dto) {
        return Multi.createFrom().iterable(notifiers)
                    .call(notifier -> notifier.notify(dto))
                    .onFailure().invoke(throwable -> LOGGER.error("Notifier error.", throwable))
                    .collect().asList()
                    .onItem().ignore().andContinueWithNull();
    }
}
