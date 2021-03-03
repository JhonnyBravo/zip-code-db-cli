package zip_code_db_cli.domain.repository.csv_stream;

import static org.hamcrest.CoreMatchers.notNullValue;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.Writer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java_itamae_contents.domain.model.ContentsAttribute;

@RunWith(Enclosed.class)
public class CsvStreamRepositoryTest {
    public static class ファイルが存在しない場合 {
        private CsvStreamRepository csr;
        private ContentsAttribute attr;

        @Before
        public void setUp() throws Exception {
            attr = new ContentsAttribute();
            attr.setPath("NotExist.csv");

            csr = new CsvStreamRepositoryImpl();
        }

        @Test(expected = FileNotFoundException.class)
        public void getReader実行時にFileNotFoundExceptionが送出されること() throws Exception {
            try {
                final Reader reader = csr.getReader(attr);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test(expected = FileNotFoundException.class)
        public void getWriter実行時にFileNotFoundExceptionが送出されること() throws Exception {
            try {
                final Writer writer = csr.getWriter(attr);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }
    }

    public static class ファイルが存在する場合 {
        private CsvStreamRepository csr;
        private ContentsAttribute attr;
        private File file;

        @Before
        public void setUp() throws Exception {
            file = new File("test.csv");
            file.createNewFile();

            attr = new ContentsAttribute();
            attr.setPath("test.csv");

            csr = new CsvStreamRepositoryImpl();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test()
        public void getReader実行時にReaderを取得できること() throws Exception {
            try (Reader reader = csr.getReader(attr)) {
                assertThat(reader, notNullValue());
            }
        }

        @Test()
        public void getWriter実行時にWriterを取得できること() throws Exception {
            try (Writer writer = csr.getWriter(attr)) {
                assertThat(writer, notNullValue());
            }
        }
    }
}
