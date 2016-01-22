package be.vdab;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter{
	
	@Bean
	public XsdSchema countriesSchema() {
	  return new SimpleXsdSchema(new ClassPathResource("serviceTypes.xsd"));
	}

	// WSDL zal er zijn op  http://localhost:8080/ws/countries.wsdl
	@Bean(name = "countries")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
	  DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
	  definition.setPortTypeName("Countries"); // naam van je service in het WSDL bestand
	  definition.setLocationUri("/ws"); // begin van de URL van het WSDL bestand
	  definition.setSchema(countriesSchema); // gebruik beam op vorige slide
	  return definition;
	}
	
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
	  MessageDispatcherServlet servlet = new MessageDispatcherServlet();
	  servlet.setApplicationContext(applicationContext);
	  // relatieve URL’s in WSDL converteren naar absolute URL’s aan de hand van URL prefix
	  // voor deze dispatcher (/ws/*):
	  servlet.setTransformWsdlLocations(true);
	  return new ServletRegistrationBean(servlet, "/ws/*");
	}


}
