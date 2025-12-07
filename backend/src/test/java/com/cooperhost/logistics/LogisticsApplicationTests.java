package com.cooperhost.logistics;


import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogisticsApplicationController.class)
class LogisticsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LogisticsApplicationService logisticsApplicationService;

	@Test
	void contextLoads() {
	}

	@Test
    public void testException_500() throws Exception {
        // Given a association to create
        when(logisticsApplicationService.hello()).thenThrow(new RuntimeException("A random exception"));
        mockMvc.perform((get("/"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errors.[0].field").isEmpty())
                .andExpect(jsonPath("$.errors.[0].message").value("A random exception"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
