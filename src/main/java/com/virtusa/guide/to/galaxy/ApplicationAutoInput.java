package com.virtusa.guide.to.galaxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ApplicationAutoInput {

    public static void main(String[] args) {

        List<String> questions = new ArrayList<>();
        List<String> unknownStatements = new ArrayList<>();

        try (InputStream in = ApplicationAutoInput.class.getResourceAsStream("/input.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                line = line.replaceAll("\\s{2,}", " ").trim();

                if (GalaxyMaster.INSTANCE.feedKnowledge(line, unknownStatements))
                    continue;

                if (line.contains("?") && line.contains("is")) {
                    questions.add(line);
                    continue;
                }

                unknownStatements.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("======== Output ========");
        unknownStatements.addAll(GalaxyMaster.INSTANCE.answerQuestions(questions));
        unknownStatements.forEach(s -> {
            System.out.println("[" + s + "] I have not idea what you talking about.");
        });

    }
}
