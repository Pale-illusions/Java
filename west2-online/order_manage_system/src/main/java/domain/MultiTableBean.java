package domain;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 这是一个 javabean 和 多表查询 对应
 */
public class MultiTableBean {
    private Integer productId;
    private Integer quantity;
    private Double price;
    private Double totalPrice;
    private String name;

    @Override
    public String toString() {
        return "\t" + productId + "\t\t" + name + "\t\t" + price + "\t\t\t" + quantity + "\t\t" + totalPrice;
    }

    public MultiTableBean() {
    }

    public MultiTableBean(Integer productId, Integer quantity, Double price, Double totalPrice, String name) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.name = name;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
