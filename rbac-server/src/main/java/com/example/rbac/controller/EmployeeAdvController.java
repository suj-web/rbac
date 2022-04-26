package com.example.rbac.controller;

import com.example.rbac.pojo.*;
import com.example.rbac.service.*;
import com.example.rbac.utils.FastDFSUtils;
import com.example.rbac.utils.PDFUtils;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * 员工高级资料
 *
 * @Author suj
 * @create 2022/1/22
 */
@RestController
@RequestMapping("/employee/advanced")
public class EmployeeAdvController {

    @Autowired
    private IEmployeeDataService employeeDataService;
    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/department/list")
    public List<Department> getAllDepartments() {
        return departmentService.list();
    }

    @ApiOperation(value = "查询员工高级资料")
    @GetMapping("/")
    public RespPageBean getAllEmpData(@RequestParam(defaultValue = "1") Integer currentPage,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   Integer depId, String name) {
        return employeeDataService.getAllEmpData(currentPage, size, depId, name);
    }

    @ApiOperation(value = "删除上传文件")
    @PutMapping("/")
    public RespBean updateEmpData(@RequestBody EmployeeData employeeData) {
        return employeeDataService.updateEmpData(employeeData);
    }

    @ApiOperation(value = "上传纸质合同")
    @PostMapping("/contract/upload")
    public RespBean uploadContract(MultipartFile file, Integer id) {
        String[] filePath = FastDFSUtils.upload(file);
        EmployeeData employeeData = employeeDataService.getById(id);
        employeeData.setContractPath(filePath[1]);
        if(employeeDataService.updateById(employeeData)) {
            return RespBean.success("上传成功",filePath[1]);
        }
        return RespBean.error("上传失败");
    }

