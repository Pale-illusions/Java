package service;

import dao.BillDAO;
import dao.MultiTableDAO;
import domain.Bill;
import domain.MultiTableBean;

import java.util.List;
import java.util.UUID;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
public class BillService {
    private BillDAO billDAO = new BillDAO();
    private MenuService menuService = new MenuService();
    private DiningTableService diningTableService = new DiningTableService();
    private MultiTableDAO multiTableDAO = new MultiTableDAO();

    // 生成账单
    // 更新对应餐桌状态
    public boolean orderMenu(int menuId, int nums, int diningTableId) {
        // 生成一个账单号
        String billId = UUID.randomUUID().toString();

        // 将账单更新到 bill 表
        int update = billDAO.update("insert into bill values(null, ?, ?, ?, ?, ?, now(), '未结账')", billId, menuId, nums, menuService.getMenuById(menuId).getPrice() * nums, diningTableId);
        return update > 0 && diningTableService.updateDiningTableState(diningTableId, "就餐中");
    }

    // 返回所有账单
    public List<Bill> list() {
        return billDAO.multipleQuery("select * from bill", Bill.class);
    }

    // 返回所有账单 和 菜品名字
    public List<MultiTableBean> list2() {
        return multiTableDAO.multipleQuery("select b.*, m.name from bill b join menu m on b.menuId = m.id", MultiTableBean.class);
    }


    // 查看某个餐桌是否由未结账的账单
    public boolean existUnpaidBillByDiningTableId(int diningTableId) {
        Long count = (Long) billDAO.scalarQuery("select count(*) from bill where diningTableId = ? and state = '未结账'", diningTableId);
        return count > 0;
    }

    // 完成结账
    public boolean payBill(int diningTableId, String payMode) {
        // 修改 bill 表
        int update = billDAO.update("update bill set state = ? where diningTableId = ? and state = '未结账'", payMode, diningTableId);
        // 修改 diningTable 表
        return update > 0 && diningTableService.updateDiningTableToFree(diningTableId, "空");
    }
}
