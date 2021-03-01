package zip_code_db_cli.domain.service.zip_code;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import zip_code_db_cli.domain.model.ZipCode;

@RunWith(Enclosed.class)
public class ZipCodeServiceTest {
    public static class テーブルが空である場合 {
        private ZipCodeService service;

        @Before
        public void setUp() throws Exception {
            service = new ZipCodeServiceImpl();
            service.deleteAll();
        }

        @After
        public void tearDown() throws Exception {
            service.deleteAll();
        }

        @Test
        public void findAll実行時に空のリストが返されること() throws Exception {
            final List<ZipCode> recordset = service.findAll();
            assertThat(recordset.size(), is(0));
        }

        @Test
        public void create実行時にレコードを登録できて終了ステータスがtrueであること() throws Exception {
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

            final boolean status = service.create(contents);
            assertThat(status, is(true));

            final List<ZipCode> recordset = service.findAll();
            assertThat(recordset.size(), is(2));
        }

        @Test
        public void create実行時に空のリストを引数に渡した場合に終了ステータスがfalseであること() throws Exception {
            final List<ZipCode> contents = new ArrayList<>();
            final boolean status = service.create(contents);
            assertThat(status, is(false));

            final List<ZipCode> recordset = service.findAll();
            assertThat(recordset.size(), is(0));
        }

        @Test(expected = PersistenceException.class)
        public void create実行時に例外が発生した場合にrollbackが実行されること() throws Exception {
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
                zipcode.setZipCode("06000429");
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

            try {
                service.create(contents);
            } catch (final Exception e) {
                final List<ZipCode> recordset = service.findAll();
                assertThat(recordset.size(), is(0));
                throw e;
            }
        }

        @Test
        public void deleteAll実行時に終了ステータスがfalseであること() throws Exception {
            final boolean status = service.deleteAll();
            assertThat(status, is(false));
        }
    }

    public static class テーブルが空ではない場合 {
        private ZipCodeService service;

        @Before
        public void setUp() throws Exception {
            service = new ZipCodeServiceImpl();
            service.deleteAll();

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

            service.create(contents);
        }

        @After
        public void tearDown() throws Exception {
            service.deleteAll();
        }

        @Test
        public void findAll実行時にレコードがリストに変換されて返されること() throws Exception {
            final List<ZipCode> recordset = service.findAll();
            assertThat(recordset.size(), is(2));
        }

        @Test
        public void create実行時にレコードを登録できて終了ステータスがtrueであること() throws Exception {
            final List<ZipCode> contents = new ArrayList<>();
            {
                final ZipCode zipcode = new ZipCode();

                zipcode.setJisCode("01102");
                zipcode.setZipCode("0028071");
                zipcode.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
                zipcode.setCityPhonetic("ｻｯﾎﾟﾛｼｷﾀｸ");
                zipcode.setAreaPhonetic("ｱｲﾉｻﾄ1ｼﾞｮｳ");
                zipcode.setPrefecture("北海道");
                zipcode.setCity("札幌市北区");
                zipcode.setArea("あいの里一条");
                zipcode.setUpdateFlag(0);
                zipcode.setReasonFlag(0);

                contents.add(zipcode);
            }

            final boolean status = service.create(contents);
            assertThat(status, is(true));

            final List<ZipCode> recordset = service.findAll();
            assertThat(recordset.size(), is(3));
        }

        @Test
        public void deleteAll実行時に終了ステータスがtrueであること() throws Exception {
            final boolean status = service.deleteAll();
            assertThat(status, is(true));
        }
    }
}
