package com.example.rbac.service.impl;

import com.example.rbac.pojo.Attendance;
import com.example.rbac.mapper.AttendanceMapper;
import com.example.rbac.service.IAttendanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
