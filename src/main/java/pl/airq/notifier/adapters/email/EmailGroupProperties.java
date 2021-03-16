package pl.airq.notifier.adapters.email;


import io.smallrye.config.ConfigMapping;
import java.util.Map;
import pl.airq.common.domain.notification.NotificationGroup;

@ConfigMapping(prefix = "notifier.email")
interface EmailGroupProperties {

    Map<NotificationGroup, String[]> groups();

}
