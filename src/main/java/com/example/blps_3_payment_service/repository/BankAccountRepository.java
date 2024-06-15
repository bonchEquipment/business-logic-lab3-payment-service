package com.example.blps_3_payment_service.repository;


import com.example.blps_3_payment_service.entity.BankAccountEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, UUID> {

        Optional<BankAccountEntity> findByUserId(UUID id);

  /*  @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE bank_account " +
                    "SET value = value + :amount " +
                    "WHERE user_id = :userId")
    void addMoneyToAccount(BigDecimal amount, UUID userId);*/

        }
