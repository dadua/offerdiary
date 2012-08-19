
package com.itech.common.resource;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.itech.common.util.StringUtil;

public class Resource
{
	public static boolean DEBUG = false;
	private static final Logger logger = Logger.getLogger( Resource.class );


	private ClassLoader classLoader = null;
	private String[] bundleNames = new String[]{ };
	private Locale defaultLocale = getServerLocale();


	public Resource( ClassLoader loader, String... bundleNames ){
		classLoader = loader;
		if (bundleNames == null || bundleNames.length < 1) {
			throw new IllegalArgumentException(
			"Resource must be instantiated with at least one bundle name." );
		}
		this.bundleNames = bundleNames;
	}


	public void setClassLoader( ClassLoader loader ) {
		classLoader = loader;
	}


	/**
	 * Returns the default Locale for this Resource.  By default it will return the server's
	 * Locale.
	 */
	public Locale getDefaultLocale()
	{
		return defaultLocale;
	}



	/**
	 * Used to set the default locale for all getXXX methods used w/o a lang specified
	 *
	 * @param loc the iso 639 two-letter locale that you want to have messages come out in by default.
	 */
	public void setDefaultLocale( String loc )
	{
		setDefaultLocale( new Locale( loc ) );
	}

	/**
	 * Sets the default Locale this Resource.
	 *
	 */
	public void setDefaultLocale( Locale loc )
	{
		if( loc == null )
		{
			loc = getServerLocale();
		}
		defaultLocale = loc;
	}

	public static Locale getServerLocale()
	{
		return Locale.getDefault();
	}

	/**
	 * Retrieves a resource string pre-escaped for HTML.
	 *
	 * @param key
	 *        resource bundle key
	 * @param loc
	 *        locale for which the String should be returned
	 */
	public String getHtmlString( String key, Locale loc )
	{
		return StringEscapeUtils.escapeHtml( getString( key, loc ) );
	}


	/**
	 * Retrieves a resource string pre-escaped for javascript.
	 *
	 * @param key
	 *        resource bundle key
	 * @param loc
	 *        locale for which the String should be returnedK
	 */
	public String getJavaScriptString( String key, Locale loc )
	{
		return StringUtil.escapeJavaScriptString( getString( key, loc ) );
	}



	/**
	 * Retrieves a resource string pre-escaped for XML.
	 *
	 * @param key
	 *        resource bundle key
	 * @param locale
	 *        locale for which the String should be returned
	 *
	 */
	public String getXmlString( String key, Locale locale )
	{
		return StringEscapeUtils.escapeXml( getString( key, locale ) );
	}


	/**
	 * Gets a localized string for the argument locale.
	 *
	 * @param key
	 *       the resource key
	 * @param loc
	 *        Locale for which the result should be returned.
	 *        If this is null, this Resource's default Locale will be used
	 * @return a String containing the localized text,
	 *         or the key itself if no localized text could be found.
	 *
	 * @see #getDefaultLocale()
	 */
	public String getString( String key, Locale loc )
	{
		return getString( getDefaultBundleName(), key, loc );
	}


	//--------------------------------------------------------------------------
	// formatString
	//--------------------------------------------------------------------------


	/**
	 * Formats a parameterized string for display.
	 *
	 * @param key    The key to lookup in the resource bundle.  The value of this key should contain
	 *               {@code params.length} parameters given by {0}, {1}, {2}, etc.  These
	 *               parameters will be replaced with the associated values of the param array.
	 *               This will all be done in the language specified by the {@code locale}
	 *               parameter.
	 * @param locale
	 *        Locale to localize the message in.
	 * @param params
	 *        the values to be substituted for for the {0}, {1}, {2}, etc. parameters.
	 * @return A substituted and localized string.
	 */
	public String formatString( String key, Locale locale, Object... params )
	{
		return formatString( getDefaultBundleName(), locale, key, params );
	}


