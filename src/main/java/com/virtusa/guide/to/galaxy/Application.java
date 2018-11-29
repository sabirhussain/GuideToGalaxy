package com.virtusa.guide.to.galaxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        List<String> questions = new ArrayList<>();
        List<String> unknownStatements = new ArrayList<>();

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Start Input (To end type DONE)");

            for (String line = sc.nextLine(); !"DONE".equals(line); line = sc.nextLine()) {
                line = line.replaceAll("\\s{2,}", " ").trim();

                if (GalaxyMaster.INSTANCE.feedKnowledge(line, unknownStatements))
                    continue;

                if (line.contains("?") && line.contains("is")) {
                    questions.add(line);
                    continue;
                }

                unknownStatements.add(line);
            }
        }

        System.out.println("======== Output ========");
        unknownStatements.addAll(GalaxyMaster.INSTANCE.answerQuestions(questions));
        unknownStatements.forEach(s -> {
            System.out.println("[" + s + "] I have not idea what you talking about.");
        });
    }

}
