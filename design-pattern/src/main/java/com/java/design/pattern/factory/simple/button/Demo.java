package com.java.design.pattern.factory.simple.button;

/**
 * @description:
 * @author: AmazeCode
 * @date: 2024/3/7 17:16
 */
public class Demo {

    private static Dialog dialog;

    public static void main(String[] args) {
        configure();
        runBusinessLogic();
    }

    static void configure() {
        if (System.getProperty("os.name").equals("Windows 10")){
            dialog = new WindowDialog();
        } else {
            dialog = new HtmlDialog();
        }
    }

    static void runBusinessLogic() {
        dialog.renderWindow();
    }
}
