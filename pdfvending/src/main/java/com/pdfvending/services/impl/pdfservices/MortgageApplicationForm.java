package com.pdfvending.services.impl.pdfservices;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.pdfvending.exception.MissingDataException;
import com.pdfvending.services.HtmlRenderer;
import com.pdfvending.services.PDFConverter;
import com.pdfvending.services.PDFGenerator;
import com.pdfvending.utils.GetCurrentTimestamp;

@Service("MortgageApplicationForm")
public class MortgageApplicationForm implements PDFGenerator {

    private static final Logger logger = LoggerFactory.getLogger(MortgageApplicationForm.class);
    private static final String TRACE_ID = "traceId";
    @Autowired
    private HtmlRenderer htmlRenderer;

    @Autowired
    private PDFConverter pdfConverter;

    @Value("${mortgage.template.name}")
    private String templateName;

    @Override
    public CompletableFuture<byte[]> generatePDF(Map<String, Object> data) {
        data.put("pdfId", MDC.get(TRACE_ID));
        data.put("dateTime", GetCurrentTimestamp.getCurrentTimeStamp());
        String renderedHtmlContent = htmlRenderer.render(templateName, data);
        validateData(data);
        logger.info("Generating the Mortgage Application form PDF");
        return pdfConverter.convert(renderedHtmlContent);
    }

    @Override
    public void validateData(Map<String, Object> data) {
        logger.info("Validating the Mortgage Application Form Data");
        if (data == null) {
            throw new MissingDataException("Oops! You forgot provide data.");
        }
        String[] requiredFields = { "applicant", "employment", "financial", "property" };
        for (String field : requiredFields) {
            if (!data.containsKey(field)) {
                throw new MissingDataException("Oh no! You missed inserting the " + field + " coin.");
            }
        }
    }
}
