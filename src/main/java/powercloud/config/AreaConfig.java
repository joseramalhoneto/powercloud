package powercloud.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import powercloud.model.Area;
import powercloud.service.AreaService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class AreaConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    CommandLineRunner runner(AreaService areaService) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Area>> typeReference = new TypeReference<List<Area>>(){};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/static/json/powercloud_structure.json");
            try {
                areaService.deleteAll();
                List<Area> areas = mapper.readValue(inputStream,typeReference);
                areaService.saveAll(areas);
            } catch (IOException e){
                logger.error(e.getMessage());
            }
        };
    }

}
