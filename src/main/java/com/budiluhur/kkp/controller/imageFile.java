package com.budiluhur.kkp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.budiluhur.kkp.entity.imageBase64;

import io.swagger.annotations.Api;

@RestController
@Api(value = "TES", tags = "TES")
public class imageFile {
	

	@Value("${si_security_key:not_defined}")
	protected String securityKey;
	
    public static final String PROFILEPICTPATH = "profilepictpath";
    public static final String INITIALSIGNPATH = "initialsignpath";
    public static final String FORMSIGNPATH = "formsignpath";
	
	@RequestMapping(method = RequestMethod.GET, value = "/getProfilePict/{fileName}")
	@ResponseBody
	public String getProfilePict(@PathVariable("fileName") String fileName) {
		return this.getImageBase64(PROFILEPICTPATH, fileName);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getInitialSign/{fileName}")
	@ResponseBody
	public String getInitialSign(@PathVariable("fileName") String fileName) {
		return this.getImageBase64(INITIALSIGNPATH, fileName);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getFormSign/{fileName}")
	@ResponseBody
	public String getFormSign(@PathVariable("fileName") String fileName) {
		return this.getImageBase64(FORMSIGNPATH, fileName);

	}
	@RequestMapping(method = RequestMethod.POST, value = "/saveProfilePict/{nrk}")
	@ResponseBody
	public ResponseEntity<String> saveProfilePict(@PathVariable("nrk") String nameFile,@RequestBody imageBase64 im) {
		this.decoderImageBase64(im.getImageBase64(), nameFile, PROFILEPICTPATH);
		return ResponseEntity.ok(nameFile);

	}
	@RequestMapping(method = RequestMethod.POST, value = "/saveFormSign/{nrk}")
	@ResponseBody
	public ResponseEntity<String> saveFormSign(@PathVariable("nrk") String nameFile,@RequestBody imageBase64 im) {
		this.decoderImageBase64(im.getImageBase64(), nameFile, FORMSIGNPATH);
		return ResponseEntity.ok(nameFile);

	}
	@RequestMapping(method = RequestMethod.POST, value = "/saveInitialSign/{nrk}")
	@ResponseBody
	public ResponseEntity<String> saveInitialSign(@PathVariable("nrk") String nameFile,@RequestBody imageBase64 im) {
		this.decoderImageBase64(im.getImageBase64(), nameFile, INITIALSIGNPATH);
		return ResponseEntity.ok(nameFile);

	}
	
	
	private String getImageBase64(String path, String fileName) {
		String location = Paths.get("").toAbsolutePath().toString() + "/" + path + "/" + fileName;
		
		String base64Image = "";
		File file = new File(location);
		try (FileInputStream imageInFile = new FileInputStream(file)) {
		    // Reading a Image file from file system
			byte imageData[] = new byte[(int) file.length()];
		    imageInFile.read(imageData);
		    base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (FileNotFoundException e) {
		    System.out.println("Image not found" + e);
		} catch (IOException ioe) {
		    System.out.println("Exception while reading the Image " + ioe);
		}
		return  base64Image;
	}
	public static void decoderImageBase64(String base64Image, String fileName,String path) {
		String pathFile = Paths.get("").toAbsolutePath().toString() + "/" + path + "/" + fileName;
		File file = new File(pathFile);
		if(file.exists()) {
			file.delete();
		}
		try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
		    // Converting a Base64 String into Image byte array
		    byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
		    imageOutFile.write(imageByteArray);
		} catch (FileNotFoundException e) {
		    System.out.println("Image not found" + e);
		} catch (IOException ioe) {
		    System.out.println("Exception while reading the Image " + ioe);
		}	  
	}

}