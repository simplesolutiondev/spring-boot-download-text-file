package dev.simplesolution.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.simplesolution.service.FileExporter;

@Controller
public class DownloadController {
	
	@Autowired
	private FileExporter fileExporter;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/download1")
	public ResponseEntity<InputStreamResource> downloadTextFileExample1() throws FileNotFoundException {
		String fileName = "example1.txt";
		String fileContent = "Simple Solution \nDownload Example 1";
		
		// Create text file
		Path exportedPath = fileExporter.export(fileContent, fileName);
		
		// Download file with InputStreamResource
		File exportedFile = exportedPath.toFile();
		FileInputStream fileInputStream = new FileInputStream(exportedFile);
		InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
				.contentType(MediaType.TEXT_PLAIN)
				.contentLength(exportedFile.length())
				.body(inputStreamResource);
	}
	
	@RequestMapping("/download2")
	public ResponseEntity<ByteArrayResource> downloadTextFileExample2() throws IOException {
		String fileName = "example2.txt";
		String fileContent = "Simple Solution \nDownload Example 2";
		
		// Create text file
		Path exportedPath = fileExporter.export(fileContent, fileName);
		
		// Download file with ByteArrayResource
		byte[] exportedFileData = Files.readAllBytes(exportedPath);
		ByteArrayResource byteArrayResource = new ByteArrayResource(exportedFileData);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
				.contentType(MediaType.TEXT_PLAIN)
				.contentLength(exportedFileData.length)
				.body(byteArrayResource);
	}
	
	@RequestMapping("/download3")
	public ResponseEntity<Resource> downloadTextFileExample3() throws IOException {
		String fileName = "example3.txt";
		String fileContent = "Simple Solution \nDownload Example 3";
		
		// Create text file
		Path exportedPath = fileExporter.export(fileContent, fileName);
		
		// Download file with Resource
		URI exportedFileUri = exportedPath.toUri();
		Resource resource = new UrlResource(exportedFileUri);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
				.contentType(MediaType.TEXT_PLAIN)
				.contentLength(resource.contentLength())
				.body(resource);
	}
	
	@RequestMapping("/download4")
	public ResponseEntity<byte[]> downloadTextFileExample4() throws IOException {
		String fileName = "example4.txt";
		String fileContent = "Simple Solution \nDownload Example 4";
		
		// Create text file
		Path exportedPath = fileExporter.export(fileContent, fileName);
		
		// Download file with byte[]
		byte[] expotedFileData = Files.readAllBytes(exportedPath);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
				.contentType(MediaType.TEXT_PLAIN)
				.contentLength(expotedFileData.length)
				.body(expotedFileData);
	}
	
	@RequestMapping("/download5")
	public void downloadTextFileExample5(HttpServletResponse response) throws IOException {
		String fileName = "example5.txt";
		String fileContent = "Simple Solution \nDownload Example 5";
		
		// Create text file
		Path exportedPath = fileExporter.export(fileContent, fileName);
		
		// Download file with HttpServletResponse
		response.setContentType(MediaType.TEXT_PLAIN_VALUE);
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
		response.setContentLength((int)exportedPath.toFile().length());
		
		// Copy file content to response output stream
		Files.copy(exportedPath, response.getOutputStream());		
		response.getOutputStream().flush();
	}
}
