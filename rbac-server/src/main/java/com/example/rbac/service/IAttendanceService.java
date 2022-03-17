package com.example.rbac.service;

import com.example.rbac.pojo.Attendance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespPageBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-03-08
 */
public interface IAttendanceService extends IService<Attendance> {

    /**
     * 出勤信息统计
     * @param currentPage
     * @param size
     * @param localDate
     * @param absenteeism
     * @return
     */
    RespPageBean getAllAttendance(Integer currentPage, Integer size, String localDate, Boolean absenteeism);
}
