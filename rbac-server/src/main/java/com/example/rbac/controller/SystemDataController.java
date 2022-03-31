package com.example.rbac.controller;

import cn.hutool.core.io.FileUtil;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.Backup;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.service.IBackupService;
import com.example.rbac.utils.FastDFSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 系统管理-备份恢复数据库
 * @Author suj
 * @create 2022/1/30
 */
@RestController
@RequestMapping("/system/data")
public class SystemDataController {

    @Autowired
    private IBackupService backupService;

    @OperationLogAnnotation(operModul = "备份恢复数据库", operType = "查询",operDesc = "查询备份文件")
    @ApiOperation(value = "查询备份文件")
    @GetMapping("/list")
    public List<Backup> getBackupFiles() {
        return backupService.list();
    }

    @OperationLogAnnotation(operModul = "备份恢复数据库", operType = "删除",operDesc = "删除备份文件")
    @ApiOperation(value = "删除备份文件")
    @DeleteMapping("/{id}")
    public RespBean deleteBackupFile(@PathVariable Integer id) {
        if(backupService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "备份恢复数据库", operType = "删除",operDesc = "批量删除备份文件")
    @ApiOperation(value = "批量删除备份文件")
    @DeleteMapping("/")
    public RespBean deleteBackupFile(Integer[] ids) {
        if(backupService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "备份恢复数据库", operType = "备份数据", operDesc = "备份数据")
    @ApiOperation(value = "备份数据")
    @GetMapping("/backup")
    public RespBean backup(String savePath) {
        String command = "mysqldump -hlocalhost -uroot -p12345678 test";
        File file = new File(savePath);
        File parentFile = new File(file.getParent());
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        String filePath = backup(command, savePath);
        if(null != filePath) {
            String[] split = filePath.split("/");
            double size = FileUtil.size(new File(savePath)) / 1024.0 / 1024.0;
            Backup backup = new Backup();
            backup.setName(split[split.length-1]);
            backup.setPath(filePath);
            backup.setSize(size);
            if(backupService.save(backup)) {
                return RespBean.success("备份成功");
            }
        }
        return RespBean.error("备份失败");
    }

    @OperationLogAnnotation(operModul = "系统管理-备份恢复数据库", operType = "恢复数据", operDesc = "恢复数据")
    @ApiOperation(value = "恢复数据")
    @PostMapping("/recover")
    public RespBean recover(MultipartFile file) {
        String command = "mysql -hlocalhost -uroot -p12345678 --default-character-set=utf8 test";
        if(null != file) {
            if(recover(command, file)) {
                return RespBean.success("恢复成功");
            }
        }
        return RespBean.error("恢复失败");
    }


    /**
     * mysql的备份方法
     *
     * @param command  命令行
     * @param savePath 备份路径
     * @return
     */
    private String backup(String command, String savePath) {
        // 获得与当前应用程序关联的Runtime对象
        Runtime r = Runtime.getRuntime();
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            // 在单独的进程中执行指定的字符串命令
            Process p = r.exec(command);
            // 获得连接到进程正常输出的输入流，该输入流从该Process对象表示的进程的标准输出中获取数据
            InputStream is = p.getInputStream();
            // InputStreamReader是从字节流到字符流的桥梁：它读取字节，并使用指定的charset将其解码为字符
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            //BufferedReader从字符输入流读取文本，缓冲字符，提供字符，数组和行的高效读取
            br = new BufferedReader(isr);
            String s;
            StringBuffer sb = new StringBuffer("");
            // 组装字符串
            while ((s = br.readLine()) != null) {
                sb.append(s + System.lineSeparator());
            }
            s = sb.toString();
            // 创建文件输出流
            FileOutputStream fos = new FileOutputStream(savePath);
            // OutputStreamWriter是从字符流到字节流的桥梁，它使用指定的charset将写入的字符编码为字节
            OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
            // BufferedWriter将文本写入字符输出流，缓冲字符，以提供单个字符，数组和字符串的高效写入
            bw = new BufferedWriter(osw);
            bw.write(s);
            bw.flush();

            File file = new File(savePath);
            FileInputStream input = new FileInputStream(file);
            MultipartFile multipartFile =new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
            String[] filePath = FastDFSUtils.upload(multipartFile);
            String url = FastDFSUtils.getTrackerUrl() + filePath[0]  + "/" + filePath[1];
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bw) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (null != br) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * mysql的还原方法
     *
     * @param command  命令行
     * @param file 上传下载的备份文件
     * @return
     */
    private boolean recover(String command, MultipartFile file) {
        boolean flag;
        Runtime r = Runtime.getRuntime();
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            Process p = r.exec(command);
            OutputStream os = p.getOutputStream();
            InputStreamReader isr = new InputStreamReader(file.getInputStream(), "utf-8");
            br = new BufferedReader(isr);
            String s;
            StringBuffer sb = new StringBuffer("");
            while ((s = br.readLine()) != null) {
                sb.append(s + System.lineSeparator());
            }
            s = sb.toString();
            OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
            bw = new BufferedWriter(osw);
            bw.write(s);
            bw.flush();
            flag = true;
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
        } finally {
            try {
                if (null != bw) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (null != br) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

}
