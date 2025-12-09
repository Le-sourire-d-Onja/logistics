package com.cooperhost.logistics.article_type;

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

import com.cooperhost.logistics.article_type.dtos.ArticleTypeDto;
import com.cooperhost.logistics.article_type.dtos.CreateArticleTypeDto;
import com.cooperhost.logistics.article_type.dtos.UpdateArticleTypeDto;
import com.cooperhost.logistics.article_type.dtos.WrongCreateArticleTypeDto;
import com.cooperhost.logistics.article_type.models.ArticleTypeEntity;
import com.cooperhost.logistics.article_type.repositories.ArticleTypeRepository;
import com.cooperhost.logistics.shared.config.IntegrationTestConfig;

import tools.jackson.databind.ObjectMapper;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ArticleTypeIntegrationTest extends IntegrationTestConfig {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    @Autowired
    private MockMvc mockMvc;

    private CreateArticleTypeDto createArticleTypeDto;
    private ArticleTypeDto articleTypeDto;
    private UpdateArticleTypeDto updateArticleTypeDto;
    private WrongCreateArticleTypeDto wrongCreateArticleTypeDto;
    private ArticleTypeEntity articleType;

    @BeforeEach()
    public void beforeEach() {
        articleTypeRepository.deleteAll();
        createArticleTypeDto = new CreateArticleTypeDto("ArticleType", 10f, 10f);
        articleTypeDto = new ArticleTypeDto("1", "ArticleType", 10f, 10f);
        updateArticleTypeDto = new UpdateArticleTypeDto("ArticleType1", null, null);
        wrongCreateArticleTypeDto =  new WrongCreateArticleTypeDto();
        articleType = new ArticleTypeEntity(null, "ArticleType", 10f, 10f);
    }

    @Test
    public void testCreate_201() throws Exception {
        mockMvc.perform((post("/api/article-types"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createArticleTypeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.id").isString())
                .andExpect(jsonPath("$.data.name").value(articleTypeDto.getName()))
                .andExpect(jsonPath("$.data.weight").value(articleTypeDto.getWeight()))
                .andExpect(jsonPath("$.data.volume").value(articleTypeDto.getVolume()));
    }

    @Test
    public void testCreate_400() throws Exception {
        mockMvc.perform((post("/api/article-types"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(wrongCreateArticleTypeDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    public void testCreate_409() throws Exception {
        articleTypeRepository.save(articleType);
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
        articleTypeRepository.save(articleType);
        mockMvc.perform((get("/api/article-types"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.[0].id").isString())
                .andExpect(jsonPath("$.data.[0].name").value(articleTypeDto.getName()))
                .andExpect(jsonPath("$.data.[0].weight").value(articleTypeDto.getWeight()))
                .andExpect(jsonPath("$.data.[0].volume").value(articleTypeDto.getVolume()));
    }

    @Test
    public void testUpdate_200() throws Exception {
        ArticleTypeEntity savedArticleType = articleTypeRepository.save(articleType);
        articleTypeDto.setName(updateArticleTypeDto.getName());
        mockMvc.perform((patch("/api/article-types/" + savedArticleType.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateArticleTypeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.id").value(savedArticleType.getId()))
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
        ArticleTypeEntity articleTypeCopy1 = new ArticleTypeEntity(null, articleType.getName() + "1", articleType.getWeight(), articleType.getVolume());
        ArticleTypeEntity articleTypeCopy2 = new ArticleTypeEntity(null, articleType.getName(), articleType.getWeight(), articleType.getVolume());

        articleTypeRepository.save(articleTypeCopy1);
        articleTypeRepository.save(articleTypeCopy2);

        mockMvc.perform((patch("/api/article-types/" + articleTypeCopy2.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateArticleTypeDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The article type already exists"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testDelete_204() throws Exception {
        ArticleTypeEntity savedArticleType = articleTypeRepository.save(articleType);
        mockMvc.perform((delete("/api/article-types/" + savedArticleType.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    public void testDelete_404() throws Exception {
        mockMvc.perform((delete("/api/article-types/" + articleTypeDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("The article type is not found"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
