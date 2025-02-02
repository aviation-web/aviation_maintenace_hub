package com.aeromaintenance.storeAcceptance;

import java.util.List;

public interface StoreAccService {
	StoreAccDto createStore(StoreAccDto partNum);
	StoreAccDto getStoreByPartNum(String partNum);
	List<String> getPartNumbersList();
	StoreAccDto updateStore(String partNum, StoreAccDto updatedStore);
	void deleteStore(String storeId);
	
}
