package zip_code_db_cli;

import java.util.List;

/**
 * テーブルの操作を管理する。
 */
public interface TableResource {
    /**
     * @param path DB の接続情報を記述した設定ファイルのパスを指定する。
     */
    public void setPath(String path);

    /**
     * DB への接続を確立する。
     */
    public void openConnection();

    /**
     * DB への接続を切断する。
     */
    public void closeConnection();

    /**
     * テーブルからレコードを削除する。
     */
    public void deleteRecord();

    /**
     * CSV ファイルをテーブルへインポートする。
     * 
     * @param csvContent インポート対象とする CSV を文字列配列に変換したリストを指定する。
     */
    public void insertRecord(List<String[]> csvContent);
}
