package zip_code_db_cli.domain.repository.csv_contents;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java_itamae_contents.domain.model.ContentsAttribute;
import zip_code_db_cli.domain.model.ZipCode;
import zip_code_db_cli.domain.repository.csv_stream.CsvStreamRepository;
import zip_code_db_cli.domain.repository.csv_stream.CsvStreamRepositoryImpl;

@RunWith(Enclosed.class)
public class CsvContentsRepositoryTest {
    public static class ファイルが存在して空である場合 {
        private File file;
        private ContentsAttribute attr;
        private CsvStreamRepository csr;
        private CsvContentsRepository ccr;

        @Before
        public void setUp() throws Exception {
            file = new File("test.csv");
            file.createNewFile();

            attr = new ContentsAttribute();
            attr.setPath("test.csv");

            csr = new CsvStreamRepositoryImpl();
            ccr = new CsvContentsRepositoryImpl();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void getContents実行時に空のリストが返されること() throws Exception {
            try (Reader reader = csr.getReader(attr)) {
                final List<ZipCode> contents = ccr.getContents(reader);
                assertThat(contents.size(), is(0));
            }
        }

        @Test
        public void setContents実行時にファイルを上書きできること() throws Exception {
            final List<ZipCode> contents = new ArrayList<>();
            {
                final ZipCode zipcode = new ZipCode();

                zipcode.setJisCode("01101");
                zipcode.setZipCode("0600000");
                zipcode.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
                zipcode.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
                zipcode.setAreaPhonetic("ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱｲ");
                zipcode.setPrefecture("北海道");
                zipcode.setCity("札幌市中央区");
                zipcode.setArea("以下に掲載がない場合");
                zipcode.setUpdateFlag(0);
                zipcode.setReasonFlag(1);

                contents.add(zipcode);
            }
            {
                final ZipCode zipcode = new ZipCode();

                zipcode.setJisCode("01101");
                zipcode.setZipCode("0600042");
                zipcode.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
                zipcode.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
                zipcode.setAreaPhonetic("ｵｵﾄﾞｵﾘﾆｼ(1-19ﾁｮｳﾒ)");
                zipcode.setPrefecture("北海道");
                zipcode.setCity("札幌市中央区");
                zipcode.setArea("大通西（１～１９丁目）");
                zipcode.setUpdateFlag(1);
                zipcode.setReasonFlag(0);

                contents.add(zipcode);
            }

            try (Writer writer = csr.getWriter(attr)) {
                final boolean status = ccr.setContents(writer, contents);
                assertThat(status, is(true));
            }

            try (Reader reader = csr.getReader(attr)) {
                final List<ZipCode> curContents = ccr.getContents(reader);
                assertThat(curContents.size(), is(2));

                assertThat(curContents.get(0).getZipCode(), is("0600000"));
                assertThat(curContents.get(0).getPrefecture(), is("北海道"));
                assertThat(curContents.get(0).getCity(), is("札幌市中央区"));
                assertThat(curContents.get(0).getArea(), is("以下に掲載がない場合"));

                assertThat(curContents.get(1).getZipCode(), is("0600042"));
                assertThat(curContents.get(1).getPrefecture(), is("北海道"));
                assertThat(curContents.get(1).getCity(), is("札幌市中央区"));
                assertThat(curContents.get(1).getArea(), is("大通西（１～１９丁目）"));
            }
        }
    }

    public static class ファイルが存在して空ではない場合 {
        private File file;
        private ContentsAttribute attr;
        private CsvStreamRepository csr;
        private CsvContentsRepository ccr;

