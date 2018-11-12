package zip_code_db_cli;

import org.springframework.stereotype.Service;

/**
 * ファイル一覧取得時に使用するプロパティを管理する。
 */
@Service
public class PathProperties {
    private String dirPath;
    private String extension;

    /**
     * @return dirPath ファイル一覧取得対象とするディレクトリのパスを返す。
     */
    public String getDirPath() {
        return dirPath;
    }

    /**
     * @param dirPath ファイル一覧取得対象とするディレクトリのパスを指定する。
     */
    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    /**
     * @return extension 取得対象とするファイルの拡張子を返す。
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension 取得対象とするファイルの拡張子を指定する。
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }
}
