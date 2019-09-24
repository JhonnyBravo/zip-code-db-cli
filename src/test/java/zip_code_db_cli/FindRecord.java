package zip_code_db_cli;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sql_resource.ConnectionResource;

/**
 * テスト用
 */
public class FindRecord extends ConnectionResource {

    /**
     * @param property DB 接続時に使用する接続情報を納めた Map オブジェクトを指定する。
     */
    public FindRecord(Map<String, String> property) {
        super(property);
    }

    /**
     * @return recordset t_zip_code に登録されている全レコードを取得して返す。
     * @throws SQLException {@link java.sql.SQLException}
     */
    public List<ZipCode> findAll() throws SQLException {
        final List<ZipCode> recordset = new ArrayList<ZipCode>();
        final Connection cn = getConnection();

        try (PreparedStatement query = cn.prepareStatement("SELECT * FROM t_zip_code;");
                ResultSet result = query.executeQuery()) {
            while (result.next()) {
                final ZipCode zipcode = new ZipCode();

                zipcode.setId(result.getLong("id"));
                zipcode.setJisCode(result.getString("jis_code"));
                zipcode.setZipCode(result.getString("zip_code"));
                zipcode.setPrefecturePhonetic(result.getString("prefecture_phonetic"));
                zipcode.setCityPhonetic(result.getString("city_phonetic"));
                zipcode.setAreaPhonetic(result.getString("area_phonetic"));
                zipcode.setPrefecture(result.getString("prefecture"));
                zipcode.setCity(result.getString("city"));
                zipcode.setArea(result.getString("area"));
                zipcode.setUpdateFlag(result.getInt("update_flag"));
                zipcode.setReasonFlag(result.getInt("reason_flag"));

                recordset.add(zipcode);
            }
        } finally {
            cn.close();
        }

        return recordset;
    }
}
