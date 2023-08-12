package com.pdfvending.utils;

import java.awt.Point;

import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextReplacedElementFactory;
import org.xhtmlrenderer.render.BlockBox;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HeaderFooterReplacedElementFactory extends ITextReplacedElementFactory {

    public HeaderFooterReplacedElementFactory(ITextOutputDevice outputDevice) {
        super(outputDevice);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ReplacedElement createReplacedElement(
            LayoutContext layoutContext, BlockBox blockBox,
            UserAgentCallback userAgentCallback, int cssWidth, int cssHeight) {

        Element element = blockBox.getElement();
        if (element == null) {
            return null;
        }

        String className = element.getAttribute("class");

        if ("header".equalsIgnoreCase(className)) {
            // Handle custom header logic
            return new CustomHeaderElement();
        } else if ("footer".equalsIgnoreCase(className)) {
            NodeList spanNodes = element.getElementsByTagName("span");
            for (int i = 0; i < spanNodes.getLength(); i++) {
                Node spanNode = spanNodes.item(i);
                if (spanNode instanceof Element) {
                    String spanClassName = ((Element) spanNode).getAttribute("class");
                    if ("pagenumber".equalsIgnoreCase(spanClassName)) {
                        // Handle custom footer logic
                        return new CustomFooterElement(layoutContext.getPage().getPageNo());
                    }
                }
            }
        }

        return super.createReplacedElement(layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);
    }

    public class CustomHeaderElement implements ReplacedElement {

        @Override
        public void detach(LayoutContext arg0) {
        }

        @Override
        public int getBaseline() {
            return 0;
        }

        @Override
        public int getIntrinsicHeight() {
            return 0;
        }

        @Override
        public int getIntrinsicWidth() {
            return 0;
        }

        @Override
        public Point getLocation() {
            return null;
        }

        @Override
        public boolean hasBaseline() {
            return false;
        }

        @Override
        public boolean isRequiresInteractivePaint() {
            return false;
        }

        @Override
        public void setLocation(int arg0, int arg1) {

        }
        // Implement methods for layout, such as getIntrinsicWidth, getIntrinsicHeight,
        // etc.
        // Render your custom header content in these methods
    }

    public class CustomFooterElement implements ReplacedElement {
        private final int pageNumber;

        public CustomFooterElement(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        @Override
        public void detach(LayoutContext arg0) {

        }

        @Override
        public int getBaseline() {
            return 0;
        }

        @Override
        public int getIntrinsicHeight() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public int getIntrinsicWidth() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public Point getLocation() {
            return null;
        }

        @Override
        public boolean hasBaseline() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isRequiresInteractivePaint() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void setLocation(int arg0, int arg1) {

        }

        // Implement methods for layout
        // Render "Page x of y" in these methods using the pageNumber member
    }

}
