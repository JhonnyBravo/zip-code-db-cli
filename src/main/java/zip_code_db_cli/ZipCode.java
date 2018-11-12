package zip_code_db_cli;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Service;

/**
 * zip_code テーブルのデータ入出力を管理する。
 */
@Service
@Entity
@Table(name = "ZipCode")
public class ZipCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column(length = 5, nullable = false)
    private String jisCode;

    @Column(length = 7, nullable = false)
    private String zipCode;

    @Column(length = 10, nullable = false)
    private String prefecturePhonetic;

    @Column(length = 30, nullable = false)
    private String cityPhonetic;

    @Column(length = 80, nullable = true)
    private String areaPhonetic;

    @Column(length = 10, nullable = false)
    private String prefecture;

    @Column(length = 20, nullable = false)
    private String city;

    @Column(length = 50, nullable = true)
    private String area;

    @Column(length = 1, nullable = false)
    private Integer updateFlag;

    @Column(length = 1, nullable = false)
    private Integer reasonFlag;

    /**
     * @return id レコード ID を返す。
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id レコード ID を指定する。
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return jisCode JIS コードを返す。
     */
    public String getJisCode() {
        return jisCode;
    }

    /**
     * @param jisCode JIS コードを指定する。
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
     * @param zipCode 郵便番号を指定する。
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return prefecturePhonetic 都道府県のカナを返す。
     */
    public String getPrefecturePhonetic() {
        return prefecturePhonetic;
    }

    /**
     * @param prefecturePhonetic 都道府県のカナを指定する。
     */
    public void setPrefecturePhonetic(String prefecturePhonetic) {
        this.prefecturePhonetic = prefecturePhonetic;
    }

    /**
     * @return cityPhonetic 市区町村のカナを返す。
     */
    public String getCityPhonetic() {
        return cityPhonetic;
    }

    /**
     * @param cityPhonetic 市区町村のカナを指定する。
     */
    public void setCityPhonetic(String cityPhonetic) {
        this.cityPhonetic = cityPhonetic;
    }

    /**
     * @return areaPhonetic 町域のカナを返す。
     */
    public String getAreaPhonetic() {
        return areaPhonetic;
    }

    /**
     * @param areaPhonetic 町域のカナを指定する。
     */
    public void setAreaPhonetic(String areaPhonetic) {
        this.areaPhonetic = areaPhonetic;
    }

    /**
     * @return prefecture 都道府県を返す。
     */
    public String getPrefecture() {
        return prefecture;
    }

    /**
     * @param prefecture 都道府県を指定する。
     */
    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    /**
     * @return city 市区町村を返す。
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city 市区町村を指定する。
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return area 町域を返す。
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area 町域を指定する。
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return updateFlag 更新区分を返す。
     */
    public Integer getUpdateFlag() {
        return updateFlag;
    }

    /**
     * @param updateFlag 更新区分を指定する。
     */
    public void setUpdateFlag(Integer updateFlag) {
        this.updateFlag = updateFlag;
    }

    /**
     * @return reasonFlag 更新理由を返す。
     */
    public Integer getReasonFlag() {
        return reasonFlag;
    }

    /**
     * @param reasonFlag 更新理由を指定する。
     */
    public void setReasonFlag(Integer reasonFlag) {
        this.reasonFlag = reasonFlag;
    }
}
