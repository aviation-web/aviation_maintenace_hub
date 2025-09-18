package com.aeromaintenance.PurchaseOrder;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aeromaintenance.PurchaseRequisition.PurchaseRequisition;
import com.aeromaintenance.PurchaseRequisition.PurchaseRequisitionDTO;
import com.aeromaintenance.PurchaseRequisition.PurchaseRequisitionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseRequisitionService purchaseRequisitionService;
    
    // Helper method to calculate totals and taxes
    private PurchaseOrder calculateTotals(PurchaseOrder purchaseOrder) {
        // 1. Calculate gross amount
        double grossAmount = 0.0;
        if (purchaseOrder.getCurrentStoke() != null && purchaseOrder.getRatePerUnit() != null) {
            grossAmount = purchaseOrder.getCurrentStoke() * purchaseOrder.getRatePerUnit();
        }
        purchaseOrder.setGrossAmount(grossAmount);

        // 2. Use 0.0 only if fields are null, but don't overwrite them with 0 if you want null to be used in calculations
        double pf = purchaseOrder.getPF() == null ? 0.0 : purchaseOrder.getPF();
        double transport = purchaseOrder.getTransportation() == null ? 0.0 : purchaseOrder.getTransportation();
        double insurance = purchaseOrder.getInsurance() == null ? 0.0 : purchaseOrder.getInsurance();
        double otherCharges = purchaseOrder.getOther_Charges() == null ? 0.0 : purchaseOrder.getOther_Charges();

        double total = grossAmount + pf + transport + insurance + otherCharges;
        purchaseOrder.setTotal(total);

        // 3. Calculate taxes on grossAmount
        double sgst = grossAmount * 0.09;
        double cgst = grossAmount * 0.09;
        double igst = grossAmount * 0.18;
//        System.out.println("PF: " + purchaseOrder.getPF());
//        System.out.println("Transport: " + purchaseOrder.getTransportation());
//        System.out.println("Insurance: " + purchaseOrder.getInsurance());
//        System.out.println("Other Charges: " + purchaseOrder.getOther_Charges());

        purchaseOrder.setSgst(sgst);
        purchaseOrder.setCgst(cgst);
        purchaseOrder.setIgst(igst);

        // 4. Calculate grand total
        double grandTotal = total + sgst + cgst + igst;
        purchaseOrder.setGrandTotal(grandTotal);

        return purchaseOrder;
    }

    // Add a new Purchase Order
