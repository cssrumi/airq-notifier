package pl.airq.notifier.adapters.email;

import io.quarkus.mailer.Mail;
import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pl.airq.common.domain.notification.FailureNotificationDto;

@ApplicationScoped
class EmailFactory {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId EUROPE_WARSAW_ZONE_ID = ZoneId.of("Europe/Warsaw");
    private static final String SUBJECT_TEMPLATE = "Wystąpił błąd przetwarzania aplikacji %s";

    private final Template failureTemplate;
    private final EmailReceiverProvider receiverProvider;

    @Inject
    EmailFactory(@ResourcePath("email/FailureEmail.html") Template failureTemplate,
                 EmailReceiverProvider receiverProvider) {
        this.failureTemplate = failureTemplate;
        this.receiverProvider = receiverProvider;
    }

    Mail create(final FailureNotificationDto dto) {
        String[] to = receiverProvider.provide(dto.groups);
        String subject = String.format(SUBJECT_TEMPLATE, dto.applicationName);
        String html = failureTemplate.data(
                "applicationName", dto.applicationName,
                "errorMessage", dto.errorMessage,
                "stackTrace", dto.stackTrace,
                "timestamp", format(dto.timestamp)
        ).render();

        return new Mail().addTo(to).setSubject(subject).setHtml(html);
    }

    private String format(OffsetDateTime timestamp) {
        ZonedDateTime zonedDateTime = timestamp.toInstant().atZone(EUROPE_WARSAW_ZONE_ID);
        return TIMESTAMP_FORMATTER.format(zonedDateTime);
    }

}
