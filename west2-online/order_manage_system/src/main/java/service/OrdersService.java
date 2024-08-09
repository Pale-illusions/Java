package service;

import dao.OrdersDAO;
import domain.Orders;
import utils.JDBCUtilsByDruid;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
public class OrdersService {
    private OrdersDAO ordersDAO = new OrdersDAO();

    /**
     * 添加新订单(顺序：先建父，再建子)，方法内同时调用 OrderItemService 的添加方法，保证orderItems成功添加
     * @param connection 使用的数据库连接
     * @param totalAmount 总金额
     * @param orderItems 订单的商品和对应商品数
     * @return 添加是否成功
     */
    public boolean insertOrder(Connection connection, double totalAmount, HashMap<Integer, Integer> orderItems) {
        String orderId = UUID.randomUUID().toString();
        int update = ordersDAO.update(connection, "insert into orders values(null, ?, now(), ?)", orderId, totalAmount);
        return update > 0 && new OrderItemService().insertOrderItems(connection, orderId, orderItems);
    }

    /**
     * 返回所有订单对象
     * @return 包含所有商品对象的集合
     */
    public List<Orders> list(String value, String order, int pageNo, int pageSize) {
        return ordersDAO.orderedMultipleQueryWithPagination(null, "select * from orders", Orders.class, value, order, pageNo, pageSize);
    }

    /**
     * 获得总页数(每页3个对象)
     * @return 总页数
     */
    public int getTotalPage() {
        return (int) ((long) ordersDAO.scalarQuery("select count(*) from orders") - 1) / 3 + 1;
    }

    /**
     * 返回单个订单对象
     * @param id 订单id
     * @return 单个订单对象
     */
    public Orders getOrderById(int id) {
        return getOrderById(null, id);
    }

    /**
     * 返回单个订单对象 （配合事务管理）
     * @param id 订单id
     * @return 单个订单对象
     */
    public Orders getOrderById(Connection connection, int id) {
        return ordersDAO.singleQuery(connection, "select * from orders where id = ?", Orders.class, id);
    }

    /**
     * 删除订单
     * @param connection 数据库连接
     * @param orderId 订单编号
     * @return 删除是否成功
     */
    public boolean deleteOrderByOrderId(Connection connection, String orderId) {
        return ordersDAO.update(connection, "delete from orders where orderID = ?", orderId) > 0;
    }

    // 更新订单总金额
    public boolean updateOrderByOrderId(Connection connection,String orderId, double totalAmount) {
        return ordersDAO.update(connection, "update orders set totalAmount = ? where orderId = ?", totalAmount, orderId) > 0;
    }
}
