package com.itech.fb.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

import com.itech.fb.model.FbCreds;

public class FbSocialService {

	private final Facebook facebook;

	private static final int DEFAULT_NO_OF_POSTS_TO_FETCH = 999;


	public FbSocialService(String accessToken) {
		facebook = new FacebookTemplate(accessToken);
	}


	public FbSocialService (FbCreds fbCreds) {
		this(fbCreds.getAccessToken());
	}


	/**
	 * 
	 * @param pageId - Either the FB Id of the page or the page Name itself
	 * @return the page object
	 */
	public Page getPageForName (String pageId) {
		return facebook.pageOperations().getPage(pageId);
	}


	public List<Post> getAllPostsForPage(Page fbPage) {
		return getAllPostsForPage(fbPage, -1, true);
	}

	public List<Post> getAllPostsForPage(Page fbPage, int maxPostsToGetCount) {
		return getAllPostsForPage(fbPage, maxPostsToGetCount, false);
	}

	private List<Post> getAllPostsForPage(Page fbPage, int maxPostsToGetCount, boolean fetchAllPosts) {
		return getAllPostsForPage(fbPage, maxPostsToGetCount, DEFAULT_NO_OF_POSTS_TO_FETCH, fetchAllPosts);
	}

	public List<Post> getAllPostsForPage(Page fbPage, int maxPostsToGetCount, int postsToGetInOneFetch, boolean fetchAllPosts) {
		return getAllPostsForFbId(fbPage.getId(),maxPostsToGetCount, postsToGetInOneFetch, fetchAllPosts);

	}

	/**
	 * 
	 * @param id
	 * @param maxPostsToGetCount
	 * @param postsToGetInOneFetch
	 * @param fetchAllPosts
	 * @return all the posts for a FB object having a fbId = id
	 */
	public List<Post> getAllPostsForFbId(String id, int maxPostsToGetCount,
			int postsToGetInOneFetch, boolean fetchAllPosts) {

		List<Post> posts = new ArrayList<Post>();

		int feedPageFetcherCount = 0;
		List<Post> feeds = null;

		while (true) {
			feeds = facebook.feedOperations().getFeed(id, feedPageFetcherCount * postsToGetInOneFetch, postsToGetInOneFetch);
			feedPageFetcherCount++;

			if (feeds.size() == 0 ) {
				break;
			}

			//Ignoring the maxPostsToGetCount when fetchAllPosts = true
			if (!fetchAllPosts && ((posts.size()+feeds.size()) > maxPostsToGetCount)) {
				for (Post post : feeds) {
					if (posts.size() == maxPostsToGetCount) {
						break;
					}
					posts.add(post);
				}
				break;
			}

			posts.addAll(feeds);
		}
		return posts;
	}


	/**
	 * Spring social uses the offset paging mentioned here: https://developers.facebook.com/docs/reference/api/pagination/
	 * 
	 * @param fbPage
	 * @param noOfPosts
	 * @param offset
	 * @return list of posts
	 */
	public List<Post> getPostsForPage(Page fbPage, int noOfPosts, int offset) {
		return facebook.feedOperations().getFeed(fbPage.getId(), offset, noOfPosts);
	}

}
