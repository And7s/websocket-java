package com.baeldung.dropwizard.introduction;

public class Mine {

    public static void main(final String[] args) throws Exception {
        System.out.println("hi");
        new IntroductionApplication().run("server",  "introduction-config.yml");
    }
}
