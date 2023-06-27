package com.example.pdf;

import com.example.pdf.entity.PdfEntity;
import com.example.pdf.repository.PdfRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.File;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PdfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PdfRepository pdfRepository;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void getAllPdfTest() throws Exception {
        List<PdfEntity> lst = mapper.readValue(new File("src/test/resources/allPdfList.json"), new TypeReference<List<PdfEntity>>(){});
        Mockito.when(pdfRepository.findAll()).thenReturn(lst);
        ResultActions response = this.mockMvc.perform(get("/get/all")).andExpect(status().isOk());
        response.andExpect(status().isOk());
    }


}