        @Before
        public void setUp() throws Exception {
            file = new File("test.csv");
            file.createNewFile();

            final List<ZipCode> contents = new ArrayList<>();

            {
                final ZipCode zipcode = new ZipCode();

                zipcode.setJisCode("01101");
                zipcode.setZipCode("0600000");
                zipcode.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
                zipcode.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
                zipcode.setAreaPhonetic("ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱｲ");
                zipcode.setPrefecture("北海道");
                zipcode.setCity("札幌市中央区");
                zipcode.setArea("以下に掲載がない場合");
                zipcode.setUpdateFlag(0);
                zipcode.setReasonFlag(1);

                contents.add(zipcode);
            }
            {
                final ZipCode zipcode = new ZipCode();

                zipcode.setJisCode("01101");
                zipcode.setZipCode("0600042");
                zipcode.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
                zipcode.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
                zipcode.setAreaPhonetic("ｵｵﾄﾞｵﾘﾆｼ(1-19ﾁｮｳﾒ)");
                zipcode.setPrefecture("北海道");
                zipcode.setCity("札幌市中央区");
                zipcode.setArea("大通西（１～１９丁目）");
                zipcode.setUpdateFlag(1);
                zipcode.setReasonFlag(0);

                contents.add(zipcode);
            }

            attr = new ContentsAttribute();
            attr.setPath("test.csv");

            csr = new CsvStreamRepositoryImpl();
            ccr = new CsvContentsRepositoryImpl();

            try (Writer writer = csr.getWriter(attr)) {
                ccr.setContents(writer, contents);
            }
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void getContents実行時にファイル全行がリストに変換されて返されること() throws Exception {
            try (Reader reader = csr.getReader(attr)) {
                final List<ZipCode> contents = ccr.getContents(reader);
                assertThat(contents.size(), is(2));

                assertThat(contents.get(0).getZipCode(), is("0600000"));
                assertThat(contents.get(0).getPrefecture(), is("北海道"));
                assertThat(contents.get(0).getCity(), is("札幌市中央区"));
                assertThat(contents.get(0).getArea(), is("以下に掲載がない場合"));

                assertThat(contents.get(1).getZipCode(), is("0600042"));
                assertThat(contents.get(1).getPrefecture(), is("北海道"));
                assertThat(contents.get(1).getCity(), is("札幌市中央区"));
                assertThat(contents.get(1).getArea(), is("大通西（１～１９丁目）"));
            }
        }

        @Test
        public void setContents実行時にファイルを上書きできること() throws Exception {
            final List<ZipCode> contents = new ArrayList<>();
            {
                final ZipCode zipcode = new ZipCode();

                zipcode.setJisCode("01101");
                zipcode.setZipCode("0600042");
                zipcode.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
                zipcode.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
                zipcode.setAreaPhonetic("ｵｵﾄﾞｵﾘﾆｼ(1-19ﾁｮｳﾒ)");
                zipcode.setPrefecture("北海道");
                zipcode.setCity("札幌市中央区");
                zipcode.setArea("大通西（１～１９丁目）");
                zipcode.setUpdateFlag(1);
                zipcode.setReasonFlag(0);

                contents.add(zipcode);
            }

            try (Writer writer = csr.getWriter(attr)) {
                final boolean status = ccr.setContents(writer, contents);
                assertThat(status, is(true));
            }

            try (Reader reader = csr.getReader(attr)) {
                final List<ZipCode> curContents = ccr.getContents(reader);
                assertThat(curContents.size(), is(1));

                assertThat(curContents.get(0).getZipCode(), is("0600042"));
                assertThat(curContents.get(0).getPrefecture(), is("北海道"));
                assertThat(curContents.get(0).getCity(), is("札幌市中央区"));
                assertThat(curContents.get(0).getArea(), is("大通西（１～１９丁目）"));
            }
        }

        public void deleteContents実行時にファイルを空にできること() throws Exception {
            try (Writer writer = csr.getWriter(attr)) {
                final boolean status = ccr.deleteContents(writer);
                assertThat(status, is(true));
            }

            try (Reader reader = csr.getReader(attr)) {
                final List<ZipCode> contents = ccr.getContents(reader);
                assertThat(contents.size(), is(0));
            }
        }
    }
}
