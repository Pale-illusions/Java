package server.service;

import common.Message;
import common.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

// 该类的一个对象和某个客户端保持通信
public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String userId;

    public Socket getSocket() {
        return socket;
    }

    public String getUserId() {
        return userId;
    }

    private boolean loop = true;

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() {

        while (loop) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                // 根据 message 类型，做对应的业务处理
                String mesType = message.getMesType();
                if (mesType.equals(MessageType.MESSAGE_GET_ONLINE_USER_REQUEST)) {
                    // 客户端要求在线用户列表
                    // 规定格式： 100 200 300
                    String onlineUser = ManageServerConnectClientThread.getOnlineUser();
                    // 返回message
                    Message retMessage = new Message();
                    retMessage.setMesType(MessageType.MESSAGE_GET_ONLINE_USER_RETURN);
                    retMessage.setContent(onlineUser);
                    retMessage.setReceiver(message.getSender());
                    // 返回客户端
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(retMessage);

                } else if (mesType.equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    // 客户端请求退出
                    socket.close();
                    System.out.println("用户：" + message.getSender() + " 退出登录");
                    ManageServerConnectClientThread.removeServerConnectClientThread(message.getSender());
                    loop = false;
                } else if (mesType.equals(MessageType.MESSAGE_COMM_MES)) {
                    // 客户端请求普通聊天

                    // 获取 接收方 receiver 线程
                    // 如果不在线，则加入数据库
                    if (!ManageServerConnectClientThread.containsServerConnectClientThread(message.getReceiver())) { // 如果不在线
                        if (!OfflineDataBase.containsKey(message.getReceiver())) continue;
                        OfflineDataBase.getOfflineMessages(message.getReceiver()).add(message);
                        continue;
                    }
                    ServerConnectClientThread receiverThread = ManageServerConnectClientThread.getServerConnectClientThread(message.getReceiver());
                    // 获取 接收方 receiver outputstream
                    ObjectOutputStream oos = new ObjectOutputStream(receiverThread.getSocket().getOutputStream());
                    oos.writeObject(message);
                } else if (mesType.equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    // 客户端请求群发消息
                    HashMap<String, ServerConnectClientThread> map = ManageServerConnectClientThread.getMap();
                    for (Map.Entry<String, ServerConnectClientThread> entry : map.entrySet()) {
                        message.setReceiver(entry.getKey());
                        message.setMesType(MessageType.MESSAGE_COMM_MES);
                        if (entry.getKey().equals(message.getSender())) continue;
                        if (!ManageServerConnectClientThread.containsServerConnectClientThread(message.getReceiver())) { // 如果不在线
                            if (!OfflineDataBase.containsKey(message.getReceiver())) continue;
                            OfflineDataBase.getOfflineMessages(message.getReceiver()).add(message);
                            continue;
                        }
                        // 获取 接收方 receiver outputstream
                        ObjectOutputStream oos = new ObjectOutputStream(entry.getValue().getSocket().getOutputStream());
                        oos.writeObject(message);
                    }
                } else if (mesType.equals(MessageType.MESSAGE_FILE_MES)) {
                    // 客户端请求发送文件
                    if (!ManageServerConnectClientThread.containsServerConnectClientThread(message.getReceiver())) { // 如果不在线
                        if (!OfflineDataBase.containsKey(message.getReceiver())) continue;
                        OfflineDataBase.getOfflineMessages(message.getReceiver()).add(message);
                        continue;
                    }
                    ObjectOutputStream oos = new ObjectOutputStream(ManageServerConnectClientThread
                            .getServerConnectClientThread(message.getReceiver())
                            .getSocket()
                            .getOutputStream());
                    oos.writeObject(message);
                } else {
                    System.out.println("暂不处理");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
