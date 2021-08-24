package powercloud.service;

import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import powercloud.exception.AreaInvalidException;
import powercloud.exception.AreaNotFoundException;
import powercloud.model.Area;
import powercloud.model.Department;
import powercloud.repository.AreaRepository;
import powercloud.utility.AreaUtility;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AreaService {

    private final AreaRepository repository;

    @Autowired
    public AreaService(AreaRepository areaRepository) {
        this.repository = areaRepository;
    }

    public List<Area> findAll() {
        return repository.findAll();
    }

    public Area findById(Long id) {
        return repository.findById(id)
                            .orElseThrow(() -> new AreaNotFoundException("Area not found."));
    }

    @Synchronized
    public Area save(Area area) {
        if(!AreaUtility.isAreaValid(area))
            throw new AreaInvalidException("Area invalid.");

        return repository.save(area);
    }

    @Synchronized
    public Area update(Area area) {
        if(!repository.existsById(area.getId()))
            throw new AreaNotFoundException("Area not found.");

        return repository.save(area);
    }

    public void saveAll(List<Area> areas) {
        repository.saveAll(areas);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    @Synchronized
    public void deleteById(Long id) {
        if(!repository.existsById(id))
            throw new AreaNotFoundException("Area not found.");

        repository.deleteById(id);
    }

    public Area getMaxRevenue() {
        List<Area> areas = repository.findAll();
//        areas.stream()
//               .collect(Collectors.groupingBy(Area::getDepartments, Collectors.maxBy(Comparator.comparing())));


        return null;
    }

    public Area getMinRevenue() {
        return null;
    }
}
