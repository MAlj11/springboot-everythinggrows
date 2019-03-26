//package cn.everythinggrows.boot.egboot.blog.aop;
//
//
//import cn.everythinggrows.boot.egboot.blog.Utils.CookieUtils;
//import cn.everythinggrows.boot.egboot.blog.Utils.EgResult;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//
//@Aspect
//@Component
//public class ControllerInterceptor {
//
//    @Autowired
//    RestTemplate restTemplate;
//
//    /**
//     * 定义拦截规则：拦截com.huidong.qzh.standard包下面的所有类中，有@RequestMapping注解的方法。
//     */
//    @Pointcut(value = "execution(* cn.everythinggrows.boot.egboot.blog.*(..))" +
//            " && ( @annotation(org.springframework.web.bind.annotation.RequestMapping )" +
//            " || @annotation(org.springframework.web.bind.annotation.GetMapping) " +
//            " || @annotation(org.springframework.web.bind.annotation.PostMapping ))")
//    public void controllerMethodPointcut() {
//    }
//
//    /**
//     * 拦截器具体实现
//     *
//     * @param pjp
//     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
//     */
//    @Around("controllerMethodPointcut()")
//    public Object Interceptor(ProceedingJoinPoint pjp) {
//        long beginTime = System.currentTimeMillis();
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod();
//        //获取被拦截的方法
//        String methodName = method.getName();
//        //获取被拦截的方法名
//
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
//
//        Object result = null;
//
//        if (isLoginRequired(method)) {
//            EgResult loginResult = isLogin(request);
//            if (loginResult.getStatus() != 200) {
//                result = loginResult;
//            }
//        }
//        if (result == null) {
//            try {
//                result = pjp.proceed();
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//                result = EgResult.error(1000000,throwable.getMessage());
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 判断一个方法是否需要登录
//     *
//     * @param method 方法
//     * @return
//     */
//    private boolean isLoginRequired(Method method) {
//        boolean result = false;
//        if (method.isAnnotationPresent(loginRequired.class)) {
//            result = method.getAnnotation(loginRequired.class).loginRequired();
//        }
//        return result;
//    }
//
//    //判断是否已经登录
//    private EgResult isLogin(HttpServletRequest request) {
//        String token = request.getHeader("x-eg-session");
//        if (StringUtils.isBlank(token)) {
//            token = CookieUtils.getCookieValue(request, "x-eg-session");
//            if (StringUtils.isBlank(token)) {
//                return EgResult.build(400, "会话过期，请重新登陆", "");
//            }
//        }
//        ResponseEntity<EgResult> responseEntity = restTemplate.getForEntity("http://QZH-SSO/token/{1}", EgResult.class, token);
//        return responseEntity.getBody();
//    }
//}
