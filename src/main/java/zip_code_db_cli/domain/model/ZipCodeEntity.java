package zip_code_db_cli.domain.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * t_zip_code テーブルのデータ入出力を管理する。
 */
@Entity
@Table(name = "t_zip_code")
public class ZipCodeEntity implements Serializable {
  private static final long serialVersionUID = 1L;

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
   * レコード ID を返す。
   *
   * @return id レコード ID
   */
  public Integer getId() {
    return id;
  }

  /**
   * レコード ID を設定する。
   *
   * @param id レコード ID として設定する整数
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * JIS コードを返す。
   *
   * @return jisCode JIS コード
   */
  public String getJisCode() {
    return jisCode;
  }

  /**
   * JIS コードを設定する。
   *
   * @param jisCode JIS コードとして設定する文字列
   */
  public void setJisCode(String jisCode) {
    this.jisCode = jisCode;
  }

  /**
   * 郵便番号を返す。
   *
   * @return zipCode 郵便番号
   */
  public String getZipCode() {
    return zipCode;
  }

  /**
   * 郵便番号を設定する。
   *
   * @param zipCode 郵便番号として設定する文字列
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  /**
   * 都道府県名のフリガナを返す。
   *
   * @return prefecturePhonetic 都道府県名のフリガナ
   */
  public String getPrefecturePhonetic() {
    return prefecturePhonetic;
  }

  /**
   * 都道府県名のフリガナを設定する。
   *
   * @param prefecturePhonetic 都道府県名のフリガナとして設定する文字列
   */
  public void setPrefecturePhonetic(String prefecturePhonetic) {
    this.prefecturePhonetic = prefecturePhonetic;
  }

  /**
   * 市区郡名のフリガナを返す。
   *
   * @return cityPhonetic 市区郡名のフリガナ
   */
  public String getCityPhonetic() {
    return cityPhonetic;
  }

  /**
   * 市区郡名のフリガナを設定する。
   *
   * @param cityPhonetic 市区郡名のフリガナとして設定する文字列
   */
  public void setCityPhonetic(String cityPhonetic) {
    this.cityPhonetic = cityPhonetic;
  }

  /**
   * 町域名のフリガナを返す。
   *
   * @return areaPhonetic 町域名のフリガナ
   */
  public String getAreaPhonetic() {
    return areaPhonetic;
  }

  /**
   * 町域名のフリガナを設定する。
   *
   * @param areaPhonetic 町域名のフリガナとして設定する文字列
   */
  public void setAreaPhonetic(String areaPhonetic) {
    this.areaPhonetic = areaPhonetic;
  }

  /**
   * 都道府県名を返す。
   *
   * @return prefecture 都道府県名
   */
  public String getPrefecture() {
    return prefecture;
  }

  /**
   * 都道府県名を設定する。
   *
   * @param prefecture 都道府県名として設定する文字列
   */
  public void setPrefecture(String prefecture) {
    this.prefecture = prefecture;
  }

  /**
   * 市区郡名を返す。
   *
   * @return city 市区郡名
   */
  public String getCity() {
    return city;
  }

  /**
   * 市区郡名を設定する。
   *
   * @param city 市区郡名として設定する文字列
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * 町域名を返す。
   *
   * @return area 町域名
   */
  public String getArea() {
    return area;
  }

  /**
   * 町域名を設定する。
   *
   * @param area 町域名として設定する文字列
   */
  public void setArea(String area) {
    this.area = area;
  }

  /**
   * 更新区分を返す。
   *
   * @return updateFlag 更新区分
   */
  public Integer getUpdateFlag() {
    return updateFlag;
  }

  /**
   * 更新区分を設定する。
   *
   * @param updateFlag 更新区分として設定する整数
   */
  public void setUpdateFlag(Integer updateFlag) {
    this.updateFlag = updateFlag;
  }

  /**
   * 更新理由を返す。
   *
   * @return reasonFlag 更新理由
   */
  public Integer getReasonFlag() {
    return reasonFlag;
  }

  /**
   * 更新理由を設定する。
   *
   * @param reasonFlag 更新理由として設定する整数
   */
  public void setReasonFlag(Integer reasonFlag) {
    this.reasonFlag = reasonFlag;
  }
}
