package zip_code_db_cli;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common_resource.PathBean;
import common_resource.StatusBean;

/**
 * ディレクトリ操作を管理する。
 */
public class DirectoryAction extends StatusBean {
    private PathBean pb = new PathBean();

    /**
     * @param path 操作対象とするディレクトリのパスを指定する。
     */
    public DirectoryAction(String path) {
        pb.setBaseName(path);
    }

    /**
     * @return cwd 操作対象ディレクトリの親ディレクトリのパスとして指定したパスを返す。
     */
    public String getCwd() {
        return pb.getDirName();
    }

    /**
     * @param cwd 操作対象ディレクトリの親ディレクトリのパスを指定する。
     */
    public void setCwd(String cwd) {
        pb.setDirName(cwd);
    }

    /**
     * 操作対象ディレクトリの配下に存在するファイルのパスの一覧を返す。
     * @param extension 取得対象とするファイルの拡張子を小文字で指定する。
     * @return List&lt;String&gt;
     */
    public List<String> getFilePathList(String extension) {
        this.setCode(0);
        this.setMessage(null);
        this.setResourceName(null);

        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File file, String str) {
                if (str.toLowerCase().endsWith(extension)) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        List<String> filePathList = new ArrayList<String>();
        String dirPath = pb.getPath();

        if (pb.getCode() == 1) {
            this.setCode(pb.getCode());
            this.setMessage(pb.getMessage());
            this.setResourceName("zip_code_db_cli");

            System.err.println(this.getStatus());
            return filePathList;
        }

        File[] fileList = new File(dirPath).listFiles(filter);

        for (int i = 0; i < fileList.length; ++i) {
            try {
                filePathList.add(fileList[i].getCanonicalPath());
            } catch (IOException e) {
                this.setCode(1);
                this.setMessage("エラーが発生しました。 " + e.toString());
                this.setResourceName("zip_code_db_cli");

                System.err.println(this.getStatus());
                return filePathList;
            }
        }

        this.setCode(2);
        return filePathList;
    }
}
