/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.cooperhost.logistics.donation;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cooperhost.logistics.article_type.dtos.ArticleTypeDto;
import com.cooperhost.logistics.donation.controllers.DonationController;
import com.cooperhost.logistics.donation.dtos.DonationDto;
import com.cooperhost.logistics.donation.dtos.ArticleDto;
import com.cooperhost.logistics.donation.dtos.CreateDonationTypeDto;
import com.cooperhost.logistics.donation.dtos.UpdateDonationDto;
import com.cooperhost.logistics.donation.dtos.UpsertArticleDto;
import com.cooperhost.logistics.donation.dtos.WrongCreateDonationDto;
import com.cooperhost.logistics.donation.exception.DonationNotFound;
import com.cooperhost.logistics.donation.services.DonationService;

import tools.jackson.databind.ObjectMapper;

/**
 *
 * @author cooper
 */
@WebMvcTest(DonationController.class)
public class DonationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DonationService donationService;

    private CreateDonationTypeDto createDonationDto;
    private DonationDto donationDto;
    private UpdateDonationDto updateDonationDtoWithoutArticles;
    private UpdateDonationDto updateDonationDtoWithArticles;
    private WrongCreateDonationDto wrongCreateDonationDto;

    @BeforeEach()
    public void beforeEach() {
        createDonationDto = new CreateDonationTypeDto("Donation", List.of(new UpsertArticleDto(null, "1", 10)));
        donationDto = new DonationDto("1", "Donation", List.of(new ArticleDto("1", new ArticleTypeDto("1", "ArticleType", 10f, 10f), 10)));
        updateDonationDtoWithoutArticles = new UpdateDonationDto("Donation1", null);
        updateDonationDtoWithArticles = new UpdateDonationDto("Donation1", List.of(new UpsertArticleDto(null, "1", 20), new UpsertArticleDto("1", "2", 20)));
        wrongCreateDonationDto =  new WrongCreateDonationDto();
    }

    @Test
    public void testCreate_201() throws Exception {
        when(donationService.create(any(CreateDonationTypeDto.class))).thenReturn(donationDto);
        mockMvc.perform((post("/api/donations"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createDonationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.id").value(donationDto.getId()))
                .andExpect(jsonPath("$.data.description").value(donationDto.getDescription()))
                .andExpect(jsonPath("$.data.articles.[0].id").value(donationDto.getArticles().get(0).getId()))
                .andExpect(jsonPath("$.data.articles.[0].quantity").value(donationDto.getArticles().get(0).getQuantity()))
                .andExpect(jsonPath("$.data.articles.[0].type.id").value(donationDto.getArticles().get(0).getType().getId()))
                .andExpect(jsonPath("$.data.articles.[0].type.name").value(donationDto.getArticles().get(0).getType().getName()))
                .andExpect(jsonPath("$.data.articles.[0].type.weight").value(donationDto.getArticles().get(0).getType().getWeight()))
                .andExpect(jsonPath("$.data.articles.[0].type.volume").value(donationDto.getArticles().get(0).getType().getVolume()));
    }

    @Test
    public void testCreate_400() throws Exception {
        mockMvc.perform((post("/api/donations"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(wrongCreateDonationDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[?(@.field == 'description')]").exists());
    }

    @Test
    public void testFindAll_200() throws Exception {
        when(donationService.findAll()).thenReturn(List.of(donationDto));
        mockMvc.perform((get("/api/donations"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.[0].id").value(donationDto.getId()))
                .andExpect(jsonPath("$.data.[0].description").value(donationDto.getDescription()))
                .andExpect(jsonPath("$.data.[0].articles.[0].id").value(donationDto.getArticles().get(0).getId()))
                .andExpect(jsonPath("$.data.[0].articles.[0].quantity").value(donationDto.getArticles().get(0).getQuantity()))
                .andExpect(jsonPath("$.data.[0].articles.[0].type.id").value(donationDto.getArticles().get(0).getType().getId()))
                .andExpect(jsonPath("$.data.[0].articles.[0].type.name").value(donationDto.getArticles().get(0).getType().getName()))
                .andExpect(jsonPath("$.data.[0].articles.[0].type.weight").value(donationDto.getArticles().get(0).getType().getWeight()))
                .andExpect(jsonPath("$.data.[0].articles.[0].type.volume").value(donationDto.getArticles().get(0).getType().getVolume()));
    }

    @Test
    public void testUpdate_WithoutArticles200() throws Exception {
        donationDto.setDescription(updateDonationDtoWithoutArticles.getDescription());
        when(donationService.update(any(String.class), any(UpdateDonationDto.class))).thenReturn(donationDto);
        mockMvc.perform((patch("/api/donations/" + donationDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDonationDtoWithoutArticles)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.id").value(donationDto.getId()))
                .andExpect(jsonPath("$.data.description").value(donationDto.getDescription()))
                .andExpect(jsonPath("$.data.articles.[0].id").value(donationDto.getArticles().get(0).getId()))
                .andExpect(jsonPath("$.data.articles.[0].quantity").value(donationDto.getArticles().get(0).getQuantity()))
                .andExpect(jsonPath("$.data.articles.[0].type.id").value(donationDto.getArticles().get(0).getType().getId()))
                .andExpect(jsonPath("$.data.articles.[0].type.name").value(donationDto.getArticles().get(0).getType().getName()))
                .andExpect(jsonPath("$.data.articles.[0].type.weight").value(donationDto.getArticles().get(0).getType().getWeight()))
                .andExpect(jsonPath("$.data.articles.[0].type.volume").value(donationDto.getArticles().get(0).getType().getVolume()));
    }

    @Test
    public void testUpdate_WithArticles200() throws Exception {
        donationDto.setDescription(updateDonationDtoWithArticles.getDescription());
        donationDto.setArticles(List.of(new ArticleDto("1", new ArticleTypeDto(updateDonationDtoWithArticles.getArticles().get(0).getTypeId(), "ArticleType", 10f, 10f), updateDonationDtoWithArticles.getArticles().get(0).getQuantity())));
        when(donationService.update(any(String.class), any(UpdateDonationDto.class))).thenReturn(donationDto);
        mockMvc.perform((patch("/api/donations/" + donationDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDonationDtoWithArticles)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.id").value(donationDto.getId()))
                .andExpect(jsonPath("$.data.description").value(donationDto.getDescription()))
                .andExpect(jsonPath("$.data.articles.[0].id").value(donationDto.getArticles().get(0).getId()))
                .andExpect(jsonPath("$.data.articles.[0].quantity").value(donationDto.getArticles().get(0).getQuantity()))
                .andExpect(jsonPath("$.data.articles.[0].type.id").value(donationDto.getArticles().get(0).getType().getId()))
                .andExpect(jsonPath("$.data.articles.[0].type.name").value(donationDto.getArticles().get(0).getType().getName()))
                .andExpect(jsonPath("$.data.articles.[0].type.weight").value(donationDto.getArticles().get(0).getType().getWeight()))
                .andExpect(jsonPath("$.data.articles.[0].type.volume").value(donationDto.getArticles().get(0).getType().getVolume()));
    }

    @Test
    public void testUpdate_400() throws Exception {
        mockMvc.perform((patch("/api/donations/" + donationDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(wrongCreateDonationDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[?(@.field == 'articles[0].typeId')]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == 'articles[0].quantity')]").exists());
    }

    @Test
    public void testUpdate_404() throws Exception {
        when(donationService.update(any(String.class), any(UpdateDonationDto.class))).thenThrow(new DonationNotFound());
        mockMvc.perform((patch("/api/donations/" + donationDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDonationDtoWithoutArticles)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The donation is not found"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testDelete_204() throws Exception {
        doNothing().when(donationService).delete(any(String.class));
        mockMvc.perform((delete("/api/donations/" + donationDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testDelete_404() throws Exception {
        doThrow(new DonationNotFound()).when(donationService).delete(any(String.class));
        mockMvc.perform((delete("/api/donations/" + donationDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The donation is not found"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}