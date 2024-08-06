package domain;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote  这是一个 javabean 和 employee 对应
 *   id INT PRIMARY KEY auto_increment,
 *   empId VARCHAR(50) unique not null default '',
 *   pwd char(32) not null default '',
 *   name varchar(50) not null default '',
 *   job varchar(50) not null default ''
 */
public class Employee {
    private Integer id;
    private String empId;
    private String name;
    private String pwd;
    private String job;

    public Employee() {
    }

    public Employee(Integer id, String empId, String name, String pwd, String job) {
        this.id = id;
        this.empId = empId;
        this.name = name;
        this.pwd = pwd;
        this.job = job;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", empId='" + empId + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
