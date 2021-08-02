package cz.atlascon.profidata.app.cfg;

import com.google.common.collect.Maps;
import org.hazlewood.connor.bottema.emailaddress.EmailAddressCriteria;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class BeanConfig {

    @Bean
    public Mailer mailer(@Value("${email.host}") String host,
                         @Value("${email.port}") int port,
                         @Value("${email.username}") String username,
                         @Value("${email.password}") String password) {
        final Map<String, String> config = Maps.newHashMap();
        config.put("mail.smtp.auth", "true");
        config.put("mail.smtp.starttls.enable", "true");

        return MailerBuilder
                .withSMTPServer(host, port, username, password)
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withEmailAddressCriteria(EmailAddressCriteria.RFC_COMPLIANT)
                .withSessionTimeout(5 * 1000)
                .withDebugLogging(false)
                .withProperties(config)
                .buildMailer();
    }

}
