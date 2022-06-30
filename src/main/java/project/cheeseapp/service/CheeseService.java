package project.cheeseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.cheeseapp.controller.request.CheeseAddRequest;
import project.cheeseapp.controller.request.CheeseUpdateRequest;
import project.cheeseapp.model.Cheese;
import project.cheeseapp.model.RipeMethod;
import project.cheeseapp.repository.CheeseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CheeseService {

    @Autowired
    private CheeseRepository cheeseRepo;

    public Cheese getCheeseByGrade(String grade) {
        Cheese cheese = cheeseRepo.findByGrade(grade);
        if (cheese == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "grade not found");
        }
        return cheese;
    }

    public List<String> getAllGrades() {
        List<Cheese> cheeses = cheeseRepo.findAll();
        List<String> grades = new ArrayList<>();
        for (Cheese ch : cheeses) {
            grades.add(ch.getGrade());
        }
        return grades;
    }

    public Cheese findById(int id) {
        Optional<Cheese> cheese = cheeseRepo.findById(id);
        if (cheese.isPresent()) {
            return cheese.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cheese with such id not found");
    }

    public Cheese createCheeseFromRequest(CheeseAddRequest request) {
        Cheese cheese = new Cheese();
        cheese.setGrade(request.getGrade());
        cheese.setType(request.getType());
        return cheese;
    }

    public Cheese updateCheeseFromRequest(Cheese cheese, CheeseUpdateRequest request) {
        cheese.setType(request.getType());
        cheese.setGrade(request.getGrade());
        return cheese;
    }

    public Cheese saveCheese(Cheese cheese) {
        return cheeseRepo.save(cheese);
    }

    public Cheese assignMethod(Cheese cheese, RipeMethod ripeMethod) {
        cheese.setRipeMethod(ripeMethod);
        return cheeseRepo.save(cheese);
    }

    public void deleteCheeseById(int cheeseId) {
        cheeseRepo.deleteById(cheeseId);
    }

    public List<Cheese> getAllCheeses() {
        return cheeseRepo.findAll();
    }
}
