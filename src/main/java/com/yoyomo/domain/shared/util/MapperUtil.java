package com.yoyomo.domain.shared.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.mongodb.core.query.Update;

import java.beans.PropertyDescriptor;

public class MapperUtil {
    private static final String CLASS = "class";
    private static final String ID = "id";
    private static final String PROPERTY_FORMAT = "%s%s";

    public static Update mapToUpdate(Object request) {
        return mapToUpdate(request, "");
    }

    public static Update mapToUpdate(Object request, String pathPrefix) {
        Update update = new Update();
        BeanWrapper beanWrapper = new BeanWrapperImpl(request);
        for (PropertyDescriptor pd : beanWrapper.getPropertyDescriptors()) {
            String propertyName = String.format(PROPERTY_FORMAT, pathPrefix, pd.getName());
            Object propertyValue = beanWrapper.getPropertyValue(pd.getName());

            if (propertyValue != null && !propertyName.contains(CLASS) && !propertyName.contains(ID)) {
                update.set(propertyName, propertyValue);
            }
        }
        return update;
    }
}
