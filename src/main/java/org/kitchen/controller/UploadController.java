package org.kitchen.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.kitchen.domain.AttachFileDTO;
import org.kitchen.domain.RecipeVO;
import org.kitchen.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

//지호: null값 유효성체크 0711
@Controller
@Log4j
public class UploadController {
	
	@Autowired
	private RecipeService recipeService;

	@GetMapping("/upload/uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}
	
	@GetMapping({"/upload/uploadAjax", "/upload/register", "/upload/registration", "/upload/test"})
	public void uploadAjax(Model model) {
		model.addAttribute("recipe", new RecipeVO());
//		log.info("upload ajax");
	}
	
//	@PostMapping("/upload/registrationTest")
//	public @ModelAttribute("recipe") RecipeVO register2save(@ModelAttribute("recipe") RecipeVO recipe) {
//		//recipeService에 저장하기
//		service.register(recipe);
//		return recipe;
//	}
	
//	@PostMapping({"/upload/register"})
//	public String register(RecipeVO recipe, RedirectAttributes rttr)
//	{
//		log.info("==================");
//		log.info("register : "+ recipe);
//		
//		if(recipe.getContentList() != null)
//		{
//			recipe.getContentList().forEach(content->log.info(content));
//		}
//		
//		service.register_w(recipe);
//		
//		return "redirect:/recipe/list";
//		
//	}
	
	
	
//	@PostMapping("/upload/uploadFormAction")
//	public void uploadFormPost(MultipartFile[] uploadFile, Model model)
//	{
//		String uploadFolder = "C:\\upload";
//		
//		for(MultipartFile multipartFile : uploadFile)
//		{
//			log.info("-------------------");
//			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
//			log.info("Upload File Size: " + multipartFile.getSize());
//			
//			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
//			
//			try {
//				multipartFile.transferTo(saveFile);
//			}catch(Exception e)
//			{
//				log.error(e.getMessage());
//			}
//		}
//	}
	
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
	}
	
	private boolean checkImageType(File file)
	{
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			return contentType.startsWith("image");
		}catch(IOException e){
			e.printStackTrace();
		}
		return false;
	}
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName, HttpServletRequest request){
		log.info("fileName : " + fileName);
		String path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "resources\\upload\\";
		String uploadFolder = path+attach_path;
		File file = new File(uploadFolder+fileName);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	
	
	@GetMapping("/deleteFile")
	public String delteFile(Model model) {
		//겟 막기
		return wrongAccess(model);
	}
	
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> delteFile(String fileName, String type, HttpServletRequest request)
	{
		 File file;
		 try {
			 String path = request.getSession().getServletContext().getRealPath("/");
			 String attach_path = "resources\\upload";
			 String uploadFolder = path+attach_path;
			 file = new File(uploadFolder+"\\"+URLDecoder.decode(fileName, "UTF-8"));
			 log.info("deleteFile:" +file);
			 file.delete();
			 
			 if(type.equals("image")) {
				 String largeFileName = file.getAbsolutePath().replace("s_","");
				 file = new File(largeFileName);
				 log.info("Large file:"+file);
				 file.delete();
			 }
		 }
		 catch(UnsupportedEncodingException e)
		 {
			 e.printStackTrace();
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
		 return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	
	@GetMapping("/upload/uploadAjaxAction")
	public String uploadAjaxPost(Model model) {
		//겟 막기
		return wrongAccess(model);
	}
	
	
	@PostMapping(value= "/upload/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile, HttpServletRequest request)
	{
		
		List<AttachFileDTO> list = new ArrayList<>();
//		String uploadFolder = "C:\\upload";
		String uploadFolderPath = getFolder();
		//make folder
		
		String path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "resources\\upload";
		String uploadFolder = path+attach_path;
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		log.info("@@@path@@@:"+path);

		
		if(uploadPath.exists() == false)
		{
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile)
		{
				
			AttachFileDTO attachDTO = new AttachFileDTO();
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			attachDTO.setShowFileName(uploadFileName);
			
			UUID uuid = UUID.randomUUID();
			String exeName = uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
			
			uploadFileName = uuid.toString()+"."+exeName; 
					//+"_"+uploadFileName;
			attachDTO.setFileName(uploadFileName);
 			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
	
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				if(checkImageType(saveFile))
				{
					attachDTO.setImage(true);
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 350, 350);
					thumbnail.close();
				}
				list.add(attachDTO);
			}
			catch(Exception e)
			{
				log.error(e.getMessage());
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	private String wrongAccess(Model model) {
		// TODO Auto-generated method stub
		model.addAttribute("result", "잘못된 접근입니다.");
		return "/error";
	}
	
}                                                       
