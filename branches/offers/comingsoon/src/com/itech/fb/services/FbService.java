package com.itech.fb.services;

import java.util.ArrayList;
import java.util.List;

import com.itech.fb.client.FbAdapter;
import com.itech.fb.model.FbAlbum;
import com.itech.fb.model.FbCreds;
import com.itech.fb.model.FbLike;
import com.itech.fb.model.FbLikes;
import com.itech.fb.model.FbPage;
import com.itech.fb.model.FbPhoto;
import com.itech.fb.model.FbProfile;

public class FbService {
	private final FbAdapter fbAdapter;

	public FbService(String accessToken) {
		fbAdapter = FbAdapterFactory.getFbAdapter(accessToken);
	}


	public FbService(FbCreds fbCreds) {
		fbAdapter = FbAdapterFactory.getFbAdapter(fbCreds.getAccessToken());
	}

	public FbService(FbAdapter fbAdapter) {
		this.fbAdapter = fbAdapter;
	}

	public FbProfile getUserProfile() {
		return fbAdapter.getFbProfile();
	}

	public FbAlbum createAlbum(String albumName, String albumDescription) {
		return fbAdapter.createFbAlbum(albumName, albumDescription);
	}

	public FbPhoto addPhotoToAlbum(String albumId, String photoUrl, String photoCaption) {
		return fbAdapter.addPhotoToAlbum(albumId, photoUrl, photoCaption);

	}
	public FbPhoto addPhotoToAlbum(String albumId, byte[] photoData, String photoCaption) {
		return fbAdapter.addPhotoToAlbum(albumId, photoData, photoCaption);

	}

	public void updateFbAlbumMetaDataAfterUpload(FbAlbum fbAlbum) {
		fbAdapter.setFbAlbumLinkFromFbAlbumId(fbAlbum);
		fbAdapter.setCoverPhotoFromAlbumId(fbAlbum);
	}

	public List<FbPage> getAuthorPagesFromLikes(FbLikes fbLikes) {
		List<FbPage.FbCategory> pageCategoriesToBeSelected = new ArrayList<FbPage.FbCategory>();
		pageCategoriesToBeSelected.add(FbPage.FbCategory.AUTHOR);
		return getPagesFromLikes(fbLikes, pageCategoriesToBeSelected);
	}

	public List<FbPage> getPagesFromLikes(FbLikes fbLikes, List<FbPage.FbCategory> fbCategoriesToBeSelected) {

		List<FbPage> fbPages = new ArrayList<FbPage>();
		for (FbLike fbLike: fbLikes.getData()) {
			try {
				if (fbCategoriesToBeSelected.contains(FbPage.FbCategory.getByName(fbLike.getCategory()))) {
					fbPages.add(fbAdapter.getFbPageFromLike(fbLike));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fbPages;
	}
}
