package zip_code_db_cli.domain.model;

import com.opencsv.bean.CsvBindByPosition;
import java.io.Serializable;

/**
 * 郵便番号 CSV ファイル用のエンティティ
 */
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
   * レコード ID を返す。 MySQL 用のレコード識別子として使用する。本項目は CSV の読み書きには使用しない。
   *
   * @return id レコード ID
   */
  public long getId() {
    return id;
  }

  /**
   * レコード ID を設定する。 MySQL 用のレコード識別子として使用する。本項目は CSV の読み書きには使用しない。
   *
   * @param id ID として設定する整数
   */
  public void setId(long id) {
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
   * 更新の表示を返す。
   *
   * @return updateFlag 更新の表示
   */
  public int getUpdateFlag() {
    return updateFlag;
  }

  /**
   * 更新の表示を設定する。
   *
   * @param updateFlag 更新の表示として設定する整数
   *        <ul>
   *        <li>0: 変更なし</li>
   *        <li>1: 変更あり</li>
   *        <li>2: 廃止</li>
   *        </ul>
   */
  public void setUpdateFlag(int updateFlag) {
    this.updateFlag = updateFlag;
  }

  /**
   * 変更理由を返す。
   *
   * @return reasonFlag 変更理由
   */
  public int getReasonFlag() {
    return reasonFlag;
  }

  /**
   * 変更理由を設定する。
   *
   * @param reasonFlag 変更理由として設定する整数
   *        <ul>
   *        <li>0: 変更なし</li>
   *        <li>1: 市政・区政・町政・分区・政令指定都市施行</li>
   *        <li>2: 住居表示の実施</li>
   *        <li>3: 区画整理</li>
   *        <li>4: 郵便区町政</li>
   *        <li>5: 訂正</li>
   *        <li>6: 廃止</li>
   *        </ul>
   */
  public void setReasonFlag(int reasonFlag) {
    this.reasonFlag = reasonFlag;
  }
}
