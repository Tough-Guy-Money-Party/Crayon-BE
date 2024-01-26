package com.yoyomo.domain.shared.util;

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
}
