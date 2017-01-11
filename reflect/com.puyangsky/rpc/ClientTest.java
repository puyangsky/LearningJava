package rpc;

/**
 * Created by puyangsky on 2016/12/25.
 */
public class ClientTest {
    public static void main(String[] args) {
        try {
            HelloService service = RPCServer.refer(HelloService.class, "127.0.0.1");
            System.out.println(service.show("hello"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
