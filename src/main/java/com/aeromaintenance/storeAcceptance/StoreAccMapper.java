
package com.aeromaintenance.storeAcceptance;
import org.modelmapper.ModelMapper;


public class StoreAccMapper {
	private final static ModelMapper modelMapper = new ModelMapper();

	public static StoreAccDto mapToStoreDto(StoreAcc store) {
		return modelMapper.map(store, StoreAccDto.class);
	}

	public static StoreAcc mapToStoreAcc(StoreAccDto storeDto) {
		return modelMapper.map(storeDto, StoreAcc.class);
	}
}
