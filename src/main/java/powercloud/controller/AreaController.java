package powercloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import powercloud.model.Area;
import powercloud.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/area")
public class AreaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AreaService service;

    @Autowired
    public AreaController(AreaService areaService) {
        this.service = areaService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Area> findAll() {
        logger.info("@GetMapping/findAll");
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Area findById(@PathVariable Long id) {
        logger.info("@GetMapping/findById");
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Area save(@RequestBody Area area) {
        logger.info("@GetMapping/save");
        return service.save(area);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Area update(@RequestBody Area area){
        logger.info("@GetMapping/update");
        return service.update(area);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id){
        logger.info("@GetMapping/deleteById");
        service.deleteById(id);
    }

    @GetMapping("/maxRevenue")
    @ResponseStatus(HttpStatus.OK)
    public Area getMaxRevenue(){
        logger.info("@GetMapping/maxRevenue");
        return service.getMaxRevenue();
    }

    @GetMapping("/minRevenue")
    @ResponseStatus(HttpStatus.OK)
    public Area getMinRevenue(){
        logger.info("@GetMapping/minRevenue");
        return service.getMinRevenue();
    }

}