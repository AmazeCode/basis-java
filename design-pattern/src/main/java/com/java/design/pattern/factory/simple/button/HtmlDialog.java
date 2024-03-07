package com.java.design.pattern.factory.simple.button;

/**
 * @description:
 * @author: AmazeCode
 * @date: 2024/3/7 17:14
 */
public class HtmlDialog extends Dialog{

    @Override
    public Button createButton() {
        return new HtmlButton();
    }
}
