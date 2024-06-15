package com.example.blps_3_payment_service.util;

import com.example.blps_3_payment_service.dto.Response;
import com.example.blps_3_payment_service.dto.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class ResponseHelper {

    public <T> ResponseEntity<Response<T>> asResponseEntity(StatusCode StatusCode) {
        return asResponseEntity(StatusCode, null);
    }

    public <T> ResponseEntity<Response<T>> asResponseEntity(StatusCode statusCode, @Nullable T data) {
        var response = asResponse(statusCode, data);
        return ResponseEntity.status(statusCode.getHttpCode()).body(response);
    }

    public <T> Response<T> asResponse(StatusCode statusCode, @Nullable T data) {
        return new Response<>(data, statusCode);
    }

}
