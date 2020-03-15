package com.infy.WikiDocsProject.Service;

import net.gjerull.etherpad.client.EPLiteClient;
import org.springframework.stereotype.Service;

@Service
public class EtherPadService {

    private final EPLiteClient epLiteClient;

    public EtherPadService(EPLiteClient epLiteClient) {
        this.epLiteClient = epLiteClient;
    }

    public void createPad(String padId){
        epLiteClient.createPad(padId);
    }

    public String getContent(String padId){
        return epLiteClient.getText(padId).get("text").toString();
    }

}
