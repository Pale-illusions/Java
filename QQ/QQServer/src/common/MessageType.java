package common;

public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1"; // 登陆成功
    String MESSAGE_LOGIN_FAIL = "2"; // 登陆失败
    String MESSAGE_LOGIN_EXIST = "7"; // 已经登陆
    String MESSAGE_COMM_MES = "3"; // 普通信息
    String MESSAGE_GET_ONLINE_USER_REQUEST = "4"; // 要求返回在线用户列表
    String MESSAGE_GET_ONLINE_USER_RETURN = "5"; // 返回在线用户列表
    String MESSAGE_CLIENT_EXIT = "6"; // 客户端请求退出
    String MESSAGE_TO_ALL_MES = "8"; // 群发消息
    String MESSAGE_FILE_MES = "9"; // 文件消息
}
