package com.pdfvending.services.pdfgenerator;

import java.util.Map;

public interface HtmlRenderer {
    String render(String templateName, Map<String, Object> data);
}
