package com.kh.finalproject.admin.model.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.kh.finalproject.admin.model.mapper.DeliveryStatusAPIMapper;
import com.kh.finalproject.dto.DeliveryStatus;
import com.kh.finalproject.mapper.DeliveryStatusMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryStatusAPIServiceImpl implements DeliveryStatusAPIService {

    private final DeliveryStatusAPIMapper mapper;

    @Override
    public List<DeliveryStatus> getDeliveryStatusAPICounts() {
        return mapper.getDeliveryStatusAPICounts();
    }
}
