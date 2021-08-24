package powercloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import powercloud.model.Department;
import powercloud.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/subarea")
public class DepartmentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DepartmentService service;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.service = departmentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Department> findAll() {
        logger.info("@GetMapping/findAll");
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department findById(@PathVariable Long id) {
        logger.info("@GetMapping/findById");
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department save(@RequestBody Department department) {
        logger.info("@GetMapping/save");
        return service.save(department);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Department update(@RequestBody Department department){
        logger.info("@GetMapping/update");
        return service.update(department);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id){
        logger.info("@GetMapping/deleteById");
        service.deleteById(id);
    }

    @GetMapping("/maxRevenue")
    @ResponseStatus(HttpStatus.OK)
    public Department getMaxRevenue(){
        logger.info("@GetMapping/maxRevenue");
        return service.getMaxRevenue();
    }

    @GetMapping("/minRevenue")
    @ResponseStatus(HttpStatus.OK)
    public Department getMinRevenue(){
        logger.info("@GetMapping/minRevenue");
        return service.getMinRevenue();
    }

}