    @ApiOperation(value = "下载纸质合同")
    @GetMapping(value = "/contract/download", produces = "application/octet-stream")
    public void uploadContract(String fileName, HttpServletResponse response) {
        BufferedInputStream bis = null;
        ServletOutputStream outputStream = null;
        try {
            bis = new BufferedInputStream(FastDFSUtils.downFile("group1", fileName));
            response.setHeader("content-type","application/octet-stream");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("合同.pdf","UTF-8"));
            outputStream = response.getOutputStream();
            PDFUtils.addWaterMark(bis, outputStream,"合同");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(null!=outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(null != bis) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "上传身份证正反面")
    @PostMapping("/idCard/upload")
    public RespBean uploadIdCard(MultipartFile file, Integer id) {
        String[] filePath = FastDFSUtils.upload(file);
        EmployeeData employeeData = employeeDataService.getById(id);
        employeeData.setIdCardPath(filePath[1]);
        if(employeeDataService.updateById(employeeData)) {
            return RespBean.success("上传成功",filePath[1]);
        }
        return RespBean.error("上传失败");
    }

    @ApiOperation(value = "下载身份证正反面")
    @GetMapping(value = "/idCard/download", produces = "application/octet-stream")
    public void uploadIdCard(String fileName, HttpServletResponse response) {
        BufferedInputStream bis = null;
        ServletOutputStream outputStream = null;
        try {
            bis = new BufferedInputStream(FastDFSUtils.downFile("group1", fileName));
            response.setHeader("content-type","application/octet-stream");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("身份证正反面.pdf","UTF-8"));
            outputStream = response.getOutputStream();
            PDFUtils.addWaterMark(bis, outputStream,"身份证正反面");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(null!=outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(null != bis) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "上传体检报告")
    @PostMapping("/medicalReport/upload")
    public RespBean uploadMedicalReport(MultipartFile file, Integer id) {
        String[] filePath = FastDFSUtils.upload(file);
        EmployeeData employeeData = employeeDataService.getById(id);
        employeeData.setMedicalReportPath(filePath[1]);
        if(employeeDataService.updateById(employeeData)) {
            return RespBean.success("上传成功",filePath[1]);
        }
        return RespBean.error("上传失败");
    }

    @ApiOperation(value = "下载体检报告")
    @GetMapping(value = "/medicalReport/download", produces = "application/octet-stream")
    public void uploadMedicalReport(String fileName, HttpServletResponse response) {
        BufferedInputStream bis = null;
        ServletOutputStream outputStream = null;
        try {
            bis = new BufferedInputStream(FastDFSUtils.downFile("group1", fileName));
            response.setHeader("content-type","application/octet-stream");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("体检报告.pdf","UTF-8"));
            outputStream = response.getOutputStream();
            PDFUtils.addWaterMark(bis, outputStream,"体检报告");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(null!=outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(null != bis) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "上传学历证书")
    @PostMapping("/degreeCertificate/upload")
    public RespBean uploadDegreeCertificate(MultipartFile file, Integer id) {
        String[] filePath = FastDFSUtils.upload(file);
        EmployeeData employeeData = employeeDataService.getById(id);
        employeeData.setDegreeCertificatePath(filePath[1]);
        if(employeeDataService.updateById(employeeData)) {
            return RespBean.success("上传成功",filePath[1]);
        }
        return RespBean.error("上传失败");
    }

    @ApiOperation(value = "下载学历证书")
    @GetMapping(value = "/degreeCertificate/download", produces = "application/octet-stream")
    public void uploadDegreeCertificate(String fileName, HttpServletResponse response) {
        BufferedInputStream bis = null;
        ServletOutputStream outputStream = null;
        try {
            bis = new BufferedInputStream(FastDFSUtils.downFile("group1", fileName));
            response.setHeader("content-type","application/octet-stream");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("学历证书.pdf","UTF-8"));
            outputStream = response.getOutputStream();
            PDFUtils.addWaterMark(bis, outputStream,"学历证书");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(null!=outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(null != bis) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "上传offer")
    @PostMapping("/offer/upload")
    public RespBean uploadOffer(MultipartFile file, Integer id) {
        String[] filePath = FastDFSUtils.upload(file);
        EmployeeData employeeData = employeeDataService.getById(id);
        employeeData.setOfferPath(filePath[1]);
        if(employeeDataService.updateById(employeeData)) {
            return RespBean.success("上传成功",filePath[1]);
        }
        return RespBean.error("上传失败");
    }

    @ApiOperation(value = "下载offer")
    @GetMapping(value = "/offer/download", produces = "application/octet-stream")
    public void uploadOffer(String fileName, HttpServletResponse response) {
        BufferedInputStream bis = null;
        ServletOutputStream outputStream = null;
        try {
            bis = new BufferedInputStream(FastDFSUtils.downFile("group1", fileName));
            response.setHeader("content-type","application/octet-stream");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("offer.pdf","UTF-8"));
            outputStream = response.getOutputStream();
            PDFUtils.addWaterMark(bis, outputStream,"offer");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(null!=outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(null != bis) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "上传简历")
    @PostMapping("/resume/upload")
    public RespBean uploadResume(MultipartFile file, Integer id) {
        String[] filePath = FastDFSUtils.upload(file);
        EmployeeData employeeData = employeeDataService.getById(id);
        employeeData.setResumePath(filePath[1]);
        if(employeeDataService.updateById(employeeData)) {
            return RespBean.success("上传成功",filePath[1]);
        }
        return RespBean.error("上传失败");
    }

    @ApiOperation(value = "下载简历")
    @GetMapping(value = "/resume/download", produces = "application/octet-stream")
    public void uploadResume(String fileName, HttpServletResponse response) {
        BufferedInputStream bis = null;
        ServletOutputStream outputStream = null;
        try {
            bis = new BufferedInputStream(FastDFSUtils.downFile("group1", fileName));
            response.setHeader("content-type","application/octet-stream");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("入职简历.pdf","UTF-8"));
            outputStream = response.getOutputStream();
            PDFUtils.addWaterMark(bis, outputStream,"入职简历");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(null!=outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(null != bis) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
