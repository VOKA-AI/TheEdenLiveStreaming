package com.live.util;

import org.springframework.beans.factory.annotation.Value;

import java.io.*;

public class VideoTool {

    /**
     * 安装工具
     */
    @Value("${rtmp.imge.tool}")
    private String rtmpTool = "D:/TOOL/ffmpeg/bin/ffmpeg.exe";
    /**
     * 图片存放地址
     */
    @Value("${rtmp.imge.url}")
    private String rtmpUrl = "C:/app/source/rtmpImge";

    /****
     * 获取指定时间内的图片
     * @param videoFilename:视频路径
     * @param thumbFilename:图片保存路径
     * @param width:图片长
     * @param height:图片宽
     * @param hour:指定时
     * @param min:指定分
     * @param sec:指定秒
     * @throws IOException
     * @throws InterruptedException
     */
    public void getThumb(String videoFilename, String thumbFilename, int width,
                         int height, int hour, int min, float sec) throws IOException, InterruptedException {
        File file = new File(rtmpUrl);
        if (!file.exists()) {
            file.mkdirs();//创建目录
        }
        ProcessBuilder processBuilder = new ProcessBuilder(rtmpTool, "-y",
                "-i", videoFilename, "-vframes", "1", "-ss", hour + ":" + min
                + ":" + sec, "-f", "mjpeg", "-s", width + "*" + height,
                "-an", rtmpUrl.concat(File.separator).concat(thumbFilename));

        Process process = processBuilder.start();

        InputStream stderr = process.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) ;
        process.waitFor();

        if (br != null)
            br.close();
        if (isr != null)
            isr.close();
        if (stderr != null)
            stderr.close();
    }

    public static void main(String[] args) {
        VideoTool videoTool = new VideoTool ();
        try {
            videoTool.getThumb("rtmp://58.200.131.2:1935/livetv/hunantv", "ffmpeg3.png", 204, 140, 0, 0, 0);
            System.out.println("over");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
