package zip_code_db_cli.domain.repository.csv_contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import jakarta.inject.Inject;
import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_contents.domain.repository.contents.ContentsRepository;
import java_itamae_contents.domain.repository.stream.StreamRepository;
import java_itamae_contents.domain.repository.stream.StreamRepositoryImpl;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import zip_code_db_cli.domain.model.ZipCode;

/**
 * CSV が空ではない場合のテスト
 */
public class NotEmptyCsv {
  @Inject
  private StreamRepository sr;
  @Inject
  private CsvContentsRepository ccr;

  private File file;
  private ContentsAttribute attr;

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

  @Rule
  public WeldInitiator weld = WeldInitiator
      .from(CsvContentsRepositoryImpl.class, StreamRepositoryImpl.class).inject(this).build();

  @Before
  public void setUp() throws Exception {
    file = new File("test.csv");
    file.createNewFile();

    attr = new ContentsAttribute();
    attr.setPath("test.csv");

    final List<ZipCode> contents = contentsSupplier.get();

    try (Writer writer = sr.getWriter(attr)) {
      ccr.updateContents(writer, contents);
    }
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /**
   * {@link CsvContentsRepository#getContents(Reader)} 実行時にファイル全行がリストに変換されて返されること。
   */
  @Test
  public void test1() throws Exception {
    final List<ZipCode> expContents = contentsSupplier.get();

    try (Reader reader = sr.getReader(attr)) {
      final List<ZipCode> actContents = ccr.getContents(reader);
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
  }

  /**
   * {@link ContentsRepository#updateContents(Writer, List)} 実行時にファイルを上書きできること。
   */
  @Test
  public void test2() throws Exception {
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

    try (Writer writer = sr.getWriter(attr)) {
      final boolean status = ccr.updateContents(writer, expContents);
      assertThat(status, is(true));
    }

    try (Reader reader = sr.getReader(attr)) {
      final List<ZipCode> actContents = ccr.getContents(reader);
      assertThat(actContents.size(), is(1));

      assertThat(actContents.get(0).getZipCode(), is(expContents.get(0).getZipCode()));
      assertThat(actContents.get(0).getPrefecture(), is(expContents.get(0).getPrefecture()));
      assertThat(actContents.get(0).getCity(), is(expContents.get(0).getCity()));
      assertThat(actContents.get(0).getArea(), is(expContents.get(0).getArea()));
    }
  }

  /**
   * {@link ContentsRepository#deleteContents(Writer)} 実行時にファイルを空にできること。
   */
  @Test
  public void test3() throws Exception {
    try (Writer writer = sr.getWriter(attr)) {
      final boolean status = ccr.deleteContents(writer);
      assertThat(status, is(true));
    }

    try (Reader reader = sr.getReader(attr)) {
      final List<ZipCode> contents = ccr.getContents(reader);
      assertThat(contents.size(), is(0));
    }
  }
}
