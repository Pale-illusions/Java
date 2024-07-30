package server.service;

import common.Message;
import common.MessageType;
import utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SendNewsToAllService implements Runnable{

    @Override
    public void run() {
        while (true) {
            System.out.println("请输入服务器要推送的新闻/消息[输入exit表示退出推送服务]：");
            String news = Utility.readString(100);
            if ("exit".equals(news)) { // 退出服务
                break;
            }
            // 群发消息
            Message message = new Message();
            message.setSender("服务器");
            message.setSendTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            message.setMesType(MessageType.MESSAGE_COMM_MES);
            message.setContent(news);
            System.out.println("服务器对所有人说：" + news);

            // 遍历所有线程，发送消息
            HashMap<String, ServerConnectClientThread> map = ManageServerConnectClientThread.getMap();
            for (Map.Entry<String, ServerConnectClientThread> entry : map.entrySet()) {
                message.setReceiver(entry.getKey());
                if (!ManageServerConnectClientThread.containsServerConnectClientThread(message.getReceiver())) { // 如果不在线
                    OfflineDataBase.getOfflineMessages(message.getReceiver()).add(message);
                    continue;
                }
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(entry.getValue().getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
