package com.cooperhost.logistics.association;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.cooperhost.logistics.association.dtos.AssociationDto;
import com.cooperhost.logistics.association.dtos.CreateAssociationDto;
import com.cooperhost.logistics.association.dtos.UpdateAssociationDto;
import com.cooperhost.logistics.association.dtos.WrongCreateAssociationDto;
import com.cooperhost.logistics.association.enums.AssociationType;
import com.cooperhost.logistics.association.models.AssociationEntity;
import com.cooperhost.logistics.association.repositories.AssociationRepository;
import com.cooperhost.logistics.shared.config.IntegrationTestConfig;

import tools.jackson.databind.ObjectMapper;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AssociationIntegrationTest extends IntegrationTestConfig {
    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private MockMvc mockMvc;

    private CreateAssociationDto createAssociationDto;
    private AssociationDto associationDto;
    private UpdateAssociationDto updateAssociationDto;
    private WrongCreateAssociationDto wrongCreateAssociationDto;
    private AssociationEntity association;

    @BeforeEach()
    public void beforeEach() {
        associationRepository.deleteAll();
        createAssociationDto = new CreateAssociationDto("Association", AssociationType.ASSOCIATION, "test", "1 rue du test", "+33101010101", "test@yopmail.com", "Ceci est une description");
        associationDto = new AssociationDto(UUID.randomUUID().toString(), "Association", AssociationType.ASSOCIATION, "test", "1 rue du test", "+33101010101", "test@yopmail.com", "Ceci est une description");
        updateAssociationDto = new UpdateAssociationDto("Association1", null, "test1", "2 rue du test", null, null, null);
        wrongCreateAssociationDto =  new WrongCreateAssociationDto();
        association = new AssociationEntity(null, "Association", AssociationType.ASSOCIATION, "test", "1 rue du test", "test@yopmail.com", "+33101010101", "Ceci est une description", Instant.now(), Instant.now());
    }

    @Test
    public void testCreate_201() throws Exception {
        mockMvc.perform((post("/api/associations"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createAssociationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.id").isString())
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
        mockMvc.perform((post("/api/associations"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(wrongCreateAssociationDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    public void testCreate_409() throws Exception {
        associationRepository.save(association);
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
        AssociationEntity savedAssociation = associationRepository.save(association);
        mockMvc.perform((get("/api/associations"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.[0].id").value(savedAssociation.getId()))
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
        AssociationEntity savedAssociation = associationRepository.save(association);
        associationDto.setName(updateAssociationDto.getName());
        associationDto.setPersonInCharge(updateAssociationDto.getPersonInCharge());
        associationDto.setAddress(updateAssociationDto.getAddress());
        mockMvc.perform((patch("/api/associations/" + savedAssociation.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateAssociationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.id").value(savedAssociation.getId()))
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
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    public void testUpdate_404() throws Exception {
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
        AssociationEntity associationCopy1 = new AssociationEntity(null, association.getName() + UUID.randomUUID().toString(), association.getType(), association.getPersonInCharge(), association.getAddress(), association.getEmail(), association.getPhone(), association.getDescription(), association.getCreatedAt(), association.getUpdatedAt());
        AssociationEntity associationCopy2 = new AssociationEntity(null, association.getName(), association.getType(), association.getPersonInCharge(), association.getAddress(), association.getEmail(), association.getPhone(), association.getDescription(), association.getCreatedAt(), association.getUpdatedAt());

        associationRepository.save(associationCopy1);
        associationRepository.save(associationCopy2);

        mockMvc.perform((patch("/api/associations/" + associationCopy2.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateAssociationDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The association already exists"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testDelete_204() throws Exception {
        AssociationEntity savedAssociation = associationRepository.save(association);
        mockMvc.perform((delete("/api/associations/" + savedAssociation.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    public void testDelete_404() throws Exception {
        mockMvc.perform((delete("/api/associations/" + associationDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The association is not found"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
