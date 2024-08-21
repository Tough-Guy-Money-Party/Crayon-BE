package com.yoyomo.domain.form.domain.entity.event;

import com.yoyomo.domain.form.domain.entity.Form;
import org.bson.Document;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

@Component
public class FormEntityListener {

    @EventListener
    public void onBeforeSave(BeforeSaveEvent<Form> event) {
        Document document = event.getDocument();

        if(!document.containsKey("enabled"))
            document.append("enabled", true);
    }
}
