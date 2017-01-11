import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
        List<String> list = new ArrayList<String>();
        for (Field field : fields) {
            String methodName = field.getName();
            String type = field.getGenericType().getTypeName();
            System.out.println(type);
            methodName = "get" + toUpper(methodName);
            try {
                Method method = clazz.getDeclaredMethod(methodName);
//                System.out.println("invoking\t" + methodName);
                Object res = method.invoke(object, null);
                if (res != null) {
                    if ("java.lang.String".equals(type)) {
                        String temp = "\"" + field.getName() + "\":\"" + res.toString() + "\"";
                        list.add(temp);
                    } else if ("int".equals(type)) {
                        String temp = "\"" + field.getName() + "\":" + res.toString();
                        list.add(temp);
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        ret += String.join(",", list);
        ret += "}";
        return ret;
    }
    public static void main(String[] args) {
//        POJO pojo = new POJO(18, "puyangsky", null);
//        long t1 = Calendar.getInstance().getTimeInMillis();
//        String s1 = serialize(pojo);
//        System.out.println(s1);
//        long t2 = Calendar.getInstance().getTimeInMillis();
//        long t3 = Calendar.getInstance().getTimeInMillis();
//        String s2 = JSON.toJSONString(pojo);
//        System.out.println(s2);
//        long t4 = Calendar.getInstance().getTimeInMillis();
//        System.out.println("my json 耗时："  + (t2 - t1));
//        System.out.println("fastjson 耗时："  + (t4 - t3));

        String s3 = "{\"age\":18,\"name\":\"puyangsky\"}";
        JSONObject object = (JSONObject) JSON.parse(s3);

        for (Map.Entry<String, Object> e : object.entrySet()) {
            System.out.println(e.getKey());
            System.out.println(e.getValue());
        }
//        System.out.println(object.toString());
    }
}
