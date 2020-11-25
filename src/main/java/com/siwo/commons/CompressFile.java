package com.siwo.commons;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

public class CompressFile {

	public static void compressFile(MultipartFile file,File replacefile) {
		
		//	.of  要压缩大小的文件名
		//	.scale  要压缩的比例
		//	.outputQuality
		try {
			Thumbnails.of(file.getOriginalFilename()).
			scale(0.1).
			outputQuality(0.25).
			toFile(replacefile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
}
