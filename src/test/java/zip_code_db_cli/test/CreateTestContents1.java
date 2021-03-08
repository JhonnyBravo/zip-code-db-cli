package zip_code_db_cli.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import zip_code_db_cli.domain.model.ZipCodeCsvEntity;

@Service
public class CreateTestContents1 implements Supplier<List<ZipCodeCsvEntity>> {

    @Override
    public List<ZipCodeCsvEntity> get() {
        final List<ZipCodeCsvEntity> contents = new ArrayList<>();

        {
            final ZipCodeCsvEntity content = new ZipCodeCsvEntity();

            content.setJisCode("01101");
            content.setZipCode("0600000");
            content.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
            content.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
            content.setAreaPhonetic("ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱｲ");
            content.setPrefecture("北海道");
            content.setCity("札幌市中央区");
            content.setArea("以下に掲載がない場合");
            content.setUpdateFlag(0);
            content.setReasonFlag(1);

            contents.add(content);
        }

        {
            final ZipCodeCsvEntity content = new ZipCodeCsvEntity();

            content.setJisCode("01101");
            content.setZipCode("0600042");
            content.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
            content.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
            content.setAreaPhonetic("ｵｵﾄﾞｵﾘﾆｼ(1-19ﾁｮｳﾒ)");
            content.setPrefecture("北海道");
            content.setCity("札幌市中央区");
            content.setArea("大通西（１～１９丁目）");
            content.setUpdateFlag(1);
            content.setReasonFlag(0);

            contents.add(content);
        }

        return contents;
    }
}
