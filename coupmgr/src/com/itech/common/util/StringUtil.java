/*******************************************************************************
 * Copyright (C) 2009, McAfee, Inc. All Rights Reserved.
 ******************************************************************************/

package com.itech.common.util;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;


public class StringUtil
{
	public static int UNLIMITED_COUNT = 0;


	/**
	 * Returns an empty string if the argument is null, empty, or blank.  Otherwise,
	 * the argument is returned.
	 */
	public static String emptyIfBlank( String string )
	{
		return defaultIfBlank( string, "" );
	}


	/**
	 * Returns an empty string if the argument is null.  Otherwise, the argument
	 * is returned.
	 */
	public static String emptyIfNull( String string )
	{
		return string == null ? "" : string;
	}



	/**
	 * Returns null if the argument is null, empty, or blank.  Otherwise,
	 * the argument is returned.
	 */
	public static String nullIfBlank( String string )
	{
		return defaultIfBlank( string, null );
	}


	/**
	 * Returns a default value if the argument is null, empty, or blank.  Otherwise,
	 * the argument is returned.
	 *
	 * @param string
	 *        string to be tested
	 * @param defaultValue
	 *        value to be returned if the string is null, empty, or blank
	 */
	public static String defaultIfBlank( String string, String defaultValue )
	{
		if( StringUtils.isBlank( string ) )
		{
			return defaultValue;
		}
		return string;
	}



	/**
	 * This removes duplicate whitespace from the middle of strings as well as trimming any
	 * whitespace from the beginning or end of a string.  If the string is null, it will return
	 * the empty string.
	 * <p/>
	 * Examples:
	 * "  ab    cd" -> "ab cd"
	 * "a b c d "   -> "a b c d"
	 * "ab c  d"    -> "ab c d"
	 *
	 * @param text
	 * @return normalized string
	 */
	public static String removeExtraWhitespace( String text )
	{
		if (text == null)
		{
			return null;
		}

		String s = StringUtils.trimToEmpty( text );

		int sz = s.length();
		char[] chs = new char[sz];
		int count = 0;
		for (int i = 0; i < sz; i++)
		{
			if (!Character.isWhitespace( s.charAt( i ) ))
			{
				chs[count++] = s.charAt( i );
			}
			else if (i < s.length() && !Character.isWhitespace( s.charAt( i + 1 ) ))
			{
				chs[count++] = s.charAt( i );
			}
		}
		if (count == sz)
		{
			return s;
		}
		return new String( chs, 0, count );
	}

	public static String substitute( String message, Map<String, String> map )
	{
		return substitute( message, map, null );
	}

	/**
	 * Escapes a string for use in a JavaScript string literal inside JSP page.  This is used when you
	 * need to write a dynamic string into some javascript like so:
	 * <p/>
	 * var myJSValue = "${someRequestVariable}";
	 * <p/>
	 * This method is also available in the orion tag library and can be embedded directly
	 * into the JSP like so:
	 * <p/>
	 * var myJSValue = "${orion:escapeJavaScriptString(someRequestVariable)}";
	 *
	 * @param text - the unescaped string
	 * @return a string with the characters escaped for a javascript string literal.
	 */
	public static String escapeJavaScriptString( String text )
	{

		String escaped = StringEscapeUtils.escapeJavaScript( text );

		if (escaped != null)
		{
			/*
			 * these there are here so that if the string literal
			 * contains "</script>" it wouldn't end the script block.
			 */
			escaped = escaped.replace( "/", "\\u002f" );
			escaped = escaped.replace( "<", "\\u003c" );
			escaped = escaped.replace( ">", "\\u003e" );
			// these two are here so that if the script is in a html tag attribute
			// simplly change ' into \' and " into \" (what StringEscapeUtils.escapeJavaScript does) would not
			// be sufficient since html engine would still interprete the quotes as ending the attribute. eg.
			// <td onclick="myFunction(\"test\")"> would still pose a script error.
			// Therefore we need to change \' and \" into its unicode representation. html would interprete it
			// corretly. We don't need to do escapeHtml here since escapeJavaScript already turned other characters
			// into unicode representation, it's only the single quote and double quote we need to worry about.
			escaped = escaped.replace( "\\\"", "\\u0022" );
			escaped = escaped.replace( "\\\'", "\\u0027" );

		}

		return escaped;
	}

	/**
	 * returns a string identical to the passed in one, except that each
	 * instanace of {name} is replaced with map.get("name"). For example, if
	 * message is "My name is {name} and I am {age} years old", and
	 * map.get("name") => Fred and map.get("age") => 12 then the return
	 * string is "My name is Fred and I am 12 years old".
	 * <p/>
	 * TODO: support escaped curlys.
	 */
	public static String substitute( String message, Map<String, String> map, String defaultText )
	{
		// TODO: BUG -- If the substituted text has {1234} in it, the - Paul's note
		//		 resulting substitution is empty.  Either the appendReplacement
		//		 below isn't working as described, or the numbers in the
		//		 substited token are causing problems...
		Pattern p = Pattern.compile( "\\{([a-zA-Z0-9\\.\\-_]+)\\}" );
		Matcher m = p.matcher( message );

		StringBuffer sb = new StringBuffer();
		boolean found = m.find();
		while (found)
		{
			String s = map.get( m.group( 1 ) );
			if (s == null && defaultText == null)
			{
				found = false;
			}
			else if (s == null && defaultText != null)
			{
				s = defaultText;
			}
			else if (s.length() == 0 && defaultText != null)
			{
				s = defaultText;
			}
			if (found)
			{
				s = escapeForRegexp( s );
			}
			if (found) {
				m.appendReplacement( sb, s );
			}
			found = m.find();
		}
		m.appendTail( sb );

		return sb.toString();
	}


