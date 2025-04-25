package com.aeromaintenance.PurchaseRequisition;

import com.common.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
public class PartDescController {

	@Autowired
    private PurchaseRequisitionRepository repository;

    @GetMapping
    public List<PurchaseRequisitionDTO> getAllPartNumbersAndDescriptions() {
        List<PurchaseRequisitionDTO> partList = repository.findAllPartNumbersAndDescriptions();
        if (repository.findAllPartNumbersAndDescriptions().isEmpty()){
            ResponseBean<Void> response = new ResponseBean<>("500","No Entries in database",null);
            return (List<PurchaseRequisitionDTO>) ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(partList).getBody();
    }
}
