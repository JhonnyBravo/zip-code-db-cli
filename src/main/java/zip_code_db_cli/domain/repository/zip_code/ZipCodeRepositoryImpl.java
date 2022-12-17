package zip_code_db_cli.domain.repository.zip_code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java_itamae_connection.domain.model.ConnectionInfo;
import zip_code_db_cli.domain.model.ZipCode;

public class ZipCodeRepositoryImpl implements ZipCodeRepository {
  /** {@link ConnectionInfo} のインスタンス */
  private transient ConnectionInfo cnInfo;

  @Override
  public void init(final ConnectionInfo cnInfo) {
    this.cnInfo = cnInfo;
  }

  @Override
  public List<ZipCode> findAll() throws Exception {
    final List<ZipCode> recordset = new ArrayList<>();

    try (Connection connection = this.getConnection(this.cnInfo);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM t_zip_code;");
        ResultSet resultset = statement.executeQuery(); ) {
      while (resultset.next()) {
        final ZipCode zipcode = new ZipCode();

        zipcode.setId(resultset.getInt("id"));
        zipcode.setJisCode(resultset.getString("jis_code"));
        zipcode.setZipCode(resultset.getString("zip_code"));

        zipcode.setPrefecture(resultset.getString("prefecture"));
        zipcode.setCity(resultset.getString("city"));
        zipcode.setArea(resultset.getString("area"));

        zipcode.setPrefecturePhonetic(resultset.getString("prefecture_phonetic"));
        zipcode.setCityPhonetic(resultset.getString("city_phonetic"));
        zipcode.setAreaPhonetic(resultset.getString("area_phonetic"));

        zipcode.setReasonFlag(resultset.getInt("reason_flag"));
        zipcode.setUpdateFlag(resultset.getInt("update_flag"));

        recordset.add(zipcode);
      }
    }

    return recordset;
  }

  @Override
  public boolean create(final List<ZipCode> contents) throws Exception {
    boolean status = false;

    try (Connection connection = this.getConnection(this.cnInfo); ) {
      try (PreparedStatement statement =
          connection.prepareStatement(
              "INSERT INTO t_zip_code (jis_code,zip_code,prefecture_phonetic,city_phonetic,area_phonetic,prefecture,city,area,update_flag,reason_flag) VALUES (?,?,?,?,?,?,?,?,?,?)")) {
        connection.setAutoCommit(false);
        long count = 0;

        for (final ZipCode zipcode : contents) {
          statement.setString(1, zipcode.getJisCode());
          statement.setString(2, zipcode.getZipCode());

          statement.setString(3, zipcode.getPrefecturePhonetic());
          statement.setString(4, zipcode.getCityPhonetic());
          statement.setString(5, zipcode.getAreaPhonetic());

          statement.setString(6, zipcode.getPrefecture());
          statement.setString(7, zipcode.getCity());
          statement.setString(8, zipcode.getArea());

          statement.setInt(9, zipcode.getUpdateFlag());
          statement.setInt(10, zipcode.getReasonFlag());

          statement.addBatch();
          count++;

          if (count % 1000 == 0) {
            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(false);
          }
        }

        statement.executeBatch();
        connection.commit();

        if (count > 0) {
          status = true;
        }
      } catch (final Exception e) {
        connection.rollback();
        throw e;
      }
    }

    return status;
  }

  @Override
  @SuppressWarnings("unused")
  public boolean deleteAll() throws Exception {
    boolean status = false;

    try (Connection connection = this.getConnection(this.cnInfo); ) {
      try (PreparedStatement statement = connection.prepareStatement("DELETE FROM t_zip_code;")) {
        connection.setAutoCommit(false);

        statement.addBatch();
        statement.addBatch("ALTER TABLE t_zip_code auto_increment=1;");
        statement.executeBatch();

        connection.commit();
        status = true;
      } catch (final Exception e) {
        connection.rollback();
        throw e;
      }
    }

    return status;
  }
}
