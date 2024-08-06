package domain;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote  这是一个 javabean 和 menu 表对应
 * id INT PRIMARY KEY auto_increment,
 * 	name VARCHAR(50) not null DEFAULT(''),
 * 	type VARCHAR(50) not null DEFAULT(''),
 * 	price DOUBLE not null default 0
 */
public class Menu {
    private Integer id;
    private String name;
    private String type;
    private Double price;

    @Override
    public String toString() {
        return id + "\t\t\t" + name + "\t\t" + type + "\t\t" + price;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Menu(Integer id, String name, String type, Double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Menu() {
    }
}
