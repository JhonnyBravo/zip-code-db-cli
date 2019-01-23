package zip_code_db_cli;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import csv_resource.CsvController;

public class ZipCodeTableTest {
    private CsvController cc = new CsvController();
    private ZipCodeTable table = new ZipCodeTable();

    /**
     * {@link zip_code_db_cli.ZipCodeTable#openConnection()},
     * {@link zip_code_db_cli.ZipCodeTable#closeConnection()},
     * {@link zip_code_db_cli.ZipCodeTable#insertRecord(List)},
     * {@link zip_code_db_cli.ZipCodeTable#deleteRecord()} のためのテスト・メソッド。
     */
    @Test
    public void test1() {
        table.setPath("src/test/resources/test1.properties");
        table.openConnection();
        assertEquals(2, table.getCode());

        cc.setPath("src/test/resources/test.csv");
        cc.setEncoding("MS932");
        List<String[]> recordset = cc.getContent();

        table.insertRecord(recordset);
        assertEquals(2, table.getCode());

        table.deleteRecord();
        assertEquals(2, table.getCode());

        table.closeConnection();
    }
}
