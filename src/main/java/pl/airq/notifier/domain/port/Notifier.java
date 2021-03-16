package pl.airq.notifier.domain.port;

import io.smallrye.mutiny.Uni;
import pl.airq.common.domain.notification.FailureNotificationDto;

public interface Notifier {

    Uni<Void> notify(FailureNotificationDto dto);

}
