package com.neo.Configs;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;
import java.io.IOException;

/**
 * Created by Praveen Gupta on 3/20/2017.
 */
public class NeoWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class<?>[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{ WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }


    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        File file=new File("teq_temp");
        try {
            registration.setMultipartConfig(new MultipartConfigElement(file.getCanonicalPath(),1000000, 2000000, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        registration.setLoadOnStartup(1);
    }

}
