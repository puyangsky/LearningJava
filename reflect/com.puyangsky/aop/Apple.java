package aop;


/**
 * Created by puyangsky on 2016/12/24.
 */
public class Apple implements Fruit{
    @Override
    public void show() {
        System.out.println("<<<<show method is invoked");
    }
}
