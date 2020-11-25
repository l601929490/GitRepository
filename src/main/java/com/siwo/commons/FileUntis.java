package com.siwo.commons;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

public class FileUntis {

	private static String getVideoTime(MultipartFile contentFile) throws Exception {
		// 将MultipartFile转换为Encoder所需的File
		CommonsMultipartFile cf = (CommonsMultipartFile) contentFile;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File f = fi.getStoreLocation();
		// 获取视频时长
		Encoder encoder = new Encoder();
		MultimediaInfo m = encoder.getInfo(f);
		long ls = m.getDuration() / 1000;
		int hour = (int) (ls / 3600);
		int minute = (int) (ls % 3600) / 60;
		int second = (int) (ls - hour * 3600 - minute * 60);
		// logger.info("视频时长为：{}时{}分{}秒", hour, minute, second);
		String time = hour + ":" + minute + ":" + second;
		return time;
	}

	// 上传文件
	public static Map<String, String> upload(MultipartFile file,Integer companyId,HttpServletRequest req) {

		Map<String, String> map = new HashMap<String, String>();
		System.out.println(file);
		String fileName = file.getOriginalFilename();
		String[]names = fileName.split("\\.");
		try {
			if (names[1].equalsIgnoreCase("mp4")) {
				String videoTime = getVideoTime(file);
				map.put("videoTime", videoTime);
			}
		} catch (Exception e) {

		}
		
		String imgAddress = "";

		if (file.getSize() != 0) {
			String name = file.getOriginalFilename();

			// 保存在本地的文件夹路径
			String path = "C:/myFile/siwo/"+companyId;

			File file3 = new File(path);

			if (!file3.exists()) {
				file3.mkdirs();
			}

//			Long time = System.currentTimeMillis();

			String tempPath = file3.getAbsolutePath() + "\\"  + name;

			File file2 = new File(tempPath);
			if (file2.exists()) {
				tempPath = file3.getAbsolutePath() + "\\"+ System.currentTimeMillis() + name;
				file2 = new File(tempPath);
			}

			try {
				file.transferTo(file2);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map.put("code", "1");
				map.put("msg", "服务器异常");
				return map;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imgAddress = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
					+ req.getContextPath() + "/"+companyId+"/" + file2.getName();

			map.put("imgAddress", imgAddress);
			map.put("filePath", file2.getAbsolutePath());
		}
		return map;
	}
	
	//	转换语音格式
	public static String m4aToMp3(String filePath,Integer companyId) {
		try {
			long time = System.currentTimeMillis();
			File file = new File("C:/myFile/siwo/"+companyId+"/"+time+".mp3");
			AudioUtils.changeLocalSourceToMp3(filePath, file.getAbsolutePath());
			return file.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
