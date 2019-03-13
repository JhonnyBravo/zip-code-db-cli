package zip_code_db_cli;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class DBControllerTest {
    DBController dbc;

    @Before
    public void setUp() throws Exception {
        dbc = new DBController("src/test/resources/test1.properties");
    }

    /**
     * CSV から DB への一括登録ができて終了コードが2であること
     */
    @Test
    public final void test1() {
        dbc.deleteAll();

        CsvIterator ci = new CsvIterator("src/test/resources/test.csv");
        ci.setEncoding("MS932");
        ci.openContext();
        Iterator<String[]> iterator = ci.iterator();

        dbc.insertAll(iterator);
        assertThat(dbc.getCode(), is(2));

        ci.closeContext();
    }

    /**
     * レコードの全件削除ができて終了コードが2であること
     */
    @Test
    public final void test2() {
        dbc.deleteAll();
        assertThat(dbc.getCode(), is(2));
    }
}
