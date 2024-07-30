package server.service;

import common.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
// 数据库：存放离线消息
public class OfflineDataBase {
    private static ConcurrentHashMap<String, List<Message>> map = new ConcurrentHashMap<>();

    static {
        map.put("100", new ArrayList<>());
        map.put("200", new ArrayList<>());
        map.put("300", new ArrayList<>());
        map.put("至尊宝", new ArrayList<>());
        map.put("紫霞仙子", new ArrayList<>());
        map.put("菩提老祖", new ArrayList<>());
    }

    public static List<Message> getOfflineMessages(String userId) {
        return map.get(userId);
    }

    public static void removeOfflineMessages(String userId) {
        map.put(userId, new ArrayList<>());
    }

    public static boolean containsKey(String userId) {
        return map.containsKey(userId);
    }
}
