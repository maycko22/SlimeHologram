package me.a8kj.bukkitprojects.hologram.util.markdown;

import org.bukkit.ChatColor;
import java.util.Arrays;
import java.util.List;

/**
 * A collection of standard {@link MarkdownRule} implementations.
 * Provides rules for color code translation, headers, and list formatting.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class MarkdownRules {

    /**
     * Creates a rule that translates standard Bukkit color codes (using '&').
     *
     * @return a MarkdownRule for color code translation
     */
    public static MarkdownRule colorRule() {
        return line -> ChatColor.translateAlternateColorCodes('&', line);
    }

    /**
     * Creates a rule that formats Markdown-style headers.
     * '#' becomes a gold bold header, '##' becomes a yellow bold header.
     *
     * @return a MarkdownRule for header formatting
     */
    public static MarkdownRule headerRule() {
        return line -> {
            if (line.startsWith("## ")) return ChatColor.YELLOW + "" + ChatColor.BOLD + line.substring(3);
            if (line.startsWith("# ")) return ChatColor.GOLD + "" + ChatColor.BOLD + line.substring(2);
            return line;
        };
    }

    /**
     * Creates a rule that formats Markdown-style list items.
     * '-' becomes a gray bullet point.
     *
     * @return a MarkdownRule for list item formatting
     */
    public static MarkdownRule listRule() {
        return line -> {
            if (line.startsWith("- ")) return ChatColor.GRAY + "• " + line.substring(2);
            return line;
        };
    }

    /**
     * Gets a list of all default Markdown rules.
     *
     * @return a list containing color, header, and list rules
     */
    public static List<MarkdownRule> defaults() {
        return Arrays.asList(colorRule(), headerRule(), listRule());
    }
}