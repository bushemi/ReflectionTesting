package services;

import annotations.CustomDateFormat;
import annotations.JsonValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationService<T> {
    private List<Method> listOfMethods =  new ArrayList<>();
    private List<Field> fieldsList =  new ArrayList<>();
    private List<String> listOfNames =  new ArrayList<>();
    private String localDateFormatPattern;

    public AnnotationService() {
    }


    private String getJsonFieldValue(Field field) {
        String result = null;
        if(field.isAnnotationPresent(JsonValue.class)){
            result = field.getAnnotation(JsonValue.class).name();
        } else {
            result = field.getName();
        }
        return result;
    }

    private String getJsonLocalDateFormat(Field field, Object object) {
        String result = null;
        LocalDate date = null;
        try {
            if (!field.isAccessible()) field.setAccessible(true);
            date = (LocalDate) field.get(object);
            field.setAccessible(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (field.isAnnotationPresent(CustomDateFormat.class)) {

            String formatter = field.getAnnotation(CustomDateFormat.class).format();
            DateTimeFormatter dateTimeFormatter;
            dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);

            result = date.format(dateTimeFormatter);
        } else {
            result = date.toString();
        }
        return result;
    }

    public List<String> parseFieldNamesToJson(Object object) {
        List<String> result = new ArrayList<>();
        Class clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            result.add(getJsonFieldValue(field));
        }
        return result;
    }

    public Map<String, String> parseFieldValuesToJson(Object object) {
        Map<String, String> result = new HashMap();
        String nameOfField = null;
        String valueOfField = null;
        Class clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            nameOfField = getJsonFieldValue(field);
            valueOfField = null;
            if (field.getType().equals(LocalDate.class)) {
                valueOfField = getJsonLocalDateFormat(field, object);
            } else {
                try {
                    if (!field.isAccessible()) field.setAccessible(true);
                    Object value = field.get(object);
                    if (value != null) valueOfField = value.toString();

                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (valueOfField != null) result.put(nameOfField, valueOfField);
        }
        return result;
    }

}
