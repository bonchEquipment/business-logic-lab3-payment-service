package com.example.blps_3_payment_service.controller;

import com.example.blps_3_payment_service.config.HeaderConstant;
import com.example.blps_3_payment_service.dto.AccountType;
import com.example.blps_3_payment_service.dto.Response;
import com.example.blps_3_payment_service.dto.StatusCode;
import com.example.blps_3_payment_service.service.AccountService;
import com.example.blps_3_payment_service.service.PartnershipService;
import com.example.blps_3_payment_service.service.TopUpBalanceService;
import com.example.blps_3_payment_service.util.ResponseHelper;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class MainController {


    private final AccountService accountService;
    private final PartnershipService partnershipService;
    private final ResponseHelper responseHelper;
    private final TopUpBalanceService topUpBalanceService;


    @PostMapping("/withdrawPartnershipMoney")
    public ResponseEntity<Response<StatusCode>> withdrawPartnershipMoney(
            @RequestHeader(value = HeaderConstant.AMOUNT, required = true)
            Integer amount,
            @RequestHeader(value = HeaderConstant.USER_ID, required = true)
            UUID userId) {
        log.info("got withdrawPartnershipMoney request {}, {}", amount, userId);
        try {
            partnershipService.withdrawPartnershipMoney(BigDecimal.valueOf(amount), userId);
            return responseHelper.asResponseEntity(StatusCode.OK);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return responseHelper.asResponseEntity(StatusCode.createRequestFailedCode(e.getMessage()));
        }
    }


    @PostMapping(path = "withdrawMoneyForSubscription", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Response<StatusCode>> withdrawMoneyForSubscription(
            @RequestHeader(value = HeaderConstant.AMOUNT, required = true)
            BigDecimal amount,
            @RequestHeader(value = HeaderConstant.USER_ID, required = true)
            UUID userId,
            @RequestHeader(value = HeaderConstant.ACCOUNT_TYPE, required = true)
            AccountType accountType) {
        log.info("got withdrawMoneyForSubscription request {}, {}", amount, userId);
        var status = accountService.withdrawMoneyFromAccount(amount, userId, accountType);
        if (status.isOperationSucceed())
            return responseHelper.asResponseEntity(StatusCode.OK);

        return responseHelper.asResponseEntity(StatusCode.createRequestFailedCode(status.getInfo()));
    }

    @PostMapping(path = "topUpBalance", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Response<StatusCode>> topUpBalance(
            @RequestHeader(value = HeaderConstant.AMOUNT, required = true)
            BigDecimal amount,
            @RequestHeader(value = HeaderConstant.USER_ID, required = true)
            UUID userId) {
        log.info("got topUpBalance request {}, {}", amount, userId);
        try {
            topUpBalanceService.topUpBalance(amount, userId);
            return responseHelper.asResponseEntity(StatusCode.OK);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return responseHelper.asResponseEntity(StatusCode.createRequestFailedCode(e.getMessage()));
        }
    }

    @PostMapping(path = "addMoneyToBankAccountFromAir", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Response<StatusCode>> addMoneyToBankAccountFromAir(
            @RequestHeader(value = HeaderConstant.AMOUNT, required = true)
            BigDecimal amount,
            @RequestHeader(value = HeaderConstant.USER_ID, required = true)
            UUID userId) {
        log.info("got addMoneyToBankAccountFromAir request {}, {}", amount, userId);
        var status = accountService.addMoneyToAccount(amount, userId, AccountType.BANK);
        if (status.isOperationSucceed())
            return responseHelper.asResponseEntity(StatusCode.OK);

        return responseHelper.asResponseEntity(StatusCode.createRequestFailedCode(status.getInfo()));
    }

}
