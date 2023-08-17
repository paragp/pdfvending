package com.pdfvending.services.doctype;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pdfvending.exception.MissingDataException;
import com.pdfvending.services.pdfgenerator.HtmlRenderer;
import com.pdfvending.services.pdfgenerator.PDFConverter;
import com.pdfvending.services.pdfgenerator.PDFGenerator;
import com.pdfvending.utils.GetCurrentTimestamp;

@Service("SanctionLetterESign")
public class SanctionLetterESignGenerator implements PDFGenerator {
    private static final Logger logger = LoggerFactory.getLogger(SanctionLetterESignGenerator.class);
    private static final String TRACE_ID = "traceId";
    private static final String[] REQUIRED_FIELDS = { "applicantName", "sanctionAmount", "repaymentSchedule" };
    @Autowired
    private HtmlRenderer htmlRenderer;

    @Autowired
    private PDFConverter pdfConverter;

    @Value("${sanctionletter-esign-name}")
    private String templateName;

    @Override
    public CompletableFuture<byte[]> generatePDF(Map<String, Object> data) {
        if (data == null) {
            throw new MissingDataException("Data cannot be null.", null);
        }
        validateData(data);
        data.put("pdfId", MDC.get(TRACE_ID));
        data.put("dateTime", GetCurrentTimestamp.getCurrentTimeStamp());
        String renderedHtmlContent = htmlRenderer.render(templateName, data);
        logger.info("Generating the Sanction Letter ESign PDF");
        return pdfConverter.convert(renderedHtmlContent, templateName);
    }

    @Override
    public void validateData(Map<String, Object> data) {
        logger.info("Validating the Sanction Letter ESign PDF Data");
        if (data == null) {
            throw new MissingDataException("Oops! You forgot provide data.", null);
        }

        for (String field : REQUIRED_FIELDS) {
            if (!data.containsKey(field)) {
                throw new MissingDataException("Oh no! You missed inserting the " + field + " coin.", null);
            }
        }
    }

}
