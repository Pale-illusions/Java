package DWA_search;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
class DWASearchTest {
    @Test
    public void testMain() throws IOException {
        long start = System.currentTimeMillis();
        DWASearch.main(new String[]{"input.txt", "output.txt"});
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + " ms");
    }
    @Test
    public void test1()throws IOException{
        //错误的参数输入
        DWASearch.main(new String[]{"input.txt"});
    }
    @Test
    public void test1_1()throws IOException{
        //错误的参数输入
        DWASearch.main(new String[]{"input"});
    }
    @Test
    public void test2()throws IOException{
        //正确的参数个数输入
        DWASearch.main(new String[]{"input.txt","output.txt"});
    }
    @Test
    public void test2_1()throws IOException{
        //正确的参数个数输入
        DWASearch.main(new String[]{"input.txt","put.txt"});
    }
    @Test
    public void test2_2()throws IOException{
        //错误的参数输入,结果可以输出put文件，可正常写入，且可以用记事本查看
        DWASearch.main(new String[]{"input.txt","put"});
    }
    @Test
    public void test2_3()throws IOException{
        //错误的参数输入结果可以输出put.p文件，可正常写入，且可以用记事本查看
        DWASearch.main(new String[]{"input.txt","put.p"});
    }
    @Test
    public void test3()throws IOException{
        //不正确的参数输入，覆盖率较高，程序运行较为成功
        DWASearch.main(new String[]{"input.txt","output.txt","others.txt"});
    }
    @Test
    public void test3_1() throws IOException{
        //不正确的参数输入，覆盖率较高
        DWASearch.main(new String[]{"input.txt","output","others.txt"});
    }

}