package cn.acyou.gemini.config;

import cn.acyou.gemini.exception.ServiceException;
import cn.acyou.gemini.util.ResultInfo;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 */
@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     *  使用CORS解决解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT");
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add((request, response, o, e) -> {
            ResultInfo resultInfo = new ResultInfo();
            ModelAndView mv = new ModelAndView("error");
            Throwable t = Throwables.getRootCause(e);
            log.error("统一异常处理，" + e.getMessage());
            e.printStackTrace();
            if (t instanceof ServiceException){
                resultInfo.setCode(400);
                resultInfo.setMessage(t.getMessage());
            }else {
                resultInfo.setCode(400);
                resultInfo.setMessage("喔呦，程序奔溃咯！");
            }
            //响应Ajax请求
            if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
                responseResult(response, resultInfo);
                return mv;
            }
            mv.addObject("code", resultInfo.getCode());
            mv.addObject("message", resultInfo.getMessage());
            String requestUrl = request.getRequestURL().toString();
            mv.addObject("requestUrl", requestUrl);
            return mv;
        });
    }

    private void responseResult(HttpServletResponse response, ResultInfo result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        response.setStatus(200);
        try {
            PrintWriter pw = response.getWriter();
            pw.write(JSON.toJSONString(result));
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
