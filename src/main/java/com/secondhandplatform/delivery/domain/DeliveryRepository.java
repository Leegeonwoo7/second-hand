package com.secondhandplatform.delivery.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query("SELECT d.receiverName, d.address FROM Delivery d WHERE d.user.id = :userId")
    List<Object[]> findReceiverNameAndAddressByUserId(@Param("userId") Long userId);
}
