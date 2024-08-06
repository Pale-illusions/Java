package domain;

import java.util.Date;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 这是一个 javabean 和 bill 表 对应
 * 	id INT PRIMARY KEY auto_increment, # 自增主键
 * 	billId VARCHAR(50) NOT NULL default '', # 账单号生成UUID
 * 	menuId int not null DEFAULT 0, # 菜品编号
 * 	nums int not null default 0, # 份数
 * 	money double not null default 0, # 金额
 * 	diningTableId int not null default 0, # 餐桌
 * 	billDate datetime not null, # 订单日期
 * 	state VARCHAR(50) not NULL default '' # 状态 ‘未结账’ ‘已经结账’ ‘挂单’ ‘支付宝’ ‘现金’
 */
public class Bill {
    private Integer id;
    private String billId;
    private Integer menuId;
    private Integer nums;
    private Double money;
    private Integer diningTableId;
    private Date billDate;
    private String state;

    @Override
    public String toString() {
        return id + "\t\t" + menuId + "\t\t\t" + nums + "\t\t\t" + money + "\t\t" + diningTableId + "\t\t" + billDate + "\t\t" + state;
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

    public Bill(Integer id, String billId, Integer menuId, Integer nums, Double money, Integer diningTableId, Date billDate, String state) {
        this.id = id;
        this.billId = billId;
        this.menuId = menuId;
        this.nums = nums;
        this.money = money;
        this.diningTableId = diningTableId;
        this.billDate = billDate;
        this.state = state;
    }

    public Bill() {
    }
}
