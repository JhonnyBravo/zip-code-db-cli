package zip_code_db_cli;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import csv_resource.CsvAction;
import mysql_resource.ConnectionBean;

/**
 * {@link zip_code_db_cli.DbAction} の単体テスト。
 */
public class DbActionTest {
    /**
     * {@link zip_code_db_cli.DbAction#deleteRecord()},
     * {@link zip_code_db_cli.DbAction#insertRecord(java.util.List)} のためのテスト・メソッド。
    */
    @Test
    public void testDbAction() {
        //DB 設定値の取得。
        Properties p=new Properties();
        InputStream input=null;

        try {
            input=new FileInputStream("src/test/resources/connection.properties");
            p.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ConnectionBean cb=new ConnectionBean();
        cb.setDbName(p.getProperty("dbName"));
        cb.setPassword(p.getProperty("password"));
        cb.setUserName(p.getProperty("userName"));

        DbAction dba=new DbAction(cb.getConnectionString(), cb.getUserName(), cb.getPassword());
        //DB から既存のレコードを削除。
        dba.deleteRecord();

        if(dba.getCode()==1) {
            return;
        }

        assertEquals(2,dba.getCode());

        //CSV からレコードを取得。
        CsvAction ca=new CsvAction("src/test/resources/01HOKKAI.CSV");
        List<String[]> recordset=ca.getRecordset("MS932");

        if(ca.getCode()==1) {
            return;
        }

        assertEquals(2,ca.getCode());

        //DB へレコード登録
        dba.insertRecord(recordset);

        assertEquals(2,dba.getCode());
    }

}
