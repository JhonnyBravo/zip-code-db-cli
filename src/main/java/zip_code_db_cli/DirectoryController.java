package zip_code_db_cli;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import status_resource.Status;

/**
 * @see zip_code_db_cli.DirectoryResource
 */
public class DirectoryController extends Status implements DirectoryResource {
    private String path = null;

    /*
     * @see zip_code_db_cli.DirectoryResource#setPath(java.lang.String)
     */
    @Override
    public void setPath(String path) {
        this.path = path;
    }

    /*
     * @see zip_code_db_cli.DirectoryResource#getPathList()
     */
    @Override
    public List<String> getPathList() {
        this.initStatus();

        List<String> pathList = null;
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.toLowerCase().endsWith("csv")) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        File directory = new File(this.path);

        if (this.path == null) {
            this.errorTerminate("path を指定してください。");
            return pathList;
        }

        if (!directory.isDirectory()) {
            this.errorTerminate(this.path + " が見つかりません。");
            return pathList;
        }

        pathList = new ArrayList<String>();
        File[] fileList = directory.listFiles(filter);

        for (int i = 0; i < fileList.length; ++i) {
            try {
                pathList.add(fileList[i].getCanonicalPath());
            } catch (IOException e) {
                this.errorTerminate("エラーが発生しました。 " + e.toString());
                return pathList;
            }
        }

        if (pathList.size() > 0) {
            this.setCode(2);
        }

        return pathList;
    }
}
