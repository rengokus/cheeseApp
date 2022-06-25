package project.cheeseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.cheeseapp.controller.request.CheeseAddRequest;
import project.cheeseapp.controller.request.MethodUpdateRequest;
import project.cheeseapp.entity.RipeMethod;
import project.cheeseapp.repository.RipeMethodRepository;

import java.util.Optional;

@Service
public class RipeMethodService {

    @Autowired
    private RipeMethodRepository methodRepo;

    public RipeMethod saveMethod(RipeMethod ripeMethod) {
        return methodRepo.save(ripeMethod);
    }

    public RipeMethod createMethodFromRequest(CheeseAddRequest request) {
        RipeMethod ripeMethod = new RipeMethod();
        ripeMethod.setMaxDays(request.getMaxDays());
        ripeMethod.setMaxHum(request.getMaxHum());
        ripeMethod.setMaxTemp(request.getMaxTemp());
        ripeMethod.setMinDays(request.getMinDays());
        ripeMethod.setMinHum(request.getMinHum());
        ripeMethod.setMinTemp(request.getMinTemp());
        return ripeMethod;
    }

    public RipeMethod findById(int id) {
        Optional<RipeMethod> method = methodRepo.findById(id);
        if (method.isPresent()) {
            return method.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "method with such id not found");
    }

    public RipeMethod updateMethodFromRequest(RipeMethod ripeMethod, MethodUpdateRequest request) {
        ripeMethod.setMinTemp(request.getMinTemp());
        ripeMethod.setMaxTemp(request.getMaxTemp());
        ripeMethod.setMinHum(request.getMinHum());
        ripeMethod.setMaxHum(request.getMaxHum());
        ripeMethod.setMinDays(request.getMinDays());
        ripeMethod.setMaxDays(request.getMaxDays());
        return ripeMethod;
    }
}
