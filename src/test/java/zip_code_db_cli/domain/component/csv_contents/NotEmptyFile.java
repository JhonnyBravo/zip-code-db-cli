package zip_code_db_cli.domain.component.csv_contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import zip_code_db_cli.domain.common.GetTestContents;
import zip_code_db_cli.domain.model.ZipCode;

/** ファイルが空ではない場合のテスト */
public class NotEmptyFile {
  private Path path;
  private ContentsModel model;
  private GetTestContents getTestContents;
  private CsvContentsComponent component;

  @Before
  public void setUp() throws Exception {
    component = new CsvContentsComponentImpl();
    getTestContents = new GetTestContents();

    model = new ContentsModel();
    model.setPath("test.csv");

    final List<ZipCode> contents = getTestContents.get();

    path = component.convertToPath(model.getPath());
    Files.createFile(path);

    component.updateContents(model, contents);
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /**
   * {@link CsvContentsComponent#getContents(ContentsModel)} 実行時にファイルの内容が {@link ZipCode} の {@link
   * List} に変換されて返されること。
   */
  @Test
  public void ccs001() throws Exception {
    final List<ZipCode> expContents = getTestContents.get();
    final List<ZipCode> actContents = component.getContents(model);
    assertThat(actContents.size(), is(2));

    for (int i = 0; i < actContents.size(); i++) {
      final ZipCode expModel = expContents.get(i);
      final ZipCode actModel = actContents.get(i);

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
   *   <li>{@link CsvContentsComponent#updateContents(ContentsModel, List)} 実行時にファイルを上書きできること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void ccs002() throws Exception {
    final List<ZipCode> expContents = new ArrayList<>();
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

      expContents.add(zipcode);
    }

    final int status = component.updateContents(model, expContents);
    assertThat(status, is(2));

    final List<ZipCode> actContents = component.getContents(model);
    assertThat(actContents.size(), is(1));

    assertThat(actContents.get(0).getZipCode(), is(expContents.get(0).getZipCode()));
    assertThat(actContents.get(0).getPrefecture(), is(expContents.get(0).getPrefecture()));
    assertThat(actContents.get(0).getCity(), is(expContents.get(0).getCity()));
    assertThat(actContents.get(0).getArea(), is(expContents.get(0).getArea()));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link CsvContentsComponent#deleteContents(ContentsModel)} 実行時にファイルを空にできること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void ccs003() throws Exception {
    final int status = component.deleteContents(model);
    assertThat(status, is(2));

    final List<ZipCode> actContents = component.getContents(model);
    assertThat(actContents.size(), is(0));
  }
}
