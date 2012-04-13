package com.itech.test;

import com.itech.fb.services.FbService;

public class TestFbService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String accessToken = "175776209113921|2.f9zsJm1EfJCny55_0_MtiQ__.3600.1301320800-100001451727305|urMVozy5_4RatjITdVQOmM2N6pQ";
		FbService fbService = new FbService(accessToken);
		fbService.createAlbum("Album Name test", "Album Desc test");


	}

}
