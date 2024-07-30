package server.service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class QQServer {
    private ServerSocket serverSocket = null;
    //创建一个集合，存放多个用户，如果是这些用户登录，认为是合法的
    private static HashMap<String, User> validUsers = new HashMap<>();

    static { // 静态代码块，初始化 validUsers
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
        validUsers.put("至尊宝", new User("至尊宝", "123456"));
        validUsers.put("紫霞仙子", new User("紫霞仙子", "123456"));
        validUsers.put("菩提老祖", new User("菩提老祖", "123456"));
    }

    public QQServer() {
        System.out.println("服务端在9999端口监听....");
        new Thread(new SendNewsToAllService()).start(); // 启动推送服务
        try {
            serverSocket = new ServerSocket(9999);
            while (true) {
                Socket socket = serverSocket.accept(); // 如果没有客户端连接，就会阻塞
                // 得到 socket 对象关联的输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User user = (User) ois.readObject(); // 读取客户端发送的User对象

                // 验证
                Message message = new Message();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                if (checkUser(user)) { // 登录成功
                    if (ManageServerConnectClientThread.containsServerConnectClientThread(user.getUserId())) { // 如果已存在登录
                        message.setMesType(MessageType.MESSAGE_LOGIN_EXIST);
                        oos.writeObject(message);
                    } else { // 新的登录
                        message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                        // 将 message 回复到 客户端
                        oos.writeObject(message);
                        // 创建线程，和客户端保持通信
                        ServerConnectClientThread sThread = new ServerConnectClientThread(socket, user.getUserId());
                        sThread.start();
                        // 把线程放入集合中管理
                        ManageServerConnectClientThread.addClientThread(user.getUserId(), sThread);
                        // 发送离线消息
                        List<Message> messages = OfflineDataBase.getOfflineMessages(user.getUserId());
                        if (!messages.isEmpty()) {
                            for (Message m : messages) {
                                ObjectOutputStream oos1 = new ObjectOutputStream(sThread.getSocket().getOutputStream());
                                oos1.writeObject(m);
                            }
                            OfflineDataBase.removeOfflineMessages(user.getUserId());
                        }
                    }
                } else { // 登陆失败
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    socket.close();
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 如果服务器推出了 while 循环，说明服务器不在监听，关闭 serversocket
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 验证用户是否有效
    private boolean checkUser(User user) {
        if (!validUsers.containsKey(user.getUserId())) { // 判断是否包含该用户
            return false;
        }
        return validUsers.get(user.getUserId()).getPwd().equals(user.getPwd()); // 判断密码是否一致
    }
}
