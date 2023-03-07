package com.ehtasham.springbootkeycloack.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ehtasham.springbootkeycloack.model.HitsModel;
import com.ehtasham.springbootkeycloack.repository.HitsRepo;
//import com.pmd.payloads.MNIResponse;
import com.sun.istack.NotNull;

import Payload.ErrorResponse;
import Payload.response;
import Payload.token;
import Payload.tokenResponse;
import Util.Constant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
@RequestMapping("/api/v1/rest/")

public class MainController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	HitsRepo hitsRepo;

	@PostMapping("/token") // Mobile Number Identification API begins for REST

	public ResponseEntity<Object> token(@RequestBody token tokenPayload) throws ParserConfigurationException {

		try {
			System.out.print("Request recieved");
			RestTemplate restTemplate = new RestTemplate();
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
			String currDate = formatter.format(date);
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			HttpEntity<String> request = new HttpEntity<String>(headers);

			String username = tokenPayload.getUsername();
			System.out.print(username);
			String password = tokenPayload.getPassword();

			MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
			requestBody.add("username", username);
			requestBody.add("password", password);
			requestBody.add("client_id", "ehtasham-app");
			requestBody.add("grant_type", "password");

			HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<MultiValueMap<String, String>>(
					requestBody, headers);

			String resourceUrl = Constant.keycloakurl;

			ResponseEntity<response> response1 = restTemplate.exchange(resourceUrl, HttpMethod.POST, formEntity,
					response.class);

			System.out.println(response1.getStatusCode());

			if (response1.getStatusCodeValue() == 200) {
				response abc = response1.getBody();

				String access_token = abc.getAccess_token();
				String expires = abc.getExpires_in();

				System.out.println(response1);

				HitsModel hitobj = new HitsModel();

				if (response1 != null) {

					hitobj.setExpires_in(expires);
					hitobj.setUsername(username);
					hitobj.setPassword(password);
					hitobj.setRefresh_token("Refresh Token");
					hitobj.setUpdate_time(currDate);
					hitobj.setAccess_token("Access Token");
					hitsRepo.save(hitobj);

				}

				return new ResponseEntity<Object>(new tokenResponse(access_token, expires), HttpStatus.OK);
			}
		} catch (Exception e) {

			System.out.println("" + "");
			return new ResponseEntity<Object>(new ErrorResponse("Username and passowrd is incorrect", "403"),
					HttpStatus.OK);
		}
		return null;

	}

	@GetMapping("/getAllRecord")
	public ResponseEntity<Object> getAllRecord() {

		System.out.print("Request recieved for ALL Records");

		List<HitsModel> data = hitsRepo.findRecordinFROMHits();

		return new ResponseEntity<Object>(data, HttpStatus.OK);

	}

	@GetMapping("/getAllRecord/{id}")
	public ResponseEntity<Object> getAllRecordbyID(@PathVariable @NotNull Long id) {

		System.out.print("Request recieved for ID");

		List<HitsModel> data = hitsRepo.findRecordinFROMHitsByid(id);

		return new ResponseEntity<Object>(data, HttpStatus.OK);

	}

	@DeleteMapping("/deleteRecord/{id}")
	public ResponseEntity<Object> deleteRecordbyID(@PathVariable @NotNull Long id) {

		System.out.print("Request recieved for ID");

		List<HitsModel> data = hitsRepo.findRecordinFROMHitsByid(id);

		if (!data.isEmpty()) {
			hitsRepo.deleteById(id);
			return new ResponseEntity<Object>(new ErrorResponse("Record Delete Successfull!!!", "200"), HttpStatus.OK);

		} else {
			return new ResponseEntity<Object>(new ErrorResponse("Record Not Found!!!", "404"), HttpStatus.OK);
		}

	}

}
