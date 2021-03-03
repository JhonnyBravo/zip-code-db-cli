package zip_code_db_cli.domain.service.zip_code;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java_itamae_connection.domain.service.connection_info.ConnectionInfoService;
import java_itamae_connection.domain.service.connection_info.ConnectionInfoServiceImpl;
import java_itamae_contents.domain.model.ContentsAttribute;
import zip_code_db_cli.domain.model.ZipCode;

@RunWith(Enclosed.class)
public class ZipCodeServiceTest {
    public static class テーブルが空である場合 {
        private ZipCodeService zcs;

        @Before
        public void setUp() throws Exception {
            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("src/test/resources/config.properties");

            final ConnectionInfoService cis = new ConnectionInfoServiceImpl();
            zcs = new ZipCodeServiceImpl(cis.getConnectionInfo(attr));

            final List<ZipCode> recordset = zcs.findAll();

            if (recordset.size() > 0) {
                zcs.deleteAll();
            }
        }

        @After
        public void tearDown() throws Exception {
            zcs.deleteAll();
        }

        @Test
        public void findAll実行時に空のリストが返されること() throws Exception {
            final List<ZipCode> recordset = zcs.findAll();
            assertThat(recordset.size(), is(0));
        }

        @Test
        public void create実行時にレコードを登録できること() throws Exception {
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

            final boolean status = zcs.create(contents);
            assertThat(status, is(true));

            final List<ZipCode> recordset = zcs.findAll();
            assertThat(recordset.size(), is(2));

            assertThat(recordset.get(0).getZipCode(), is("0600000"));
            assertThat(recordset.get(0).getPrefecture(), is("北海道"));
            assertThat(recordset.get(0).getCity(), is("札幌市中央区"));
            assertThat(recordset.get(0).getArea(), is("以下に掲載がない場合"));

            assertThat(recordset.get(1).getZipCode(), is("0600042"));
            assertThat(recordset.get(1).getPrefecture(), is("北海道"));
            assertThat(recordset.get(1).getCity(), is("札幌市中央区"));
            assertThat(recordset.get(1).getArea(), is("大通西（１～１９丁目）"));
        }

        public void create実行時に空のリストを渡した場合に終了ステータスfalseがであること() throws Exception {
            final List<ZipCode> contents = new ArrayList<>();

            final boolean status = zcs.create(contents);
            assertThat(status, is(false));

            final List<ZipCode> recordset = zcs.findAll();
            assertThat(recordset.size(), is(0));
        }
    }

    public static class テーブルが空ではない場合 {
        private ZipCodeService zcs;

        @Before
        public void setUp() throws Exception {
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

            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("src/test/resources/config.properties");

            final ConnectionInfoService cis = new ConnectionInfoServiceImpl();
            zcs = new ZipCodeServiceImpl(cis.getConnectionInfo(attr));

            final List<ZipCode> recordset = zcs.findAll();

            if (recordset.size() > 0) {
                zcs.deleteAll();
            }

            zcs.create(contents);
        }

        @After
        public void tearDown() throws Exception {
            zcs.deleteAll();
        }

        @Test
        public void findAll実行時にレコードがリストに変換されて返されること() throws Exception {
            final List<ZipCode> recordset = zcs.findAll();
            assertThat(recordset.size(), is(2));

            assertThat(recordset.get(0).getZipCode(), is("0600000"));
            assertThat(recordset.get(0).getPrefecture(), is("北海道"));
            assertThat(recordset.get(0).getCity(), is("札幌市中央区"));
            assertThat(recordset.get(0).getArea(), is("以下に掲載がない場合"));

            assertThat(recordset.get(1).getZipCode(), is("0600042"));
            assertThat(recordset.get(1).getPrefecture(), is("北海道"));
            assertThat(recordset.get(1).getCity(), is("札幌市中央区"));
            assertThat(recordset.get(1).getArea(), is("大通西（１～１９丁目）"));
        }

        @Test
        public void create実行時にレコードを追加できること() throws Exception {
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

            final boolean status = zcs.create(contents);
            assertThat(status, is(true));

            final List<ZipCode> recordset = zcs.findAll();
            assertThat(recordset.size(), is(3));

            assertThat(recordset.get(2).getZipCode(), is("0028071"));
            assertThat(recordset.get(2).getPrefecture(), is("北海道"));
            assertThat(recordset.get(2).getCity(), is("札幌市北区"));
            assertThat(recordset.get(2).getArea(), is("あいの里一条"));
        }

        public void deleteAll実行時にテーブルを空にできること() throws Exception {
            final boolean status = zcs.deleteAll();
            assertThat(status, is(true));

            final List<ZipCode> recordset = zcs.findAll();
            assertThat(recordset.size(), is(0));
        }
    }
}
