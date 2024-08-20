package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.tax.DeveloperTax;
import com.workintech.s17d2.tax.Taxable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class DeveloperController {
    private Taxable taxable;
    public Map<Integer, Developer> developers = new HashMap<>();

    @Autowired
    public DeveloperController(Taxable taxable){
        this.taxable = taxable;
    }


    @GetMapping("/developers")
    public List<Developer> seeDevs(){
        return developers.values().stream().toList();
    }
    @GetMapping("/developers/{id}")
    public Developer seeDev(@PathVariable int id){
        Developer developer = developers.get(id);
        return developer;
    }
    @PostMapping("/developers")
    @ResponseStatus(HttpStatus.CREATED)
    public Developer postDev(@RequestBody Developer developer){
        Developer developer1 = new Developer();
        switch (developer.getExperience()){
            case JUNIOR:
                developer1.setSalary(developer.getSalary()- developer.getSimpleTaxRate());
                break;
            case MID:
                developer1.setSalary(developer.getSalary()- developer.getMiddleTaxRate());
                break;
            case SENIOR:
                developer1.setSalary(developer.getSalary()- developer.getUpperTaxRate());
                break;
        }
        developers.put(developer.getId(), (developer));
        return developer;
    }
    @PutMapping("/developers/{id}")
    public Developer upDate(@PathVariable int id, @RequestBody Developer developer){
        developers.put(id, developer);
        return developer;
    }
    @DeleteMapping("/developers/{id}")
    public void delete(@PathVariable int id){
        developers.remove(id);
    }
    public void init() {

    }
}
