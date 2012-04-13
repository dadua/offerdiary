package com.itech.fb.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;
import org.json.me.JSONObject;

import com.google.gson.Gson;
import com.itech.common.util.UtilHttp;
import com.itech.fb.model.FbAlbum;
import com.itech.fb.model.FbLike;
import com.itech.fb.model.FbLikes;
import com.itech.fb.model.FbPage;
import com.itech.fb.model.FbPhoto;
import com.itech.fb.model.FbProfile;

public class FbAdapter {

	private static final Logger logger = Logger.getLogger(FbAdapter.class);

	public String applicationId = "";

	public String applicationSecret = "";

	public String callbackURL = "";

	public static final String FB_GRAPH_URL = "graph.facebook.com";

	public static final String FB_OAUTH_APP_AUTHORIZATION_URL_PATH = "oauth/authorize";

	public static final String FB_OAUTH_APP_ACCESS_TOKEN_URL_PATH = "oauth/access_token";

	public static final String FB_APP_PERMISSIONS = "read_stream,user_photos,publish_stream,offline_access";
	//TODO: Make a FBAppPermissions class

	private String accessToken = "";

	private URI authUrl;

	/**
	 * @param req
	 * @param resp
	 * @return Forward to a JSP which can show user's FB data
	 * This action will handle the user code that FB sends.
	 * This code can be used to get access token by sending a request to FBQURL/oauth/access_token with:
	 *   1. APPID
	 *   2. APPSECRET
	 *   3. USERCODE
	 *   4. CALLBACKURL
	 * @throws UnsupportedEncodingException
	 */
	public FbAdapter(String applicationId, String applicationSecret,
			String callbackURL, String accessToken) {
		super();
		this.applicationId = applicationId;
		this.applicationSecret = applicationSecret;
		this.callbackURL = callbackURL;
		this.accessToken = accessToken;
		initializeOauthUrls();
	}

	public void authenticate(String userCode) {
		this.userCode = userCode;
		initAccessFetchUrl();
		fetchAccessTokenFromFb();
	}

