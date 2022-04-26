package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rbac.mapper.EmployeeDataMapper;
import com.example.rbac.pojo.EmployeeData;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IEmployeeDataService;
import com.example.rbac.utils.FastDFSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-04-25
 */
@Service
public class EmployeeDataServiceImpl extends ServiceImpl<EmployeeDataMapper, EmployeeData> implements IEmployeeDataService {

    @Autowired
    private EmployeeDataMapper employeeDataMapper;
    /**
     * 查询员工高级资料
     * @param currentPage
     * @param size
     * @param depId
     * @param name
     * @return
     */
    @Override
    public RespPageBean getAllEmpData(Integer currentPage, Integer size, Integer depId, String name) {
        Page<EmployeeData> page = new Page<>(currentPage, size);
        IPage<EmployeeData> employeeDataIPage = employeeDataMapper.getAllEmpData(page, depId, name);
        RespPageBean respPageBean = new RespPageBean(employeeDataIPage.getTotal(), employeeDataIPage.getRecords());
        return respPageBean;
    }

    /**
     * 修改员工高级资料
     * @param employeeData
     * @return
     */
    @Override
    public RespBean updateEmpData(EmployeeData employeeData) {
        EmployeeData data = employeeDataMapper.selectById(employeeData);
        try {
            if ((null == employeeData.getContractPath() || "".equals(employeeData.getContractPath())) && (null != data.getContractPath() && !"".equals(data.getContractPath()))) {
                FastDFSUtils.deleteFile("group1",data.getContractPath());
            }
            if ((null == employeeData.getDegreeCertificatePath() || "".equals(employeeData.getDegreeCertificatePath())) && (null != data.getDegreeCertificatePath() && !"".equals(data.getDegreeCertificatePath()))) {
                FastDFSUtils.deleteFile("group1",data.getDegreeCertificatePath());
            }
            if ((null == employeeData.getIdCardPath() || "".equals(employeeData.getIdCardPath())) && (null != data.getIdCardPath() && !"".equals(data.getIdCardPath()))) {
                FastDFSUtils.deleteFile("group1",data.getIdCardPath());
            }
            if ((null == employeeData.getMedicalReportPath() || "".equals(employeeData.getMedicalReportPath())) && (null != data.getMedicalReportPath() && !"".equals(data.getMedicalReportPath()))) {
                FastDFSUtils.deleteFile("group1",data.getMedicalReportPath());
            }
            if ((null == employeeData.getOfferPath() || "".equals(employeeData.getOfferPath())) && (null != data.getOfferPath() && !"".equals(data.getOfferPath()))) {
                FastDFSUtils.deleteFile("group1",data.getOfferPath());
            }
            if ((null == employeeData.getResumePath() || "".equals(employeeData.getResumePath())) && (null != data.getResumePath() && !"".equals(data.getResumePath()))) {
                FastDFSUtils.deleteFile("group1",data.getResumePath());
            }
            if (1 == employeeDataMapper.updateById(employeeData)) {
                return RespBean.success("删除成功");
            }
        } catch (Exception e) {
            return RespBean.error("删除失败");
        }
        return RespBean.error("删除失败");
    }
}
