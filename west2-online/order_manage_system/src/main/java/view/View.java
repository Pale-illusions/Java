package view;

import domain.MultiTableBean;
import domain.OrderItem;
import domain.Orders;
import domain.Product;
import service.OrderItemService;
import service.OrdersService;
import service.ProductService;
import utils.JDBCUtilsByDruid;
import utils.Utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.*;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
public class View {
    public static void main(String[] args) {
        new View().mainMenu();
    }

    // 控制循环
    private boolean loop = true;
    // 接收用户输入
    private String key = "";
    // 定义 ProductService 对象
    private ProductService productService = new ProductService();
    // 定义 OrdersService 对象
    private OrdersService ordersService = new OrdersService();
    // 定义 OrderItemService 对象
    private OrderItemService orderItemService = new OrderItemService();

    public void mainMenu() {
        while (loop) {
            System.out.println("\n========================订单管理系统========================");
            System.out.println("\t\t 1 增加/修改/删除商品");
            System.out.println("\t\t 2 查询所有商品");
            System.out.println("\t\t 3 查询单个商品");
            System.out.println("\t\t 4 增加/修改/删除订单");
            System.out.println("\t\t 5 查询所有订单");
            System.out.println("\t\t 6 查询单个订单");
            System.out.println("\t\t 9 退出");
            System.out.print("请输入你的选择：");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    updateProducts();
                    break;
                case "2":
                    listProducts();
                    break;
                case "3":
                    listSingleProduct();
                    break;
                case "4":
                    updateOrders();
                    break;
                case "5":
                    listOrders();
                    break;
                case "6":
                    listSingleOrder();
                    break;
                case "9":
                    loop = false;
                    System.out.println("退出");
                    break;
                default:
                    System.out.println("输入有误，请重新输入！");
            }
        }
    }

    // 显示所有商品信息
    public void listProducts() {
        System.out.print("请输入排序对象(price:价格//enter:不排序)：");
        String value = Utility.readString(50, "");
        if (!(value.equals("price") || value.isEmpty())) {
            System.out.println("错误输入！");
            return;
        }

        System.out.print("请输入排序方式(asc:升序/desc:降序/enter:不排序)：");
        String order = Utility.readString(50, "");
        if (!(order.equals("asc") || order.equals("desc") || order.isEmpty())) {
            System.out.println("错误输入！");
            return;
        }
        // 分页查询
        int totalpage = productService.getTotalPage();
        for (int i = 1; i <= totalpage;) {
            List<Product> list = productService.list(value, order, i, 5);
            System.out.println("\n========================商品信息========================");
            System.out.println("商品编号\t\t商品名\t\t价格");
            for (Product product : list) {
                System.out.println(product);
            }
            System.out.println("第" + i + "页，共" + totalpage + "页");
            System.out.print("输入希望跳转的页数(-1:退出/enter:下一页)：");
            int nextPage = Utility.readInt(114514);
            if (nextPage == -1) break;
            if (nextPage == 114514) {
                i++;
                continue;
            }
            i = nextPage;
        }
        System.out.println("========================显示完毕========================");
    }

    // 查询单个商品信息
    public void listSingleProduct() {
        System.out.print("请输入你要查询的商品ID(-1退出)：");
        int id = Utility.readInt();
        if (id == -1) return;
        Product product = productService.getProductById(id);
        if (product == null) {
            System.out.println("该商品不存在！");
            return;
        }
        System.out.println("\n========================商品信息========================");
        System.out.println("商品编号\t\t商品名\t\t价格");
        System.out.println(product);
        System.out.println("========================显示完毕========================");
    }

    // 增加商品
    public void insertProducts() {
        System.out.print("请输入新的商品名字：");
        String name = Utility.readString(50);
        System.out.print("请输入新的商品价格：");
        double price = Utility.readDouble();
        if (name.isEmpty() || price <= 0) { // 判断商品名和价格是否合法
            System.out.println("非法商品名或价格！");
            return;
        }
        System.out.println(productService.insertProduct(name, price) ? "添加成功" : "添加失败");
    }

    // 更新商品信息
    public void modifyProducts() {
        System.out.print("请输入你要修改的商品ID(-1退出)：");
        int id = Utility.readInt();
        if (id == -1) return;
        if (productService.getProductById(id) == null) { // 判断商品是否存在
            System.out.println("该商品不存在！");
            return;
        }
        System.out.print("请输入新的商品名字(回车:不修改)：");
        String name = Utility.readString(50, "");
        System.out.print("请输入新的商品价格(回车:不修改)：");
        double price = Utility.readDouble(-1);
        System.out.println(productService.updateProductById(id, name, price) ? "修改成功！" : "修改失败！");
    }

    // 删除商品
    public void deleteProducts() {
        System.out.print("请输入你要删除的商品ID(-1退出)：");
        int id = Utility.readInt();
        if (id == -1) return;
        if (productService.getProductById(id) == null) { // 判断商品是否存在
            System.out.println("该商品不存在！");
            return;
        }
        char key = Utility.readConfirmSelection();
        if (key == 'N') return;
        System.out.println(productService.deleteProduct(id) ? "删除成功" : "删除失败");
    }

    // 增加/修改/删除商品
    public void updateProducts() {
        System.out.print("请输入你要进行的操作(1:增加/2:修改/3:删除/-1:退出)：");
        int choice = Utility.readInt();
        switch (choice) {
            case 1: // 增加
                insertProducts();
                break;
            case 2: // 修改
                modifyProducts();
                break;
            case 3: // 删除
                deleteProducts();
                break;
            case -1:
                return;
            default:
                System.out.println("错误输入！");
        }
    }

    // 增加订单中的商品信息
    // 如果存在，在原有基础上增加数量
    public void insertOrders(Connection connection) {
        // 记录该订单下的商品，key 为 商品ID，Value 为 商品数量
        HashMap<Integer, Integer> orderItems = new HashMap<>();
        double totalAmount = 0;
        while (true) {
            System.out.print("\n请输入你想购买的商品ID(-1退出)：");
            int id = Utility.readInt();
            if (id == -1) break; // -1退出
            // 验证商品是否存在
            Product product = productService.getProductById(id);
            if (product == null) {
                System.out.println("该商品不存在！");
                continue;
            }
            System.out.print("请输入你想购买的商品数量(-1退出)：");
            int quantity = Utility.readInt();
            if (quantity == -1) break; // -1 退出
            // 验证数量是否合法
            if (quantity <= 0) {
                System.out.println("非法的商品数量！");
                continue;
            }
            // 如果存在
            totalAmount += product.getPrice() * quantity;
            orderItems.put(id, orderItems.getOrDefault(id, 0) + quantity);
        }
        if (orderItems.isEmpty()) {
            System.out.println("订单为空，添加失败！");
            return;
        }
        System.out.println(ordersService.insertOrder(connection, totalAmount, orderItems) ? "添加成功！" : "添加失败！");
    }

    // 修改订单商品信息
    public void modifyOrders(Connection connection) throws SQLException {
        System.out.print("请输入你要修改的订单ID(-1退出)：");
        int id = Utility.readInt();
        if (id == -1) return;
        Orders order = ordersService.getOrderById(id);
        // 验证订单存在性
        if (order == null) {
            System.out.println("订单不存在！");
            return;
        }
        String orderId = order.getOrderId();
        // 设置保存点
        Savepoint[] savepoints = new Savepoint[1000];
        int size = 0;

        a:
        while (true) {
            System.out.println("\n你想要修改的订单信息如下：");
            System.out.println("\n========================订单基本信息========================");
            System.out.println(" 订单编号\t\t订单日期\t\t\t\t总金额");
            System.out.println(ordersService.getOrderById(connection, id));
            System.out.println("\n========================订单商品信息========================");
            System.out.println("商品编号\t\t商品名\t\t价格\t\t\t数量\t\t总价格");
            List<MultiTableBean> orderItemsList = orderItemService.getOrderItemsByOrderId(connection, orderId);
            for (MultiTableBean orderItem : orderItemsList) {
                System.out.println(orderItem);
            }
            System.out.println("========================显示完毕========================");
            // 获得当前订单总金额
            double totalAmount = ordersService.getOrderById(connection, id).getTotalAmount();
            // 新建保存点
            savepoints[++size] = JDBCUtilsByDruid.createSavepoint(connection);

            System.out.print("请输入你要进行的操作(1:增加/2:修改/3:删除/4:回滚/-1:退出)：");
            int operation = Utility.readInt();

            // 基本变量
            Product product;
            int quantity;
            OrderItem item;
            int productId;
            b:
            switch (operation) {
                case 1: // 增加
                    System.out.print("请输入新增商品的编号：");
                    productId = Utility.readInt();
                    // 验证商品是否存在 product 表中
                    product = productService.getProductById(productId);
                    if (product == null) {
                        System.out.println("该商品不存在！");
                        continue a;
                    }
                    System.out.print("请输入新增商品的数量：");
                    quantity = Utility.readInt();
                    if (quantity <= 0) { // 判断商品名和价格是否合法
                        System.out.println("非法的商品数量！");
                        continue a;
                    }
                    totalAmount += product.getPrice() * quantity;
                    // 判断商品是否存在订单中
                    item = orderItemService.getOrderItemByOrderIdAndProductId(connection, orderId, productId);
                    if (item == null) { // 如果要添加的商品不存在订单中 insert
                        System.out.println(orderItemService.insertOrderItemsByOrderId(connection, orderId, quantity, product, totalAmount) ? "添加成功" : "添加失败");
                    } else { // 如果存在订单中 update
                        System.out.println(orderItemService.updateOrderItemsByOrderId(connection, orderId, item.getQuantity() + quantity, productId, product, totalAmount) ? "添加成功" : "添加失败");
                    }
                    break b;
                case 2: // 修改
                    System.out.print("请输入你要修改的商品ID：");
                    productId = Utility.readInt();
                    item = orderItemService.getOrderItemByOrderIdAndProductId(connection, orderId, productId);
                    if (item == null) { // 判断商品是否存在该订单中
                        System.out.println("该商品在订单中不存在！");
                        continue a;
                    }
                    System.out.print("请输入新的商品ID(回车或0:不修改)：");
                    productId = Utility.readInt(0);
                    if (productId != 0) {
                        product = productService.getProductById(productId);
                    } else {
                        product = productService.getProductById(item.getProductId());
                    }
                    System.out.print("请输入新的商品数量(回车或0:不修改)：");
                    quantity = Utility.readInt(0);
                    if (quantity < 0) {
                        System.out.println("商品数量不合法！");
                        continue a;
                    }
                    if (quantity == 0) {
                        quantity = item.getQuantity();
                    }
                    totalAmount += product.getPrice() * quantity - item.getTotalPrice();
                    System.out.println(orderItemService.updateOrderItemsByOrderId(connection, orderId, quantity, item.getProductId(), product, totalAmount) ? "修改成功！" : "修改失败！");
                    break b;
                case 3: // 删除
                    System.out.print("请输入你要删除的商品ID：");
                    productId = Utility.readInt();
                    item = orderItemService.getOrderItemByOrderIdAndProductId(connection, orderId, productId);
                    if (item == null) { // 判断商品是否存在该订单中
                        System.out.println("该商品在订单中不存在！");
                        continue a;
                    }
                    char key = Utility.readConfirmSelection();
                    if (key == 'N') continue a;
                    totalAmount -= item.getTotalPrice();
                    System.out.println(orderItemService.deleteOrderItemsByOrderId(connection, orderId, productId, totalAmount) ? "删除成功" : "删除失败");
                    break b;
                case 4: // 回滚
                    System.out.println("你有"+size+"个保存点(未修改为第1个保存点)");
                    System.out.print("请输入你要回滚到的保存点编号(-1退出)：");
                    int target = Utility.readInt();
                    if (target == -1) continue a;
                    if (target <= 0 || target > size) {
                        System.out.println("保存点编号不在范围内！");
                        continue a;
                    }
                    JDBCUtilsByDruid.rollbackToSavepoint(connection, savepoints[target]);
                    size = target - 1;
                    break b;
                case -1:
                    break a;
                default:
                    System.out.println("错误输入！");
            }
        }
    }

    // 删除订单商品信息
    public void deleteOrders(Connection connection) {
        // 删除顺序：先删子，再删父
        System.out.print("请输入你要删除的订单ID(-1退出)：");
        int id = Utility.readInt();
        if (id == -1) return;
        Orders order = ordersService.getOrderById(id);
        // 验证订单存在性
        if (order == null) {
            System.out.println("订单不存在！");
            return;
        }
        String orderId = order.getOrderId();
        System.out.println(orderItemService.deleteOrderItemsByOrderId(connection, orderId) ? "删除成功！" : "删除失败！");
    }

    // 增加/修改/删除订单信息
    public void updateOrders() {
        System.out.print("请输入你要进行的操作(1:增加/2:修改/3:删除/-1:退出)：");
        int choice = Utility.readInt();
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            JDBCUtilsByDruid.beginTransaction(connection); // 开启事务
            switch (choice) {
                case 1: // 增加
                    insertOrders(connection);
                    break;
                case 2: // 修改
                    modifyOrders(connection);
                    break;
                case 3: // 删除
                    deleteOrders(connection);
                    break;
                case -1:
                    return;
                default:
                    System.out.println("错误输入！");
            }
            // 提交事务
            JDBCUtilsByDruid.commitTransaction(connection);
        } catch (SQLException e) {
            // 事务回滚
            if (connection != null) {
                try {
                    JDBCUtilsByDruid.rollbackTransaction(connection);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
         throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                JDBCUtilsByDruid.close(null, null, connection);
            }
        }
    }

    // 显示所有订单基本信息
    public void listOrders() {
        System.out.print("请输入排序对象(price:价格/time:时间/enter:不排序)：");
        String value = Utility.readString(50, "");
        if (!(value.equals("price") || value.equals("time") || value.isEmpty())) {
            System.out.println("错误输入！");
            return;
        }
        // 映射到对应排序对象
        if (value.equals("price")) value = "totalAmount";
        if (value.equals("time")) value = "orderDate";

        System.out.print("请输入排序方式(asc:升序/desc:降序/enter:不排序)：");
        String order = Utility.readString(50, "");
        if (!(order.equals("asc") || order.equals("desc") || order.isEmpty())) {
            System.out.println("错误输入！");
            return;
        }
        // 分页查询
        int totalpage = ordersService.getTotalPage();
        for (int i = 1; i <= totalpage; ) {
            List<Orders> list = ordersService.list(value, order, i, 3);
            System.out.println("\n========================订单基本信息========================");
            System.out.println(" 订单编号\t\t订单日期\t\t\t\t总金额");
            for (Orders orders : list) {
                System.out.println(orders);
            }
            System.out.println("第" + i + "页，共" + totalpage + "页");
            System.out.print("输入希望跳转的页数(-1:退出/enter:下一页)：");
            int nextPage = Utility.readInt(114514);
            if (nextPage == -1) break;
            if (nextPage == 114514) {
                i++;
                continue;
            }
            i = nextPage;
        }
        System.out.println("========================显示完毕========================");
    }

    // 查询单个订单详细信息
    public void listSingleOrder() {
        System.out.print("请输入你要查询的订单ID(-1退出)：");
        int id = Utility.readInt();
        if (id == -1) return;
        Orders order = ordersService.getOrderById(id);
        if (order == null) {
            System.out.println("订单不存在！");
            return;
        }
        System.out.println("\n========================订单基本信息========================");
        System.out.println(" 订单编号\t\t订单日期\t\t\t\t总金额");
        System.out.println(order);
        System.out.println("\n========================订单商品信息========================");
        System.out.println("商品编号\t\t商品名\t\t价格\t\t\t数量\t\t总价格");
        List<MultiTableBean> orderItems = orderItemService.getOrderItemsByOrderId(order.getOrderId());
        for (MultiTableBean orderItem : orderItems) {
            System.out.println(orderItem);
        }
        System.out.println("========================显示完毕========================");
    }
}
