package zip_code_db_cli;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 指定したディレクトリ配下に存在するファイルの一覧を取得する。
 */
public class GetPathList implements TZipCodeState {
    private int code;
    private String message;
    private String path;
    private String extension;
    private List<String> pathList=null;

    /**
     * @param path 検索対象とするディレクトリのパスを指定する。
     * @param extension 検索対象とするファイルの拡張子を指定する。
     */
    public GetPathList(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    /**
     * 終了コードとメッセージを初期化する。
     */
    @Override
    public void initStatus() {
        this.code = 0;
        this.message = null;
    }

    /**
     * 指定したディレクトリの配下から条件に合致するファイルのパスを取得し、リストへ格納する。
     */
    @Override
    public void runCommand() {
        this.initStatus();

        FilenameFilter filter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                if (name.toLowerCase().endsWith(extension)) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        File directory = new File(this.path);

        if (directory.isDirectory() == false) {
            this.message = this.path + " が見つかりません。";
            this.errorTerminate();
            return;
        }

        this.pathList = new ArrayList<String>();
        File[] fileList = directory.listFiles(filter);

        for (int i = 0; i < fileList.length; ++i) {
            try {
                this.pathList.add(fileList[i].getCanonicalPath());
            } catch (IOException e) {
                this.message = "エラーが発生しました。 " + e.toString();
                this.errorTerminate();
            }
        }

        if (this.pathList.size() > 0) {
            this.code = 2;
            this.message = null;
        }
    }

    /**
     * エラーメッセージを出力する。
     */
    @Override
    public void errorTerminate() {
        this.code = 1;
        System.err.println(this.message);
    }

    /**
     * @return pathList ディレクトリ配下に存在するファイルのパス一覧を返す。
     */
    public List<String> getPathList() {
        return pathList;
    }

    /**
     * @return code 終了ステータスを返す。
     */
    public int getCode() {
        return code;
    }
}
