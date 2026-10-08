package com.example.ResumeAnalyser.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class SkillExtractorService {

    private final List<String> availableSkills = Arrays.asList(

            "Spring Boot",
            "Spring",
            "JavaScript",
            "REST API",
            "GitHub",
            "MongoDB",
            "MySQL",
            "Python",
            "React",
            "Java",
            "HTML",
            "CSS",
            "C++",
            "C",
            "Git",
            "SQL"
    );

    public List<String> extractSkills(String resumeText) {

        Set<String> extractedSkills =
                new LinkedHashSet<>();

        if (resumeText == null ||
                resumeText.isBlank()) {

            return new ArrayList<>();
        }

        String normalizedText =
                normalizeText(resumeText);

        for (String skill : availableSkills) {

            if (containsSkill(
                    normalizedText,
                    skill)) {

                extractedSkills.add(skill);
            }
        }

        return new ArrayList<>(extractedSkills);
    }

    private boolean containsSkill(
            String text,
            String skill) {

        String normalizedSkill =
                normalizeText(skill);

        String regex;

        /*
         * Special handling for C++
         */
        if (normalizedSkill.equals("c++")) {

            regex = "(?<![a-z0-9])c\\+\\+(?![a-z0-9])";

        }

        /*
         * Special handling for C
         */
        else if (normalizedSkill.equals("c")) {

            regex = "(?<![a-z0-9])c(?![a-z0-9])";

        }

        /*
         * Normal skills
         */
        else {

            regex =
                    "(?<![a-z0-9])"
                    + Pattern.quote(normalizedSkill)
                    + "(?![a-z0-9])";
        }

        return Pattern
                .compile(
                        regex,
                        Pattern.CASE_INSENSITIVE
                )
                .matcher(text)
                .find();
    }

    private String normalizeText(String text) {

        return text
                .replace("\u00A0", " ")
                .replaceAll("\\s+", " ")
                .trim()
                .toLowerCase();
    }
}