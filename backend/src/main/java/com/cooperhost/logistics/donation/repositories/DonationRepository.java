package com.cooperhost.logistics.donation.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cooperhost.logistics.donation.models.DonationEntity;

public interface DonationRepository extends JpaRepository<DonationEntity, String> {
}
