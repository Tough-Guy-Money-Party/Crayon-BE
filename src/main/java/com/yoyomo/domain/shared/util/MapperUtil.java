package com.yoyomo.domain.shared.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.mongodb.core.query.Update;

import java.beans.PropertyDescriptor;

public class MapperUtil {

    private static final String CLASS = "class";

    public static Update mapToUpdate(Object request) {
        Update update = new Update();
        BeanWrapper beanWrapper = new BeanWrapperImpl(request);
        for (PropertyDescriptor pd : beanWrapper.getPropertyDescriptors()) {
            String propertyName = pd.getName();
            Object propertyValue = beanWrapper.getPropertyValue(propertyName);

            if (propertyValue != null && !propertyName.equals(CLASS)) {
                update.set(propertyName, propertyValue);
            }
        }
        return update;
    }
}
