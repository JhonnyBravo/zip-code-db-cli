package zip_code_db_cli.domain.repository.connection;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_contents.domain.repository.stream.StreamRepository;
import java_itamae_contents.domain.repository.stream.StreamRepositoryImpl;
import java_itamae_properties.domain.repository.properties.PropertiesRepository;
import java_itamae_properties.domain.repository.properties.PropertiesRepositoryImpl;
import zip_code_db_cli.domain.model.ConnectionInfo;

public class ConnectionRepositoryImpl implements ConnectionRepository {

    @Override
    public Connection getConnection(ContentsAttribute attr) throws Exception {
        final StreamRepository sr = new StreamRepositoryImpl();
        final PropertiesRepository pr = new PropertiesRepositoryImpl();
        Map<String, String> properties = new HashMap<String, String>();

        try (Reader reader = sr.getReader(attr)) {
            properties = pr.getProperties(reader);
        }

        final ConnectionInfo cnInfo = new ConnectionInfo();

        // オプション項目のデフォルト値設定
        if (properties.get("hostName") == null || properties.get("hostName").isEmpty()) {
            cnInfo.setHostName("localhost");
        } else {
            cnInfo.setHostName(properties.get("hostName"));
        }

        if (properties.get("portNumber") == null || properties.get("portNumber").isEmpty()) {
            cnInfo.setPortNumber("3306");
        } else {
            cnInfo.setPortNumber(properties.get("portNumber"));
        }

        if (properties.get("encoding") == null || properties.get("encoding").isEmpty()) {
            cnInfo.setEncoding("UTF-8");
        } else {
            cnInfo.setEncoding(properties.get("encoding"));
        }

        if (properties.get("timeZone") == null || properties.get("timeZone").isEmpty()) {
            cnInfo.setTimeZone("JST");
        } else {
            cnInfo.setTimeZone(properties.get("timeZone"));
        }

        // 必須項目のチェック
        if (properties.get("dbName") == null || properties.get("dbName").isEmpty()) {
            throw new Exception("dbName が設定されていません。 " + attr.getPath() + " を確認してください。");
        } else {
            cnInfo.setDbName(properties.get("dbName"));
        }

        if (properties.get("userName") == null || properties.get("userName").isEmpty()) {
            throw new Exception("userName が設定されていません。 " + attr.getPath() + " を確認してください。");
        } else {
            cnInfo.setUserName(properties.get("userName"));
        }

        if (properties.get("password") == null || properties.get("password").isEmpty()) {
            throw new Exception("password が設定されていません。 " + attr.getPath() + " を確認してください。");
        } else {
            cnInfo.setPassword(properties.get("password"));
        }

        final StringBuilder buffer = new StringBuilder();

        buffer.append("jdbc:mysql://");
        buffer.append(cnInfo.getHostName() + ":");
        buffer.append(cnInfo.getPortNumber() + "/");
        buffer.append(cnInfo.getDbName());
        buffer.append("?characterEncoding=" + cnInfo.getEncoding());
        buffer.append("&serverTimezone=" + cnInfo.getTimeZone());

        final String cnStr = new String(buffer);
        final Connection connection = DriverManager.getConnection(cnStr, cnInfo.getUserName(), cnInfo.getPassword());

        return connection;
    }
}
