package com.example.rbac.controller;

import cn.hutool.core.io.FileUtil;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.Backup;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.service.IBackupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;

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

    @ApiOperation(value = "查询备份文件")
    @GetMapping("/list")
    public List<Backup> getBackupFiles() {
        return backupService.list();
    }

    @OperationLogAnnotation(operModul = "系统管理-备份恢复数据库", operType = "备份数据", operDesc = "备份数据")
    @ApiOperation(value = "备份数据")
    @GetMapping("/backup")
    public RespBean backup(String savePath) {
        String command = "mysqldump -hlocalhost -uroot -p12345678 test";
        if(backup(command,savePath)) {
            String[] path = savePath.split("/");
            double size = FileUtil.size(new File(savePath)) / 1024.0 / 1024.0;
            Backup backup = new Backup();
            backup.setName(path[path.length-1]);
            backup.setPath(savePath);
            backup.setSize(size);
            if(backupService.save(backup)) {
                return RespBean.success("备份成功");
            }
        }
        return RespBean.error("备份失败");
    }

    @OperationLogAnnotation(operModul = "系统管理-备份恢复数据库", operType = "恢复数据", operDesc = "恢复数据")
    @ApiOperation(value = "恢复数据")
    @GetMapping("/recover")
    public RespBean recover(Integer id) {
        Backup backup = backupService.getById(id);
        String command = "mysql -hlocalhost -uroot -p12345678 --default-character-set=utf8 test";
        if(null != backup) {
            if(recover(command, backup.getPath())) {
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
    private boolean backup(String command, String savePath) {
        boolean flag;
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
            flag = true;
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
        } finally {
            //由于输入输出流使用的是装饰器模式，所以在关闭流时只需要调用外层装饰类的close()方法即可，
            //它会自动调用内层流的close()方法
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

    /**
     * mysql的还原方法
     *
     * @param command  命令行
     * @param savePath 还原路径
     * @return
     */
    private boolean recover(String command, String savePath) {
        boolean flag;
        Runtime r = Runtime.getRuntime();
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            Process p = r.exec(command);
            OutputStream os = p.getOutputStream();
            FileInputStream fis = new FileInputStream(savePath);
            InputStreamReader isr = new InputStreamReader(fis, "utf-8");
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
