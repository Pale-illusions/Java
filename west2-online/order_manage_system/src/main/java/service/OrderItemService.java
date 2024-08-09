package service;

import dao.MultiTableDAO;
import dao.OrderItemDAO;
import domain.MultiTableBean;
import domain.OrderItem;
import domain.Product;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
public class OrderItemService {
    private OrderItemDAO orderItemDAO = new OrderItemDAO();
    private ProductService productService = new ProductService();
    private MultiTableDAO multiTableDAO = new MultiTableDAO();

    /**
     * 添加新订单
     * @param connection mysql连接
     * @param orderId 订单编号
     * @param orderItems 订单的商品和对应商品数
     * @return 添加是否成功
     */
    public boolean insertOrderItems(Connection connection, String orderId, HashMap<Integer, Integer> orderItems) {
        StringBuilder sql = new StringBuilder("insert into orderitems (orderId, productId, quantity, totalPrice) values");
        for (Map.Entry<Integer, Integer> entry : orderItems.entrySet()) {
            int id = entry.getKey();
            int quantity = entry.getValue();
            double totalPrice = productService.getProductById(id).getPrice() * quantity;
            sql.append(String.format("(\"%s\", %d, %d, %f),", orderId, id, quantity, totalPrice));
        }
        // 删除最后一个逗号
        sql.delete(sql.length() - 1, sql.length());
        return orderItemDAO.update(connection, sql.toString()) > 0;
    }

    /**
     * 根据订单编号返回所有商品信息
     * @param orderId 订单编号
     * @return 包含所有商品信息的集合
     */
    public List<MultiTableBean> getOrderItemsByOrderId(String orderId) {
        return getOrderItemsByOrderId(null, orderId);
    }

    /**
     * 根据订单编号返回所有商品信息（配合事务管理）
     * @param orderId 订单编号
     * @return 包含所有商品信息的集合
     */
    public List<MultiTableBean> getOrderItemsByOrderId(Connection connection, String orderId) {
        return multiTableDAO.multipleQuery(connection, "select o.productId, p.name, p.price, o.quantity, o.totalPrice from orderitems o join product p on p.id = o.productId where o.orderId = ?", MultiTableBean.class, orderId);
    }

    /**
     * 根据订单编号和产品编号返回单个 OrderItem 对象
     * @param orderId 订单编号
     * @param productId 产品编号
     * @return OrderItem 对象
     */
    public OrderItem getOrderItemByOrderIdAndProductId(String orderId, int productId) {
        return getOrderItemByOrderIdAndProductId(null, orderId, productId);
    }

    /**
     * 根据订单编号和产品编号返回单个 OrderItem 对象（配合事务管理）
     * @param orderId 订单编号
     * @param productId 产品编号
     * @return OrderItem 对象
     */
    public OrderItem getOrderItemByOrderIdAndProductId(Connection connection, String orderId, int productId) {
        return orderItemDAO.singleQuery(connection, "select * from orderitems where orderId = ? and productId = ?", OrderItem.class, orderId, productId);
    }

    /**
     * 删除订单全部信息（删除顺序：先删子，再删父), 方法内调用 OrdersService 删除方法，保证订单成功删除
     * @param connection 数据库连接
     * @param orderId 订单编号
     * @return 删除是否成功
     */
    public boolean deleteOrderItemsByOrderId(Connection connection, String orderId) {
        int update = orderItemDAO.update(connection, "delete from orderitems where orderId = ?", orderId);
        return update > 0 && new OrdersService().deleteOrderByOrderId(connection, orderId);
    }

    /**
     * 新增订单商品数据到目标订单
     * @param connection 数据库连接
     * @param orderID 目标订单编号
     * @param quantity 商品数量
     * @param product 商品对象
     * @param totalAmount 总金额
     * @return 添加是否成功
     */
    public boolean insertOrderItemsByOrderId(Connection connection, String orderID, int quantity, Product product, double totalAmount) {
        return orderItemDAO.update(connection, "insert into orderitems values(null, ?, ?, ?, ?)", orderID, product.getId(), quantity, product.getPrice() * quantity) > 0
                && new OrdersService().updateOrderByOrderId(connection, orderID, totalAmount);
    }

    /**
     * 修改目标订单中的商品数据
     * @param connection 数据库连接
     * @param orderID 目标订单编号
     * @param quantity 商品数量
     * @param product 商品对象
     * @param totalAmount 总金额
     * @return 修改是否成功
     */
    public boolean updateOrderItemsByOrderId(Connection connection, String orderID, int quantity, int oldProductId, Product product, double totalAmount) {
        return orderItemDAO.update(connection, "update orderitems set productId = ?, quantity = ?, totalPrice = ? where orderId = ? and productId = ?",  product.getId(), quantity, product.getPrice() * quantity, orderID, oldProductId) > 0
                && new OrdersService().updateOrderByOrderId(connection, orderID, totalAmount);
    }

    /**
     * 删除目标订单中的商品数据
     * @param connection 数据库连接
     * @param orderId 目标订单编号
     * @param productId 商品编号
     * @param totalAmount 总金额
     * @return 删除是否成功
     */
    public boolean deleteOrderItemsByOrderId(Connection connection, String orderId, int productId, double totalAmount) {
        return orderItemDAO.update(connection, "delete from orderitems where orderId = ? and productId = ?", orderId, productId) > 0
                && new OrdersService().updateOrderByOrderId(connection, orderId, totalAmount);
    }
}
