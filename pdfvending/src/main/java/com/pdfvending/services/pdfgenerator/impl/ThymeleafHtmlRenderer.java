package com.pdfvending.services.pdfgenerator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.pdfvending.services.pdfgenerator.HtmlRenderer;

import java.util.Map;

@Service
public class ThymeleafHtmlRenderer implements HtmlRenderer {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public String render(String templateName, Map<String, Object> data) {
        Context context = new Context();
        context.setVariables(data);
        return templateEngine.process(templateName, context);
    }
}
