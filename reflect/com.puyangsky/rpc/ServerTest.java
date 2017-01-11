package rpc;

/**
 * Created by puyangsky on 2016/12/25.
 */
public class ServerTest {
    public static void main(String[] args) throws Exception {
        HelloService helloService = new HelloServiceImpl();
        RPCServer.open(helloService);
    }
}
