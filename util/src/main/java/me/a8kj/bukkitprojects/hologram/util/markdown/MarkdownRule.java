package me.a8kj.bukkitprojects.hologram.util.markdown;

/**
 * A functional interface representing a single text formatting rule.
 * Used in conjunction with {@link MarkdownParser} to process strings.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@FunctionalInterface
public interface MarkdownRule {

    /**
     * Applies this formatting rule to the given string.
     *
     * @param line the input string
     * @return the modified string
     */
    String apply(String line);
}