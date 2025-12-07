/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.cooperhost.logistics.association;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cooperhost.logistics.association.controllers.AssociationController;
import com.cooperhost.logistics.association.dtos.AssociationDto;
import com.cooperhost.logistics.association.dtos.UpsertAssociationDto;
import com.cooperhost.logistics.association.dtos.WrongUpsertAssociationDto;
import com.cooperhost.logistics.association.enums.AssociationType;
import com.cooperhost.logistics.association.exception.AssociationAlreadyExists;
import com.cooperhost.logistics.association.services.AssociationService;

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

    private final UpsertAssociationDto createAssociationDto = new UpsertAssociationDto("Association", AssociationType.ASSOCIATION, "test", "1 rue du test", "test@yopmail.com", "0101010101", "Ceci est une description");
    private final AssociationDto associationDto = new AssociationDto("1", "Association", AssociationType.ASSOCIATION, "test", "1 rue du test", "test@yopmail.com", "0101010101", "Ceci est une description");
    private final WrongUpsertAssociationDto wrongUpsertAssociationDto = new WrongUpsertAssociationDto();

    @Test
    public void testCreate_201() throws Exception {
        when(associationService.create(any(UpsertAssociationDto.class))).thenReturn(associationDto);
        mockMvc.perform((post("/api/associations/"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createAssociationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.name").value("Association"))
                .andExpect(jsonPath("$.data.type").value("ASSOCIATION"))
                .andExpect(jsonPath("$.data.personInCharge").value("test"))
                .andExpect(jsonPath("$.data.address").value("1 rue du test"))
                .andExpect(jsonPath("$.data.email").value("test@yopmail.com"))
                .andExpect(jsonPath("$.data.phone").value("0101010101"))
                .andExpect(jsonPath("$.data.description").value("Ceci est une description"));
    }

    @Test
    public void testCreate_400() throws Exception {
        // Given a association to create
        mockMvc.perform((post("/api/associations/"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(wrongUpsertAssociationDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].field").value("address"))
                .andExpect(jsonPath("$.errors.[0].message").value("must not be blank"));
    }

    @Test
    public void testCreate_401() throws Exception {
        when(associationService.create(any(UpsertAssociationDto.class))).thenThrow(new AssociationAlreadyExists());
        mockMvc.perform((post("/api/associations/"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createAssociationDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The association already exists"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}