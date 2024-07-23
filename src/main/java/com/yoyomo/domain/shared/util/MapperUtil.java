package com.yoyomo.domain.shared.util;

import com.yoyomo.domain.application.application.dto.req.AssessmentRequest;
import com.yoyomo.domain.application.domain.entity.Assessment;
import com.yoyomo.domain.club.domain.entity.ClubLandingStyle;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.interview.domain.entity.Interview;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.mongodb.core.query.Update;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

public class MapperUtil {
    private static final String CLASS = "class";
    private static final String ID = "id";
    private static final String EMPTY_STRING = "";
    private static final String DELIMITER = ".";

    public static Update mapToUpdate(Object... requests) {
        return mapToUpdate(EMPTY_STRING, requests);
    }

    public static Update mapToUpdate(String pathPrefix, Object... requests) {
        Update update = new Update();
        Arrays.stream(requests).forEach(request -> {
            BeanWrapper beanWrapper = new BeanWrapperImpl(request);
            for (PropertyDescriptor pd : beanWrapper.getPropertyDescriptors()) {
                String propertyName = String.join(DELIMITER, pathPrefix, pd.getName());
                Object propertyValue = beanWrapper.getPropertyValue(pd.getName());

                if (propertyValue != null && !propertyName.contains(CLASS) && !propertyName.contains(ID)) {
                    update.set(propertyName, propertyValue);
                }
            }
        });
        return update;
    }

    public static Update mapToUpdate(String notionPageLink) {
        return new Update().set("notionPageLink", notionPageLink);
    }

    public static Update mapToUpdate(ClubLandingStyle clubLandingStyle) {
        return new Update().set(clubLandingStyle.getClass().getSimpleName(), clubLandingStyle);
    }

    public static Update mapToUpdate(Form form) {
        return new Update().set(form.getClass().getSimpleName(), form);
    }

    public static Update mapToUpdate(Interview interview) {
        return new Update().set(interview.getClass().getSimpleName(), interview);
    }
}
