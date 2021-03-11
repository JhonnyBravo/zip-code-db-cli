package zip_code_db_cli.domain.repository.csv_contents;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java_itamae_contents.domain.model.ContentsAttribute;
import zip_code_db_cli.domain.model.ZipCodeCsvEntity;
import zip_code_db_cli.domain.repository.csv_stream.CsvStreamRepository;
import zip_code_db_cli.test.CreateTestContents1;
import zip_code_db_cli.test.CreateTestContents2;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CsvContentsRepositoryTest {
    @Autowired
    private CsvStreamRepository csr;
    @Autowired
    private CsvContentsRepository ccr;
    @Autowired
    private CreateTestContents1 createTestContents1;
    @Autowired
    private CreateTestContents2 createTestContents2;

    private ContentsAttribute attr;
    private File file;

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

    // ファイルが存在して空である場合
    @Test
    public void getContents実行時に空のリストが返されること() throws Exception {
        try (Reader reader = csr.getReader(attr)) {
            final List<ZipCodeCsvEntity> contents = ccr.getContents(reader);
            assertThat(contents.size(), is(0));
        }
    }

    @Test
    public void setContents実行時にファイルへ書込みできること() throws Exception {
        final List<ZipCodeCsvEntity> contents = createTestContents1.get();

        try (Writer writer = csr.getWriter(attr)) {
            final boolean status = ccr.setContents(writer, contents);
            assertThat(status, is(true));
        }

        try (Reader reader = csr.getReader(attr)) {
            final List<ZipCodeCsvEntity> curContents = ccr.getContents(reader);
            assertThat(curContents.size(), is(2));

            final ZipCodeCsvEntity content1 = curContents.get(0);
            assertThat(content1.getZipCode(), is("0600000"));
            assertThat(content1.getPrefecture(), is("北海道"));
            assertThat(content1.getCity(), is("札幌市中央区"));
            assertThat(content1.getArea(), is("以下に掲載がない場合"));

            final ZipCodeCsvEntity content2 = curContents.get(1);
            assertThat(content2.getZipCode(), is("0600042"));
            assertThat(content2.getPrefecture(), is("北海道"));
            assertThat(content2.getCity(), is("札幌市中央区"));
            assertThat(content2.getArea(), is("大通西（１～１９丁目）"));
        }
    }

    // ファイルが存在して空ではない場合
    @Test
    public void setContents実行時にファイルを上書きできること() throws Exception {
        final List<ZipCodeCsvEntity> contents1 = createTestContents1.get();

        try (Writer writer = csr.getWriter(attr)) {
            ccr.setContents(writer, contents1);
        }

        final List<ZipCodeCsvEntity> contents2 = createTestContents2.get();

        try (Writer writer = csr.getWriter(attr)) {
            final boolean status = ccr.setContents(writer, contents2);
            assertThat(status, is(true));
        }

        try (Reader reader = csr.getReader(attr)) {
            final List<ZipCodeCsvEntity> curContents = ccr.getContents(reader);
            assertThat(curContents.size(), is(1));

            final ZipCodeCsvEntity content = curContents.get(0);
            assertThat(content.getZipCode(), is("0600042"));
            assertThat(content.getPrefecture(), is("北海道"));
            assertThat(content.getCity(), is("札幌市中央区"));
            assertThat(content.getArea(), is("大通西（１～１９丁目）"));
        }
    }

    @Test
    public void deleteContents実行時にファイルを空にできること() throws Exception {
        final List<ZipCodeCsvEntity> contents = createTestContents1.get();

        try (Writer writer = csr.getWriter(attr)) {
            ccr.setContents(writer, contents);
        }

        try (Writer writer = csr.getWriter(attr)) {
            final boolean status = ccr.deleteContents(writer);
            assertThat(status, is(true));
        }

        try (Reader reader = csr.getReader(attr)) {
            final List<ZipCodeCsvEntity> curContents = ccr.getContents(reader);
            assertThat(curContents.size(), is(0));
        }
    }
}
