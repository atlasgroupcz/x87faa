package cz.atlascon.profidata.app.cfg;

import org.hazlewood.connor.bottema.emailaddress.EmailAddressCriteria;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public Mailer mailer(@Value("${email.host}") String host,
                         @Value("${email.port}") int port,
                         @Value("${email.username}") String username,
                         @Value("${email.password}") String password) {
        return MailerBuilder
                .withSMTPServer(host, port, username, password)
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withEmailAddressCriteria(EmailAddressCriteria.RFC_COMPLIANT)
                .withSessionTimeout(5 * 1000)
                .withDebugLogging(false)
                .buildMailer();
    }

}