	/**
	 * This method will take an incoming string and escape all of the characters that have special
	 * meaning in a regular expression by adding a '\' character before it.  Note: this method is
	 * used by the substitute method, so it is not necessary to call this separately from substitute.
	 *
	 * @param s the string to be escaped
	 * @return a string with all regular expression special characters escaped with '\'
	 */
	private static String escapeForRegexp( String s )
	{
		if (s != null)
		{
			if (s.indexOf( '\\' ) != -1)
			{
				s = s.replaceAll( "\\\\", "\\\\\\\\" );
			}
			if (s.indexOf( '$' ) != -1)
			{
				s = s.replaceAll( "\\$", "\\\\\\$" );
			}
			if (s.indexOf( '^' ) != -1)
			{
				s = s.replaceAll( "\\^", "\\\\\\^" );
			}
			if (s.indexOf( '.' ) != -1)
			{
				s = s.replaceAll( "\\.", "\\\\\\." );
			}
			if (s.indexOf( '[' ) != -1)
			{
				s = s.replaceAll( "\\[", "\\\\\\[" );
			}
			if (s.indexOf( ']' ) != -1)
			{
				s = s.replaceAll( "\\]", "\\\\\\]" );
			}
			if (s.indexOf( '(' ) != -1)
			{
				s = s.replaceAll( "\\(", "\\\\\\(" );
			}
			if (s.indexOf( ')' ) != -1)
			{
				s = s.replaceAll( "\\)", "\\\\\\)" );
			}
			if (s.indexOf( '?' ) != -1)
			{
				s = s.replaceAll( "\\?", "\\\\\\?" );
			}
			if (s.indexOf( '{' ) != -1)
			{
				s = s.replaceAll( "\\{", "\\\\\\{" );
			}
			if (s.indexOf( '}' ) != -1)
			{
				s = s.replaceAll( "\\}", "\\\\\\}" );
			}
		}
		return s;
	}

	public static String chop( String str, int maxWidth )
	{
		if (str == null)
		{
			return null;
		}
		if (maxWidth < 10)
		{
			throw new IllegalArgumentException( "Minimum chop width is 10" );
		}
		if (str.length() <= maxWidth)
		{
			return str;
		}
		return str.substring( 0, maxWidth );
	}

	public static List<String> substitute( List items, Map<String, String> map )
	{
		return substitute( items, map, null );
	}

	public static List<String> substitute( List items, Map<String, String> map, String defaultText )
	{
		List<String> result = new LinkedList<String>();
		ListIterator iter = items.listIterator();

		while (iter.hasNext())
		{
			Object o = iter.next();
			if (o != null)
			{
				String text = o.toString();
				String r = substitute( text, map, defaultText );
				result.add( r );
			}
		}

		return result;
	}





	/**
	 * remove the beginning and ending brackets from the incoming string
	 * @param str
	 * @return
	 */
	public static String removeBrackets( String str )
	{
		if (str == null)
		{
			return "";
		}
		str = str.trim();
		if (str.startsWith( "(" ) && str.endsWith( ")" ))
		{
			str = str.substring( 1 );
			str = str.substring( 0, str.length() - 1 );
		}
		return str;
	}


	/**
	 * Appends a String to a StringBuilder if it is not blank.  If the StringBuilder
	 * does not have any contents, a newline character will be appended before
	 * appending the message.
	 *
	 * @param builder
	 *        StringBuilder which to append
	 * @param message
	 *        message to append
	 * @exclude
	 */
	public static void appendMessage( StringBuilder builder, String message )
	{
		if( StringUtils.isNotBlank( message ) )
		{
			if( builder.length() > 0 )
			{
				builder.append( "\n" );
			}
			builder.append( message );
		}
	}


	/**
	 * Returns the concatenation of the argument strings with a '.' character
	 * as a separator if the "parent" argument is not blank.  Otherwise, just
	 * the "child" argument is returned.

	 */
	public static String qualify( String parent, String child )
	{
		if( StringUtils.isNotBlank( parent ) )
		{
			return parent + "." + child;
		}
		return child;
	}


	/**
	 * Escapes a string for XML.  This method differs from StringEscapeUtils.escapeHtml()
	 * and StringEscapeUtils.escapeXml() in that it leaves double-byte characters
	 * intact, rather than replacing them with HTML/XML entities.  This allows
	 * double-byte characters to be written out in their native UTF-8 encoding.
	 */
	public static String escapeXml( String str )
	{
		if( str == null || str.length() == 0) {
			return str;
		}

		StringBuilder builder = new StringBuilder( (int) ( str.length() * 1.1 ) );
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if     ( '&' == c ) {
				builder.append( "&amp;" );
			} else if( '<' == c ) {
				builder.append( "&lt;" );
			} else if( '>' == c ) {
				builder.append( "&gt;" );
			} else if( '"' == c ) {
				builder.append( "&quot;" );
			} else if( '\'' == c ) {
				builder.append( "&apos;" );
			} else {
				builder.append( c );
			}
		}
		return builder.toString();
	}

	public static String escapeXmlTag( String str )
	{
		if( str == null || str.length() == 0) {
			return str;
		}

		StringBuilder builder = new StringBuilder( (int) ( str.length() * 1.1 ) );
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if     ( '&' == c ) {
				builder.append( "&amp;" );
			} else if( '<' == c ) {
				builder.append( "&lt;" );
			} else if( '>' == c ) {
				builder.append( "&gt;" );
			} else {
				builder.append( c );
			}
		}
		return builder.toString();
	}
}

