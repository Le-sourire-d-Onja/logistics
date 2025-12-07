package com.cooperhost.logistics.association;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.cooperhost.logistics.association.dtos.AssociationDto;
import com.cooperhost.logistics.association.dtos.UpsertAssociationDto;
import com.cooperhost.logistics.association.enums.AssociationType;
import com.cooperhost.logistics.association.exception.AssociationAlreadyExists;
import com.cooperhost.logistics.association.models.AssociationEntity;
import com.cooperhost.logistics.association.repositories.AssociationRepository;
import com.cooperhost.logistics.association.services.AssociationService;

@WebMvcTest(AssociationService.class)
public class AssociationServiceTest {
    @Autowired
    private AssociationService associationService;

    @MockitoBean
    private AssociationRepository associationRepository;

    private final UpsertAssociationDto createAssociationDto = new UpsertAssociationDto("Association", AssociationType.ASSOCIATION, "test", "1 rue du test", "0101010101", "test@yopmail.com", "Ceci est une description");
    private final AssociationDto associationDto = new AssociationDto("1", "Association", AssociationType.ASSOCIATION, "test", "1 rue du test", "0101010101", "test@yopmail.com", "Ceci est une description");
    private final AssociationEntity association = new AssociationEntity("1", "Association", AssociationType.ASSOCIATION, "test", "1 rue du test", "test@yopmail.com", "0101010101", "Ceci est une description", Instant.now(), Instant.now());

    @Test
    public void testShouldCreateAnAssociation() {
        when(associationRepository.existsByName(any(String.class))).thenReturn(false);
        when(associationRepository.save(any(AssociationEntity.class))).thenReturn(association);
        AssociationDto result = associationService.create(createAssociationDto);
        assertEquals(result, associationDto);
    }

    @Test
    public void testShouldThrowAssociationAlreadyExists() {
        when(associationRepository.existsByName(any(String.class))).thenReturn(true);
        assertThrows(AssociationAlreadyExists.class, () -> associationService.create(createAssociationDto));
    }
}
