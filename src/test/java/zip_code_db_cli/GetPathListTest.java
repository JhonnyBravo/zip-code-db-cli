package zip_code_db_cli;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetPathListTest {
    @Autowired
    private GetPathList gpl;

    /**
     * {@link zip_code_db_cli.GetPathList#runCommand()},
     * {@link zip_code_db_cli.GetPathList#getCode()} のためのテスト・メソッド。
     */
    @Test
    public void test1() {
        gpl.init("src/test/resources", "csv");
        List<String> pathList = gpl.runCommand();
        assertEquals(2, gpl.getCode());
        assertEquals(1, pathList.size());

        for (String path : pathList) {
            System.out.println(path);
        }
    }

    @Test
    public void test2() {
        gpl.init("src/test/resources/NotExist", "csv");
        List<String> pathList = gpl.runCommand();

        assertEquals(1, gpl.getCode());
        assertEquals(null, pathList);
    }

    @Test
    public void test3() {
        gpl.init("src/test/resources", "txt");
        List<String> pathList = gpl.runCommand();

        assertEquals(0, gpl.getCode());
        assertEquals(0, pathList.size());
    }
}
