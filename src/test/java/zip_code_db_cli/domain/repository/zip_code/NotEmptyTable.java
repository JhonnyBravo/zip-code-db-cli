package zip_code_db_cli.domain.repository.zip_code;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java_itamae.domain.component.properties.PropertiesComponent;
import java_itamae.domain.component.properties.PropertiesComponentImpl;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae_connection.domain.model.ConnectionInfo;
import java_itamae_connection.domain.model.ConnectionInfoConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import zip_code_db_cli.domain.common.GetTestContents;
import zip_code_db_cli.domain.model.ZipCode;

/** テーブルが空ではない場合のテスト */
public class NotEmptyTable {
  private PropertiesComponent component;
  private ConnectionInfoConverter converter;
  private ZipCodeRepository repository;
  private ConnectionInfo cnInfo;
  private GetTestContents getTestContents;

  @Before
  public void setUp() throws Exception {
    getTestContents = new GetTestContents();
    final List<ZipCode> contents = getTestContents.get();

    final ContentsModel model = new ContentsModel();
    model.setPath("src/test/resources/config.properties");

    component = new PropertiesComponentImpl();
    final Map<String, String> properties = component.getProperties(model);

    converter = new ConnectionInfoConverter();
    cnInfo = converter.apply(properties);

    repository = new ZipCodeRepositoryImpl();
    repository.init(cnInfo);
    final List<ZipCode> recordset = repository.findAll();

    if (recordset.size() > 0) {
      repository.deleteAll();
    }

    repository.create(contents);
  }

  @After
  public void tearDown() throws Exception {
    repository.deleteAll();
  }

  /**
   * {@link ZipCodeRepository#findAll(java.sql.Connection)} 実行時にレコードセットが {@link ZipCode} の {@link
   * List} に変換されて返されること。
   */
  @Test
  public void zcrs001() throws Exception {
    final List<ZipCode> contents = getTestContents.get();
    final List<ZipCode> recordset = repository.findAll();
    assertThat(recordset.size(), is(2));

    for (int i = 0; i < recordset.size(); i++) {
      final ZipCode expModel = contents.get(i);
      final ZipCode actModel = recordset.get(i);

      assertThat(actModel.getZipCode(), is(expModel.getZipCode()));
      assertThat(actModel.getPrefecture(), is(expModel.getPrefecture()));
      assertThat(actModel.getCity(), is(expModel.getCity()));
      assertThat(actModel.getArea(), is(expModel.getArea()));
    }
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link ZipCodeRepository#create(java.sql.Connection, List)} 実行時にレコードを追加できること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void zcrs002() throws Exception {
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

    final boolean status = repository.create(contents);
    assertThat(status, is(true));

    final List<ZipCode> recordset = repository.findAll();
    assertThat(recordset.size(), is(3));

    assertThat(recordset.get(2).getZipCode(), is("0028071"));
    assertThat(recordset.get(2).getPrefecture(), is("北海道"));
    assertThat(recordset.get(2).getCity(), is("札幌市北区"));
    assertThat(recordset.get(2).getArea(), is("あいの里一条"));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link ZipCodeRepository#deleteAll(java.sql.Connection)} 実行時にテーブルを空にできること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void zcrs003() throws Exception {
    final boolean status = repository.deleteAll();
    assertThat(status, is(true));

    final List<ZipCode> recordset = repository.findAll();
    assertThat(recordset.size(), is(0));
  }
}
