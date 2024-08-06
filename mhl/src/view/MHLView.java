package view;

import domain.*;
import service.BillService;
import service.DiningTableService;
import service.EmployeeService;
import service.MenuService;
import utils.Utility;

import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 这是主界面
 */
public class MHLView {
    public static void main(String[] args) {
        new MHLView().mainMenu();
    }

    // 控制是否退出菜单
    private boolean loop = true;
    private String key = ""; // 接收用户输入
    // 定义 EmployeeService 对象
    private EmployeeService employeeService = new EmployeeService();
    // 定义 DiningTableService 对象
    private DiningTableService diningTableService = new DiningTableService();
    // 定义 MenuService 对象
    private MenuService menuService = new MenuService();
    // 定义 BillService 对象
    private BillService billService = new BillService();

    // 显示主菜单
    public void mainMenu() {
        while (loop) {
            System.out.println("======================满汉楼======================");
            System.out.println("\t\t 1 登录满汉楼");
            System.out.println("\t\t 2 退出满汉楼");
            System.out.println("请输入你的选择：");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.println("请输入员工号：");
                    String empId = Utility.readString(50);
                    System.out.println("请输入密码：");
                    String pwd = Utility.readString(50);
                    Employee employee = employeeService.getEmployeeByIdAndPwd(empId, pwd); // 查询 employee 对象
                    if (employee != null) {
                        System.out.println("======================登陆成功[" + employee.getName() + "]======================");
                        // 显示二级菜单
                        while (loop) {
                            System.out.println("======================满汉楼二级菜单======================");
                            System.out.println("\t\t 1 显示餐桌状态");
                            System.out.println("\t\t 2 预定餐桌");
                            System.out.println("\t\t 3 显示所有菜品");
                            System.out.println("\t\t 4 点餐服务");
                            System.out.println("\t\t 5 查看账单");
                            System.out.println("\t\t 6 结账");
                            System.out.println("\t\t 9 退出满汉楼");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    listDiningTable();
                                    break;
                                case "2":
                                    orderDiningTable();
                                    break;
                                case "3":
                                    listMenu();
                                    break;
                                case "4":
                                    orderMenu();
                                    break;
                                case "5":
                                    listBill2();
                                    break;
                                case "6":
                                    payBill();
                                    break;
                                case "9":
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("输入有误，请重新输入！");
                            }
                        }
                    } else {
                        System.out.println("======================登陆失败======================");
                    }
                    break;
                case "2":
                    loop = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入！");
            }
        }
        System.out.println("退出");
    }

    // 显示所有餐桌状态
    public void listDiningTable() {
        List<DiningTable> list = diningTableService.list();
        System.out.println("id\t\t餐桌状态");
        for (DiningTable diningTable : list) {
            System.out.println(diningTable);
        }
        System.out.println("======================显示完毕======================");
    }

    // 订座
    public void orderDiningTable() {
        System.out.println("======================预订餐桌======================");
        System.out.println("请选择要预订的餐桌编号(-1退出)：");
        int orderId = Utility.readInt();
        if (orderId == -1) {
            System.out.println("======================取消预订餐桌======================");
            return;
        }
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {
            DiningTable diningTable = diningTableService.getDiningTableById(orderId);
            // 如果不存在，返回
            if (diningTable == null) {
                System.out.println("======================预订餐桌不存在======================");
                return;
            }
            // 如果不为空， 返回
            if (!diningTable.getState().equals("空")) {
                System.out.println("======================该餐桌已预订或就餐中======================");
                return;
            }
            // 更新状态
            System.out.print("预订人的名字：");
            String orderName = Utility.readString(50);
            System.out.print("预订人的电话：");
            String orderTel = Utility.readString(50);
            if (diningTableService.orderDiningTable(orderId, orderName, orderTel)) {
                System.out.println("======================预订餐桌成功======================");
            } else {
                System.out.println("======================预订餐桌失败======================");
            }
        } else {
            System.out.println("======================取消预订餐桌======================");
        }
    }

    // 显示菜单
    public void listMenu() {
        List<Menu> list = menuService.list();
        System.out.println("\n菜品编号\t\t菜品名\t\t类别\t\t价格");
        for (Menu menu : list) {
            System.out.println(menu);
        }
        System.out.println("======================显示完毕======================");
    }

    // 点餐服务
    public void orderMenu() {
        System.out.println("======================点餐服务======================");
        System.out.print("请输入点餐的桌号(-1退出)：");
        int orderDiningTableId = Utility.readInt();
        if (orderDiningTableId == -1) {
            System.out.println("======================取消点餐======================");
            return;
        }
        System.out.print("请输入点餐的菜品号(-1退出)：");
        int orderMenuId = Utility.readInt();
        if (orderMenuId == -1) {
            System.out.println("======================取消点餐======================");
            return;
        }
        System.out.print("请输入点餐的菜品数量(-1退出)：");
        int orderNums = Utility.readInt();
        if (orderNums == -1) {
            System.out.println("======================取消点餐======================");
            return;
        }

        // 验证餐桌是否存在
        DiningTable diningTable = diningTableService.getDiningTableById(orderDiningTableId);
        if (diningTable == null) {
            System.out.println("======================餐桌不存在======================");
            return;
        }

        // 验证菜品是否存在
        Menu menu = menuService.getMenuById(orderMenuId);
        if (menu == null) {
            System.out.println("======================菜品不存在======================");
            return;
        }

        // 点餐
        if (billService.orderMenu(orderMenuId, orderNums, orderDiningTableId)) {
            System.out.println("======================点餐成功======================");
        } else {
            System.out.println("======================点餐失败======================");
        }
    }

    // 显示账单信息
    public void listBill() {
        List<Bill> list = billService.list();
        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t状态");
        for (Bill bill : list) {
            System.out.println(bill);
        }
    }

    // 显示账单信息 + 菜品名字
    public void listBill2() {
        List<MultiTableBean> list = billService.list2();
        System.out.println("\n编号\t\t菜品号\t\t菜品名\t\t菜品量\t\t金额\t桌号\t\t\t日期\t\t\t\t\t\t状态");
        for (MultiTableBean multiTableBean : list) {
            System.out.println(multiTableBean);
        }
    }

    public void payBill() {
        System.out.println("======================结账服务======================");
        System.out.print("请选择要结账的餐桌编号(-1退出)：");
        int diningTableId = Utility.readInt();
        if (diningTableId == -1) {
            System.out.println("======================取消结账======================");
            return;
        }
        // 验证餐桌是否存在
        DiningTable diningTable = diningTableService.getDiningTableById(diningTableId);
        if (diningTable == null) {
            System.out.println("======================餐桌不存在======================");
            return;
        }
        // 验证餐桌是否有需要结账的账单
        if (!billService.existUnpaidBillByDiningTableId(diningTableId)) {
            System.out.println("======================不存在未结账账单======================");
            return;
        }
        System.out.print("结账方式(现金/支付宝/微信)(回车表示退出)：");
        String payMode = Utility.readString(20, ""); // 如果回车，返回 默认值 ""
        if (payMode.equals("")) {
            System.out.println("======================取消结账======================");
            return;
        }
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {
            if (billService.payBill(diningTableId, payMode)) {
                System.out.println("======================结账成功======================");
            } else {
                System.out.println("======================结账失败======================");
            }
        } else {
            System.out.println("======================取消结账======================");
        }
    }
}
