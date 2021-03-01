package zip_code_db_cli.domain.repository.zip_code;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import zip_code_db_cli.domain.model.ZipCode;

@RunWith(Enclosed.class)
public class ZipCodeRepositoryTest {
    public static class テーブルが空である場合 {
        private static SqlSessionFactory factory;
        private ZipCodeRepository repository;

        @BeforeClass
        public static void setUpClass() throws Exception {
            factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        }

        @Before
        public void setUp() throws Exception {
            try (SqlSession session = factory.openSession()) {
                repository = session.getMapper(ZipCodeRepository.class);
                repository.deleteAll();
                repository.resetId();
                session.commit();
            }
        }

        @After
        public void tearDown() throws Exception {
            try (SqlSession session = factory.openSession()) {
                repository = session.getMapper(ZipCodeRepository.class);
                repository.deleteAll();
                repository.resetId();
                session.commit();
            }
        }

        @Test
        public void findAll実行時に空のリストが返されること() throws Exception {
            try (SqlSession session = factory.openSession()) {
                repository = session.getMapper(ZipCodeRepository.class);
                final List<ZipCode> recordset = repository.findAll();
                assertThat(recordset.size(), is(0));
            }
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

            List<ZipCode> recordset = new ArrayList<>();

            try (SqlSession session = factory.openSession()) {
                repository = session.getMapper(ZipCodeRepository.class);

                final boolean status = repository.create(contents);
                assertThat(status, is(true));

                session.commit();

                recordset = repository.findAll();
                assertThat(recordset.size(), is(2));
            }

            assertThat(recordset.get(0).getZipCode(), is("0600000"));
            assertThat(recordset.get(0).getPrefecture(), is("北海道"));
            assertThat(recordset.get(0).getCity(), is("札幌市中央区"));
            assertThat(recordset.get(0).getArea(), is("以下に掲載がない場合"));

            assertThat(recordset.get(1).getZipCode(), is("0600042"));
            assertThat(recordset.get(1).getPrefecture(), is("北海道"));
            assertThat(recordset.get(1).getCity(), is("札幌市中央区"));
            assertThat(recordset.get(1).getArea(), is("大通西（１～１９丁目）"));
        }

        public void create実行時に空のリストを渡した場合に終了ステータスがfalseであること() throws Exception {
            final List<ZipCode> contents = new ArrayList<>();

            try (SqlSession session = factory.openSession()) {
                repository = session.getMapper(ZipCodeRepository.class);

                final boolean status = repository.create(contents);
                assertThat(status, is(false));

                session.commit();

                final List<ZipCode> recordset = repository.findAll();
                assertThat(recordset.size(), is(0));
            }
        }
    }

    public static class テーブルが空ではない場合 {
        private static SqlSessionFactory factory;
        private ZipCodeRepository repository;

        @BeforeClass
        public static void setUpClass() throws Exception {
            factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        }

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

            try (SqlSession session = factory.openSession()) {
                repository = session.getMapper(ZipCodeRepository.class);
                repository.deleteAll();
                repository.resetId();

                repository.create(contents);
                session.commit();
            }
        }

        @After
        public void tearDown() throws Exception {
            try (SqlSession session = factory.openSession()) {
                repository = session.getMapper(ZipCodeRepository.class);
                repository.deleteAll();
                repository.resetId();
                session.commit();
            }
        }

        @Test
        public void findAll実行時にレコードがリストに変換されて返されること() throws Exception {
            List<ZipCode> recordset = new ArrayList<>();

            try (SqlSession session = factory.openSession()) {
                repository = session.getMapper(ZipCodeRepository.class);
                recordset = repository.findAll();
            }

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
        public void create実行時にレコードを追加できて終了ステータスがtrueであること() throws Exception {
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

            List<ZipCode> recordset = new ArrayList<>();

            try (SqlSession session = factory.openSession()) {
                repository = session.getMapper(ZipCodeRepository.class);

                final boolean status = repository.create(contents);
                assertThat(status, is(true));

                session.commit();

                recordset = repository.findAll();
                assertThat(recordset.size(), is(3));
            }

            assertThat(recordset.get(2).getZipCode(), is("0028071"));
            assertThat(recordset.get(2).getPrefecture(), is("北海道"));
            assertThat(recordset.get(2).getCity(), is("札幌市北区"));
            assertThat(recordset.get(2).getArea(), is("あいの里一条"));
        }

        public void deleteAll実行時にテーブルを空にできて終了ステータスがtrueであること() throws Exception {
            List<ZipCode> recordset = new ArrayList<>();

            try (SqlSession session = factory.openSession()) {
                repository = session.getMapper(ZipCodeRepository.class);

                final boolean status = repository.deleteAll();
                assertThat(status, is(true));

                session.commit();

                recordset = repository.findAll();
                assertThat(recordset.size(), is(0));
            }
        }
    }
}
