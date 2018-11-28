package com.virtusa.guide.to.galaxy;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestApplication {

    @Test
    public void shouldAnswerQuestion() {
        StringBuilder sb = new StringBuilder();
        sb.append("blog is I\n");
        sb.append("blog Gold is 2 Credits\n");
        sb.append("how many Credits is blog Gold ?\n");
        sb.append("how much is blog ?\n");
        sb.append("DONE");
        ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Application.main(null);
        String output = out.toString();
        Assert.assertTrue(output.contains("blog Gold is 2.0 Credits"));
    }

    @Test
    public void shouldHaveUnknownQuestion() {
        StringBuilder sb = new StringBuilder();
        sb.append("blog is I\n");
        sb.append("blog Gold is 2 Credits\n");
        sb.append("how much blog blog ?\n");
        sb.append("DONE");
        ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Application.main(null);
        String output = out.toString();
        Assert.assertTrue(output.contains("I have not idea what you talking about"));
    }
}
