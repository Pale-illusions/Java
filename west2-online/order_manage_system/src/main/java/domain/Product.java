package domain;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 这是一个 javabean 和 Product 表 对应
 * create table product (
 *      id int primary key auto_increment, # 自增主键
 *      name varchar(50) not null default '', # 商品名字
 *      price double not null default 0 # 商品价格
 * ) charset = utf8;
 */
public class Product {
    private Integer id;
    private String name;
    private Double price;

    @Override
    public String toString() {
        return "\t" + id + "\t\t" + name + "\t\t\t" + price;
    }

    public Product() {
    }

    public Product(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

/*
Product表数据包括：
INSERT INTO product (id, name, price) VALUES
    (1, '苹果', 5.0),
    (2, '香蕉', 4.0),
    (3, '橙子', 6.0),
    (4, '牛奶', 12.0),
    (5, '饼干', 8.0),
    (6, '酸奶', 10.0),
    (7, '巧克力', 15.0),
    (8, '薯片', 7.0),
    (9, '面包', 10.0),
    (10, '火腿肠', 5.0),
    (11, '果汁', 6.0),
    (12, '矿泉水', 2.0),
    (13, '可乐', 3.0),
    (14, '啤酒', 8.0),
    (15, '冰淇淋', 10.0),
    (16, '雪糕', 5.0),
    (17, '酸梅汤', 12.0),
    (18, '玩具车', 20.0),
    (19, '毛绒熊', 30.0),
    (20, '积木', 25.0),
    (21, '洋娃娃', 35.0),
    (22, '滑板', 50.0),
    (23, '陀螺', 15.0),
    (24, '遥控车', 60.0),
    (25, '积木', 45.0),
    (26, '拼图', 20.0),
    (27, '手枪', 18.0),
    (28, '变形金刚', 75.0),
    (29, '火车', 40.0),
    (30, '无人机', 150.0),
    (31, '手机', 2000.0),
    (32, '电视', 3000.0),
    (33, '冰箱', 2500.0),
    (34, '洗衣机', 1800.0),
    (35, '空调', 3500.0),
    (36, '微波炉', 500.0),
    (37, '电饭锅', 400.0),
    (38, '烤箱', 800.0),
    (39, '吹风机', 150.0),
    (40, '电动牙刷', 200.0),
    (41, '电水壶', 100.0),
    (42, '耳机', 150.0),
    (43, '键盘', 300.0),
    (44, '鼠标', 100.0),
    (45, '平板', 2200.0),
    (46, '笔记本', 4500.0),
    (47, '相机', 3500.0),
    (48, '音响', 1200.0),
    (49, '手表', 1000.0),
    (50, '路由器', 200.0);

 */