	private void initAuthUrl() {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("client_id", applicationId));
			params.add(new BasicNameValuePair("scope", FB_APP_PERMISSIONS));
			params.add(new BasicNameValuePair("redirect_uri", callbackURL));
			authUrl = URIUtils.createURI("https", FB_GRAPH_URL, -1,
					FB_OAUTH_APP_AUTHORIZATION_URL_PATH, URLEncodedUtils.format(params, "UTF-8"), null);
		} catch (Exception e) {
			logger.error("Error in url creation", e);
			throw new FbAdapterException("Auth URL Creation error", e);
		}
	}

	public URI getAuthUrl() {
		return authUrl;
	}

	public String getAuthUrlString() {
		return authUrl.toString();
	}

	private URI accessTokenFetchUrl;

	private String userCode;

	private void initAccessFetchUrl() {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("client_id", applicationId));
			params.add(new BasicNameValuePair("client_secret", applicationSecret));
			params.add(new BasicNameValuePair("code", userCode));
			params.add(new BasicNameValuePair("redirect_uri", callbackURL));
			accessTokenFetchUrl = URIUtils.createURI("https", FB_GRAPH_URL, -1,
					FB_OAUTH_APP_ACCESS_TOKEN_URL_PATH, URLEncodedUtils.format(params, "UTF-8"), null);
		} catch(Exception e) {
			logger.error("Error in url creation", e);
			throw new FbAdapterException("Access URL Creation error", e);
		}

	}

	private void fetchAccessTokenFromFb () {
		UtilHttp utilHttpGet = new UtilHttp();
		String authResponse = utilHttpGet.getHttpResponse(accessTokenFetchUrl.toString());
		accessToken = UtilHttp.parseQueryString(authResponse).get("access_token").get(0);
	}

	public FbProfile getProfile() {
		FbProfile fbProfile = new FbProfile();
		/* TODO:
		 * Separate out URL Fetchers as is common
		 * Also should have maps of JSON keys to model members.
		 * Have to throw proper exception after catching
		 */
		try {
			UtilHttp utilHttpGet = new UtilHttp();
			ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("access_token", accessToken));
			URI url = URIUtils.createURI("https", FB_GRAPH_URL, -1, "me",
					URLEncodedUtils.format(params, "UTF-8"),null);
			String profileJsonStr = utilHttpGet.getHttpResponse(url.toString());
			JSONObject profileJsonObj = new JSONObject(profileJsonStr);

			if (profileJsonObj.has("error")) {
				//TODO: This error case has to be handled
			}
			fbProfile.setId(profileJsonObj.getString("id"));
			fbProfile.setFullName(profileJsonObj.getString("name"));
			fbProfile.setFirstName(profileJsonObj.getString("first_name"));
			fbProfile.setLastName(profileJsonObj.getString("last_name"));
			fbProfile.setProfileUrl(profileJsonObj.getString("link"));
			fbProfile.setGender(profileJsonObj.getString("gender"));
			fbProfile.setTimeZone(profileJsonObj.getString("timezone"));
			fbProfile.setEmailId(profileJsonObj.getString("email"));
			fbProfile.setLocale(profileJsonObj.getString("locale"));

		} catch (Exception e) {
			//TODO: Throw FbAdapterException
			e.printStackTrace();
		}
		return fbProfile;
	}

	public FbAlbum createFbAlbum(String albumName, String albumDescription) {
		//TODO: Handle Error checks
		//extract POST data as util method
		// In all of the url requests to fb graphApi and access_token is common have to add a wrapper.
		// Have to throw proper exception after catching

		FbAlbum fbAlbum = new FbAlbum();

		try {
			URI url = URIUtils.createURI("https", FB_GRAPH_URL, -1, "me/albums",
					null, null);

			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("access_token", accessToken));
			params.add(new BasicNameValuePair("name", albumName));
			params.add(new BasicNameValuePair("message", albumDescription));

			HttpPost httpPost = new HttpPost(url);
			HttpResponse httpResp = null;
			httpPost.setEntity(new UrlEncodedFormEntity(params));

			HttpClient httpClient = new DefaultHttpClient();
			httpResp = httpClient.execute(httpPost);

			int contentLength = (int) httpResp.getEntity().getContentLength();
			byte [] resp = new byte[contentLength];
			httpResp.getEntity().getContent().read(resp, 0, contentLength);
			String albumPostResp = new String(resp);
			JSONObject albumPostResponseJsonObj = new JSONObject(albumPostResp);
			fbAlbum.setId(albumPostResponseJsonObj.getString("id"));
			fbAlbum.setAlbumName(albumName);
			fbAlbum.setDescription(albumDescription);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fbAlbum;
	}

	/*
	 * 'link=http://www.example.com/article.html' \
     -F 'picture=http://www.example.com/article-thumbnail.jpg' \
     -F 'name=Article Title' \
     -F 'caption=Caption for the link' \
     -F 'description=Longer description of the link' \

	 */
	public void postDataToWall(String link, String linkPicture, String name, String caption, String linkDescription) {

		try {
			URI url = URIUtils.createURI("https", FB_GRAPH_URL, -1, "me/feed",
					null, null);

			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("access_token", accessToken));
			params.add(new BasicNameValuePair("link", link));
			params.add(new BasicNameValuePair("picture", linkPicture));
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("caption", caption));
			params.add(new BasicNameValuePair("description", linkDescription));

			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params));

			HttpClient httpClient = new DefaultHttpClient();
			httpClient.execute(httpPost);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public FbLikes getLikes() {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("access_token", accessToken));
			URI url = URIUtils.createURI("https", FB_GRAPH_URL, -1, "me/likes",
					URLEncodedUtils.format(params, "UTF-8"), null);

			UtilHttp httpGet = new UtilHttp();
			String resp = httpGet.getHttpResponse(url.toString());
			Gson gson = new Gson();
			FbLikes fbLikes = gson.fromJson(resp, FbLikes.class);
			return fbLikes;

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return null;
	}


	public FbPage getFbPageFromLike(FbLike fbLike) {
		return getFbPageById(fbLike.getId());
	}

	public FbPage getFbPageById(String id) {
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("access_token", accessToken));
			URI url = URIUtils.createURI("https", FB_GRAPH_URL, -1, id,
					URLEncodedUtils.format(params, "UTF-8"), null);

			UtilHttp httpGet = new UtilHttp();
			String resp = httpGet.getHttpResponse(url.toString());
			Gson gson = new Gson();
			FbPage fbPage = gson.fromJson(resp, FbPage.class);
			return fbPage;

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setCoverPhotoFromAlbumId(FbAlbum fbAlbum) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("access_token", accessToken));

		try {
			URI url = URIUtils.createURI("https", FB_GRAPH_URL, -1, fbAlbum.getId()+"/picture",
					URLEncodedUtils.format(params, "UTF-8"), null);

			HttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter("http.protocol.handle-redirects",false);

			HttpGet httpGetter = new HttpGet(url);
			httpGetter.setParams(httpParams);
			HttpClient httpClient = new DefaultHttpClient();

			HttpResponse response = httpClient.execute(httpGetter);
			Header httpHeader = response.getFirstHeader("location");
			if (httpHeader!= null) {
				fbAlbum.setCoverPhoto(httpHeader.getValue());
			} else {
				//TODO: Log this condition
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<BasicNameValuePair> setFbAlbumLinkFromFbAlbumId(FbAlbum fbAlbum) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("access_token", accessToken));
		try {
			URI url = URIUtils.createURI("https", FB_GRAPH_URL, -1, fbAlbum.getId(),
					URLEncodedUtils.format(params, "UTF-8"), null);

			UtilHttp httpGet = new UtilHttp();
			String resp = httpGet.getHttpResponse(url.toString());
			JSONObject albumMetaJson = new JSONObject(resp);
			fbAlbum.setAlbumLink(albumMetaJson.getString("link"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	//public FbPhoto addPhotoToAlbum(String albumId, File photo, String photoCaption) {
	//public FbPhoto addPhotoToAlbum(String albumId, byte[] photo, String photoCaption) {



	public FbPhoto addPhotoToAlbum(String albumId, String  photoUrl, String photoCaption) {
		InputStream photoDataStream = null;
		try {
			UtilHttp httpGetter = new UtilHttp();
			photoDataStream = httpGetter.getHttpResponseAsInputStream(photoUrl);
			return addPhotoToAlbum(albumId, photoDataStream, photoCaption);
		} finally {
			if(photoDataStream != null) {
				try {
					photoDataStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public FbPhoto addPhotoToAlbum(String albumId, InputStream photoDataStream, String photoCaption) {

		FbPhoto fbPhoto = new FbPhoto();
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("access_token", accessToken));
			params.add(new BasicNameValuePair("message", photoCaption));
			URI url = URIUtils.createURI("https", FB_GRAPH_URL, -1, albumId+"/photos",
					URLEncodedUtils.format(params, "UTF-8"), null);
			HttpPost httpPost = new HttpPost(url);
			InputStreamBody photoInRightFormat = new InputStreamBody(photoDataStream, "testphoto.jpg");
			//ByteArrayBody byteArrayBody = new ByteArrayBody(photoData, "testPhoto.jpg");
			//new FileBody(photo);
			MultipartEntity requestEntity = new MultipartEntity();
			requestEntity.addPart("source", photoInRightFormat);
			httpPost.setEntity(requestEntity);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int contentLength = (int) httpResponse.getEntity().getContentLength();
			byte [] resp = new byte[contentLength];
			httpResponse.getEntity().getContent().read(resp, 0, contentLength);
			String albumPostResp = new String(resp);
			JSONObject albumPostResponseJsonObj = new JSONObject(albumPostResp);

			fbPhoto.setCaption(photoCaption);
			fbPhoto.setId(albumPostResponseJsonObj.getString("id"));
			//			fbPhoto.setFullPhotoUrl(albumPostResponseJsonObj.getString("source"));


		} catch (Exception e) {
			e.printStackTrace();
		}
		return fbPhoto;
	}
	public FbPhoto addPhotoToAlbum(String albumId, byte[] photoData, String photoCaption) {

		FbPhoto fbPhoto = new FbPhoto();
		try {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("access_token", accessToken));
			params.add(new BasicNameValuePair("message", photoCaption));
			URI url = URIUtils.createURI("https", FB_GRAPH_URL, -1, albumId+"/photos",
					URLEncodedUtils.format(params, "UTF-8"), null);
			HttpPost httpPost = new HttpPost(url);
			//InputStreamBody photoInRightFormat = new InputStreamBody(photo, "testphoto.jpg");
			ByteArrayBody byteArrayBody = new ByteArrayBody(photoData, "testPhoto.jpg");
			//new FileBody(photo);
			MultipartEntity requestEntity = new MultipartEntity();
			requestEntity.addPart("source", byteArrayBody);
			httpPost.setEntity(requestEntity);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int contentLength = (int) httpResponse.getEntity().getContentLength();
			byte [] resp = new byte[contentLength];
			httpResponse.getEntity().getContent().read(resp, 0, contentLength);
			String albumPostResp = new String(resp);
			JSONObject albumPostResponseJsonObj = new JSONObject(albumPostResp);

			fbPhoto.setCaption(photoCaption);
			fbPhoto.setId(albumPostResponseJsonObj.getString("id"));
			//			fbPhoto.setFullPhotoUrl(albumPostResponseJsonObj.getString("source"));


		} catch (Exception e) {
			e.printStackTrace();
		}
		return fbPhoto;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserCode() {
		return userCode;
	}

	private void initializeOauthUrls() {
		initAuthUrl();

	}

	public FbAdapter(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
