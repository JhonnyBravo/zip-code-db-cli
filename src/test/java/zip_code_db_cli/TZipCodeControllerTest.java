package zip_code_db_cli;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TZipCodeControllerTest {
    private TZipCodeController tzcc = new TZipCodeController();

    /**
     * {@link zip_code_db_cli.TZipCodeController#importCsv(java.lang.String)},
     * {@link zip_code_db_cli.TZipCodeController#deleteRecord()},
     * {@link zip_code_db_cli.TZipCodeController#resetNo()},
     * {@link zip_code_db_cli.TZipCodeController#insertRecord(List)},
     * {@link zip_code_db_cli.TZipCodeController#getCode()} のためのテスト・メソッド。
     */
    @Test
    public void test1() {
        tzcc.init("src/test/resources/test1.properties");

        // CSV の読込ができること。
        List<String[]> recordset = tzcc.importCsv("src/test/resources/test.csv");
        assertEquals(2, tzcc.getCode());
        assertEquals(5, recordset.size());

        // レコード登録ができること。
        tzcc.openConnection();
        assertEquals(2, tzcc.getCode());

        tzcc.deleteRecord();
        tzcc.resetNo();

        tzcc.insertRecord(recordset);
        assertEquals(2, tzcc.getCode());

        // レコード削除時の終了コードが正しく設定されること。
        tzcc.deleteRecord();
        assertEquals(2, tzcc.getCode());

        // id のカウンターリセット時の終了コードが正しく設定されること。
        tzcc.resetNo();
        assertEquals(2, tzcc.getCode());

        tzcc.closeStatement();
        assertEquals(2, tzcc.getCode());

        tzcc.closeConnection();
        assertEquals(2, tzcc.getCode());
    }
}
