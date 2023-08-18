package com.BCB.bcb.domain.dbo.twilio;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.BCB.bcb.domain.dbo.client.dtos.BaseMessageDTO;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class TwilioService {

    private final Environment environment;

    public TwilioService(Environment environment) {
        this.environment = environment;
    }

    public Message sendMessage(BaseMessageDTO dto) {

        if (dto.getWhatsapp()) {
            return sendMessageWhats(dto);
        } else {
            return sendMessageSms(dto);
        }
    }

    public Message sendMessageWhats(BaseMessageDTO dto) {
        Twilio.init(environment.getProperty("twilio.id"), environment.getProperty("twilio.token"));

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:" + dto.getNumber()),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                dto.getMessage()).create();

        return message;
    }

    public Message sendMessageSms(BaseMessageDTO dto) {
        Twilio.init(environment.getProperty("twilio.id"), environment.getProperty("twilio.token"));

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(dto.getNumber()),
                new com.twilio.type.PhoneNumber("+13135133527"),
                dto.getMessage()).create();

        return message;
    }

}
