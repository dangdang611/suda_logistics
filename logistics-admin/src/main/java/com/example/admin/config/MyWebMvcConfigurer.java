package com.example.admin.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.admin.componet.JwtOperator;
import com.example.admin.componet.LoginInterceptor;
import com.example.system.componet.RedisOperator;
import com.example.system.service.LogisticsUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Locale;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private JwtOperator jwtOperator;

    @Autowired
    private LogisticsUserService logisticsUserService;

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor().
                setJwtOperator(jwtOperator)
                .setRedisOperator(redisOperator)
                .setLogisticsUserService(logisticsUserService))
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/register","/system/index/pageViews");//页面访问量操作
    }
    /**
     * 拦截器转换
     * web层统一处理Long转String问题
     * @param converters 需要转换的对象
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        //将Long转为string 解决id过大 js显示问题
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(0, jackson2HttpMessageConverter);
    }

    // 配置空串传过来变成null值
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(String.class, new Formatter<String>() {
            @Override
            public String parse(String text, Locale locale) throws ParseException {
                if(StringUtils.isBlank(text)){
                    return null;
                }
                return text;
            }
            @Override
            public String print(String object, Locale locale) {
                return object;
            }
        });
    }
}