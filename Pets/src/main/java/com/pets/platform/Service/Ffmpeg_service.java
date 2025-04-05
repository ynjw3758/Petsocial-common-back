package com.pets.platform.Service;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//import net.bramp.ffmpeg.FFmpeg;
//import net.bramp.ffmpeg.FFprobe;

@Service
public class Ffmpeg_service {
	
	
	@Value("${ffmpeg.location}")
	private String ffmpegpath;
	
	@Value("${ffprobe.location}")
	private String ffprobepath; 
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//public FFmpeg peg;
	//public FFprobe probe;
	

}
