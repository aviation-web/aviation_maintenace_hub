package com.aeromaintenance.storeAcceptance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/storeAcceptance")
public class StoreAccController {
	
	@Autowired
	private StoreAccService StoreAccService;
	
	//REST API
	@PostMapping
	public ResponseEntity<StoreAccDto> createStoreAcc(@RequestBody StoreAccDto storeDto)
	{	StoreAccDto savedStoreAcc = StoreAccService.createStore(storeDto);
		return new ResponseEntity<>(savedStoreAcc, HttpStatus.CREATED);
	}
	
	//REST API
	@GetMapping("{partNum}")
	public ResponseEntity<StoreAccDto> getStoreById(@PathVariable("partNum") String partNum)
	{
		StoreAccDto storeAccDto = StoreAccService.getStoreByPartNum(partNum);
		return ResponseEntity.ok(storeAccDto);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<String>> getAllStore()
	{
		List<String> partNum = StoreAccService.getPartNumbersList();
		return ResponseEntity.ok(partNum);
	}
	
	
	@PutMapping("{partNum}")
	public ResponseEntity<StoreAccDto> updateEmploye(@PathVariable("partNum") String partNum,@RequestBody StoreAccDto storeAccDto)
	{
		StoreAccDto updatedstoreAccDto = StoreAccService.updateStore(partNum, storeAccDto);
		return ResponseEntity.ok(updatedstoreAccDto);
	}
	
	@DeleteMapping("{partNum}")
	public ResponseEntity<String> deleteStore (@PathVariable("partNum") String partNum)
	{
		StoreAccService.deleteStore(partNum);;
		return ResponseEntity.ok("store with part number :" + partNum + ", has been deleted");
	}
}
