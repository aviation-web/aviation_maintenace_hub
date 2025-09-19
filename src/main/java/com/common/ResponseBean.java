package com.common;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponseBean<T> {
		private String respCode;  // Response code (200 for success, 400 for validation errors)
	    private String message; // Success/Error message
	    private T data; // Generic data field for returning any object
}
