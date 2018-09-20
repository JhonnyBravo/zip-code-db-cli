package zip_code_db_cli;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class GetPathListTest {

    /**
     * {@link zip_code_db_cli.GetPathList#runCommand()},
     * {@link zip_code_db_cli.GetPathList#getPathList()},
     * {@link zip_code_db_cli.GetPathList#getCode()} のためのテスト・メソッド。
     */
    @Test
    public void test1() {
        GetPathList gpl = new GetPathList("src/test/resources", "csv");
        gpl.runCommand();
        assertEquals(2, gpl.getCode());

        List<String> pathList = gpl.runCommand();
        assertEquals(1, pathList.size());

        for (String path : pathList) {
            System.out.println(path);
        }
    }

    @Test
    public void test2() {
        GetPathList gpl = new GetPathList("src/test/resources/NotExist", "csv");
        List<String> pathList = gpl.runCommand();

        assertEquals(1, gpl.getCode());
        assertEquals(null, pathList);
    }

    @Test
    public void test3() {
        GetPathList gpl = new GetPathList("src/test/resources", "txt");
        List<String> pathList = gpl.runCommand();

        assertEquals(0, gpl.getCode());
        assertEquals(0, pathList.size());
    }
}
