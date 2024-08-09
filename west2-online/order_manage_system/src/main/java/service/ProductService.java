package service;

import dao.ProductDAO;
import domain.Product;

import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
public class ProductService {
    private ProductDAO productDAO = new ProductDAO();

    /**
     * 返回所有商品对象
     * @return 包含所有商品的集合
     */
    public List<Product> list(String value, String order, int pageNo, int pageSize) {
        return productDAO.orderedMultipleQueryWithPagination(null, "select * from product", Product.class, value, order, pageNo, pageSize);
    }

    /**
     * 根据 ID 返回 一个商品对象
     * @param id 商品id
     * @return 商品对象
     */
    public Product getProductById(int id) {
        return productDAO.singleQuery("select * from product where id = ?", Product.class, id);
    }

    /**
     * 更新商品名字和价格
     * @param id 商品id
     * @param name 新的名字
     * @param price 新的价格
     * @return 更新是否成功
     */
    public boolean updateProductById(int id, String name, double price) {
        Product product = getProductById(id);
        return productDAO.update("update product set name = ?, price = ? where id = ?", (name.isEmpty() ? product.getName() : name), (price == -1 ? product.getPrice() : price), id) > 0;
    }

    /**
     * 添加新商品
     * @param name 新商品名字
     * @param price 新商品价格
     * @return 返回添加是否成功
     */
    public boolean insertProduct(String name, double price) {
        return productDAO.update("insert into product values(null, ?, ?)", name, price) > 0;
    }

    /**
     * 删除商品
     * @param id 商品id
     * @return 返回删除是否成功
     */
    public boolean deleteProduct(int id) {
        return productDAO.update("delete from product where id = ?", id) > 0;
    }

    /**
     * 获得总页数(每页5个对象)
     * @return 总页数
     */
    public int getTotalPage() {
        return (int) ((long) productDAO.scalarQuery("select count(*) from product") - 1) / 5 + 1;
    }
}
