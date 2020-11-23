package com.siwo.imgys;

import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;

public class ImgYs {

	public static void main(String[] args) {
		try {
			Thumbnails.of("D:\\图片/5d4a6190e66d6.jpg").scalingMode(
			        ScalingMode.BICUBIC).
			        // 图片缩放80%, 不能和size()一起使用
			        scale(0.2).
			        // 图片质量压缩80%
			        outputQuality(0.2).
			        toFile("D:\\图片/ceshi2.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
