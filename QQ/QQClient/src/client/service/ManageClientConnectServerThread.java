package client.service;

import java.util.HashMap;

public class ManageClientConnectServerThread {
    // key 为用户id，value 为线程
    private static HashMap<String, ClientConnectServerThread> map = new HashMap<>();

    public static void addClientConnectServerThread(String userId, ClientConnectServerThread cThread) {
        map.put(userId, cThread);
    }

    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return map.get(userId);
    }
}
