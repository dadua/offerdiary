
package com.itech.common.resource;


import java.util.Locale;

public class MockResource extends Resource
{

	private Locale defaultLocale = null;

	public MockResource( Locale defaultLocale )
	{
		this();
		this.defaultLocale = defaultLocale;
	}


	public MockResource()
	{
		super( null, "ThisBundleDoesNotExist" );
	}


	@Override
	public Locale getDefaultLocale()
	{
		return defaultLocale;
	}

	@Override
	public String getString( String key, Locale loc )
	{
		return key + " : " + loc;
	}

	@Override
	public String formatString( String key, Locale locale, Object... params )
	{
		return key + " : " + locale + " : " + params;
	}
}
