package com.itech.test;

import java.util.List;

import org.junit.Assert;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.Post;

import com.itech.common.test.CommonTestUtil;
import com.itech.fb.services.FbService;
import com.itech.fb.services.FbSocialService;
import com.itech.web.FbConstants;

public class TestFbService extends CommonTestUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//testFbService();

		TestFbService testFbService = new TestFbService();
		testFbService.testFbSocialService();


	}

	public void testFbSocialService() {
		FbSocialService fbSocialService = new FbSocialService(FbConstants.APP_DEV_SECRET_ACCESS_TOKEN);
		Page tradusPage = fbSocialService.getPageForName("tradus");
		//testAllPostsFetch(fbSocialService, tradusPage);
		List<Post> firstHundredPosts = fbSocialService.getAllPostsForPage(tradusPage, 100);
		Assert.assertEquals("Max posts size to fetch should be equal", 100, firstHundredPosts.size());
	}

	private void testAllPostsFetch(FbSocialService fbSocialService,
			Page tradusPage) {
		List<Post> allPostsForPage = fbSocialService.getAllPostsForPage(tradusPage);
		System.out.println(allPostsForPage.size());
		printAllPosts(allPostsForPage);
	}

	private static void printAllPosts(List<Post> allPostsForPage) {
		int postCount = 0;
		for (Post post : allPostsForPage) {
			for (int i=0;i < 80; i++) {
				System.out.print('-');
			}
			System.out.println("");
			System.out.println("Post Count: (" + postCount++ + ")");
			System.out.println("Message: " + post.getMessage());
			System.out.println("Created time: " + post.getCreatedTime());
			System.out.println("Updated Time: " + post.getUpdatedTime());
			System.out.println("Description: " + post.getDescription());
			System.out.println("Picture: " + post.getPicture());
		}
	}

	private static void testFbService() {
		String accessToken = "175776209113921|2.f9zsJm1EfJCny55_0_MtiQ__.3600.1301320800-100001451727305|urMVozy5_4RatjITdVQOmM2N6pQ";
		FbService fbService = new FbService(accessToken);
		fbService.createAlbum("Album Name test", "Album Desc test");
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub

	}

}
