package com.kh.finalproject.admin.model.mapper;

import java.util.List;
import com.kh.finalproject.dto.DeliveryStatus; // 만들어두신 DTO
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryStatusAPIMapper {
    // 아까 XML에 만든 id="getDeliveryStatusCounts"와 이름을 맞춥니다.
    List<DeliveryStatus> getDeliveryStatusAPICounts();
}