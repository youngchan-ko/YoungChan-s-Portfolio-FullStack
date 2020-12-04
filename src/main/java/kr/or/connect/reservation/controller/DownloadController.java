package kr.or.connect.reservation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import kr.or.connect.reservation.dto.DownloadDto;
import kr.or.connect.reservation.service.DownloadService;

@Controller
public class DownloadController {
	@Autowired
	DownloadService downloadService;
	
	private String basePath = File.separator + "temp" + File.separator;
	
	@GetMapping("/download/{fileInfoId}")
	public void download(HttpServletResponse response, 
			@PathVariable("fileInfoId") int fileInfoId) {
		
		DownloadDto fileData = downloadService.getDownloadFileInfo(fileInfoId);
        
		String fileName = fileData.getFileName();
		String saveFileName = basePath + fileData.getSaveFileName();
		String contentType = fileData.getContentType();
		long fileLength = new File(saveFileName).length();
		
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
        
        try(
                FileInputStream fis = new FileInputStream(saveFileName);
                OutputStream out = response.getOutputStream();
        ){
        	    int readCount = 0;
        	    byte[] buffer = new byte[1024];
            while((readCount = fis.read(buffer)) != -1){
            		out.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Save Error");
        }
	}
}
