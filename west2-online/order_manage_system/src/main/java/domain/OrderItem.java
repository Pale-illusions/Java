package domain;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 这是一个 JavaBean 和 orderItems 表对应
 * create table orderItems (
 * 	    id int PRIMARY KEY auto_increment, # 自增主键
 * 	    orderId varchar(50), # 外键，关联订单表中的账单号
 * 	    productId int not null DEFAULT 0, # 商品ID
 * 	    quantity INT not null DEFAULT 0, # 商品数量
 * 	    totalPrice double not null default 0, # 当前商品总价(单价 * 数量)
 * 	    FOREIGN KEY (orderId) REFERENCES orders(orderId) # 创建外键
 * ) CHARSET = utf8;
 */
public class OrderItem {
    private Integer id;
    private String orderId;
    private Integer productId;
    private Integer quantity;
    private Double totalPrice;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public OrderItem() {
    }

    public OrderItem(Integer id, String orderId, Integer productId, Integer quantity, Double totalPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
