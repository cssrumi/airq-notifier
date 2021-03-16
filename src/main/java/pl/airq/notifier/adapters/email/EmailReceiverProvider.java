package pl.airq.notifier.adapters.email;

import io.quarkus.runtime.Startup;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pl.airq.common.domain.notification.NotificationGroup;

@Startup
@ApplicationScoped
class EmailReceiverProvider {

    private final Map<NotificationGroup, String[]> receivers;

    @Inject
    EmailReceiverProvider(EmailGroupProperties properties) {
        this.receivers = properties.groups();
    }

    String[] provide(List<NotificationGroup> groups) {
        return receivers.entrySet()
                        .stream()
                        .filter(entry -> groups.contains(entry.getKey()))
                        .flatMap(entry -> Arrays.stream(entry.getValue()))
                        .toArray(String[]::new);
    }

}
