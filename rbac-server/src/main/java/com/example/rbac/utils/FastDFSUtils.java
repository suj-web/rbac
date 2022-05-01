package com.example.rbac.utils;

import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * FastDFS工具类
 */
public class FastDFSUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastDFSUtils.class);

    /**
     * 初始化客户端
     */
    static {
        try{
            Properties properties = new Properties();
            InputStream resourceAsStream = FastDFSUtils.class.getClassLoader().getResourceAsStream("fdfs_client.properties");
            properties.load(resourceAsStream);
            ClientGlobal.initByProperties(properties);
        } catch (Exception e){
            LOGGER.error("初始化FastDFS失败=======>",e.getMessage());
        }
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    public static String[] upload(MultipartFile file){
        String name = file.getOriginalFilename();
        LOGGER.info("文件名:" + name);
        StorageClient storageClient = null;
        String[] uploadResults = null;
        try{
            //获取storage客户端
            storageClient = getStorageClient();
            //上传
            uploadResults = storageClient.upload_file(file.getBytes(),name.substring(name.lastIndexOf(".")+1),null);
        } catch (Exception e){
            LOGGER.error("上传文件失败========>"+e.getMessage());
        }
        if(null == uploadResults){
            LOGGER.error("上传失败<======>",storageClient.getErrorCode());
        }
        return uploadResults;
    }

    /**
     * 获取文件信息
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static FileInfo getFileInfo(String groupName, String remoteFileName){
        StorageClient storageClient = null;
        try {
            storageClient = getStorageClient();
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (Exception e){
            LOGGER.error("文件信息获取失败",e.getMessage());
        }
        return null;
    }

    /**
     * 下载文件
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static InputStream downFile(String groupName, String remoteFileName){
        StorageClient storageClient = null;
        try {
            storageClient = getStorageClient();
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            InputStream inputStream = new ByteArrayInputStream(fileByte);
            return inputStream;
        } catch (Exception e){
            LOGGER.error("文件信息获取失败",e.getMessage());
        }
        return null;
    }

    /**
     * 删除文件
     * @param groupName
     * @param remoteFileName
     */
    public static void deleteFile(String groupName, String remoteFileName){
        StorageClient storageClient = null;
        try {
            storageClient = getStorageClient();
            storageClient.delete_file(groupName,remoteFileName);
        } catch (Exception e){
            LOGGER.error("文件删除失败",e.getMessage());
        }
    }

    /**
     * 获取文件路径
     * @return
     */
    public static String getTrackerUrl(){
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = null;
        StorageServer storageStorage = null;
        try {
            trackerServer = trackerClient.getTrackerServer();
            storageStorage = trackerClient.getStoreStorage(trackerServer);
        } catch (Exception e){
            LOGGER.error("文件路径获取失败",e.getMessage());
        }
        return "http://"+storageStorage.getInetSocketAddress().getHostString()+":8888/";
    }


    /**
     * 生成storageClient客户端
     * @return
     * @throws IOException
     */
    private static StorageClient getStorageClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }

    /**
     * 生成trackerServer服务器
     * @return
     * @throws IOException
     */
    private static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        return trackerServer;
    }
}