//    public PurchaseOrder addPurchaseOrder(PurchaseOrder purchaseOrder) {
//        PurchaseOrder calculatedOrder = calculateTotals(purchaseOrder);
//        return purchaseOrderRepository.save(calculatedOrder);
//    }

    private String generatePoNumber() {
        String currentYear = String.valueOf(java.time.LocalDate.now().getYear());
        List<String> poNumbers = purchaseOrderRepository.findLastPoNumberForYear(currentYear);

        int nextNumber = 1;
        if (!poNumbers.isEmpty()) {
            String lastPo = poNumbers.get(0); // e.g. PO-2025-007
            String[] parts = lastPo.split("-");
            if (parts.length == 3) {
                nextNumber = Integer.parseInt(parts[2]) + 1;
            }
        }
        return String.format("PO-%s-%03d", currentYear, nextNumber);
    }

    public void saveOrder(PurchaseOrder order) {
        // Automatically generate PO number if not supplied
        if (order.getPoNumber() == null || order.getPoNumber().isEmpty()) {
            order.setPoNumber(generatePoNumber());
        }

        // Default status if null
        if (order.getStatus() == null || order.getStatus().isEmpty()) {
            order.setStatus("Open");
        }

        PurchaseOrder calculatedOrder = calculateTotals(order);
        purchaseOrderRepository.save(calculatedOrder);
    }
    // Delete a Purchase Order by ID
    public void deletePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }

    // View a Purchase Order by ID
    public PurchaseOrder viewPurchaseOrderById(Long id) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(id);
        return purchaseOrder.orElse(null); // return null or handle it as per your needs
    }

    // View all Purchase Orders
    public List<PurchaseOrder> viewAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }
    
    public List<PurchaseRequisitionDTO> getRequisitionsByBatchNumber(String batchNumber) {
        List<PurchaseRequisition> requisitions = purchaseRequisitionService.getByBatchNumberForPurchaseOrder(batchNumber);

        return requisitions.stream()
                .map(req -> {
                    PurchaseRequisitionDTO dto = new PurchaseRequisitionDTO();
                    BeanUtils.copyProperties(req, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrderDTO updatedPurchaseOrderDTO) {
        Optional<PurchaseOrder> existingOrder = purchaseOrderRepository.findById(id);
        if (existingOrder.isPresent()) {
            PurchaseOrder purchaseOrder = existingOrder.get();

            // Update fields only if they are non-null
            // Basic information
            if (updatedPurchaseOrderDTO.getPoNumber() != null) {
                purchaseOrder.setPoNumber(updatedPurchaseOrderDTO.getPoNumber());
            }
            if (updatedPurchaseOrderDTO.getPoDate() != null) {
                purchaseOrder.setPoDate(updatedPurchaseOrderDTO.getPoDate());
            }
            if (updatedPurchaseOrderDTO.getOurReference() != null) {
                purchaseOrder.setOurReference(updatedPurchaseOrderDTO.getOurReference());
            }
            if (updatedPurchaseOrderDTO.getYourReference() != null) {
                purchaseOrder.setYourReference(updatedPurchaseOrderDTO.getYourReference());
            }
            if (updatedPurchaseOrderDTO.getDelivery() != null) {
                purchaseOrder.setDelivery(updatedPurchaseOrderDTO.getDelivery());
            }
            if (updatedPurchaseOrderDTO.getDeliveryAddress() != null) {
                purchaseOrder.setDeliveryAddress(updatedPurchaseOrderDTO.getDeliveryAddress());
            }
            if (updatedPurchaseOrderDTO.getPaymentTerms() != null) {
                purchaseOrder.setPaymentTerms(updatedPurchaseOrderDTO.getPaymentTerms());
            }
            if (updatedPurchaseOrderDTO.getStatus() != null){
                purchaseOrder.setStatus(updatedPurchaseOrderDTO.getStatus());
            }

            // Line item details
            if (updatedPurchaseOrderDTO.getPartNumber() != null) {
                purchaseOrder.setPartNumber(updatedPurchaseOrderDTO.getPartNumber());
            }
            if (updatedPurchaseOrderDTO.getDescription() != null) {
                purchaseOrder.setDescription(updatedPurchaseOrderDTO.getDescription());
            }
            if (updatedPurchaseOrderDTO.getCurrentStoke() != null) {
                purchaseOrder.setCurrentStoke(updatedPurchaseOrderDTO.getCurrentStoke());
            }
            if (updatedPurchaseOrderDTO.getUnit() != null) {
                purchaseOrder.setUnit(updatedPurchaseOrderDTO.getUnit());
            }
            if (updatedPurchaseOrderDTO.getRatePerUnit() != null) {
                purchaseOrder.setRatePerUnit(updatedPurchaseOrderDTO.getRatePerUnit());
            }

            // Taxes & totals fields
            if (updatedPurchaseOrderDTO.getPF() != null) {
                purchaseOrder.setPF(updatedPurchaseOrderDTO.getPF());
            }
            if (updatedPurchaseOrderDTO.getTransportation() != null) {
                purchaseOrder.setTransportation(updatedPurchaseOrderDTO.getTransportation());
            }
            if (updatedPurchaseOrderDTO.getInsurance() != null) {
                purchaseOrder.setInsurance(updatedPurchaseOrderDTO.getInsurance());
            }
            if (updatedPurchaseOrderDTO.getOther_Charges() != null) {
                purchaseOrder.setOther_Charges(updatedPurchaseOrderDTO.getOther_Charges());
            }

            // Recalculate totals and taxes after updates
            PurchaseOrder calculatedOrder = calculateTotals(purchaseOrder);
            return purchaseOrderRepository.save(calculatedOrder);
        } else {
            return null; // or handle the case when the order is not found
        }
    }

    
public List<PurchaseOrderDTO> getAllPurchaseOrderNo() {
		
		return purchaseOrderRepository.getAllPurchaseOrderNo();
	}

	public List<PurchaseOrderDTO> getAllPartNoByPurchaseOrders(String PONumber) {
		return purchaseOrderRepository.getAllPartNoByPurchaseOrders(PONumber);
	}

	public PurchaseOrderDTO getDetailsByPartNo(String partNumber) {
		return purchaseOrderRepository.getDetailsByPartNo(partNumber);

	}
 
    
}