package client.service;

import common.Message;
import common.MessageType;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class ClientConnectServerThread extends Thread{
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                // 如果服务器，没有发送Message对象，会阻塞
                Message message = (Message) ois.readObject();
                // 判断 message 类型
                String mesType = message.getMesType();
                // 服务端返回在线用户列表
                if (mesType.equals(MessageType.MESSAGE_GET_ONLINE_USER_RETURN)) {
                    // 取出在线用户列表
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("========当前在线用户列表========");
                    for (String i : onlineUsers) {
                        System.out.println("用户：" + i);
                    }
                } else if (mesType.equals(MessageType.MESSAGE_COMM_MES)) { // 普通聊天消息
                    System.out.println("\n" + message.getSendTime() + " 用户：" + message.getSender() + " 对你说：" + message.getContent());
                } else if (mesType.equals(MessageType.MESSAGE_FILE_MES)) {
                    // 文件消息
                    System.out.println("\n" + message.getSender() + " 给我发送文件：" + message.getSrc() + " 到 " + message.getDest());

                    // 取出文件
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(message.getDest()));
                    bos.write(message.getFileBytes());
                    bos.close();
                    System.out.println("保存文件成功！\n");
                } else {
                    System.out.println("暂时不处理");
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
