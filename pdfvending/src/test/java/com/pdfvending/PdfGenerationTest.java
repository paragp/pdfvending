package com.pdfvending;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class PdfGenerationTest {

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void testGeneratePdf() throws Exception {
                // Create a sample JSON payload for the request.
                String jsonPayload = "{"
                                + "\"applicantName\": \"John Doe\""
                                + "}";

                // Trigger the PDF generation through MockMvc
                MockHttpServletResponse response = mockMvc.perform(
                                post("/api/pdf/generate/WelcomeLetter")
                                                .contentType("application/json")
                                                .content(jsonPayload))
                                .andExpect(status().isOk()) // Check for HTTP 200 OK status.
                                .andReturn().getResponse();

                byte[] responseBytes = response.getContentAsByteArray();

                // Check if PDF is generated and not empty.
                assertNotNull(responseBytes);
                assertTrue(responseBytes.length > 0);

                // Optionally: Check for specific content or layout in the PDF. This can be
                // complex
                // and might require libraries that can read and parse PDF content.
        }
}
