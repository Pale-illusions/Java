package server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageServerConnectClientThread {

    private static HashMap<String, ServerConnectClientThread> map = new HashMap<>();

    static {
        map.put("100", null);
        map.put("200", null);
        map.put("300", null);
        map.put("至尊宝", null);
        map.put("紫霞仙子", null);
        map.put("菩提老祖", null);
    }

    public static HashMap<String, ServerConnectClientThread> getMap() {
        return map;
    }

    public static void addClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {
        map.put(userId, serverConnectClientThread);
    }

    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return map.get(userId);
    }

    public static boolean containsServerConnectClientThread(String userId) {
        return map.containsKey(userId) && map.get(userId) != null;
    }

    // 删除用户
    public static void removeServerConnectClientThread(String userId) {
        map.put(userId, null);
    }

    // 返回在线用户列表
    public static String getOnlineUser() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ServerConnectClientThread> entry : map.entrySet()) {
            if (entry.getValue() == null) continue;
            sb.append(entry.getKey()).append(" ");
        }
        return sb.toString();
    }
}
