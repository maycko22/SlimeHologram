package me.a8kj.bukkitprojects.hologram.util.markdown;

import java.util.List;

/**
 * Parser for applying a chain of {@link MarkdownRule}s to a string.
 * This allows for dynamic formatting of hologram text using simple Markdown syntax.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class MarkdownParser {

    /**
     * The list of rules to apply sequentially.
     */
    private final List<MarkdownRule> rules;

    /**
     * Constructs a new MarkdownParser with the specified list of rules.
     *
     * @param rules the rules to apply during parsing
     */
    public MarkdownParser(List<MarkdownRule> rules) {
        this.rules = rules;
    }

    /**
     * Parses a string by applying all configured rules in order.
     *
     * @param line the input string to parse
     * @return the formatted string
     */
    public String parse(String line) {
        for (MarkdownRule rule : rules) {
            line = rule.apply(line);
        }
        return line;
    }
}