package services;

import annotations.CustomDateFormat;
import annotations.JsonValue;
import dto.Human;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationService {
    private List<Method> listOfMethods =  new ArrayList<>();
    private List<Field> fieldsList =  new ArrayList<>();
    private List<String> listOfNames =  new ArrayList<>();
    private String localDateFormatPattern;
    public AnnotationService() {
    }

    public String getLocalDateFormatPattern() {
        return localDateFormatPattern;
    }

    public void getAnnotation(Human human) throws InvocationTargetException, IllegalAccessException {
        getListOfAnnotations(human);
    }
    public List<Method> getListOfMethods(Human human) {
        Class clazz = human.getClass();
        for(Method method : clazz.getDeclaredMethods()){
//            System.out.println(method.toString());
            if (method.getName().contains("get")){
                System.out.println(method.getReturnType().toString());
                if (method.getReturnType().toString().equals("class java.lang.String"))
                listOfMethods.add(method);
            }

        }
        listOfMethods.forEach(System.out::println);
        return listOfMethods;
    }

    public List<Field> getListOfAnnotations(Human human) {
        Class clazz = human.getClass();
        for(Field field : clazz.getDeclaredFields()){

            if (field.getType().toString().equals("class java.lang.String"))
           fieldsList.add(field);
        }
        return fieldsList;
    }

    public List<String> getNamesList(){

        fieldsList.forEach((e) -> addMethodName(e));
        return listOfNames;
    }

    private void addMethodName(Field field){
        String name="";
        if(field.isAnnotationPresent(JsonValue.class)){

            name = field.getAnnotation(JsonValue.class).name();
        }else if(field.isAnnotationPresent(CustomDateFormat.class)){

            localDateFormatPattern = field.getAnnotation(CustomDateFormat.class).format();

            name = field.getName();
        }else{

            name = field.getName();
        }
        listOfNames.add(name);
    }
}
