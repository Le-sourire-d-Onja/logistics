package com.cooperhost.logistics.donation;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.cooperhost.logistics.article_type.dtos.ArticleTypeDto;
import com.cooperhost.logistics.article_type.models.ArticleTypeEntity;
import com.cooperhost.logistics.donation.dtos.ArticleDto;
import com.cooperhost.logistics.donation.dtos.CreateDonationTypeDto;
import com.cooperhost.logistics.donation.dtos.DonationDto;
import com.cooperhost.logistics.donation.dtos.UpdateDonationDto;
import com.cooperhost.logistics.donation.dtos.UpsertArticleDto;
import com.cooperhost.logistics.donation.exception.DonationNotFound;
import com.cooperhost.logistics.donation.models.ArticleEntity;
import com.cooperhost.logistics.donation.models.DonationEntity;
import com.cooperhost.logistics.donation.repositories.DonationRepository;
import com.cooperhost.logistics.donation.services.DonationService;

@WebMvcTest(DonationService.class)
public class DonationServiceUnitTest {
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

    // -- Unit tests --
    @Test
    public void testCreate_ShouldCreateAnDonation() {
        when(donationRepository.save(any(DonationEntity.class))).thenReturn(donation);
        DonationDto result = donationService.create(createDonationDto);
        assertEquals(result, donationDto);
    }

    @Test
    public void testFindAll_ShouldReturnAllData() {
        when(donationRepository.findAll()).thenReturn(List.of(donation));
        List<DonationDto> result = donationService.findAll();
        assertEquals(result, List.of(donationDto));
    }

    @Test
    public void testUpdate_ShouldUpdateADonationWithoutArticles() {
        donationDto.setDescription(updateDonationDtoWithoutArticles.getDescription());
        when(donationRepository.findById(any(String.class))).thenReturn(Optional.of(donation));
        when(donationRepository.save(any(DonationEntity.class))).thenReturn(donation);
        DonationDto result = donationService.update(donationDto.getId(), updateDonationDtoWithoutArticles);
        assertEquals(result, donationDto);
    }

    @Test
    public void testUpdate_ShouldUpdateADonationWithArticles() {
        donationDto.setDescription(updateDonationDtoWithArticles.getDescription());
        donationDto.setArticles(List.of(new ArticleDto("1", new ArticleTypeDto(updateDonationDtoWithArticles.getArticles().get(0).getTypeId(), "ArticleType", 10f, 10f), updateDonationDtoWithArticles.getArticles().get(0).getQuantity())));
        when(donationRepository.findById(any(String.class))).thenReturn(Optional.of(donation));
        when(donationRepository.save(any(DonationEntity.class))).thenReturn(donation);
        DonationDto result = donationService.update(donationDto.getId(), updateDonationDtoWithArticles);
        assertEquals(result, donationDto);
    }

    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    public void testUpdate_ShouldThrowAssociatioNotFound() {
        when(donationRepository.findById(any(String.class))).thenThrow(new DonationNotFound());
        assertThrows(DonationNotFound.class, () -> donationService.update(donationDto.getId(), updateDonationDtoWithoutArticles));
    }

    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    public void testDelete_ShouldThrowAssociatioNotFound() {
        doThrow(new DonationNotFound()).when(donationRepository).deleteById(any(String.class));
        assertThrows(DonationNotFound.class, () -> donationService.delete(donationDto.getId()));
    }

    // -- Integrations tests --
    
}
