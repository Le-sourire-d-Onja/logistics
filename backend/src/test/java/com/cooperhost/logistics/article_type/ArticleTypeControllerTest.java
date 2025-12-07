/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.cooperhost.logistics.article_type;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cooperhost.logistics.article_type.controllers.ArticleTypeController;
import com.cooperhost.logistics.article_type.services.ArticleTypeService;
import com.cooperhost.logistics.article_type.dtos.ArticleTypeDto;
import com.cooperhost.logistics.article_type.dtos.CreateArticleTypeDto;
import com.cooperhost.logistics.article_type.dtos.UpdateArticleTypeDto;
import com.cooperhost.logistics.article_type.dtos.WrongCreateArticleTypeDto;
import com.cooperhost.logistics.article_type.exception.ArticleTypeAlreadyExists;
import com.cooperhost.logistics.article_type.exception.ArticleTypeNotFound;

import tools.jackson.databind.ObjectMapper;

/**
 *
 * @author cooper
 */
@WebMvcTest(ArticleTypeController.class)
public class ArticleTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArticleTypeService articleTypeService;

    private CreateArticleTypeDto createArticleTypeDto;
    private ArticleTypeDto articleTypeDto;
    private UpdateArticleTypeDto updateArticleTypeDto;
    private WrongCreateArticleTypeDto wrongCreateArticleTypeDto;

    @BeforeEach()
    public void beforeEach() {
        createArticleTypeDto = new CreateArticleTypeDto("ArticleType", 10f, 10f);
        articleTypeDto = new ArticleTypeDto("1", "ArticleType", 10f, 10f);
        updateArticleTypeDto = new UpdateArticleTypeDto("ArticleType1", null, null);
        wrongCreateArticleTypeDto =  new WrongCreateArticleTypeDto();
    }

    @Test
    public void testCreate_201() throws Exception {
        when(articleTypeService.create(any(CreateArticleTypeDto.class))).thenReturn(articleTypeDto);
        mockMvc.perform((post("/api/article-types"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createArticleTypeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.id").value(articleTypeDto.getId()))
                .andExpect(jsonPath("$.data.name").value(articleTypeDto.getName()))
                .andExpect(jsonPath("$.data.weight").value(articleTypeDto.getWeight()))
                .andExpect(jsonPath("$.data.volume").value(articleTypeDto.getVolume()));
    }

    @Test
    public void testCreate_400() throws Exception {
        // Given a articleType to create
        mockMvc.perform((post("/api/article-types"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(wrongCreateArticleTypeDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    public void testCreate_409() throws Exception {
        when(articleTypeService.create(any(CreateArticleTypeDto.class))).thenThrow(new ArticleTypeAlreadyExists());
        mockMvc.perform((post("/api/article-types"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createArticleTypeDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The article type already exists"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testFindAll_200() throws Exception {
        when(articleTypeService.findAll()).thenReturn(List.of(articleTypeDto));
        mockMvc.perform((get("/api/article-types"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.[0].id").value(articleTypeDto.getId()))
                .andExpect(jsonPath("$.data.[0].name").value(articleTypeDto.getName()))
                .andExpect(jsonPath("$.data.[0].weight").value(articleTypeDto.getWeight()))
                .andExpect(jsonPath("$.data.[0].volume").value(articleTypeDto.getVolume()));
    }

    @Test
    public void testUpdate_200() throws Exception {
        articleTypeDto.setName(updateArticleTypeDto.getName());
        when(articleTypeService.update(any(String.class), any(UpdateArticleTypeDto.class))).thenReturn(articleTypeDto);
        mockMvc.perform((patch("/api/article-types/" + articleTypeDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateArticleTypeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.id").value(articleTypeDto.getId()))
                .andExpect(jsonPath("$.data.name").value(articleTypeDto.getName()))
                .andExpect(jsonPath("$.data.weight").value(articleTypeDto.getWeight()))
                .andExpect(jsonPath("$.data.volume").value(articleTypeDto.getVolume()));
    }

    @Test
    public void testUpdate_400() throws Exception {
        mockMvc.perform((patch("/api/article-types/" + articleTypeDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(wrongCreateArticleTypeDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    public void testUpdate_404() throws Exception {
        when(articleTypeService.update(any(String.class), any(UpdateArticleTypeDto.class))).thenThrow(new ArticleTypeNotFound());
        mockMvc.perform((patch("/api/article-types/" + articleTypeDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateArticleTypeDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The article type is not found"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testUpdate_409() throws Exception {
        when(articleTypeService.update(any(String.class), any(UpdateArticleTypeDto.class))).thenThrow(new ArticleTypeAlreadyExists());
        mockMvc.perform((patch("/api/article-types/" + articleTypeDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateArticleTypeDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The article type already exists"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testDelete_204() throws Exception {
        doNothing().when(articleTypeService).delete(any(String.class));
        mockMvc.perform((delete("/api/article-types/" + articleTypeDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    public void testDelete_404() throws Exception {
        doThrow(new ArticleTypeNotFound()).when(articleTypeService).delete(any(String.class));
        mockMvc.perform((delete("/api/article-types/" + articleTypeDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The article type is not found"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}