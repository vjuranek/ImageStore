package cz.jurankovi.imgserver.util;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Singleton
//@ApplicationScoped
@Startup
public class ConfigurationProducer {

    private Properties props;

    @PostConstruct
    public void loadProperties() {
        try {
            props = new Properties();
            props.load(ConfigurationProducer.class.getResourceAsStream("/config.properties"));
            for (Object key : props.keySet()) {
                System.out.printf("%s -> %s ", key, props.get(key));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot open config.properties");
        }
    }

    @Produces
    @Configuration(property = "")
    public String getProperty(InjectionPoint ip) {
        Configuration cfgAnnot = ip.getAnnotated().getAnnotation(Configuration.class);
        if (cfgAnnot != null && props.containsKey(cfgAnnot.property())) {
            return props.getProperty(cfgAnnot.property());
        }
        throw new IllegalStateException("No configuration property for injection point " + ip);
    }

}
