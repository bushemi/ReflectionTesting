package services;

import annotations.CustomDateFormat;
import annotations.JsonValue;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationService<T> {

    public AnnotationService() {
    }

    private String getJsonFieldName(Field field) {
        String result = null;
        if(field.isAnnotationPresent(JsonValue.class)){
            result = field.getAnnotation(JsonValue.class).name();
        } else {
            result = field.getName();
        }
        return result;
    }

    private String getJsonLocalDateFormat(Field field, Object object) {
        String result;
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

    public List<String> parseFieldNamesToJson(Class clazz) {
        List<String> result = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            result.add(getJsonFieldName(field));
        }
        return result;
    }

    public Map<String, String> parseFieldValuesToJson(Object object) {
        Map<String, String> result = new HashMap();
        String nameOfField = null;
        String valueOfField = null;
        Class clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            nameOfField = getJsonFieldName(field);
            valueOfField = null;
            if (field.getType().equals(LocalDate.class)) {
                valueOfField = getJsonLocalDateFormat(field, object);
            } else {
                try {
                    boolean flag = field.isAccessible();
                    if (!flag) field.setAccessible(true);
                    Object value = field.get(object);
                    if (value != null) valueOfField = value.toString();
                    if (!flag) field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (valueOfField != null) result.put(nameOfField, valueOfField);
        }
        return result;
    }

    private String checkFieldNameAnnotation(String fieldName, Object object) {
        String result = null;
        Class clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {

            if (field.isAnnotationPresent(JsonValue.class)) {
                if (field.getAnnotation(JsonValue.class).name().equals(fieldName)) {
                    result = field.getName();
                    return result;
                }
            }

        }
        return result;
    }

    private LocalDate checkFieldDateType(Field field, Object data) {
        String result = null;
        LocalDate date = null;
        if (field.getType().toString().equals("class java.time.LocalDate")) {
            if (field.isAnnotationPresent(CustomDateFormat.class)) {
                String formatter = field.getAnnotation(CustomDateFormat.class).format();
                DateTimeFormatter dateTimeFormatter;
                dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
                date = LocalDate.parse(data.toString(), dateTimeFormatter);
            } else {
                date = LocalDate.parse(data.toString());
            }
        }
        return date;
    }

    public void inject(String fieldName, Object data, Object object) throws NoSuchFieldException, IllegalAccessException {
        String s = checkFieldNameAnnotation(fieldName, object);
        Field field = null;
        if (s != null) {
            field = object.getClass().getDeclaredField(s);
        } else {
            field = object.getClass().getDeclaredField(fieldName);
        }

        Object fieldValue = checkFieldDateType(field, data);
        boolean flag = field.isAccessible();
        if (!flag) field.setAccessible(true);
        if (fieldValue != null) {
            field.set(object, fieldValue);
        } else {

            switch (field.getType().toString()) {
                default:
                    field.set(object, data);
                    break;
                case "class java.lang.Integer":
                case "int":
                    field.set(object, Integer.parseInt(data.toString()));
                    break;
                case "class java.lang.Float":
                case "float":
                    field.set(object, Float.parseFloat(data.toString()));
                    break;
                case "class java.lang.Double":
                case "double":
                    field.set(object, Double.parseDouble(data.toString()));
                    break;
                case "class java.lang.Long":
                case "long":
                    field.set(object, Long.parseLong(data.toString()));
                    break;
            }
        }
        if (!flag) field.setAccessible(false);

    }

}
