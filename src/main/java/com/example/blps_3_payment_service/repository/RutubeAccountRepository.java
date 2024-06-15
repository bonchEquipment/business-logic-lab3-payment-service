package com.example.blps_3_payment_service.repository;

import com.example.blps_3_payment_service.entity.RutubeAccountEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RutubeAccountRepository extends JpaRepository<RutubeAccountEntity, UUID> {


    Optional<RutubeAccountEntity> findByUserId(UUID id);
}
