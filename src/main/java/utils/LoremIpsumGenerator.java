package utils;

import java.util.Random;

public class LoremIpsumGenerator {
    private static final String[] WORDS = {
            "lorem", "ipsum", "dolor", "sit", "amet", "consectetur",
            "adipiscing", "elit", "sed", "do", "eiusmod", "tempor",
            "incididunt", "ut", "labore", "et", "dolore", "magna",
            "aliqua", "enim", "ad", "minim", "veniam", "quis",
            "nostrud", "exercitation", "ullamco", "laboris",
            "nisi", "aliquip", "ex", "ea", "commodo", "consequat"
    };

    private static final Random RANDOM = new Random();

    public static String generateLoremIpsum() {
        int sentenceCount = 3 + RANDOM.nextInt(3); // 3–5 câu
        StringBuilder paragraph = new StringBuilder();

        for (int i = 0; i < sentenceCount; i++) {
            int wordCount = 8 + RANDOM.nextInt(8); // 8–15 từ
            paragraph.append(generateSentence(wordCount)).append(" ");
        }

        return paragraph.toString().trim();
    }

    private static String generateSentence(int wordCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            String word = WORDS[RANDOM.nextInt(WORDS.length)];
            if (i == 0) {
                word = word.substring(0, 1).toUpperCase() + word.substring(1);
            }
            sb.append(word);
            if (i < wordCount - 1) {
                sb.append(" ");
            } else {
                sb.append(".");
            }
        }
        return sb.toString();
    }
}
