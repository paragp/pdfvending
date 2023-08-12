package com.pdfvending.services;

import java.util.Map;

public interface HtmlRenderer {
    String render(String templateName, Map<String, Object> data);
}
