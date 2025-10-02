package com.aeromaintenance.customerOrder;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Entity
	@Table(name = "Customer_Order")
	public class CustomerOrder {
		@Id
		@GeneratedValue(generator = "cust-id-generator")
		@GenericGenerator(
				name = "cust-id-generator",
				strategy = "com.aeromaintenance.customerOrder.CustomerOrderIdGenerator"
		)
		@Column(name = "sr_no", length = 20)
		private String srNo;

		@Column
	    private Long orderNo;
		
		@Column
		private Long roNo;
		
		@Column
		private String roReceiveDate;
		
		@Column
		private String customerName;
		
		@Column
		private String partNo;
		
		@Column
		private String partDescription;
		
		@Column
		private Integer quantity;
		
		@Column
		private Long batchNo;
		
		@Column
		private String status;	
		
		@Column
		private String remark;
		
		@Column
		private String makerUserName;
		
		@Column
		private String makerUserId;
		
		@Column
		private LocalDate makerDate;
		
		@Column
		private String checkerUserName;
		
		@Column
		private String checkerUserId;
		
		@Column
		private LocalDate checkerDate;
		
		@Column
		private String userAction;
		
		@Column
		private String userRole;
		
		@Column
		private String documentPath;


}
