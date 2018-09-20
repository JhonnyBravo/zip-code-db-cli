package zip_code_db_cli;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import status_resource.StatusController;

/**
 * 指定したディレクトリ配下に存在するファイルの一覧を取得する。
 */
public class GetPathList extends StatusController {
    private String path;
    private String extension;

    /**
     * @param path      検索対象とするディレクトリのパスを指定する。
     * @param extension 検索対象とするファイルの拡張子を指定する。
     */
    public GetPathList(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    /**
     * 指定したディレクトリの配下から条件に合致するファイルのパスを取得し、リストへ格納して返す。
     * 
     * @return List&lt;String&gt; ファイルパスを格納したリストを返す。
     */
    public List<String> runCommand() {
        this.initStatus();
        List<String> pathList = null;

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
            this.setMessage(this.path + " が見つかりません。");
            this.errorTerminate();
            return pathList;
        }

        pathList = new ArrayList<String>();
        File[] fileList = directory.listFiles(filter);

        for (int i = 0; i < fileList.length; ++i) {
            try {
                pathList.add(fileList[i].getCanonicalPath());
            } catch (IOException e) {
                this.setMessage("エラーが発生しました。 " + e.toString());
                this.errorTerminate();
                return pathList;
            }
        }

        if (pathList.size() > 0) {
            this.setCode(2);
            this.setMessage(null);
        }

        return pathList;
    }
}
