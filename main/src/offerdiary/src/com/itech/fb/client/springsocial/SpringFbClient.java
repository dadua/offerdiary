package com.itech.fb.client.springsocial;

import java.util.List;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

import com.itech.web.FbConstants;

public class SpringFbClient {

	public static void main (String [] args) {
		//Facebook facebook = new FacebookTemplate("BAACEdEose0cBANObfwTzCgQMNRHfNviAs7OJFsnkVZBPdTtKUvKalW2OIls8QcvMGAawqZBkXjiWqD5I9IVSX5DzIQzZBe5jtKMm2OCnSfl3N0UJjZBkaKoebpprBSjbh9ne9slokXjV3Wb3SskS9xvCeR6I2hY0q4ZCxStpoPn9vbaKc0FBWUkgQAlK1PllGoOsLZC9J4B5c9VIRqLkKd");
		//253396431422694|aQ-xkGC2r8UMArmRv7PptOI0PIA

		Facebook facebook = new FacebookTemplate(FbConstants.APP_DEV_SECRET_ACCESS_TOKEN);

		//String name = facebook.userOperations().getUserProfile().getName();
		//System.out.println(name);
		//		String name2 = facebook.pageOperations().getPage("offerdiary").getName();
		Page page = facebook.pageOperations().getPage("tradus");
		System.out.println(page.getName());

		List<Post> feed = facebook.feedOperations().getFeed("207960498808", 0, 1000);
		int postCount = 0;
		int feedPageFetcherCount = 1;

		while (true) {
			for (Post post : feed) {
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
				//System.out.println("Picture: " + post.get());
			}
			feed = facebook.feedOperations().getFeed(page.getId(), feedPageFetcherCount * 25, 25);
			feedPageFetcherCount++;
			if (feed.size() == 0 ) {
				break;
			}
		}
		System.out.println(page);

	}

}
