package service;

import dao.MenuDAO;
import domain.Menu;

import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
public class MenuService {
    private MenuDAO menuDAO = new MenuDAO();

    // 返回所有菜品
    public List<Menu> list() {
        return menuDAO.multipleQuery("select * from menu", Menu.class);
    }

    // 根据 ID 返回一个 Menu 对象
    public Menu getMenuById(int id) {
        return menuDAO.singleQuery("select * from menu where id = ?", Menu.class, id);
    }
}
