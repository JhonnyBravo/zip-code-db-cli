package zip_code_db_cli;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

/**
 * {@link zip_code_db_cli.DirectoryAction} の単体テスト。
 */
public class DirectoryActionTest {
    /**
    * {@link zip_code_db_cli.DirectoryAction#getFilePathList(java.lang.String)} のためのテスト・メソッド。
    */
    @Test
    public void testGetFilePathList1() {
        DirectoryAction da = new DirectoryAction("src/test/resources");
        List<String> filePathList = da.getFilePathList("csv");

        assertEquals(1,filePathList.size());
        assertEquals(2,da.getCode());

        for (int i = 0; i < filePathList.size(); i++) {
            System.out.println(filePathList.get(i));
        }
    }

    /**
     * {@link zip_code_db_cli.DirectoryAction#setCwd(String)},
     * {@link zip_code_db_cli.DirectoryAction#getFilePathList(java.lang.String)} のためのテスト・メソッド。
     */
     @Test
     public void testGetFilePathList2() {
         DirectoryAction da = new DirectoryAction("resources");
         da.setCwd("src/test");
         List<String> filePathList = da.getFilePathList("csv");

         assertEquals(1,filePathList.size());
         assertEquals(2,da.getCode());

         for (int i = 0; i < filePathList.size(); i++) {
             System.out.println(filePathList.get(i));
         }
     }

}
