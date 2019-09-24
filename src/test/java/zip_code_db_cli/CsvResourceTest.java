package zip_code_db_cli;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class CsvResourceTest {
    public static class ファイルが存在しない場合 {
        private final CsvResource resource = new CsvResource("not_exist.csv");

        @Test(expected = FileNotFoundException.class)
        public void setContent実行時にFileNotFoundExceptionが送出されること() throws Exception {
            final ZipCode content = new ZipCode();
            content.setZipCode("0100956");
            content.setPrefecture("秋田県");
            content.setCity("秋田市");
            content.setArea("山王臨海町");

            final List<ZipCode> contents = new ArrayList<ZipCode>();
            contents.add(content);

            resource.setContent(contents);
        }

        @Test(expected = FileNotFoundException.class)
        public void getContent実行時にFileNotFoundExceptionが送出されること() throws Exception {
            resource.getContent();
        }
    }

    public static class ファイルが存在するが空である場合 {
        private final CsvResource resource = new CsvResource("test.csv");
        private final File file = new File("test.csv");

        @Before
        public void setUp() throws Exception {
            file.createNewFile();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void getContent実行時に空のリストが返されること() throws Exception {
            final List<ZipCode> contents = resource.getContent();
            assertThat(contents.size(), is(0));
        }

        @Test
        public void setContent実行時にファイルへ書込みできること() throws Exception {
            final ZipCode content = new ZipCode();
            content.setJisCode("01101");
            content.setZipCode("0600041");
            content.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
            content.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
            content.setAreaPhonetic("ｵｵﾄﾞｵﾘﾋｶﾞｼ");
            content.setPrefecture("北海道");
            content.setCity("札幌市中央区");
            content.setArea("大通東");
            content.setUpdateFlag(1);
            content.setReasonFlag(4);

            final List<ZipCode> contents = new ArrayList<ZipCode>();
            contents.add(content);

            final boolean status = resource.setContent(contents);
            assertThat(status, is(true));

            final List<ZipCode> curContents = resource.getContent();

            assertThat(curContents.size(), is(1));
            assertThat(curContents.get(0).getJisCode(), is("01101"));
            assertThat(curContents.get(0).getZipCode(), is("0600041"));
            assertThat(curContents.get(0).getPrefecturePhonetic(), is("ﾎｯｶｲﾄﾞｳ"));
            assertThat(curContents.get(0).getCityPhonetic(), is("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ"));
            assertThat(curContents.get(0).getAreaPhonetic(), is("ｵｵﾄﾞｵﾘﾋｶﾞｼ"));
            assertThat(curContents.get(0).getPrefecture(), is("北海道"));
            assertThat(curContents.get(0).getCity(), is("札幌市中央区"));
            assertThat(curContents.get(0).getArea(), is("大通東"));
            assertThat(curContents.get(0).getUpdateFlag(), is(1));
            assertThat(curContents.get(0).getReasonFlag(), is(4));
        }
    }

    public static class ファイルが存在して空ではない場合 {
        private final CsvResource resource = new CsvResource("test.csv");
        private final File file = new File("test.csv");

        @Before
        public void setUp() throws Exception {
            file.createNewFile();

            final ZipCode content1 = new ZipCode();
            content1.setJisCode("01101");
            content1.setZipCode("0600041");
            content1.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
            content1.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
            content1.setAreaPhonetic("ｵｵﾄﾞｵﾘﾋｶﾞｼ");
            content1.setPrefecture("北海道");
            content1.setCity("札幌市中央区");
            content1.setArea("大通東");
            content1.setUpdateFlag(1);
            content1.setReasonFlag(4);

            final ZipCode content2 = new ZipCode();
            content2.setJisCode("01101");
            content2.setZipCode("0600031");
            content2.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
            content2.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
            content2.setAreaPhonetic("ｷﾀ1ｼﾞｮｳﾋｶﾞｼ");
            content2.setPrefecture("北海道");
            content2.setCity("札幌市中央区");
            content2.setArea("北一条東");
            content2.setUpdateFlag(2);
            content2.setReasonFlag(6);

            final List<ZipCode> contents = new ArrayList<ZipCode>();
            contents.add(content1);
            contents.add(content2);
            resource.setContent(contents);
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void getContent実行時に全行が取得されること() throws Exception {
            final List<ZipCode> contents = resource.getContent();
            assertThat(contents.size(), is(2));

            for (final ZipCode content : contents) {
                System.out.println(content.getJisCode());
                System.out.println(content.getZipCode());
                System.out.println(content.getPrefecturePhonetic());
                System.out.println(content.getCityPhonetic());
                System.out.println(content.getAreaPhonetic());
                System.out.println(content.getPrefecture());
                System.out.println(content.getCity());
                System.out.println(content.getArea());
                System.out.println(content.getUpdateFlag());
                System.out.println(content.getReasonFlag());
            }
        }

        @Test
        public void setContent実行時にファイルの内容を上書きできること() throws Exception {
            final ZipCode content = new ZipCode();
            content.setJisCode("01101");
            content.setZipCode("0600032");
            content.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
            content.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
            content.setAreaPhonetic("ｷﾀ2ｼﾞｮｳﾋｶﾞｼ");
            content.setPrefecture("北海道");
            content.setCity("札幌市中央区");
            content.setArea("北二条東");
            content.setUpdateFlag(1);
            content.setReasonFlag(5);

            final List<ZipCode> contents = resource.getContent();
            contents.add(content);

            final boolean status = resource.setContent(contents);
            assertThat(status, is(true));

            final List<ZipCode> curContents = resource.getContent();
            assertThat(curContents.size(), is(3));
            assertThat(curContents.get(2).getJisCode(), is("01101"));
            assertThat(curContents.get(2).getZipCode(), is("0600032"));
            assertThat(curContents.get(2).getPrefecturePhonetic(), is("ﾎｯｶｲﾄﾞｳ"));
            assertThat(curContents.get(2).getCityPhonetic(), is("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ"));
            assertThat(curContents.get(2).getAreaPhonetic(), is("ｷﾀ2ｼﾞｮｳﾋｶﾞｼ"));
            assertThat(curContents.get(2).getPrefecture(), is("北海道"));
            assertThat(curContents.get(2).getCity(), is("札幌市中央区"));
            assertThat(curContents.get(2).getArea(), is("北二条東"));
        }

        @Test
        public void ファイルの内容を空にできること() throws Exception {
            final boolean status = resource.deleteContent();
            assertThat(status, is(true));

            final List<ZipCode> contents = resource.getContent();
            assertThat(contents.size(), is(0));
        }
    }
}
