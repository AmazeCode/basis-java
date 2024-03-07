package com.java.design.pattern.factory.simple.button;

/**
 * @description: Html按钮实现
 * @author: AmazeCode
 * @date: 2024/3/7 16:22
 */
public class HtmlButton implements Button{

    @Override
    public void render() {
        System.out.println("<button>Test Button</button>");
        onClick();
    }

    @Override
    public void onClick() {
        System.out.println("Click! Button says - 'Hello World!'");
    }
}
