package cn.ubiot.common.autobean.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * 文件读写操作工具类
 * @author
 *
 */
public class FileUtil {
    /**
     *创建目录
     * @param dirName 目录的路径
     */
    public static boolean createDir(String dirName){
        File file=new File(dirName);
        if(!file.exists()){
            if(file.mkdirs()){
                return true;
            }
        }
        return false;
    }

    /**
     * 将内容写进文件
     * @param filepath
     * @param content
     * @return
     */
    public static boolean writeFileContent(String filepath,String content){
        Boolean isok = false;
        File file =new File(filepath);
        if(file.exists()){
            try {
                //以写入的方式打开文件
                FileWriter fw = new FileWriter(filepath);
                //文件内容写入
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);//向文件里面写入内容
                bw.close();
                fw.close();
                isok=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isok;
    }

    /**
     *创建带内容的文件
     * @param fileName 文件名
     */
    public static boolean createFile(String fileName,String content){
        File file=new File(fileName);
        if(!file.exists()){
            try {
                //创建文件并写入内容
                file.createNewFile();
                writeFileContent(fileName,content);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 指定路径下创建带内容的文件
     * @param path 路径
     * @param fileName 文件名
     */
    public static boolean createFileAtPath(String path,String fileName,String content){
        //路径不存在先创建
        if(createDir(path)==true){
            StringBuffer bf =new StringBuffer(path);
            bf.append(fileName);
            File file = new File(bf.toString());
            if(!file.exists()){
                try {
                    //创建文件并写入内容
                    file.createNewFile();
                    writeFileContent(bf.toString(),content);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{//路径存在直接创建文件并写入内容
            return createFile(path+"/"+fileName,content);
        }
        return false;
    }

    /**
     * 创建临时文件
     * @param fileName 文件名
     * @return
     */
    public static File createTempFile(String fileName){
        //boolean isok = false;
        File file = new File("temp"+fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
                //当你退出的时候，就把我这个临时文件删除掉
                file.deleteOnExit();
                //isok=true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 删除文件
     * @param fileName 文件名
     */
    public static boolean deleteFile(String fileName){
        try {
            //直接创建一个文件的操作对象
            File file = new File(fileName);
            if(file.exists()){
                if(file.delete()){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取目录文件夹下的所有文件
     * @param path
     * @return
     */
    public static ArrayList<File> getFiles(String path) {
        ArrayList<File> files = new ArrayList<File>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        if(tempList==null){
            return files;
        }
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i]);
            }
        }
        return files;
    }

    /**
     * 读取目录文件夹下的所有文件夹
     * @param path
     * @return
     */
    public static ArrayList<File> getFileDirectory(String path) {
        ArrayList<File> fileDirectorys = new ArrayList<File>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        if(tempList==null){
            return fileDirectorys;
        }
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isDirectory()) {
                fileDirectorys.add(tempList[i]);
            }
        }
        return fileDirectorys;
    }

    public static void main(String[]args){
        ArrayList<File> list = getFileDirectory("E:/other/base/src/main/java/com/springboot/base/module");
        System.out.println(list);
        for(File file:list){
            ArrayList<File> list2 = getFiles(file.getPath());
            System.out.println(list2);
        }
    }
}
