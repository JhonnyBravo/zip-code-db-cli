package zip_code_db_cli;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TZipCodeControllerTest {

    /**
     * {@link zip_code_db_cli.TZipCodeController#importCsv(java.lang.String)},
     * {@link zip_code_db_cli.TZipCodeController#deleteRecord(String)},
     * {@link zip_code_db_cli.TZipCodeController#resetNo(String)},
     * {@link zip_code_db_cli.TZipCodeController#insertRecord(String, List)},
     * {@link zip_code_db_cli.TZipCodeController#getCode()} のためのテスト・メソッド。
     */
    @Test
    public void test1() {
        // CSV の読込ができること。
        TZipCodeController tzcc = new TZipCodeController();
        List<String[]> recordset = tzcc.importCsv("src/test/resources/test.csv");
        assertEquals(2, tzcc.getCode());
        assertEquals(5, recordset.size());

        // レコード登録ができること。
        tzcc.deleteRecord("src/test/resources/test1.properties");
        tzcc.resetNo("src/test/resources/test1.properties");
        tzcc.insertRecord("src/test/resources/test1.properties", recordset);
        assertEquals(2, tzcc.getCode());

        // レコード削除時の終了コードが正しく設定されること。
        tzcc.deleteRecord("src/test/resources/test1.properties");
        assertEquals(2, tzcc.getCode());

        // id のカウンターリセット時の終了コードが正しく設定されること。
        tzcc.resetNo("src/test/resources/test1.properties");
        assertEquals(2, tzcc.getCode());
    }
}
