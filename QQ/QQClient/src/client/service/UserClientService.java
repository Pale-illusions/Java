package client.service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

// 完成用户登录验证和用户注册等功能
public class UserClientService {

    private User user = new User();
    private Socket socket;
    // 根据userId 和 pwd 到服务器验证该用户是否合法
    public String checkUser(String userId, String pwd) {
        String check = "";
        // 创建User对象
        user.setUserId(userId);
        user.setPwd(pwd);

        // 连接服务端，发送 u 对象
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user); // 发送 User 对象

            // 读取从服务器返回的 Message 对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();
            check = ms.getMesType();
            // 判断是否登录成功
            if (check.equals(MessageType.MESSAGE_LOGIN_SUCCEED)) { // 登陆成功
                // 创建一个和服务端保持通信的线程 -> 创建一个类 ClientConnectServerThread
                ClientConnectServerThread cThread = new ClientConnectServerThread(socket);
                cThread.start();
                // 为了方便管理，将线程加入集合中
                ManageClientConnectServerThread.addClientConnectServerThread(userId, cThread);
            } else { // 登录失败
                socket.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return check;
    }

    // 向服务器端请求在线用户列表
    public void onlineFriendList() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_USER_REQUEST);
        message.setSender(user.getUserId());

        // 发送给服务器
        try {
            // 在管理线程的集合中，通过userId，获得线程
            // 通过线程对应的socket获得输出流
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                            .getClientConnectServerThread(user.getUserId())
                            .getSocket()
                            .getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 向服务器发出退出消息
    public void logOut() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(user.getUserId());

        // 发送给服务器
        try {
            // 在管理线程的集合中，通过userId，获得线程
            // 通过线程对应的socket获得输出流
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnectServerThread(user.getUserId())
                    .getSocket()
                    .getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
