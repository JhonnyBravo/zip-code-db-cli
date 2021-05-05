package zip_code_db_cli.domain.service.csv_contents;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
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
import zip_code_db_cli.test.CreateTestContents1;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CsvContentsServiceTest {
    @Autowired
    private CsvContentsService service;
    @Autowired
    private CreateTestContents1 createTestContents1;

    private ContentsAttribute attr;
    private File file;

    @Before
    public void setUp() throws Exception {
        file = new File("test.csv");
        file.createNewFile();

        attr = new ContentsAttribute();
        attr.setPath("test.csv");
        service.init(attr);
    }

    @After
    public void tearDown() throws Exception {
        file.delete();
    }

    // ファイルが存在して空である場合
    @Test
    public void getContents実行時に空のリストが返されること() throws Exception {
        final List<ZipCodeCsvEntity> contents = service.getContents();
        assertThat(contents.size(), is(0));
    }

    @Test
    public void setContent実行時にファイルへ書込みできること() throws Exception {
        final List<ZipCodeCsvEntity> contents = createTestContents1.get();
        final boolean status = service.updateContent(contents.get(0));
        assertThat(status, is(true));

        final List<ZipCodeCsvEntity> curContents = service.getContents();
        assertThat(curContents.size(), is(1));

        final ZipCodeCsvEntity content1 = curContents.get(0);
        assertThat(content1.getZipCode(), is("0600000"));
        assertThat(content1.getPrefecture(), is("北海道"));
        assertThat(content1.getCity(), is("札幌市中央区"));
        assertThat(content1.getArea(), is("以下に掲載がない場合"));
    }

    @Test
    public void appendContent実行時にファイルへ書込みできること() throws Exception {
        final List<ZipCodeCsvEntity> contents = createTestContents1.get();
        final boolean status = service.updateContent(contents.get(0));
        assertThat(status, is(true));

        final List<ZipCodeCsvEntity> curContents = service.getContents();
        assertThat(curContents.size(), is(1));

        final ZipCodeCsvEntity content1 = curContents.get(0);
        assertThat(content1.getZipCode(), is("0600000"));
        assertThat(content1.getPrefecture(), is("北海道"));
        assertThat(content1.getCity(), is("札幌市中央区"));
        assertThat(content1.getArea(), is("以下に掲載がない場合"));
    }

    // ファイルが存在して空ではない場合
    @Test
    public void setContent実行時にファイルを上書きできること() throws Exception {
        final List<ZipCodeCsvEntity> contents = createTestContents1.get();
        service.updateContent(contents.get(0));

        final boolean status = service.updateContent(contents.get(1));
        assertThat(status, is(true));

        final List<ZipCodeCsvEntity> curContents = service.getContents();
        assertThat(curContents.size(), is(1));

        final ZipCodeCsvEntity content2 = curContents.get(0);
        assertThat(content2.getZipCode(), is("0600042"));
        assertThat(content2.getPrefecture(), is("北海道"));
        assertThat(content2.getCity(), is("札幌市中央区"));
        assertThat(content2.getArea(), is("大通西（１～１９丁目）"));
    }

    @Test
    public void appendContent実行時にファイルへ追記できること() throws Exception {
        final List<ZipCodeCsvEntity> contents = createTestContents1.get();
        service.updateContent(contents.get(0));

        final boolean status = service.appendContent(contents.get(1));
        assertThat(status, is(true));

        final List<ZipCodeCsvEntity> curContents = service.getContents();
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

    @Test
    public void deleteContents実行時にファイルを空にできること() throws Exception {
        final List<ZipCodeCsvEntity> contents = createTestContents1.get();
        service.updateContent(contents.get(0));

        final boolean status = service.deleteContents();
        assertThat(status, is(true));

        final List<ZipCodeCsvEntity> curContents = service.getContents();
        assertThat(curContents.size(), is(0));
    }

}
