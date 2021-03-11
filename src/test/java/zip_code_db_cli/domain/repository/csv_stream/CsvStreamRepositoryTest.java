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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java_itamae_contents.domain.model.ContentsAttribute;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CsvStreamRepositoryTest {
    private File file;
    private ContentsAttribute attr;
    @Autowired
    private CsvStreamRepository repository;

    @Before
    public void setUp() throws Exception {
        file = new File("test.csv");
        file.createNewFile();

        attr = new ContentsAttribute();
        attr.setPath("test.csv");
    }

    @After
    public void tearDown() throws Exception {
        file.delete();
    }

    // ファイルが存在しない場合
    @Test(expected = FileNotFoundException.class)
    public void getReader実行時にFileNotFoundExceptionが送出されること() throws Exception {
        attr.setPath("NotExist.csv");

        try {
            final Reader reader = repository.getReader(attr);
        } catch (final Exception e) {
            System.err.println(e);
            throw e;
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void getWriter実行時にFileNotFoundExceptionが送出されること() throws Exception {
        attr.setPath("NotExist.csv");

        try {
            final Writer writer = repository.getWriter(attr);
        } catch (final Exception e) {
            System.err.println(e);
            throw e;
        }
    }

    // ファイルが存在する場合
    @Test
    public void getReader実行時にReaderを取得できること() throws Exception {
        try (Reader reader = repository.getReader(attr)) {
            assertThat(reader, notNullValue());
        }
    }

    @Test
    public void getWriter実行時にWriterを取得できること() throws Exception {
        try (Writer writer = repository.getWriter(attr)) {
            assertThat(writer, notNullValue());
        }
    }

}
