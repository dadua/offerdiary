package com.itech.fb.model;

public class FbAlbum {
	/*
    "id": "160174247374298",
    "from": {
       "name": "Crocy Hanks",
       "id": "100001451727305"
    },
    "name": "Your Orkut Album",
    "description": "Album has been imported from orkut",
    "link": "http://www.facebook.com/album.php?aid=34786&id=100001451727305",
    "cover_photo": "160174254040964",
    "privacy": "everyone",
    "count": 31,
    "type": "normal",
    "created_time": "2011-03-22T10:41:36+0000",
    "updated_time": "2011-03-22T10:43:02+0000"
    */
	
	String id;
	
	String albumName;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAlbumLink() {
		return albumLink;
	}

	public void setAlbumLink(String albumLink) {
		this.albumLink = albumLink;
	}

	public String getPhotosCount() {
		return photosCount;
	}

	public void setPhotosCount(String photosCount) {
		this.photosCount = photosCount;
	}

	public String getCoverPhoto() {
		return coverPhoto;
	}

	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}

	String description;
	
	String albumLink;
	
	String photosCount;
	
	String coverPhoto;
	
}
