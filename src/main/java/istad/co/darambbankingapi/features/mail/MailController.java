package istad.co.darambbankingapi.features.mail;

import istad.co.darambbankingapi.features.mail.dto.MailRequest;
import istad.co.darambbankingapi.features.mail.dto.MailResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping
    public MailResponse sendMail(@Valid @RequestBody MailRequest mailRequest) {
        return mailService.sendMail(mailRequest);
    }
}
