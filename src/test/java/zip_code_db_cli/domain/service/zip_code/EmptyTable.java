package zip_code_db_cli.domain.service.zip_code;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import zip_code_db_cli.domain.model.ZipCodeEntity;
import zip_code_db_cli.domain.repository.zip_code.ZipCodeRepositoryImpl;

/**
 * テーブルが空である場合のテスト
 */
public class EmptyTable {
  @Inject
  private ZipCodeService service;

  private final Supplier<List<ZipCodeEntity>> recordsetSupplier = () -> {
    final List<ZipCodeEntity> recordset = new ArrayList<>();

    {
      final ZipCodeEntity zipcode = new ZipCodeEntity();

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

      recordset.add(zipcode);
    }
    {
      final ZipCodeEntity zipcode = new ZipCodeEntity();

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

      recordset.add(zipcode);
    }

    return recordset;
  };

  @Rule
  public WeldInitiator weld = WeldInitiator
      .from(ZipCodeServiceImpl.class, ZipCodeRepositoryImpl.class).inject(this).build();

  @Before
  public void setUp() throws Exception {
    final List<ZipCodeEntity> recordset = service.findAll();

    if (recordset.size() > 0) {
      service.deleteAll();
    }
  }

  @After
  public void tearDown() throws Exception {
    service.deleteAll();
  }

  /**
   * {@link ZipCodeService#findAll()} 実行時に空の {@link List} が返されること。
   */
  @Test
  public void test1() throws Exception {
    final List<ZipCodeEntity> recordset = service.findAll();
    assertThat(recordset.size(), is(0));
  }

  /**
   * {@link ZipCodeService#create(List)} 実行時にレコードを登録できること。
   */
  @Test
  public void test2() throws Exception {
    final List<ZipCodeEntity> expRecordset = recordsetSupplier.get();

    final boolean status = service.create(expRecordset);
    assertThat(status, is(true));

    final List<ZipCodeEntity> actRecordset = service.findAll();
    assertThat(actRecordset.size(), is(2));

    assertThat(actRecordset.get(0).getZipCode(), is(expRecordset.get(0).getZipCode()));
    assertThat(actRecordset.get(0).getPrefecture(), is(expRecordset.get(0).getPrefecture()));
    assertThat(actRecordset.get(0).getCity(), is(expRecordset.get(0).getCity()));
    assertThat(actRecordset.get(0).getArea(), is(expRecordset.get(0).getArea()));

    assertThat(actRecordset.get(1).getZipCode(), is(expRecordset.get(1).getZipCode()));
    assertThat(actRecordset.get(1).getPrefecture(), is(expRecordset.get(1).getPrefecture()));
    assertThat(actRecordset.get(1).getCity(), is(expRecordset.get(1).getCity()));
    assertThat(actRecordset.get(1).getArea(), is(expRecordset.get(1).getArea()));
  }

  /**
   * {@link ZipCodeService#create(List)} 実行時に空の {@link List} を渡した場合に終了ステータスが false であること
   */
  public void test3() throws Exception {
    final List<ZipCodeEntity> newRecordset = new ArrayList<>();

    final boolean status = service.create(newRecordset);
    assertThat(status, is(false));

    final List<ZipCodeEntity> actRecordset = service.findAll();
    assertThat(actRecordset.size(), is(0));
  }
}
