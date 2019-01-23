package zip_code_db_cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

public class DirectoryControllerTest {
    private DirectoryController dc = new DirectoryController();

    /**
     * {@link zip_code_db_cli.DirectoryController#getPathList()},
     * {@link zip_code_db_cli.DirectoryController#getCode()} のためのテスト・メソッド。
     */
    @Test
    public void test1() {
        dc.setPath("src/test/resources");
        List<String> pathList = dc.getPathList();
        assertEquals(2, dc.getCode());
        assertEquals(1, pathList.size());

        for (String path : pathList) {
            System.out.println(path);
        }
    }

    @Test
    public void test2() {
        dc.setPath("src/test/resources/NotExist");
        List<String> pathList = dc.getPathList();

        assertEquals(1, dc.getCode());
        assertNull(pathList);
    }
}
