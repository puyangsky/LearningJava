package rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by puyangsky on 2016/12/25.
 */
public class RPCServer {
    private static final int PORT = 8000;
    /**
     * 暴露服务
     *
     * @param service 服务的对象实例
     * */
    public static void open(final Object service) throws Exception {
        if (service == null) {
            throw new IllegalArgumentException();
        }
        System.out.println("Service is opening for " + service.getClass().getName() + " at port: " + PORT);
        //开启ServerSocket监听8000端口
        final ServerSocket server = new ServerSocket(PORT);
        for (;;) {
            try {
                //接收到一个客户端请求
                final Socket client = server.accept();
                //开一个线程处理
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                            try {
                                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                                try {
                                    String methodName = input.readUTF();
                                    System.out.println(">>>>methodName: " + methodName);
                                    Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                                    Object[] arguments = (Object[]) input.readObject();
                                    System.out.println(">>>>arguments: " + arguments.toString());
                                    ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                                    try {
                                        //利用反射获取到方法对象
                                        Method method = service.getClass().getMethod(methodName, parameterTypes);
                                        //调用方法并获取返回值
                                        Object result = method.invoke(service, arguments);
                                        //把返回值写入socket，返回给客户端
                                        out.writeObject(result);
                                        //                                out.flush();
                                    } catch (Throwable t) {
                                        out.writeObject(t);
                                    } finally {
                                        out.close();
                                    }
                                } finally {
                                    input.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 指定在远程主机上的服务
     *
     * @param <T> 接口泛型
     * @param interfaceClass 接口
     * @param host 远程主机IP
    * */

    @SuppressWarnings("unchecked")
    public static <T> T refer(final Class<T> interfaceClass, final String host) {
        if (interfaceClass == null) {
            throw new IllegalArgumentException("invalid interface");
        }
        if (host == null || "".equals(host)) {
            throw new IllegalArgumentException("invalid host");
        }
        System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + PORT);
        //动态代理返回对象实例，并且利用泛型转成服务类型
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = new Socket(host, PORT);
                        try {
                            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                            try {
                                //发送方法名
                                out.writeUTF(method.getName());
                                //发生方法参数列表
                                out.writeObject(method.getParameterTypes());
                                //发生方法需要的参数
                                out.writeObject(args);
                                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                                try {
                                    //获取返回值
                                    Object result = input.readObject();
                                    if (result instanceof Throwable) {
                                        throw (Throwable) result;
                                    }
                                    return result;
                                }finally {
                                    input.close();
                                }
                            }finally {
                                out.close();
                            }
                        } finally {
                            socket.close();
                        }
                    }
                });
    }
}


//
//public class RPCServer {
//
//    private static final int PORT = 8000;
//    /**
//     * 暴露服务
//     *
//     * @param service 服务实现
//     * @throws Exception
//     */
//    public static void open(final Object service) throws Exception {
//        if (service == null)
//            throw new IllegalArgumentException("service instance == null");
//        int port = PORT;
//        System.out.println("Export service " + service.getClass().getName() + " on port " + port);
//        ServerSocket server = new ServerSocket(port);
//        for(;;) {
//            try {
//                final Socket socket = server.accept();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            try {
//                                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
//                                try {
//                                    String methodName = input.readUTF();
//                                    Class<?>[] parameterTypes = (Class<?>[])input.readObject();
//                                    Object[] arguments = (Object[])input.readObject();
//                                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
//                                    try {
//                                        Method method = service.getClass().getMethod(methodName, parameterTypes);
//                                        Object result = method.invoke(service, arguments);
//                                        output.writeObject(result);
//                                    } catch (Throwable t) {
//                                        output.writeObject(t);
//                                    } finally {
//                                        output.close();
//                                    }
//                                } finally {
//                                    input.close();
//                                }
//                            } finally {
//                                socket.close();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 引用服务
//     *
//     * @param <T> 接口泛型
//     * @param interfaceClass 接口类型
//     * @param host 服务器主机名
//     * @return 远程服务
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    public static <T> T refer(final Class<T> interfaceClass, final String host) throws Exception {
//        final int port = PORT;
//        if (interfaceClass == null)
//            throw new IllegalArgumentException("Interface class == null");
//        if (! interfaceClass.isInterface())
//            throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");
//        if (host == null || host.length() == 0)
//            throw new IllegalArgumentException("Host == null!");
//        if (port <= 0 || port > 65535)
//            throw new IllegalArgumentException("Invalid port " + port);
//        System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port);
//        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new InvocationHandler() {
//            public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
//                Socket socket = new Socket(host, port);
//                try {
//                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
//                    try {
//                        output.writeUTF(method.getName());
//                        output.writeObject(method.getParameterTypes());
//                        output.writeObject(arguments);
//                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
//                        try {
//                            Object result = input.readObject();
//                            if (result instanceof Throwable) {
//                                throw (Throwable) result;
//                            }
//                            return result;
//                        } finally {
//                            input.close();
//                        }
//                    } finally {
//                        output.close();
//                    }
//                } finally {
//                    socket.close();
//                }
//            }
//        });
//    }
//
//}