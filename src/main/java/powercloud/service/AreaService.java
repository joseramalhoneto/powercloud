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

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        if(!AreaUtility.isAreaValid(area))
            throw new AreaInvalidException("Area invalid.");

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

    public double getRevenueById(Long id) {
        if(!repository.existsById(id))
            throw new AreaNotFoundException("Area not found.");

        return repository.getRevenueById(id);
    }

    public List<Department> getDepartmentsById(Long id) {
        if(!repository.existsById(id))
            throw new AreaNotFoundException("Area not found.");

        Optional<Area> first = repository.findAll()
                                    .stream()
                                    .filter(area -> area.getId().equals(id))
                                    .findAny();

        return first.get().getDepartments();
    }

    public Area getMaxRevenue() {
        Map<?, ?> maxRevenue = repository.getMaxRevenue();
        Optional<Area> areaMaxRevenue = repository.
                findById(Long.parseLong(maxRevenue.get("area_id").toString()));

        if(areaMaxRevenue.isEmpty())
            return null;

        return areaMaxRevenue.get();
    }

}
