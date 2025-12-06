/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.cooperhost.logistics.association;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import com.cooperhost.logistics.association.dtos.UpsertAssociationDto;
import com.cooperhost.logistics.association.dtos.AssociationDto;
import com.cooperhost.logistics.association.enums.AssociationType;
import com.cooperhost.logistics.association.exception.AssociationAlreadyExists;

import tools.jackson.databind.ObjectMapper;

/**
 *
 * @author cooper
 */
@WebMvcTest(AssociationController.class)
public class AssociationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AssociationService associationService;

    @Test
    public void testCreate_201() throws Exception {
        // Given a association to create
        UpsertAssociationDto createAssociationDto = new UpsertAssociationDto("Association", AssociationType.ASSOCIATION, "1", "1 rue du test", "test@yopmail.com", "0101010101", "Ceci est une description");
        AssociationDto associationDto = new AssociationDto("1", "Association", AssociationType.ASSOCIATION, "test", "1 rue du test", "test@yopmail.com", "0101010101", "Ceci est une description");

        when(associationService.create(any(UpsertAssociationDto.class))).thenReturn(associationDto);
        mockMvc.perform((post("/api/associations/"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createAssociationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.results.id").value("1"))
                .andExpect(jsonPath("$.results.name").value("Association"))
                .andExpect(jsonPath("$.results.type").value("ASSOCIATION"))
                .andExpect(jsonPath("$.results.personInCharge").value("test"))
                .andExpect(jsonPath("$.results.address").value("1 rue du test"))
                .andExpect(jsonPath("$.results.email").value("test@yopmail.com"))
                .andExpect(jsonPath("$.results.phone").value("0101010101"))
                .andExpect(jsonPath("$.results.description").value("Ceci est une description"));
    }

    @Test
    public void testCreate_401() throws Exception {
        // Given a association to create
        UpsertAssociationDto createAssociationDto = new UpsertAssociationDto("Association", AssociationType.ASSOCIATION, "1", "1 rue du test", "test@yopmail.com", "0101010101", "Ceci est une description");

        when(associationService.create(any(UpsertAssociationDto.class))).thenThrow(new AssociationAlreadyExists());
        mockMvc.perform((post("/api/associations/"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createAssociationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors").value("[\"The association already exists\"]"))
                .andExpect(jsonPath("$.results").isEmpty());
    }
}