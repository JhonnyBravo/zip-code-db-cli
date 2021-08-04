package zip_code_db_cli.domain.service.zip_code;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java_itamae_connection.domain.service.connection_info.ConnectionInfoService;
import java_itamae_connection.domain.service.connection_info.ConnectionInfoServiceImpl;
import java_itamae_contents.domain.model.ContentsAttribute;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import zip_code_db_cli.domain.model.ZipCode;

/**
 * テーブルが空である場合のテスト
 */
public class EmptyTable {
  private ZipCodeService zcs;

  private final Supplier<List<ZipCode>> contentsSupplier = () -> {
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

    return contents;
  };

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

  /**
   * {@link ZipCodeService#findAll()} 実行時に空の {@link List} が返されること。
   */
  @Test
  public void test1() throws Exception {
    final List<ZipCode> recordset = zcs.findAll();
    assertThat(recordset.size(), is(0));
  }

  /**
   * {@link ZipCodeService#create(List)} 実行時にレコードを登録できること。
   */
  @Test
  public void test2() throws Exception {
    final List<ZipCode> expContents = contentsSupplier.get();

    final boolean status = zcs.create(expContents);
    assertThat(status, is(true));

    final List<ZipCode> actContents = zcs.findAll();
    assertThat(actContents.size(), is(2));

    assertThat(actContents.get(0).getZipCode(), is(expContents.get(0).getZipCode()));
    assertThat(actContents.get(0).getPrefecture(), is(expContents.get(0).getPrefecture()));
    assertThat(actContents.get(0).getCity(), is(expContents.get(0).getCity()));
    assertThat(actContents.get(0).getArea(), is(expContents.get(0).getArea()));

    assertThat(actContents.get(1).getZipCode(), is(expContents.get(1).getZipCode()));
    assertThat(actContents.get(1).getPrefecture(), is(expContents.get(1).getPrefecture()));
    assertThat(actContents.get(1).getCity(), is(expContents.get(1).getCity()));
    assertThat(actContents.get(1).getArea(), is(expContents.get(1).getArea()));
  }

  /**
   * {@link ZipCodeService#create(List)} 実行時に空の {@link List} を渡した場合に終了ステータスが false であること
   */
  public void test3() throws Exception {
    final List<ZipCode> contents = new ArrayList<>();

    final boolean status = zcs.create(contents);
    assertThat(status, is(false));

    final List<ZipCode> recordset = zcs.findAll();
    assertThat(recordset.size(), is(0));
  }
}
