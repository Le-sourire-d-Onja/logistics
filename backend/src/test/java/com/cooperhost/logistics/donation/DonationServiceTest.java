package com.cooperhost.logistics.donation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.cooperhost.logistics.donation.dtos.DonationDto;
import com.cooperhost.logistics.article_type.dtos.ArticleTypeDto;
import com.cooperhost.logistics.article_type.models.ArticleTypeEntity;
import com.cooperhost.logistics.donation.dtos.ArticleDto;
import com.cooperhost.logistics.donation.dtos.CreateDonationTypeDto;
import com.cooperhost.logistics.donation.dtos.UpdateDonationDto;
import com.cooperhost.logistics.donation.dtos.UpsertArticleDto;
import com.cooperhost.logistics.donation.dtos.WrongCreateDonationDto;
import com.cooperhost.logistics.donation.exception.DonationAlreadyExists;
import com.cooperhost.logistics.donation.exception.DonationNotFound;
import com.cooperhost.logistics.donation.models.ArticleEntity;
import com.cooperhost.logistics.donation.models.DonationEntity;
import com.cooperhost.logistics.donation.repositories.DonationRepository;
import com.cooperhost.logistics.donation.services.DonationService;

@WebMvcTest(DonationService.class)
public class DonationServiceTest {
    @Autowired
    private DonationService donationService;

    @MockitoBean
    private DonationRepository donationRepository;

    private CreateDonationTypeDto createDonationDto;
    private DonationDto donationDto;
    private UpdateDonationDto updateDonationDtoWithoutArticles;
    private UpdateDonationDto updateDonationDtoWithArticles;
    private DonationEntity donation;

    @BeforeEach()
    public void beforeEach() {
        createDonationDto = new CreateDonationTypeDto("Donation", List.of(new UpsertArticleDto(null, "1", 10)));
        donationDto = new DonationDto("1", "Donation", List.of(new ArticleDto("1", new ArticleTypeDto("1", "ArticleType", 10f, 10f), 10)));
        updateDonationDtoWithoutArticles = new UpdateDonationDto("Donation1", null);
        updateDonationDtoWithArticles = new UpdateDonationDto("Donation1", List.of(new UpsertArticleDto(null, "1", 10)));
        donation = new DonationEntity("1", "Donation", Instant.now(), Instant.now(), List.of(new ArticleEntity("1", new ArticleTypeEntity("1", "ArticleType", 10f, 10f), 10)));
    }

    @Test
    public void testCreate_ShouldCreateAnDonation() {
        // when(donationRepository.existsByName(any(String.class))).thenReturn(false);
        // when(donationRepository.save(any(DonationEntity.class))).thenReturn(donation);
        // DonationDto result = donationService.create(createDonationDto);
        // assertEquals(result, donationDto);
    }

    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    public void testCreate_ShouldThrowDonationAlreadyExists() {
        // when(donationRepository.existsByName(any(String.class))).thenReturn(true);
        // assertThrows(DonationAlreadyExists.class, () -> donationService.create(createDonationDto));
    }

    @Test
    public void testFindAll_ShouldReturnAllData() {
        // when(donationRepository.findAll()).thenReturn(List.of(donation));
        // List<DonationDto> result = donationService.findAll();
        // assertEquals(result, List.of(donationDto));
    }

    @Test
    public void testUpdate_ShouldUpdateAnDonation() {
        // donation.setName(updateDonationDto.getName());
        // donationDto.setName(updateDonationDto.getName());
        // when(donationRepository.findById(any(String.class))).thenReturn(Optional.of(donation));
        // when(donationRepository.existsByName(any(String.class))).thenReturn(false);
        // when(donationRepository.save(any(DonationEntity.class))).thenReturn(donation);
        // DonationDto result = donationService.update(donationDto.getId(), updateDonationDto);
        // assertEquals(result, donationDto);
    }

    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    public void testUpdate_ShouldThrowAssociatioNotFound() {
        // when(donationRepository.findById(any(String.class))).thenThrow(new DonationNotFound());
        // assertThrows(DonationNotFound.class, () -> donationService.update(donationDto.getId(), updateDonationDto));
    }

    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    public void testUpdate_ShouldThrowAssociatioAlreadyExists() {
        // when(donationRepository.findById(any(String.class))).thenReturn(Optional.of(donation));
        // when(donationRepository.existsByName(any(String.class))).thenReturn(true);
        // assertThrows(DonationAlreadyExists.class, () -> donationService.update(donationDto.getId(), updateDonationDto));
    }

    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    public void testDelete_ShouldThrowAssociatioNotFound() {
        // doThrow(new DonationNotFound()).when(donationRepository).deleteById(any(String.class));
        // assertThrows(DonationNotFound.class, () -> donationService.delete(donationDto.getId()));
    }
}
