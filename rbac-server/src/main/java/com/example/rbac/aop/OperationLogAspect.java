package com.example.rbac.aop;

import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.mapper.OplogMapper;
import com.example.rbac.pojo.Oplog;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.utils.IpUtil;
import com.example.rbac.utils.UserUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 操作日志切面处理类
 * @Author suj
 * @create 2022/3/14
 */
@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private OplogMapper oplogMapper;

    /**
     * 设置操作日志切入点 在注解的位置切入代码
     */

    @Pointcut("@annotation(com.example.rbac.annotation.OperationLogAnnotation)")
    public void operLogPointCut() {
    }

    /**
     * 记录操作日志
     * @param joinPoint 方法的执行点
     * @param result 方法返回值
     * @throws Throwable
     */
    @AfterReturning(returning = "result", value = "operLogPointCut()")
    public void saveOperLog(JoinPoint joinPoint, Object result) throws Throwable {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            RespBean respBean;
            try {
                respBean = (RespBean) request;
            } catch (Exception e) {
                respBean = RespBean.success("操作成功");
            }
            Oplog oplog = new Oplog();
            //从切面切入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取操作
            OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
            if(annotation != null) {
                oplog.setModel(annotation.operModul());
                oplog.setType(annotation.operType());
                oplog.setDescription(annotation.operDesc());
            }
            //操作用户
            oplog.setOperator(UserUtils.getCurrentUser().getUsername());
            //操作ip
            oplog.setIp(IpUtil.getIpAddress(request));
            //返回值信息
            oplog.setResult(respBean.getMessage());
            //保存日志
            oplogMapper.insert(oplog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
