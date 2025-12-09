package com.cooperhost.logistics.donation;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.cooperhost.logistics.article_type.dtos.ArticleTypeDto;
import com.cooperhost.logistics.article_type.models.ArticleTypeEntity;
import com.cooperhost.logistics.donation.dtos.ArticleDto;
import com.cooperhost.logistics.donation.dtos.CreateDonationDto;
import com.cooperhost.logistics.donation.dtos.DonationDto;
import com.cooperhost.logistics.donation.dtos.UpdateDonationDto;
import com.cooperhost.logistics.donation.dtos.UpsertArticleDto;
import com.cooperhost.logistics.donation.dtos.WrongCreateDonationDto;
import com.cooperhost.logistics.donation.models.ArticleEntity;
import com.cooperhost.logistics.donation.models.DonationEntity;
import com.cooperhost.logistics.donation.repositories.DonationRepository;
import com.cooperhost.logistics.shared.config.IntegrationTestConfig;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class DonationIntegrationTest extends IntegrationTestConfig {
    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private MockMvc mockMvc;

    private CreateDonationDto createDonationDto;
    private DonationDto donationDto;
    private UpdateDonationDto updateDonationDtoWithoutArticles;
    private UpdateDonationDto updateDonationDtoWithArticles;
    private WrongCreateDonationDto wrongCreateDonationDto;
    private DonationEntity donation;

    @BeforeEach()
    public void beforeEach() {
        donationRepository.deleteAll();
        createDonationDto = new CreateDonationDto("Donation", List.of(new UpsertArticleDto(null, UUID.randomUUID().toString(), 10)));
        donationDto = new DonationDto(UUID.randomUUID().toString(), "Donation", List.of(new ArticleDto(UUID.randomUUID().toString(), new ArticleTypeDto(UUID.randomUUID().toString(), "ArticleType", 10f, 10f), 10)));
        updateDonationDtoWithoutArticles = new UpdateDonationDto("Donation1", null);
        updateDonationDtoWithArticles = new UpdateDonationDto("Donation1", List.of(new UpsertArticleDto(null, UUID.randomUUID().toString(), 20), new UpsertArticleDto(UUID.randomUUID().toString(), "2", 20)));
        wrongCreateDonationDto =  new WrongCreateDonationDto();
        donation = new DonationEntity(null, "Donation", null, null, List.of(new ArticleEntity(null, new ArticleTypeEntity(null, "ArticleType", 10f, 10f), 10)));
    }

    @Test
    public void testCreate_201() throws Exception {
        // mockMvc.perform((post("/api/donations"))
        //                 .contentType(MediaType.APPLICATION_JSON)
        //                 .content(new ObjectMapper().writeValueAsString(createDonationDto)))
        //         .andExpect(status().isCreated())
        //         .andExpect(jsonPath("$.errors").isEmpty())
        //         .andExpect(jsonPath("$.data.id").isString())
        //         .andExpect(jsonPath("$.data.description").value(donationDto.getDescription()))
        //         .andExpect(jsonPath("$.data.articles.[0].id").value(donationDto.getArticles().get(0).getId()))
        //         .andExpect(jsonPath("$.data.articles.[0].quantity").value(donationDto.getArticles().get(0).getQuantity()))
        //         .andExpect(jsonPath("$.data.articles.[0].type.id").value(donationDto.getArticles().get(0).getType().getId()))
        //         .andExpect(jsonPath("$.data.articles.[0].type.name").value(donationDto.getArticles().get(0).getType().getName()))
        //         .andExpect(jsonPath("$.data.articles.[0].type.weight").value(donationDto.getArticles().get(0).getType().getWeight()))
        //         .andExpect(jsonPath("$.data.articles.[0].type.volume").value(donationDto.getArticles().get(0).getType().getVolume()));
    }

    @Test
    public void testCreate_400() throws Exception {
        // mockMvc.perform((post("/api/donations"))
        //                 .contentType(MediaType.APPLICATION_JSON)
        //                 .content(new ObjectMapper().writeValueAsString(wrongCreateDonationDto)))
        //         .andExpect(status().isBadRequest())
        //         .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    public void testFindAll_200() throws Exception {
        // DonationEntity savedDonation = donationRepository.save(donation);
        // mockMvc.perform((get("/api/donations"))
        //                 .contentType(MediaType.APPLICATION_JSON))
        //         .andExpect(status().isOk())
        //         .andExpect(jsonPath("$.errors").isEmpty())
        //         .andExpect(jsonPath("$.data.[0].id").value(savedDonation.getId()))
        //         .andExpect(jsonPath("$.data.[0].description").value(donationDto.getDescription()))
        //         .andExpect(jsonPath("$.data.[0].articles.[0].id").value(donationDto.getArticles().get(0).getId()))
        //         .andExpect(jsonPath("$.data.[0].articles.[0].quantity").value(donationDto.getArticles().get(0).getQuantity()))
        //         .andExpect(jsonPath("$.data.[0].articles.[0].type.id").value(donationDto.getArticles().get(0).getType().getId()))
        //         .andExpect(jsonPath("$.data.[0].articles.[0].type.name").value(donationDto.getArticles().get(0).getType().getName()))
        //         .andExpect(jsonPath("$.data.[0].articles.[0].type.weight").value(donationDto.getArticles().get(0).getType().getWeight()))
        //         .andExpect(jsonPath("$.data.[0].articles.[0].type.volume").value(donationDto.getArticles().get(0).getType().getVolume()));
    }

    @Test
    public void testUpdate_WithoutArticles200() throws Exception {
        // DonationEntity savedDonation = donationRepository.save(donation);
        // donationDto.setDescription(updateDonationDtoWithoutArticles.getDescription());
        // mockMvc.perform((patch("/api/donations/" + savedDonation.getId()))
        //                 .contentType(MediaType.APPLICATION_JSON)
        //                 .content(new ObjectMapper().writeValueAsString(updateDonationDtoWithoutArticles)))
        //         .andExpect(status().isOk())
        //         .andExpect(jsonPath("$.errors").isEmpty())
        //         .andExpect(jsonPath("$.data.id").value(savedDonation.getId()))
        //         .andExpect(jsonPath("$.data.description").value(donationDto.getDescription()))
        //         .andExpect(jsonPath("$.data.articles.[0].id").value(donationDto.getArticles().get(0).getId()))
        //         .andExpect(jsonPath("$.data.articles.[0].quantity").value(donationDto.getArticles().get(0).getQuantity()))
        //         .andExpect(jsonPath("$.data.articles.[0].type.id").value(donationDto.getArticles().get(0).getType().getId()))
        //         .andExpect(jsonPath("$.data.articles.[0].type.name").value(donationDto.getArticles().get(0).getType().getName()))
        //         .andExpect(jsonPath("$.data.articles.[0].type.weight").value(donationDto.getArticles().get(0).getType().getWeight()))
        //         .andExpect(jsonPath("$.data.articles.[0].type.volume").value(donationDto.getArticles().get(0).getType().getVolume()));
    }


    @Test
    public void testUpdate_WithArticles200() throws Exception {
        // DonationEntity savedDonation = donationRepository.save(donation);
        // donationDto.setDescription(updateDonationDtoWithArticles.getDescription());
        // donationDto.setArticles(List.of(new ArticleDto(savedDonation.getArticles().get(0).getId(), new ArticleTypeDto(updateDonationDtoWithArticles.getArticles().get(0).getTypeId(), donationDto.getArticles().get(0).getType().getName(), donationDto.getArticles().get(0).getType().getWeight(), donationDto.getArticles().get(0).getType().getVolume()), updateDonationDtoWithArticles.getArticles().get(0).getQuantity())));
        // mockMvc.perform((patch("/api/donations/" + savedDonation.getId()))
        //                 .contentType(MediaType.APPLICATION_JSON)
        //                 .content(new ObjectMapper().writeValueAsString(updateDonationDtoWithoutArticles)))
        //         .andExpect(status().isOk())
        //         .andExpect(jsonPath("$.errors").isEmpty())
        //         .andExpect(jsonPath("$.data.id").value(savedDonation.getId()))
        //         .andExpect(jsonPath("$.data.description").value(donationDto.getDescription()))
        //         .andExpect(jsonPath("$.data.articles.[0].id").value(donationDto.getArticles().get(0).getId()))
        //         .andExpect(jsonPath("$.data.articles.[0].quantity").value(donationDto.getArticles().get(0).getQuantity()))
        //         .andExpect(jsonPath("$.data.articles.[0].type.id").value(donationDto.getArticles().get(0).getType().getId()))
        //         .andExpect(jsonPath("$.data.articles.[0].type.name").value(donationDto.getArticles().get(0).getType().getName()))
        //         .andExpect(jsonPath("$.data.articles.[0].type.weight").value(donationDto.getArticles().get(0).getType().getWeight()))
        //         .andExpect(jsonPath("$.data.articles.[0].type.volume").value(donationDto.getArticles().get(0).getType().getVolume()));
    }

    @Test
    public void testUpdate_400() throws Exception {
        // mockMvc.perform((patch("/api/donations/" + donationDto.getId()))
        //                 .contentType(MediaType.APPLICATION_JSON)
        //                 .content(new ObjectMapper().writeValueAsString(wrongCreateDonationDto)))
        //         .andExpect(status().isBadRequest())
        //         .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    public void testUpdate_404() throws Exception {
        // mockMvc.perform((patch("/api/donations/" + donationDto.getId()))
        //                 .contentType(MediaType.APPLICATION_JSON)
        //                 .content(new ObjectMapper().writeValueAsString(updateDonationDto)))
        //         .andExpect(status().isNotFound())
        //         .andExpect(jsonPath("$.errors.[0].field").isEmpty())
        //         .andExpect(jsonPath("$.errors.[0].message").value("The donation is not found"))
        //         .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testUpdate_409() throws Exception {
        // DonationEntity donationCopy1 = new DonationEntity(null, donation.getName() + UUID.randomUUID().toString(), donation.getType(), donation.getPersonInCharge(), donation.getAddress(), donation.getEmail(), donation.getPhone(), donation.getDescription(), donation.getCreatedAt(), donation.getUpdatedAt());
        // DonationEntity donationCopy2 = new DonationEntity(null, donation.getName(), donation.getType(), donation.getPersonInCharge(), donation.getAddress(), donation.getEmail(), donation.getPhone(), donation.getDescription(), donation.getCreatedAt(), donation.getUpdatedAt());

        // donationRepository.save(donationCopy1);
        // donationRepository.save(donationCopy2);

        // mockMvc.perform((patch("/api/donations/" + donationCopy2.getId()))
        //                 .contentType(MediaType.APPLICATION_JSON)
        //                 .content(new ObjectMapper().writeValueAsString(updateDonationDto)))
        //         .andExpect(status().isConflict())
        //         .andExpect(jsonPath("$.errors.[0].field").isEmpty())
        //         .andExpect(jsonPath("$.errors.[0].message").value("The donation already exists"))
        //         .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testDelete_204() throws Exception {
        // DonationEntity savedDonation = donationRepository.save(donation);
        // mockMvc.perform((delete("/api/donations/" + savedDonation.getId()))
        //                 .contentType(MediaType.APPLICATION_JSON))
        //         .andExpect(status().isNoContent())
        //         .andExpect(jsonPath("$.errors").isEmpty())
        //         .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    public void testDelete_404() throws Exception {
        // mockMvc.perform((delete("/api/donations/" + donationDto.getId()))
        //                 .contentType(MediaType.APPLICATION_JSON))
        //         .andExpect(status().isNotFound())
        //         .andExpect(jsonPath("$.errors.[0].field").isEmpty())
        //         .andExpect(jsonPath("$.errors.[0].message").value("The donation is not found"))
        //         .andExpect(jsonPath("$.data").isEmpty());
    }
}
