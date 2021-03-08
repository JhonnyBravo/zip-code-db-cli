package zip_code_db_cli.domain.model;

import java.io.Serializable;

import com.opencsv.bean.CsvBindByPosition;

public class ZipCodeCsvEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    @CsvBindByPosition(position = 0)
    private String jisCode;
    @CsvBindByPosition(position = 2)
    private String zipCode;
    @CsvBindByPosition(position = 3)
    private String prefecturePhonetic;
    @CsvBindByPosition(position = 4)
    private String cityPhonetic;
    @CsvBindByPosition(position = 5)
    private String areaPhonetic;
    @CsvBindByPosition(position = 6)
    private String prefecture;
    @CsvBindByPosition(position = 7)
    private String city;
    @CsvBindByPosition(position = 8)
    private String area;
    @CsvBindByPosition(position = 13)
    private int updateFlag;
    @CsvBindByPosition(position = 14)
    private int reasonFlag;

    /**
     * @return id ID を返す。 MySQL 用のレコード識別子として使用する。本項目は CSV の読み書きには使用しない。
     */
    public long getId() {
        return id;
    }

    /**
     * @param id ID として設定する整数を指定する。 MySQL 用のレコード識別子として使用する。本項目は CSV の読み書きには使用しない。
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return jisCode JIS コードを返す。
     */
    public String getJisCode() {
        return jisCode;
    }

    /**
     * @param jisCode JIS コードとして設定する文字列を指定する。
     */
    public void setJisCode(String jisCode) {
        this.jisCode = jisCode;
    }

    /**
     * @return zipCode 郵便番号を返す。
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode 郵便番号として設定する文字列を指定する。
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return prefecturePhonetic 都道府県名のフリガナを返す。
     */
    public String getPrefecturePhonetic() {
        return prefecturePhonetic;
    }

    /**
     * @param prefecturePhonetic 都道府県名のフリガナとして設定する文字列を指定する。
     */
    public void setPrefecturePhonetic(String prefecturePhonetic) {
        this.prefecturePhonetic = prefecturePhonetic;
    }

    /**
     * @return cityPhonetic 市区郡名のフリガナを返す。
     */
    public String getCityPhonetic() {
        return cityPhonetic;
    }

    /**
     * @param cityPhonetic 市区郡名のフリガナとして設定する文字列を指定する。
     */
    public void setCityPhonetic(String cityPhonetic) {
        this.cityPhonetic = cityPhonetic;
    }

    /**
     * @return areaPhonetic 町域名のフリガナを返す。
     */
    public String getAreaPhonetic() {
        return areaPhonetic;
    }

    /**
     * @param areaPhonetic 町域名のフリガナとして設定する文字列を指定する。
     */
    public void setAreaPhonetic(String areaPhonetic) {
        this.areaPhonetic = areaPhonetic;
    }

    /**
     * @return prefecture 都道府県名を返す。
     */
    public String getPrefecture() {
        return prefecture;
    }

    /**
     * @param prefecture 都道府県名として設定する文字列を指定する。
     */
    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    /**
     * @return city 市区郡名を返す。
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city 市区郡名として設定する文字列を指定する。
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return area 町域名を返す。
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area 町域名として設定する文字列を指定する。
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return updateFlag 更新の表示を返す。
     */
    public int getUpdateFlag() {
        return updateFlag;
    }

    /**
     * @param updateFlag 更新の表示として設定する整数を指定する。
     *                   <ul>
     *                   <li>0: 変更なし</li>
     *                   <li>1: 変更あり</li>
     *                   <li>2: 廃止</li>
     *                   </ul>
     */
    public void setUpdateFlag(int updateFlag) {
        this.updateFlag = updateFlag;
    }

    /**
     * @return reasonFlag 変更理由を返す。
     */
    public int getReasonFlag() {
        return reasonFlag;
    }

    /**
     * @param reasonFlag 変更理由として設定する整数を指定する。
     *                   <ul>
     *                   <li>0: 変更なし</li>
     *                   <li>1: 市政・区政・町政・分区・政令指定都市施行</li>
     *                   <li>2: 住居表示の実施</li>
     *                   <li>3: 区画整理</li>
     *                   <li>4: 郵便区町政</li>
     *                   <li>5: 訂正</li>
     *                   <li>6: 廃止</li>
     *                   </ul>
     */
    public void setReasonFlag(int reasonFlag) {
        this.reasonFlag = reasonFlag;
    }
}
