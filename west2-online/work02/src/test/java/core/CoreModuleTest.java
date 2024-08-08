package core;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
class CoreModuleTest {

    @Test
    void testDisplayAllPlayersInfo() throws IOException {
        String input = "input.txt";
        String output = "output.txt";
        // 写入测试数据
        BufferedWriter bw = new BufferedWriter(new FileWriter(input));

    }

    @Test
    void testDisplayResults() {
    }

    @Test
    void testDisplayDetailedResults() {
    }
}