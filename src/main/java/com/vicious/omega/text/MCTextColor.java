package com.vicious.omega.text;

import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This is an exact copy of Bungee's ChatColor api.
 */
public enum MCTextColor {
    /**
     * Represents black.
     */
    BLACK( '0'),
    /**
     * Represents dark blue.
     */
    DARK_BLUE( '1'),
    /**
     * Represents dark green.
     */
    DARK_GREEN( '2'),
    /**
     * Represents dark blue (aqua).
     */
    DARK_AQUA( '3'),
    /**
     * Represents dark red.
     */
    DARK_RED( '4'),
    /**
     * Represents dark purple.
     */
    DARK_PURPLE( '5'),
    /**
     * Represents gold.
     */
    GOLD( '6'),
    /**
     * Represents gray.
     */
    GRAY( '7'),
    /**
     * Represents dark gray.
     */
    DARK_GRAY( '8'),
    /**
     * Represents blue.
     */
    BLUE( '9'),
    /**
     * Represents green.
     */
    GREEN( 'a'),
    /**
     * Represents aqua.
     */
    AQUA( 'b'),
    /**
     * Represents red.
     */
    RED( 'c'),
    /**
     * Represents light purple.
     */
    LIGHT_PURPLE( 'd'),
    /**
     * Represents yellow.
     */
    YELLOW( 'e'),
    /**
     * Represents white.
     */
    WHITE( 'f'),
    /**
     * Represents magical characters that change around randomly.
     */
    MAGIC( 'k'),
    /**
     * Makes the text bold.
     */
    BOLD( 'l'),
    /**
     * Makes a line appear through the text.
     */
    STRIKETHROUGH( 'm'),
    /**
     * Makes the text appear underlined.
     */
    UNDERLINE( 'n'),
    /**
     * Makes the text italic.
     */
    ITALIC( 'o'),
    /**
     * Resets all previous chat colors or formats.
     */
    RESET( 'r');
    /**
     * The special character which prefixes all chat colour codes. Use this if
     * you need to dynamically convert colour codes from your custom format.
     */
    public static final char COLOR_CHAR = '\u00A7';
    public static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr";
    /**
     * Pattern to remove all colour codes.
     */
    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile( "(?i)" + String.valueOf( COLOR_CHAR ) + "[0-9A-FK-OR]" );
    /**
     * Colour instances keyed by their active character.
     */
    private static final Map<Character, MCTextColor> BY_CHAR = new HashMap<>();
    /**
     * The code appended to {@link #COLOR_CHAR} to make usable colour.
     */
    private final char code;
    static
    {
        for (MCTextColor color : values() )
        {
            BY_CHAR.put( color.code, color);
        }
    }
    private final String str;
    MCTextColor(char code)
    {
        this.code = code;
        this.str=String.valueOf(COLOR_CHAR) + code;
    }

    @Override
    public String toString()
    {
        return str;
    }

    /**
     * Strips the given message of all color codes
     *
     * @param input String to strip of color
     * @return A copy of the input string, without any coloring
     */
    public static String stripColor(final String input)
    {
        if ( input == null )
        {
            return null;
        }

        return STRIP_COLOR_PATTERN.matcher( input ).replaceAll( "" );
    }

    public static String translateAlternateColorCodes(char altColorChar, String textToTranslate)
    {
        char[] b = textToTranslate.toCharArray();
        for ( int i = 0; i < b.length - 1; i++ )
        {
            if ( b[i] == altColorChar && ALL_CODES.indexOf( b[i + 1] ) > -1 )
            {
                b[i] = ChatColor.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase( b[i + 1] );
            }
        }
        return new String( b );
    }

    /**
     * Get the colour represented by the specified code.
     *
     * @param code the code to search for
     * @return the mapped colour, or null if non exists
     */
    public static MCTextColor getByChar(char code)
    {
        return BY_CHAR.get( code );
    }
}
