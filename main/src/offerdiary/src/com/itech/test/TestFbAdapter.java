package com.itech.test;

import java.util.List;

import com.itech.fb.client.FbAdapter;
import com.itech.fb.model.FbAlbum;
import com.itech.fb.model.FbLike;
import com.itech.fb.model.FbLikes;
import com.itech.fb.model.FbPage;
import com.itech.fb.model.FbProfile;
import com.itech.fb.services.FbAdapterFactory;
import com.itech.fb.services.FbService;

public class TestFbAdapter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String accessToken = "AAACqK9c7qWQBAFz9XgP7W2slHMi7TCNvdX1CZBk9hZBgrID2HipBqRgMoqsZB1B5K7VQzTAJyx9XYZAR6lWmPMV4UPspnUAZD";
		FbAdapter fbAdapter = FbAdapterFactory.getFbAdapter(accessToken);
		List<FbProfile> friendProfiles = fbAdapter.getFriendProfiles();
		FbProfile profile = fbAdapter.getFbProfile();
		System.out.println(profile.getFirstName());
		//		testAlbumCreateAndPost(fbAdapter);
		//		testPostToWall(fbAdapter);
		//		testGetLikes(fbAdapter);
		//		testGetFbPages(fbAdapter);
		testPostRequest(fbAdapter, profile);
	}

	private static void testPostRequest(FbAdapter fbAdapter, FbProfile profile) {
		fbAdapter.createAppToUserRequest(profile, "Test App to User Notification");
	}

	private static void testGetFbPages(FbAdapter fbAdapter) {
		FbLikes likes = fbAdapter.getLikes();
		FbService fbService = new FbService(fbAdapter);

		List<FbPage> authorPagesFromLikes = fbService.getAuthorPagesFromLikes(likes);

		for (FbPage authorPage: authorPagesFromLikes) {
			System.out.println(authorPage.getDescription());
		}
	}

	private static void testGetLikes(FbAdapter fbAdapter) {
		FbLikes likes = fbAdapter.getLikes();

		List<FbLike> fbLikes = likes.getData();

		for (FbLike like: fbLikes) {
			//System.out.println(like.getName());

			if ("author".equalsIgnoreCase(like.getCategory())) {
				System.out.println(like.getName());
			}
		}
	}

	private static void testPostToWall(FbAdapter fbAdapter) {
		fbAdapter.postDataToWall("www.socialbaba.com", "http://dl.dropbox.com/u/8774260/socialbaba/images/logo.jpg", "www.socialbaba.com",
				"Orkut To Facebook Album Exporter",
				"Imported 10 albums containing 50 photos from Orkut to facebook using www.socialbaba.com");

	}

	public static void testAlbumCreateAndPost(FbAdapter fbAdapter) {
		FbAlbum album = fbAdapter.createFbAlbum("Test Java Album", "Test Java Description");
		//	fbAdapter.addPhotoToAlbum(album.getId(), new File("C:\\Documents and Settings\\alok\\Desktop\\new_bliss_tree_green_landscape_scenery_wallpaper.jpg"), "Test photo caption");
		//		fbAdapter.addPhotoToAlbum(album.getId(), byteArray, "Test photo caption");
		String validOrkutPhotoUrlStr = "http://images.orkut.com/orkut/photos/"+
				"OgAAABnqEztTilS9D7H6sI1mdy_sCtVlCZfSHUX1ligb5b3Bi7taKyLfE_gLs06hWAO9w97TEw5sjTS5TFk3a0Z5pk0Am1T1UPqtak-lRnrU8otaPII2ukBdBo92.jpg";
		fbAdapter.addPhotoToAlbum(album.getId(),validOrkutPhotoUrlStr,
				"Test photo caption");
	}

}
