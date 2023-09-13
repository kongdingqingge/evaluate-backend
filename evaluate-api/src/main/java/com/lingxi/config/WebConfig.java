package com.lingxi.config;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        // 解决 SWAGGER 404报错
//        registry.addResourceHandler("swagger-ui/index.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        //springboot 2.0后fastJson不生效，需增加以下代码,就是遍历删除原有的 MappingJackson2HttpMessageConverter
        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                iterator.remove();
            }
        }

        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //1.注册fastJSON转换器
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd");
        config.setCharset(Charset.forName("UTF-8"));
        //2.配置相关序列化的操作
        config.setSerializerFeatures(
                SerializerFeature.PrettyFormat, //结果是否格式化,默认为false
                SerializerFeature.WriteMapNullValue,  //是否输出值为null的字段，默认为false
                SerializerFeature.WriteNullListAsEmpty, //List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullStringAsEmpty //字符类型字段如果为null,输出为”“,而非null
        );
        //3.将配置写入到FastJsonConfig的配置中
        converter.setFastJsonConfig(config);
        //4.添加媒体类型,将自定义的fastJSON转换器添加到所有converters中
        converters.add(converter);
        List<MediaType> mediaTypes = new ArrayList<>(16);
        mediaTypes.add(MediaType.APPLICATION_JSON);//用来告诉服务端，消息主体是序列化后的JSON字符串
        converter.setSupportedMediaTypes(mediaTypes);
        converters.add(0, converter);//指定
    }
}


