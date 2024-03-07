package com.java.design.pattern.factory.simple.button;

/**
 * @description:
 * @author: AmazeCode
 * @date: 2024/3/7 17:11
 */
public abstract class Dialog {

    public void renderWindow() {
        // other code
        Button okButton = createButton();
        okButton.render();
    }

    public abstract Button createButton();
}
