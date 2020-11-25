package com.siwo;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication(exclude = { MultipartAutoConfiguration.class })
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@ComponentScan(basePackageClasses = EduCloudConfig.class)
public class EduCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduCloudApplication.class, args);
	}

	@Bean("multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setMaxInMemorySize(30);
		return resolver;
	}

	/**
	 * http重定向到https
	 * 
	 * @return
	 */
//	@Bean
//	public ServletWebServerFactory servletContainer() {
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//			@Override
//			protected void postProcessContext(Context context) {
//				SecurityConstraint constraint = new SecurityConstraint();
//				constraint.setUserConstraint("CONFIDENTIAL");
//				SecurityCollection collection = new SecurityCollection();
//				collection.addPattern("/*");
//				collection.addMethod("HEAD");
//				collection.addMethod("PUT");
//				collection.addMethod("DELETE");
//				collection.addMethod("OPTIONS");
//				collection.addMethod("TRACE");
//				collection.addMethod("COPY");
//				collection.addMethod("SEARCH");
//				collection.addMethod("PROPFIND");
//				collection.addMethod("DEFAULT_PROTOCOL");
//				constraint.addCollection(collection);
//				context.addConstraint(constraint);
//			}
//		};
//		tomcat.addAdditionalTomcatConnectors(httpConnector());
//		return tomcat;
//	}
//
//	@Bean
//	public Connector httpConnector() {
//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		connector.setScheme("http");
//		// Connector监听的http的默认端口号
//		connector.setPort(80);
//		connector.setSecure(false);
//		// 监听到http的端口号后转向到的https的端口号,也就是项目配置的port
//		connector.setRedirectPort(443);
//		return connector;
//	}
}
