package com.elai.controller;

import com.sensorsdata.webhook.entry.SfWebhookEntry;
import com.sensorsdata.webhook.processor.SfWebhookProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*webhook消息发送*/
@RestController
@RequestMapping("/push/webhooknmsg")
public class WebhookController {

//    @Autowired
    private SfWebhookProcessor sfWebhookProcessor;

    //webhook消息发送
    @PostMapping("handleWebhookEntries")
    public void handleWebhookEntries(List<SfWebhookEntry> events) {
        sfWebhookProcessor.handleWebhookEntries(events);
    }
}
