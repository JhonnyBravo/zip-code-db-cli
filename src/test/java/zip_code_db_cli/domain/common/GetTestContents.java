package zip_code_db_cli.domain.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import zip_code_db_cli.domain.model.ZipCode;

public class GetTestContents implements Supplier<List<ZipCode>> {
  @Override
  public List<ZipCode> get() {
    final List<ZipCode> contents = new ArrayList<>();

    // sample1
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

    // sample2
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
  }
}
