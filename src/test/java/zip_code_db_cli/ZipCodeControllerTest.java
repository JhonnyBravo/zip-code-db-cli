package zip_code_db_cli;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import content_resource.ContentResource;
import property_resource.PropertyResource;

@RunWith(Enclosed.class)
public class ZipCodeControllerTest {
    public static class レコードが存在しない場合 {
        private ImportResource<List<ZipCode>> resource;
        private FindRecord fr;

        @Before
        public void setUp() throws Exception {
            final ContentResource<Map<String, String>> pr = new PropertyResource(
                    "src/test/resources/config.properties");
            pr.setEncoding("UTF8");
            final Map<String, String> properties = pr.getContent();

            fr = new FindRecord(properties);
            resource = new ZipCodeController(properties);
            resource.delete();
        }

        @After
        public void tearDown() throws Exception {
            resource.delete();
        }

        @Test
        public void 新規レコードの登録ができること() throws Exception {
            final ZipCode record1 = new ZipCode();
            record1.setId(1);
            record1.setJisCode("01101");
            record1.setZipCode("0600041");
            record1.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
            record1.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
            record1.setAreaPhonetic("ｵｵﾄﾞｵﾘﾋｶﾞｼ");
            record1.setPrefecture("北海道");
            record1.setCity("札幌市中央区");
            record1.setArea("大通東");
            record1.setUpdateFlag(1);
            record1.setReasonFlag(4);

            final ZipCode record2 = new ZipCode();
            record2.setId(2);
            record2.setJisCode("01101");
            record2.setZipCode("0600031");
            record2.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
            record2.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
            record2.setAreaPhonetic("ｷﾀ1ｼﾞｮｳﾋｶﾞｼ");
            record2.setPrefecture("北海道");
            record2.setCity("札幌市中央区");
            record2.setArea("北一条東");
            record2.setUpdateFlag(2);
            record2.setReasonFlag(6);

            final List<ZipCode> recordset = new ArrayList<ZipCode>();
            recordset.add(record1);
            recordset.add(record2);

            final boolean status = resource.create(recordset);
            assertThat(status, is(true));

            final List<ZipCode> curRecordset = fr.findAll();
            assertThat(curRecordset.size(), is(2));
            assertThat(curRecordset.get(1).getId(), is((long) 2));
            assertThat(curRecordset.get(1).getJisCode(), is("01101"));
            assertThat(curRecordset.get(1).getZipCode(), is("0600031"));
            assertThat(curRecordset.get(1).getPrefecturePhonetic(), is("ﾎｯｶｲﾄﾞｳ"));
            assertThat(curRecordset.get(1).getCityPhonetic(), is("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ"));
            assertThat(curRecordset.get(1).getAreaPhonetic(), is("ｷﾀ1ｼﾞｮｳﾋｶﾞｼ"));
            assertThat(curRecordset.get(1).getPrefecture(), is("北海道"));
            assertThat(curRecordset.get(1).getCity(), is("札幌市中央区"));
            assertThat(curRecordset.get(1).getArea(), is("北一条東"));
            assertThat(curRecordset.get(1).getUpdateFlag(), is(2));
            assertThat(curRecordset.get(1).getReasonFlag(), is(6));
        }
    }

    public static class レコードが存在する場合 {
        private ImportResource<List<ZipCode>> resource;
        private FindRecord fr;

        @Before
        public void setUp() throws Exception {
            final ContentResource<Map<String, String>> pr = new PropertyResource(
                    "src/test/resources/config.properties");
            pr.setEncoding("UTF8");
            final Map<String, String> properties = pr.getContent();

            fr = new FindRecord(properties);
            resource = new ZipCodeController(properties);
            resource.delete();

            final ZipCode record = new ZipCode();
            record.setJisCode("01101");
            record.setZipCode("0600041");
            record.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
            record.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
            record.setAreaPhonetic("ｵｵﾄﾞｵﾘﾋｶﾞｼ");
            record.setPrefecture("北海道");
            record.setCity("札幌市中央区");
            record.setArea("大通東");
            record.setUpdateFlag(1);
            record.setReasonFlag(4);

            final List<ZipCode> recordset = new ArrayList<ZipCode>();
            recordset.add(record);
            resource.create(recordset);
        }

        @After
        public void tearDown() throws Exception {
            resource.delete();
        }

        @Test
        public void 新規レコードの登録ができること() throws Exception {
            final ZipCode record = new ZipCode();
            record.setJisCode("01101");
            record.setZipCode("0600031");
            record.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
            record.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
            record.setAreaPhonetic("ｷﾀ1ｼﾞｮｳﾋｶﾞｼ");
            record.setPrefecture("北海道");
            record.setCity("札幌市中央区");
            record.setArea("北一条東");
            record.setUpdateFlag(2);
            record.setReasonFlag(6);

            final List<ZipCode> recordset = new ArrayList<ZipCode>();
            recordset.add(record);

            final boolean status = resource.create(recordset);
            assertThat(status, is(true));

            final List<ZipCode> curRecordset = fr.findAll();
            assertThat(curRecordset.size(), is(2));
            assertThat(curRecordset.get(1).getId(), is((long) 2));
            assertThat(curRecordset.get(1).getJisCode(), is("01101"));
            assertThat(curRecordset.get(1).getZipCode(), is("0600031"));
            assertThat(curRecordset.get(1).getPrefecturePhonetic(), is("ﾎｯｶｲﾄﾞｳ"));
            assertThat(curRecordset.get(1).getCityPhonetic(), is("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ"));
            assertThat(curRecordset.get(1).getAreaPhonetic(), is("ｷﾀ1ｼﾞｮｳﾋｶﾞｼ"));
            assertThat(curRecordset.get(1).getPrefecture(), is("北海道"));
            assertThat(curRecordset.get(1).getCity(), is("札幌市中央区"));
            assertThat(curRecordset.get(1).getArea(), is("北一条東"));
            assertThat(curRecordset.get(1).getUpdateFlag(), is(2));
            assertThat(curRecordset.get(1).getReasonFlag(), is(6));
        }

        public void レコードの削除ができること() throws Exception {
            final boolean status = resource.delete();
            assertThat(status, is(true));

            final List<ZipCode> recordset = fr.findAll();
            assertThat(recordset.size(), is(0));
        }
    }
}
