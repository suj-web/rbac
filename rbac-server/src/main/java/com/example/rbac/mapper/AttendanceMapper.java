package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.Attendance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

/**
 * 考勤Mapper
 * @author suj
 * @since 2022-03-08
 */
@Repository
public interface AttendanceMapper extends BaseMapper<Attendance> {

    /**
     * 人事管理-员工考勤-查询员工考勤信息
     * @param page
     * @param beginDateScope
     * @return
     */
    IPage<Attendance> getAttendances(Page<Attendance> page, @Param("depId")Integer depId, @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     * 查询考勤信息用于导出Excel
     * @param dateScope
     * @return
     */
    List<Attendance> getAttendanceForExcel(@Param("dateScope") LocalDate[] dateScope);
}
