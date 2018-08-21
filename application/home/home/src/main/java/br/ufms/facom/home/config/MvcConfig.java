package br.ufms.facom.home.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/anunciante/cadastrar").setViewName("/anunciante/cadastrar");
        registry.addViewController("/imovel/cadastrar").setViewName("/imovel/cadastrar");
    }
}