package project.cheeseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.cheeseapp.controller.request.CheeseAddRequest;
import project.cheeseapp.controller.request.MethodUpdateRequest;
import project.cheeseapp.model.RipeMethod;
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
        ripeMethod.setMaxHum(request.getMaxHum());
        ripeMethod.setMaxTemp(request.getMaxTemp());
        ripeMethod.setRipeningDays(request.getRipeningDays());
        ripeMethod.setMinHum(request.getMinHum());
        ripeMethod.setMinTemp(request.getMinTemp());
        ripeMethod.setMaintainDays(request.getMaintainDays());
        return methodRepo.save(ripeMethod);
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
        ripeMethod.setRipeningDays(request.getRipeningDays());
        return ripeMethod;
    }
}
