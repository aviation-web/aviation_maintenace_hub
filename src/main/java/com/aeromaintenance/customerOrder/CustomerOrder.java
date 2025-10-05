package com.aeromaintenance.customerOrder;

import java.time.LocalDate;

import javax.persistence.*;

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
		@GeneratedValue(generator = "customer-order-generator")
		@GenericGenerator(
				name = "customer-order-generator",
				strategy = "com.aeromaintenance.customerOrder.CustomerOrderIdGenerator"
		)
		@Column(name = "sr_no")
		private String srNo;

		@Column
	    private Long orderNo;
		
		@Column
		private Long roNo;
		
		@Column
		private String roReceiveDate;

		@Column
		private String roDate;
		
		@Column
		private String customerName;
		
		@Column
		private String partNo;
		
		@Column
		private String partDescription;
		
		@Column
		private Integer quantity;
		
		@Column
		private String batchNo;

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

	@PrePersist
	public void prePersist() {
		if (this.status == null) {
			this.status = "OPEN";
		}
	}
}
