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
public class ZipCodeControllerTest {
    @Autowired
    private ZipCodeController tzcc;

    /**
     * {@link zip_code_db_cli.ZipCodeController#importCsv(java.lang.String)},
     * {@link zip_code_db_cli.ZipCodeController#deleteRecord()},
     * {@link zip_code_db_cli.ZipCodeController#insertRecord(List)},
     * {@link zip_code_db_cli.ZipCodeController#getCode()} のためのテスト・メソッド。
     */
    @Test
    public void test1() {
        // CSV の読込ができること。
        List<String[]> recordset = tzcc.importCsv("src/test/resources/test.csv");
        assertEquals(2, tzcc.getCode());
        assertEquals(5, recordset.size());

        // レコード登録ができること。
        tzcc.deleteRecord();
        tzcc.insertRecord(recordset);
        assertEquals(2, tzcc.getCode());

        // レコード削除時の終了コードが正しく設定されること。
        tzcc.deleteRecord();
        assertEquals(2, tzcc.getCode());
    }
}
