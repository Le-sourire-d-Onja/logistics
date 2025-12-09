package com.cooperhost.logistics.article_type;

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
import com.cooperhost.logistics.article_type.dtos.CreateArticleTypeDto;
import com.cooperhost.logistics.article_type.dtos.UpdateArticleTypeDto;
import com.cooperhost.logistics.article_type.exception.ArticleTypeAlreadyExists;
import com.cooperhost.logistics.article_type.exception.ArticleTypeNotFound;
import com.cooperhost.logistics.article_type.models.ArticleTypeEntity;
import com.cooperhost.logistics.article_type.repositories.ArticleTypeRepository;
import com.cooperhost.logistics.article_type.services.ArticleTypeService;

@WebMvcTest(ArticleTypeService.class)
public class ArticleTypeServiceUnitTest {
    @Autowired
    private ArticleTypeService articleTypeService;

    @MockitoBean
    private ArticleTypeRepository articleTypeRepository;

    private CreateArticleTypeDto createArticleTypeDto;
    private ArticleTypeDto articleTypeDto;
    private UpdateArticleTypeDto updateArticleTypeDto;
    private ArticleTypeEntity articleType;

    @BeforeEach()
    public void beforeEach() {
        createArticleTypeDto = new CreateArticleTypeDto("ArticleType", 10f, 10f);
        articleTypeDto = new ArticleTypeDto("1", "ArticleType", 10f, 10f);
        updateArticleTypeDto = new UpdateArticleTypeDto("ArticleType1", null, null);
        articleType = new ArticleTypeEntity("1", "ArticleType", 10f, 10f);
    }

    @Test
    public void testCreate_ShouldCreateAnArticleType() {
        when(articleTypeRepository.existsByName(any(String.class))).thenReturn(false);
        when(articleTypeRepository.save(any(ArticleTypeEntity.class))).thenReturn(articleType);
        ArticleTypeDto result = articleTypeService.create(createArticleTypeDto);
        assertEquals(result, articleTypeDto);
    }

    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    public void testCreate_ShouldThrowArticleTypeAlreadyExists() {
        when(articleTypeRepository.existsByName(any(String.class))).thenReturn(true);
        assertThrows(ArticleTypeAlreadyExists.class, () -> articleTypeService.create(createArticleTypeDto));
    }

    @Test
    public void testFindAll_ShouldReturnAllData() {
        when(articleTypeRepository.findAll()).thenReturn(List.of(articleType));
        List<ArticleTypeDto> result = articleTypeService.findAll();
        assertEquals(result, List.of(articleTypeDto));
    }

    @Test
    public void testUpdate_ShouldUpdateAnArticleType() {
        articleType.setName(updateArticleTypeDto.getName());
        articleTypeDto.setName(updateArticleTypeDto.getName());
        when(articleTypeRepository.findById(any(String.class))).thenReturn(Optional.of(articleType));
        when(articleTypeRepository.existsByName(any(String.class))).thenReturn(false);
        when(articleTypeRepository.save(any(ArticleTypeEntity.class))).thenReturn(articleType);
        ArticleTypeDto result = articleTypeService.update(articleTypeDto.getId(), updateArticleTypeDto);
        assertEquals(result, articleTypeDto);
    }

    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    public void testUpdate_ShouldThrowAssociatioNotFound() {
        when(articleTypeRepository.findById(any(String.class))).thenThrow(new ArticleTypeNotFound());
        assertThrows(ArticleTypeNotFound.class, () -> articleTypeService.update(articleTypeDto.getId(), updateArticleTypeDto));
    }

    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    public void testUpdate_ShouldThrowAssociatioAlreadyExists() {
        when(articleTypeRepository.findById(any(String.class))).thenReturn(Optional.of(articleType));
        when(articleTypeRepository.existsByName(any(String.class))).thenReturn(true);
        assertThrows(ArticleTypeAlreadyExists.class, () -> articleTypeService.update(articleTypeDto.getId(), updateArticleTypeDto));
    }

    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    public void testDelete_ShouldThrowAssociatioNotFound() {
        doThrow(new ArticleTypeNotFound()).when(articleTypeRepository).deleteById(any(String.class));
        assertThrows(ArticleTypeNotFound.class, () -> articleTypeService.delete(articleTypeDto.getId()));
    }
}
