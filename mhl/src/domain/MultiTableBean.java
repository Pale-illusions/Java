package domain;

import java.util.Date;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 这是一个 javabean 和 多表查询 对应
 */
public class MultiTableBean {
    private Integer id;
    private String billId;
    private Integer menuId;
    private Integer nums;
    private Double money;
    private Integer diningTableId;
    private Date billDate;
    private String state;
    // 来自 menu表 的列
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultiTableBean(Integer id, String billId, Integer menuId, Integer nums, Double money, Integer diningTableId, Date billDate, String state, String name) {
        this.id = id;
        this.billId = billId;
        this.menuId = menuId;
        this.nums = nums;
        this.money = money;
        this.diningTableId = diningTableId;
        this.billDate = billDate;
        this.state = state;
        this.name = name;
    }

    public MultiTableBean() {
    }

    @Override
    public String toString() {
        return id + "\t\t" + menuId + "\t\t\t" + name + "\t\t\t" + nums + "\t\t" + money + "\t" + diningTableId + "\t\t" + billDate + "\t\t" + state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getDiningTableId() {
        return diningTableId;
    }

    public void setDiningTableId(Integer diningTableId) {
        this.diningTableId = diningTableId;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}
