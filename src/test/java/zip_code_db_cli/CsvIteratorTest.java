package zip_code_db_cli;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import org.junit.Test;

public class CsvIteratorTest {
    private CsvIterator ci;

    @Test
    public final void CSVからIteratorを取得できて終了コードが2であること() {
        ci = new CsvIterator("src/test/resources/test.csv");
        ci.setEncoding("MS932");
        ci.openContext();

        Iterator<String[]> iterator = ci.iterator();
        assertThat(ci.getCode(), is(2));

        while (iterator.hasNext()) {
            String[] record = iterator.next();
            System.out.println(String.join(" ", record));
        }

        ci.closeContext();
    }
}
