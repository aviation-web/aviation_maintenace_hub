package com.aeromaintenance.storeAcceptance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class StoreAccServiceImpl implements StoreAccService {@Autowired
	private StoreAccRepository StoreAccRepository;


	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public StoreAccDto createStore(StoreAccDto partNum) 
	{
		StoreAcc store = StoreAccMapper.mapToStoreAcc(partNum); 
		StoreAcc savedStoreAcc =  StoreAccRepository.save(store);
		
		return StoreAccMapper.mapToStoreDto(savedStoreAcc);
	}

	@Override
	public StoreAccDto getStoreByPartNum(String partNum) {

		StoreAcc StoreAcc =  StoreAccRepository.findById(partNum)
				.orElseThrow(() -> null);
		
		StoreAccDto StoreAccDto = StoreAccMapper.mapToStoreDto(StoreAcc);
		return StoreAccDto;
	}

	@Override
	public StoreAccDto updateStore(String partNum, StoreAccDto updatedStore) 
	{
		StoreAcc storeAcc = StoreAccRepository.findById(partNum).orElseThrow(() -> null);
		
		if (storeAcc == null)
		{
			return null;
		}
		
		updatedStore.setPartNum(partNum);
		StoreAcc updatedStoreAcc = StoreAccRepository.save(StoreAccMapper.mapToStoreAcc(updatedStore));
		return StoreAccMapper.mapToStoreDto(updatedStoreAcc);
		
	}

	@Override
	public void deleteStore(String partNum) {
		
		StoreAcc storeAcc = StoreAccRepository.findById(partNum).orElseThrow(() -> null);
		if(storeAcc == null)
		{
			return ;
		}
		StoreAccRepository.deleteById(partNum);
		
	}

	public List<String> getPartNumbersList() {
        String sql = "SELECT DISTINCT PART_NUM FROM STORE_ACCEPTANCE";
        
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);

        List<String> partNumbers = new ArrayList();
        for (Map<String, Object> row : results) {
            partNumbers.add((String) row.get("PART_NUM"));
        }
        
        return partNumbers;
    }}
