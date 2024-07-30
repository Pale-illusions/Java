package client.service;

import common.Message;
import common.MessageType;

import java.io.*;

public class FileClientService {
    /**
     * 发送文件消息
     * @param src 源文件
     * @param des 目标目录
     * @param senderId 发送用户id
     * @param receiverId 接收用户id
     */
    public void sendFileToOne(String src, String des, String senderId, String receiverId) {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setReceiver(receiverId);
        message.setSrc(src);
        message.setDest(des);

        // 读取文件
        byte[] fileBytes = new byte[(int) new File(src).length()];
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(src));
            bis.read(fileBytes); // 将src文件读入到字节数组
            message.setFileBytes(fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // debug 信息
        System.out.println("\n" + senderId + " 给 " + receiverId + "发送文件：" + src + " 到 " + des);

        // 发送message
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
