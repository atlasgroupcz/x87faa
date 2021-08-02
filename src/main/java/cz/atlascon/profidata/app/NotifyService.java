package cz.atlascon.profidata.app;

import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;

@Named
public class NotifyService {

    private final Mailer mailer;
    private final String notifyTarget;
    private final String username;

    @Inject
    public NotifyService(Mailer mailer,
                         @Value("${notify.target}") String notifyTarget,
                         @Value("${email.username}") String username) {
        this.mailer = mailer;
        this.notifyTarget = notifyTarget;
        this.username = username;
    }

    public void notifyPickup(String msg, String decoded) {
        mailer.sendMail(EmailBuilder.startingBlank().withSubject("Message pickup")
                .from(username)
                .to(notifyTarget).appendText("Msg " + msg + " pickup on " + ZonedDateTime.now() + ", decoded: " + decoded)
                .buildEmail());
    }
}
