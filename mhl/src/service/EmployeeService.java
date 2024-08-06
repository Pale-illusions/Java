package service;

import dao.EmployeeDAO;
import domain.Employee;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 该类通过调用 EmployeeDAO 对象 完成 对 employee 表的各种操作
 */
public class EmployeeService {
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    /**
     * 根据用户id和密码查询，密码使用 md5 加密
     * @param empId 用户id
     * @param pwd 用户密码
     * @return Employee 对象 (查询不到返回 null)
     */
    public Employee getEmployeeByIdAndPwd(String empId, String pwd) {
        return employeeDAO.singleQuery("select * from employee where empId = ? and pwd = md5(?)", Employee.class, empId, pwd);
    }


}
