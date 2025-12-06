package com.cooperhost.logistics.association;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooperhost.logistics.association.models.AssociationEntity;

public interface AssociationRepository extends JpaRepository<AssociationEntity, String> {
}
