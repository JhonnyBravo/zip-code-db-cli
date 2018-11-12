package zip_code_db_cli;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import csv_resource.CsvController;
import status_resource.StatusController;

/**
 * zip_code テーブルのレコード操作を管理する。
 */
@Service
@Import({ CsvController.class, StatusController.class })
public class ZipCodeController extends StatusController {
    @Autowired
    private CsvController ctrlCsv;

    @Autowired
    private ZipCodeRepository repository;

    /**
     * @param path インポート対象とするファイルのパスを指定する。
     * @return List&lt;String[]&gt; CSV のデータを文字列配列のリストに変換して返す。
     */
    public List<String[]> importCsv(String path) {
        this.initStatus();

        List<String[]> recordset = ctrlCsv.getRecordset(path, "MS932");
        this.setCode(ctrlCsv.getCode());

        return recordset;
    }

    /**
     * zip_code テーブルからレコードを削除する。
     */
    @Transactional(readOnly = false)
    public void deleteRecord() {
        this.initStatus();

        long count = repository.count();
        repository.deleteAll();

        System.out.println(count + " 件のレコードを削除しました。");
        this.setCode(2);
    }

    /**
     * zip_code テーブルへ CSV データを一括登録する。
     * 
     * @param recordset zip_code テーブルへ登録する CSV データのリストを指定する。
     */
    @Transactional(readOnly = false)
    public void insertRecord(List<String[]> recordset) {
        this.initStatus();
        int count = 0;

        for (String[] record : recordset) {
            ZipCode zipcode = new ZipCode();

            zipcode.setJisCode(record[0]);
            zipcode.setZipCode(record[2]);
            zipcode.setPrefecturePhonetic(record[3]);
            zipcode.setCityPhonetic(record[4]);
            zipcode.setAreaPhonetic(record[5]);
            zipcode.setPrefecture(record[6]);
            zipcode.setCity(record[7]);
            zipcode.setArea(record[8]);
            zipcode.setUpdateFlag(Integer.parseInt(record[13]));
            zipcode.setReasonFlag(Integer.parseInt(record[14]));

            repository.save(zipcode);
            count++;
        }

        if (count != 0) {
            System.out.println(count + " 件のレコードを追加しました。");
            this.setCode(2);
        }
    }
}
