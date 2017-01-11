package com.test.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by puyangsky on 2016/11/20.
 */
public class ReflectTest {
    public static String toUpper(String s) {
        char[] chars = s.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }
    public static String serialize(Object object) {
        Class clazz = object.getClass();
        //该方法不能获取私有属性
//        Field[] fields = clazz.getFields();
        //该方法可以获取所有属性
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getMethods();
        Constructor[] constructors = clazz.getConstructors();
        String ret = "{";
        for (Field field : fields) {
            String methodName = field.getName();
            String type = field.getGenericType().getTypeName();
            System.out.println(type);
            methodName = "get" + toUpper(methodName);
            try {
                Method method = clazz.getDeclaredMethod(methodName);
                System.out.println("invoking\t" + methodName);
                Object res = method.invoke(object, null);
                if (res != null) {
                    ret += "\"" + field.getName() + "\":" + res.toString() + ",";
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        ret += "}";
        return ret;
    }
    public static void main(String[] args) {
        POJO pojo = new POJO(18, "puyangsky", null);
        String s1 = serialize(pojo);
    }
}
