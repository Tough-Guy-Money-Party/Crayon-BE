package com.yoyomo.domain.shared.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.mongodb.core.query.Update;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

public class MapperUtil {
    private static final String CLASS = "class";
    private static final String ID = "id";
    private static final String PROPERTY_FORMAT = "%s%s";

    public static Update mapToUpdate(Object... requests) {
        return mapToUpdate("", requests);
    }

    public static Update mapToUpdate(String pathPrefix, Object... requests) {
        Update update = new Update();
        Arrays.stream(requests).forEach(request -> {
            BeanWrapper beanWrapper = new BeanWrapperImpl(request);
            for (PropertyDescriptor pd : beanWrapper.getPropertyDescriptors()) {
                String propertyName = String.format(PROPERTY_FORMAT, pathPrefix, pd.getName());
                Object propertyValue = beanWrapper.getPropertyValue(pd.getName());

                if (propertyValue != null && !propertyName.contains(CLASS) && !propertyName.contains(ID)) {
                    update.set(propertyName, propertyValue);
                }
            }
        });
        return update;
    }
}
