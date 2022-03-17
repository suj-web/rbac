package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.Attendance;
import com.example.rbac.mapper.AttendanceMapper;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IAttendanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-03-08
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements IAttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    /**
     * 考勤信息统计
     * @param currentPage
     * @param size
     * @param localDate
     * @param absenteeism
     * @return
     */
    @Override
    public RespPageBean getAllAttendance(Integer currentPage, Integer size, String localDate, Boolean absenteeism) {
        Page<Attendance> page = new Page<>(currentPage, size);
        IPage<Attendance> attendanceIPage = attendanceMapper.getAllAttendance(page, localDate, absenteeism);
        RespPageBean respPageBean = new RespPageBean(attendanceIPage.getTotal(), attendanceIPage.getRecords());
        return respPageBean;
    }
}
