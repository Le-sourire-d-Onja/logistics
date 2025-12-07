/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.cooperhost.logistics.association;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cooperhost.logistics.association.controllers.AssociationController;
import com.cooperhost.logistics.association.dtos.AssociationDto;
import com.cooperhost.logistics.association.dtos.CreateAssociationDto;
import com.cooperhost.logistics.association.dtos.UpdateAssociationDto;
import com.cooperhost.logistics.association.dtos.WrongCreateAssociationDto;
import com.cooperhost.logistics.association.enums.AssociationType;
import com.cooperhost.logistics.association.exception.AssociationAlreadyExists;
import com.cooperhost.logistics.association.exception.AssociationNotFound;
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

    private CreateAssociationDto createAssociationDto;
    private AssociationDto associationDto;
    private UpdateAssociationDto updateAssociationDto;
    private WrongCreateAssociationDto wrongCreateAssociationDto;

    @BeforeEach()
    public void beforeEach() {
        createAssociationDto = new CreateAssociationDto("Association", AssociationType.ASSOCIATION, "test", "1 rue du test", "+33101010101", "test@yopmail.com", "Ceci est une description");
        associationDto = new AssociationDto("1", "Association", AssociationType.ASSOCIATION, "test", "1 rue du test", "+33101010101", "test@yopmail.com", "Ceci est une description");
        updateAssociationDto = new UpdateAssociationDto("Association1", null, null, null, null, null, null);
        wrongCreateAssociationDto =  new WrongCreateAssociationDto();
    }

    @Test
    public void testCreate_201() throws Exception {
        when(associationService.create(any(CreateAssociationDto.class))).thenReturn(associationDto);
        mockMvc.perform((post("/api/associations"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createAssociationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.id").value(associationDto.getId()))
                .andExpect(jsonPath("$.data.name").value(associationDto.getName()))
                .andExpect(jsonPath("$.data.type").value(associationDto.getType().toString()))
                .andExpect(jsonPath("$.data.personInCharge").value(associationDto.getPersonInCharge()))
                .andExpect(jsonPath("$.data.address").value(associationDto.getAddress()))
                .andExpect(jsonPath("$.data.email").value(associationDto.getEmail()))
                .andExpect(jsonPath("$.data.phone").value(associationDto.getPhone()))
                .andExpect(jsonPath("$.data.description").value(associationDto.getDescription()));
    }

    @Test
    public void testCreate_400() throws Exception {
        // Given a association to create
        mockMvc.perform((post("/api/associations"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(wrongCreateAssociationDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[?(@.field == 'address')]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == 'email')]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == 'phone')]").exists());
    }

    @Test
    public void testCreate_409() throws Exception {
        when(associationService.create(any(CreateAssociationDto.class))).thenThrow(new AssociationAlreadyExists());
        mockMvc.perform((post("/api/associations"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createAssociationDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The association already exists"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testFindAll_200() throws Exception {
        when(associationService.findAll()).thenReturn(List.of(associationDto));
        mockMvc.perform((get("/api/associations"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.[0].id").value(associationDto.getId()))
                .andExpect(jsonPath("$.data.[0].name").value(associationDto.getName()))
                .andExpect(jsonPath("$.data.[0].type").value(associationDto.getType().toString()))
                .andExpect(jsonPath("$.data.[0].personInCharge").value(associationDto.getPersonInCharge()))
                .andExpect(jsonPath("$.data.[0].address").value(associationDto.getAddress()))
                .andExpect(jsonPath("$.data.[0].email").value(associationDto.getEmail()))
                .andExpect(jsonPath("$.data.[0].phone").value(associationDto.getPhone()))
                .andExpect(jsonPath("$.data.[0].description").value(associationDto.getDescription()));
    }

    @Test
    public void testUpdate_200() throws Exception {
        associationDto.setName(updateAssociationDto.getName());
        when(associationService.update(any(String.class), any(UpdateAssociationDto.class))).thenReturn(associationDto);
        mockMvc.perform((patch("/api/associations/" + associationDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateAssociationDto)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.id").value(associationDto.getId()))
                .andExpect(jsonPath("$.data.name").value(associationDto.getName()))
                .andExpect(jsonPath("$.data.type").value(associationDto.getType().toString()))
                .andExpect(jsonPath("$.data.personInCharge").value(associationDto.getPersonInCharge()))
                .andExpect(jsonPath("$.data.address").value(associationDto.getAddress()))
                .andExpect(jsonPath("$.data.email").value(associationDto.getEmail()))
                .andExpect(jsonPath("$.data.phone").value(associationDto.getPhone()))
                .andExpect(jsonPath("$.data.description").value(associationDto.getDescription()));
    }

    @Test
    public void testUpdate_400() throws Exception {
        mockMvc.perform((patch("/api/associations/" + associationDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(wrongCreateAssociationDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[?(@.field == 'email')]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == 'phone')]").exists());
    }

    @Test
    public void testUpdate_404() throws Exception {
        when(associationService.update(any(String.class), any(UpdateAssociationDto.class))).thenThrow(new AssociationNotFound());
        mockMvc.perform((patch("/api/associations/" + associationDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateAssociationDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The association is not found"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testUpdate_409() throws Exception {
        when(associationService.update(any(String.class), any(UpdateAssociationDto.class))).thenThrow(new AssociationAlreadyExists());
        mockMvc.perform((patch("/api/associations/" + associationDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateAssociationDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The association already exists"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}