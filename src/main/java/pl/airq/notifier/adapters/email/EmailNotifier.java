package pl.airq.notifier.adapters.email;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pl.airq.common.domain.notification.FailureNotificationDto;
import pl.airq.notifier.domain.port.Notifier;

@ApplicationScoped
class EmailNotifier implements Notifier {

    private final ReactiveMailer mailer;
    private final EmailFactory factory;

    @Inject
    EmailNotifier(ReactiveMailer mailer, EmailFactory factory) {
        this.mailer = mailer;
        this.factory = factory;
    }

    @Override
    public Uni<Void> notify(FailureNotificationDto dto) {
        Mail mail = factory.create(dto);
        return mailer.send(mail);
    }
}
