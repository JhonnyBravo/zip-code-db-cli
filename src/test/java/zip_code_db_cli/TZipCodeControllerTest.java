/**
 *
 */
package zip_code_db_cli;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

/**
 * @author sanfr
 *
 */
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
        TZipCodeController tzcc = new TZipCodeController();
        List<String[]> recordset = tzcc.importCsv("src/test/resources/test.csv");
        assertEquals(2, tzcc.getCode());
        assertEquals(5, recordset.size());

        tzcc.deleteRecord("src/test/resources/test1.properties");
        assertEquals(2, tzcc.getCode());

        tzcc.resetNo("src/test/resources/test1.properties");
        assertEquals(2,tzcc.getCode());

        tzcc.insertRecord("src/test/resources/test1.properties", recordset);
        assertEquals(2, tzcc.getCode());
    }
}
