package domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 这是一个 JavaBean 和 orders 表对应
 * create table orders (
 * 	    id int PRIMARY KEY auto_increment, # 自增主键
 * 	    orderId VARCHAR(50) UNIQUE, # 账单号生成UUID
 * 	    orderDate DATETIME not null, # 订单日期
 * 	    totalAmount DOUBLE not null default 0 # 总金额
 * ) charset = utf8;
 */
public class Orders {
    private Integer id;
    private String orderId;
    private Date orderDate;
    private Double totalAmount;

    @Override
    public String toString() {
        return "\t" + id + "\t\t" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(orderDate) + "\t\t" + totalAmount;
    }

    public Orders() {
    }

    public Orders(Integer id, String orderId, Date orderDate, Double totalAmount) {
        this.id = id;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
