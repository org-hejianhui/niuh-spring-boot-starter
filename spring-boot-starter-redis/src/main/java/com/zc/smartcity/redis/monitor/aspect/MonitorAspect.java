package com.zc.smartcity.redis.monitor.aspect;

import com.zc.smartcity.redis.common.Constants;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * <p>
 *  MonitorAspect
 * </p>
 *
 * @author: hejianhui
 * @create: 2019-04-09 16:01
 * @see MonitorAspect
 * @since JDK1.8
 */
@Aspect
public class MonitorAspect {

    @Around("execution(* com.zc.smartcity.redis.service.impl..*(..))l")
    public Object AspectHandlerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        //调用目标方法之前执行的动作
        // System.out.println("调用方法之前: invocation对象：[" + methodInvocation + "]");
        long beginTime = System.currentTimeMillis();
        //调用目标方法
        Object obj=joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        Signature sig = joinPoint.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
             throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = joinPoint.getTarget();

        //System.out.println(msig.getName());

        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        StringBuilder methodFullName = new StringBuilder("[");
        methodFullName.append(currentMethod);
        methodFullName.append("]");
        String usedNumName = methodFullName + Constants.DEFAULT_SEPARATOR + Constants.MONITOR_GEDIS_USED_NUM_NAME;
        String usedTimeName = methodFullName + Constants.DEFAULT_SEPARATOR + Constants.MONITOR_GEDIS_USED_TIME_NAME;
        Integer usedNum = Constants.MONITOR_MAP.get(usedNumName);
        if(usedNum == null){
            usedNum = 0;
        }
        usedNum +=1;
        Integer usedTime = Constants.MONITOR_MAP.get(usedTimeName);
        if(usedTime == null){
            usedTime = 0;
        }
        usedTime += Long.bitCount(endTime - beginTime);
        Constants.MONITOR_MAP.put(usedNumName, usedNum);
        Constants.MONITOR_MAP.put(usedTimeName, usedTime);
//        System.out.println("调用方法: invocation对象：[" + methodInvocation.getMethod() + "]"
//                + " Run time is " + usedTime +" ms");
//        System.out.println(Constants.MONITOR_MAP);
        //调用目标方法之后执行的动作
//        System.out.println("调用结束...");
        return obj;
    }
}
