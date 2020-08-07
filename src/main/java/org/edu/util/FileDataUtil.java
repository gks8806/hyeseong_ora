package org.edu.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileDataUtil {
	 
	private ArrayList<String> extNameArray = new ArrayList<String>() 
	{
		{
			add("gif");
			add("jpg");
			add("png");
		}
	};
	//첨부파일 업로드 경로 변수값으로 가져옴 servlet-context
		@Resource(name="uploadPath")
		private String uploadpath;
		
	public String getUploadpath() {
			return uploadpath;
		}

		public void setUploadpath(String uploadpath) {
			this.uploadpath = uploadpath;
		}

	/**
	* 게시물 상세보기에서 첨부파일 다운로드 메서드 구현(공통)
	*/
	@RequestMapping(value="/download" , method=RequestMethod.GET)
	@ResponseBody 
	public FileSystemResource fileDownload(@RequestParam("filename") String fileName, HttpServletResponse response){
		File file = new File(uploadpath + "/" + fileName);
		response.setContentType("application/download; utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		return new FileSystemResource(file);
	}
	
	/*
	 * 게시물 이미지일때 미리보기 메서드 구현(IE, 크롬에서 공통)
	 */
	@RequestMapping(value="/image_preview",method=RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] imagePreview(@RequestParam("filename") String fileName, HttpServletResponse response) throws IOException {
		FileInputStream fis = null;//변수초기화
		ByteArrayOutputStream baos = new ByteArrayOutputStream();//인스턴스 변수생성 사용가능한 클래스를 변수로 만드는거
		fis = new FileInputStream(uploadpath + "/" + fileName);
		int readCount = 0;
		byte[] buffer = new byte[1024];
		byte[] fileArray = null;
		while((readCount = fis.read(buffer)) != -1) {
			baos.write(buffer,0,readCount);
			/*
		      buffer = 버퍼데이터(파일내용) byte[]
		      off(0) = 버퍼데이터의 0부터 시작 오프셋
		      - 옵셋(offset): bottom offset 화면하단기준에서 더해진 값.
		           top offset 화면상단에서 얼마큼 거리
		           readCount = 쓸 바이트 수 (int) = 버퍼데이터크기만큼
		    */
		}
		fileArray = baos.toByteArray();//자료변환 후 변수에저장, 형변환한 것을 fileArray로 넣는다.
		fis.close();
		baos.close();
		return fileArray;
	}
	
	/**
	 * 파일 업로드 메서드(공통)	
	 * @throws IOException 
	 */
	public String[] fileUpload(MultipartFile file) throws IOException {
		String originalName = file.getOriginalFilename();//jsp에서 전송받은 파일의 이름 가져옴
		UUID uid = UUID.randomUUID();//랜덤문자 구하기
		String saveName = uid.toString() + "." + originalName.split("\\.")[1];//한글 파일명 처리때문에
		String[] files = new String[] {saveName};//saveName을 형변환 get set으로 사용하려고 
		byte[] fileData = file.getBytes();//전송한 첫번째 파일이 fileData에 들어감
		File target = new File(uploadpath, saveName);
		FileCopyUtils.copy(fileData, target);
		return files;
	}

	public ArrayList<String> getExtNameArray() {
		return extNameArray;
	}

	public void setExtNameArray(ArrayList<String> extNameArray) {
		this.extNameArray = extNameArray;
	}

}