	/**
	 * Returns the result of invoking
	 * {@link #formatString(String, java.util.Locale, Object[]) formatString()}
	 * and escaping it for HTML.
	 *
	 * @param key
	 *        resource bundle key
	 * @param locale
	 *        locale for which the String should be returned
	 * @param params
	 *        formatting parameters
	 *
	 * @see #formatString(String, java.util.Locale, Object[])
	 *
	 */
	public String formatHtmlString( String key, Locale locale, Object... params )
	{
		return StringEscapeUtils
		.escapeHtml( formatString( key, locale, params ) );
	}


	/**
	 * Returns the result of invoking
	 * {@link #formatString(String, java.util.Locale, Object[]) formatString()}
	 * and escaping it for XML.
	 *
	 * @param key
	 *        resource bundle key
	 * @param locale
	 *        locale for which the String should be returned
	 * @param params
	 *        formatting parameters
	 *
	 * @see #formatString(String, java.util.Locale, Object[])
	 *
	 */
	public String formatXmlString( String key, Locale locale, Object... params )
	{
		return StringEscapeUtils
		.escapeXml( formatString( key, locale, params ) );
	}

	/**
	 * Returns the result of invoking
	 * {@link #formatString(String, java.util.Locale, Object[]) formatString()}
	 * and escaping it for Javascript.
	 *
	 * @param key
	 *        resource bundle key
	 * @param locale
	 *        locale for which the String should be returned
	 * @param params
	 *        formatting parameters
	 *
	 * @see #formatString(String, java.util.Locale, Object[])
	 *
	 */
	public String formatJavaScriptString( String key, Locale locale, Object... params )
	{
		return StringUtil.escapeJavaScriptString( formatString( key, locale, params ) );
	}

	/**
	 * Returns the string from the specified bundle with the specified locale.
	 *
	 * @param bundleName the base name of the resource bundle to look in
	 * @param key        the key in the bundle to load
	 * @param loc        the locale of the bundle to load (if null, the default locale is used).
	 * @return the string gotten from the appropriate bundle for the reqeusted locale.
	 * @throws NullPointerException if bundleName or key are null.
	 */
	private final String getString( String bundleName, String key, Locale loc ) {
		if( loc == null )
		{
			loc = getDefaultLocale();
		}
		try
		{
			if (DEBUG) {
				return key;
			}
			ResourceBundle b = ResourceBundle.getBundle( bundleName, loc, classLoader );
			return b.getString( key );
		}
		catch (MissingResourceException e)
		{
			logger.debug( "could not find String for key [" + key + "] in bundle " + bundleName +
			". Returning the key itself." );
			return key;
		}
		catch (Exception e)
		{
			logger.error( "could not find String for key [" + key +
					"] due to an error. Returning the key itself.", e );
			return key;
		}
	}


	/**
	 * Formats a parameterized string for display.
	 * NOTE: All strings retrieved from the localized bundles will have any single quotes doubled
	 * so that they will show up in the resulting string. <b>This breaks the functionality of the
	 * underlying MessageFormatter such that any string in a resource bundle that needs to have
	 * a literal defined will not show up as a literal.</b>
	 *
	 * @param key   The key to lookup in the resource bundle.  The value of this key should contain params.length
	 *              parameters given by {0}, {1}, {2}, ...
	 *              These parameters will be replaced with the associated values of the param array.  This will all
	 *              be done in the language specified by the loc parameter.
	 * @param params the value to be substituted for for the {0} parameter.
	 * @param loc   Java locale in which to display.  If this argument is null, this Resource's default Locale will be used.
	 * @return A substituted and localized string.
	 */
	private String formatString( String bundle, Locale loc, String key, Object... params )
	{
		String ret = getString( bundle, key, loc );
		if( params == null || params.length < 1 )
		{
			// nothing provided for formatting!
			return ret;
		}
		ret = ret.replaceAll( "'", "''" );

		MessageFormat formatter = new MessageFormat( "" );
		formatter.setLocale( loc );
		formatter.applyPattern( ret );
		return formatter.format( params );
	}

	private String getDefaultBundleName()
	{
		return bundleNames[0];
	}

}
