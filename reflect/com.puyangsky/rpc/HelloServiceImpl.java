package rpc;

/**
 * Created by puyangsky on 2016/12/25.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String show(String name) {
        System.out.println(name);
        return "name: " + name;
    }
}
