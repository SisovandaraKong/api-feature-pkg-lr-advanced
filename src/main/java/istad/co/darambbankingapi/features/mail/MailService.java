package istad.co.darambbankingapi.features.mail;

import istad.co.darambbankingapi.features.mail.dto.MailRequest;
import istad.co.darambbankingapi.features.mail.dto.MailResponse;

public interface MailService {

    MailResponse sendMail(MailRequest mailRequest);
}
