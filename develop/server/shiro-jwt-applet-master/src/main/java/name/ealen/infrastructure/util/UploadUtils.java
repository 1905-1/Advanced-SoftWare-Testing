package name.ealen.infrastructure.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadUtils {
    public final static String IMG_PATH_PREFIX = "static/upload/imgs/";

    public static File getImgDirFile(){
        String fileDirPath = new String("src/main/resources/"+IMG_PATH_PREFIX);
        File fileDir = new File(fileDirPath);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        return fileDir;
    }


    //静态方法：三个参数：文件的二进制，文件路径，文件名
    //通过该方法将在指定目录下添加指定文件
    public static void fileupload(byte[] file,String filePath,String fileName) throws IOException {
        //目标目录
        File targetfile = new File(filePath);
        if(targetfile.exists()) {
            targetfile.mkdirs();
        }

        //二进制流写入
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        try {
            out.write(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }
}
