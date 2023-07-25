package com.sky.aop;

import com.sky.annotion.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Component
@Aspect
@Slf4j
public class AutoFillAspect {

    @Before("execution(* com.sky.mapper.*.*(..))&&@annotation(com.sky.annotion.AutoFill)")
    public void autoFill(JoinPoint joinPoint) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
//   1.得到方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        得到方法对象
        Method method = signature.getMethod();
        AutoFill annotation = method.getAnnotation(AutoFill.class);

//        1.1拿到参数--得到对象
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length== 0){
            return;
        }
        Object arg = args[0];
        log.info("参数对象类型为：{}",arg.toString());
//        1.2
        if (annotation.value()==OperationType.INSERT) {
//           执行增加
            Method setCreateTime = arg.getClass().getMethod("setCreateTime", LocalDateTime.class);
            setCreateTime.invoke(arg, LocalDateTime.now());
            Method setUpdateTime = arg.getClass().getMethod("setUpdateTime", LocalDateTime.class);
            setUpdateTime.invoke(arg, LocalDateTime.now());
            Method setCreateUser = arg.getClass().getMethod("setCreateUser", Long.class);
//            Method setCreateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            setCreateUser.invoke(arg, BaseContext.getCurrentId());
            Method setUpdateUser = arg.getClass().getMethod("setUpdateUser", Long.class);
            setUpdateUser.invoke(arg, BaseContext.getCurrentId());
            log.info("参数对象类型为：{}",arg.toString());
        } else {
//           执行更新
            Method setUpdateTime = arg.getClass().getMethod("setUpdateTime", LocalDateTime.class);
            setUpdateTime.invoke(arg, LocalDateTime.now());
            Method setUpdateUser = arg.getClass().getMethod("setUpdateUser", Long.class);
            setUpdateUser.invoke(arg, BaseContext.getCurrentId());
            log.info("参数对象类型为：{}",arg.toString());
        }
    }


}

