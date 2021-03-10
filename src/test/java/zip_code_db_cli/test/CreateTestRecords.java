package zip_code_db_cli.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import zip_code_db_cli.domain.model.ZipCodeEntity;

@Service
public class CreateTestRecords implements Supplier<List<ZipCodeEntity>> {

    @Override
    public List<ZipCodeEntity> get() {
        final List<ZipCodeEntity> recordset = new ArrayList<>();

        {
            final ZipCodeEntity record = new ZipCodeEntity();

            record.setJisCode("01101");
            record.setZipCode("0600000");
            record.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
            record.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
            record.setAreaPhonetic("ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱｲ");
            record.setPrefecture("北海道");
            record.setCity("札幌市中央区");
            record.setArea("以下に掲載がない場合");
            record.setUpdateFlag(0);
            record.setReasonFlag(1);

            recordset.add(record);
        }

        {
            final ZipCodeEntity record = new ZipCodeEntity();

            record.setJisCode("01101");
            record.setZipCode("0600042");
            record.setPrefecturePhonetic("ﾎｯｶｲﾄﾞｳ");
            record.setCityPhonetic("ｻｯﾎﾟﾛｼﾁｭｳｵｳｸ");
            record.setAreaPhonetic("ｵｵﾄﾞｵﾘﾆｼ(1-19ﾁｮｳﾒ)");
            record.setPrefecture("北海道");
            record.setCity("札幌市中央区");
            record.setArea("大通西（１～１９丁目）");
            record.setUpdateFlag(1);
            record.setReasonFlag(0);

            recordset.add(record);
        }

        return recordset;
    }
}
