package com.kh.finalproject.service;

import java.util.List;
import java.util.Map;

import com.kh.finalproject.dto.DeliveryRequest;
import com.kh.finalproject.dto.PaymentManagement;

public interface PaymentManagementService {

 
	List<DeliveryRequest> selectPaymentList();
}