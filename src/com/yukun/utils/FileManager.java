package com.yukun.utils;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by Arcane on 2016/10/13.
 */
public class FileManager {
    public static void main(String[] args) {
        FileManager fm = new FileManager();
        try {
            fm.copyFile("D:\\Develop\\temp\\test.txt", "D:\\Develop\\temp\\批量下载\\test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件或文件夹
     * 删除文件夹，需要先删除文件夹下的所有内容
     * @param file
     */
    public void deleteFile(File file) {
        // 判断文件是否存在
        if (file.exists()) {
            if (file.isFile()) { // 文件直接删除
                file.delete();
            } else if (file.isDirectory()) { // 文件夹，先删除其中的全部内容
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
                file.delete();
            }
        } else {
            throw new RuntimeException("文件不存在！");
        }
    }

    /**
     * 用 apache ant 压缩 zip 文件
     * @param srcPath
     * @param zipPath
     * @return
     */
    public File zipByAnt(String srcPath, String zipPath) {
        File zipFile = new File(zipPath);
        File srcDir = new File(srcPath);
        if (!srcDir.exists()) {
            throw new RuntimeException(srcPath + "不存在！");
        }

        Project prj = new Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setDestFile(zipFile);
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        fileSet.setDir(srcDir);
        zip.addFileset(fileSet);
        zip.execute();

        return zipFile;
    }

    /**
     *
     * @param from
     * @param to
     * @throws IOException
     */
    public void copyFile(String from, String to) throws IOException {
        Path fromPath = Paths.get(from);
        System.out.println(fromPath.getFileName());
        Path toPath = Paths.get(to);
        Files.copy(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
