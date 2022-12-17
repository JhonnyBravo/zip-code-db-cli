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

/** テーブルが空である場合のテスト */
public class EmptyTable {
  private PropertiesComponent component;
  private ConnectionInfoConverter converter;
  private ZipCodeRepository repository;
  private ConnectionInfo cnInfo;

  @Before
  public void setUp() throws Exception {
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
  }

  @After
  public void tearDown() throws Exception {
    repository.deleteAll();
  }

  /** {@link ZipCodeRepository#findAll(java.sql.Connection)} 実行時に空の {@link List} が返されること。 */
  @Test
  public void zcrs001() throws Exception {
    final List<ZipCode> recordset = repository.findAll();
    assertThat(recordset.size(), is(0));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link ZipCodeRepository#create(java.sql.Connection, List)} 実行時にレコードを登録できること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void zcrs002() throws Exception {
    final List<ZipCode> contents = new GetTestContents().get();

    final boolean status = repository.create(contents);
    assertThat(status, is(true));

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
   * {@link ZipCodeRepository#create(java.sql.Connection, List)} 実行時に空の {@link List} を渡した場合に終了ステータスが
   * false であること。
   */
  @Test
  public void zcrs003() throws Exception {
    final List<ZipCode> contents = new ArrayList<>();

    final boolean status = repository.create(contents);
    assertThat(status, is(false));

    final List<ZipCode> recordset = repository.findAll();
    assertThat(recordset.size(), is(0));
  }
}
