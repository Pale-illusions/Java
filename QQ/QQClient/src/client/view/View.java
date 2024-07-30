package client.view;

import client.service.FileClientService;
import client.service.MessageClientService;
import client.service.UserClientService;
import client.utils.Utility;
import common.MessageType;

public class View {

    public static void main(String[] args) {
        new View().mainMenu();
    }

    private boolean loop = true; // 控制是否显示菜单
    private String key;
    private UserClientService userClientService = new UserClientService(); // 用于登陆服务
    private MessageClientService messageClientService = new MessageClientService(); // 用于消息服务
    private FileClientService fileClientService = new FileClientService(); // 用于文件服务
    // 显示主菜单
    private void mainMenu() {
        while (loop) {
            System.out.println("==========欢迎登录网络通信系统==========");
            System.out.println("\t\t 1 登陆系统");
            System.out.println("\t\t 9 退出系统");
            System.out.println("请输入你的选择");

            key = Utility.readString(1);
            // 根据用户的输入，处理不同逻辑
            switch (key) {
                case "1":
                    System.out.println("请输入用户号：");
                    String userId = Utility.readString(50);
                    System.out.println("请输入密码：");
                    String pwd = Utility.readString(50);
                    String check = userClientService.checkUser(userId, pwd);
                    if (check.equals(MessageType.MESSAGE_LOGIN_SUCCEED)) { // 登陆成功
                        System.out.println("==========欢迎 (用户 " + userId + " )!==========");
                        // 进入二级菜单
                        while (loop) {
                            System.out.println("\n==========网络通信系统二级菜单(用户 " + userId + " )==========");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.println("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("请输入群发消息：");
                                    String groupContent = Utility.readString(100);
                                    // 群发消息
                                    messageClientService.sendMessageToAll(groupContent, userId);
                                    break;
                                case "3":
                                    System.out.println("请输入目标用户号：");
                                    String targetId = Utility.readString(50);
                                    System.out.println("请输入想说的话：");
                                    String singleContent = Utility.readString(100);
                                    // 发送给服务端
                                    messageClientService.sendMessageToTarget(singleContent, userId, targetId);
                                    break;
                                case "4":
                                    System.out.println("请输入接收文件的用户：");
                                    targetId = Utility.readString(100);
                                    System.out.println("请输入目标文件的路径：");
                                    String src = Utility.readString(100);
                                    System.out.println("请输入目标文件的保存路径：");
                                    String des = Utility.readString(100);
                                    fileClientService.sendFileToOne(src, des, userId, targetId);
                                    break;
                                case "9":
                                    loop = false;
                                    userClientService.logOut();
                                    System.exit(0);
                                    break;
                            }
                        }
                    } else if (check.equals(MessageType.MESSAGE_LOGIN_FAIL)){ // 密码错误
                        System.out.println("=========登陆失败(密码错误或用户名不存在)！=========");
                    } else if (check.equals(MessageType.MESSAGE_LOGIN_EXIST)) { // 重复登录
                        System.out.println("=========已存在登录，请勿重复登录！=========");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }
        }
    }
}
