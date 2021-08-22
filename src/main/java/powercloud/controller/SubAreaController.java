package powercloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import powercloud.model.SubArea;
import powercloud.service.SubAreaService;

import java.util.List;

@RestController
@RequestMapping("/subarea")
public class SubAreaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SubAreaService service;

    @Autowired
    public SubAreaController(SubAreaService subAreaService) {
        this.service = subAreaService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SubArea> findAll() {
        logger.info("@GetMapping/findAll");
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubArea findById(@PathVariable Long id) {
        logger.info("@GetMapping/findById");
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubArea save(@RequestBody SubArea subArea) {
        logger.info("@GetMapping/save");
        return service.save(subArea);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public SubArea update(@RequestBody SubArea subArea){
        logger.info("@GetMapping/update");
        return service.update(subArea);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id){
        logger.info("@GetMapping/deleteById");
        service.deleteById(id);
    }

    @GetMapping("/maxRevenue")
    @ResponseStatus(HttpStatus.OK)
    public SubArea getMaxRevenue(){
        logger.info("@GetMapping/maxRevenue");
        return service.getMaxRevenue();
    }

    @GetMapping("/minRevenue")
    @ResponseStatus(HttpStatus.OK)
    public SubArea getMinRevenue(){
        logger.info("@GetMapping/minRevenue");
        return service.getMinRevenue();
    }

}
