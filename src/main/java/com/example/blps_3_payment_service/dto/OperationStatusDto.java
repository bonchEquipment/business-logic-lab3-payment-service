package com.example.blps_3_payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationStatusDto {


    private boolean operationSucceed;

    private String info;


}
