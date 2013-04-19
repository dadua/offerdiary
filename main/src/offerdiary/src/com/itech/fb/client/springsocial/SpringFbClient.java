package com.itech.fb.client.springsocial;

import java.util.List;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

public class SpringFbClient {

	public static void main (String [] args) {
		Facebook facebook = new FacebookTemplate("BAACEdEose0cBANObfwTzCgQMNRHfNviAs7OJFsnkVZBPdTtKUvKalW2OIls8QcvMGAawqZBkXjiWqD5I9IVSX5DzIQzZBe5jtKMm2OCnSfl3N0UJjZBkaKoebpprBSjbh9ne9slokXjV3Wb3SskS9xvCeR6I2hY0q4ZCxStpoPn9vbaKc0FBWUkgQAlK1PllGoOsLZC9J4B5c9VIRqLkKd");

		String name = facebook.userOperations().getUserProfile().getName();
		System.out.println(name);
		String name2 = facebook.pageOperations().getPage("tradus").getName();
		Page page = facebook.pageOperations().getPage("tradus");
		List<Post> feed = facebook.feedOperations().getFeed(page.getId());

		System.out.println(feed);
		for (Post post : feed) {
			System.out.println(post.getMessage());

		}
		System.out.println(name2);
		System.out.println(page);

	}

}
