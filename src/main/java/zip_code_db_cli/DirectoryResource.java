package zip_code_db_cli;

import java.util.List;

/**
 * インポート対象とする CSV ファイルのパスのリストを取得する。
 */
public interface DirectoryResource {
    /**
     * @param path 操作対象とするディレクトリのパスを指定する。
     */
    public void setPath(String path);

    /**
     * @return pathList 指定したディレクトリの配下に存在する CSV ファイルのパスを取得し、リストへ格納して返す。
     */
    public List<String> getPathList();
}
