package kr.co.kmac.common.util;

import kr.co.kmac.common.dto.AttachFileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtil {
	public static String tempPath;

	@Value("${spring.servlet.multipart.location}")
    public void setKey(String value) {
    	tempPath = value;
    }
	
	/**
	 * 첨부로 등록된 파일들을 서버에 업로드한다.
	 * parameter 
	 * @param files
	 * @return
	 * @throws Exception
	 */
	
	public static List<AttachFileDto.Info> uploadFiles(List<MultipartFile> files) throws Exception {
		AttachFileDto.Info commonFile = null;
		List<AttachFileDto.Info> commonFileList = null;
		
		File destdir = new File(tempPath);
        if(!destdir.exists()){
            destdir.mkdirs();
        }
        
		commonFileList = new ArrayList<AttachFileDto.Info>();

		for (MultipartFile file : files) {
			File tmp = new File(tempPath + UUID.randomUUID().toString());
			file.transferTo(tmp);			
			
			commonFile = AttachFileDto.Info.builder().build();
			String fileNm = file.getOriginalFilename();
			commonFile.setFileNm(fileNm);
			commonFile.setFileExtn(fileNm.substring(fileNm.lastIndexOf(".") + 1));
			commonFile.setFileSize(new Long(file.getSize()).intValue());
			commonFile.setFilePath(tmp.getPath());
			commonFileList.add(commonFile);
		}
		
		return commonFileList;
	}

	/**
	 * 첨부로 등록된 파일 1개 서버에 업로드한다.
	 * parameter
	 * @param file
	 * @return
	 * @throws Exception
	 */

	public static AttachFileDto.Info uploadFile(MultipartFile file) throws Exception {
		AttachFileDto.Info commonFile = null;

		File destdir = new File(tempPath);
		if(!destdir.exists()){
			destdir.mkdirs();
		}

		File tmp = new File(tempPath + UUID.randomUUID().toString());
		file.transferTo(tmp);

		commonFile = AttachFileDto.Info.builder().build();
		String fileNm = file.getOriginalFilename();
		commonFile.setFileNm(fileNm);
		commonFile.setFileExtn(fileNm.substring(fileNm.lastIndexOf(".") + 1));
		commonFile.setFileSize(new Long(file.getSize()).intValue());
		commonFile.setFilePath(tmp.getPath());

		return commonFile;
	}

	/**
	 * 물리 파일을 삭제한다.
	 * parameter 
	 * @param file
	 * @return
	 * @throws Exception
	 */
    public static void deleteFile(AttachFileDto.Info file) {
    	 new File(file.getFilePath()).delete();
    }

	/**
	 * 파일 다운로드
	 * parameter
	 * @param file
	 * @return
	 */
	public static boolean downloadFile(HttpServletResponse response, AttachFileDto.Info file) {

		try {

			//파일을 다운 받아 클라이언트컴에 파일이름 생성시 한글 깨짐 방지
			String originalFileName = new String(file.getFileNm().getBytes("euc-kr"),"ISO-8859-1");//8859_1

			File f = new File(file.getFilePath());

			if(!f.exists())
				return false;

			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", 
					"attachment;fileName=" + originalFileName);

			BufferedInputStream bis = 
					new BufferedInputStream(new FileInputStream(f));

			OutputStream out = response.getOutputStream();

			int n;
			byte[] data = new byte[4096];
			while((n=bis.read(data, 0, 4096))!=-1) {
				out.write(data,0,n);
			}

			out.flush();
			out.close();
			bis.close();

		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}

		return true;	
	}
}
