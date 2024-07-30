package client.service;

import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

// 提供消息相关的功能
public class MessageClientService {

    public void sendMessageToTarget(String content, String senderId, String receiverId) {
        Message message = new Message();
        message.setSender(senderId);
        message.setReceiver(receiverId);
        message.setContent(content);
        message.setSendTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        message.setMesType(MessageType.MESSAGE_COMM_MES); // 设置为普通聊天消息
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnectServerThread(senderId)
                    .getSocket()
                    .getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessageToAll(String content, String senderId) {
        Message message = new Message();
        message.setSender(senderId);
        message.setContent(content);
        message.setSendTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnectServerThread(senderId)
                    .getSocket()
                    .getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
