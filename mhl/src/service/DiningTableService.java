package service;

import dao.DiningTableDAO;
import domain.DiningTable;

import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
public class DiningTableService {
    // 定义 DiningTableDAO 对象
    private DiningTableDAO diningTableDAO = new DiningTableDAO();

    // 返回所有餐桌信息
    public List<DiningTable> list() {
        return diningTableDAO.multipleQuery("select id, state from diningTable", DiningTable.class);
    }

    // 根据 id 返回对应餐桌 DiningTable 对象
    // 如果为 null 代表不存在
    public DiningTable getDiningTableById(int id) {
        return diningTableDAO.singleQuery("select * from diningTable where id = ?", DiningTable.class, id);
    }

    // 更新餐桌状态（预订）
    public boolean orderDiningTable(int id, String orderName, String orderTel) {
        int update = diningTableDAO.update("update diningTable set state = '已经预订', orderName = ?, orderTel = ? where id = ?", orderName, orderTel, id);
        return update > 0;
    }

    // 更新餐桌状态（一般）
    public boolean updateDiningTableState(int id, String state) {
        int update = diningTableDAO.update("update diningTable set state = ? where id = ?", state, id);
        return update > 0;
    }

    // 将餐桌状态设置为 空闲
    public boolean updateDiningTableToFree(int id, String state) {
        int update = diningTableDAO.update("update diningTable set state = ?, orderName = '', orderTel = '' where id = ?", state, id);
        return update > 0;
    }
}
