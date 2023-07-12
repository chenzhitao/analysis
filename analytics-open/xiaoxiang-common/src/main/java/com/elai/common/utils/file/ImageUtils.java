package com.elai.common.utils.file;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.elai.common.config.XiaoXiangConfig;
import com.elai.common.constant.Constants;
import com.elai.common.utils.StringUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

/**
 * 图片处理工具类
 *
 * @author elai
 */
public class ImageUtils
{
    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    public static byte[] getImage(String imagePath)
    {
        InputStream is = getFile(imagePath);
        try
        {
            return IOUtils.toByteArray(is);
        }
        catch (Exception e)
        {
            log.error("图片加载异常 {}", e);
            return null;
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
    }

    public static InputStream getFile(String imagePath)
    {
        try
        {
            byte[] result = readFile(imagePath);
            result = Arrays.copyOf(result, result.length);
            return new ByteArrayInputStream(result);
        }
        catch (Exception e)
        {
            log.error("获取图片异常 {}", e);
        }
        return null;
    }

    /**
     * 读取文件为字节数据
     *
     * @return 字节数据
     */
    public static byte[] readFile(String url)
    {
        InputStream in = null;
        ByteArrayOutputStream baos = null;
        try
        {
            if (url.startsWith("http"))
            {
                // 网络地址
                URL urlObj = new URL(url);
                URLConnection urlConnection = urlObj.openConnection();
                urlConnection.setConnectTimeout(30 * 1000);
                urlConnection.setReadTimeout(60 * 1000);
                urlConnection.setDoInput(true);
                in = urlConnection.getInputStream();
            }
            else
            {
                // 本机地址
                String localPath = XiaoXiangConfig.getProfile();
                String downloadPath = localPath + StringUtils.substringAfter(url, Constants.RESOURCE_PREFIX);
                in = new FileInputStream(downloadPath);
            }
            return IOUtils.toByteArray(in);
        }
        catch (Exception e)
        {
            log.error("获取文件路径异常 {}", e);
            return null;
        }
        finally
        {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(baos);
        }
    }

    /**
     * 改变图片 像素
     *
     * @param file
     * @param qality 参数qality是取值0~1范围内  清晰程度  数值越小分辨率越低
     * @param imageType 图片写出类型 比如 jpg
     * @return
     * @throws IOException
     */
    public static File compressPictureByQality(File file, float qality, String imageType) throws IOException {
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;
        log.info("开始设定压缩图片参数");
        // 指定写图片的方式为 jpg
        imgWrier = ImageIO.getImageWritersByFormatName(imageType).next();
        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
                null);
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        // 这里指定压缩的程度，参数qality是取值0~1范围内，
        imgWriteParams.setCompressionQuality(qality);
        imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
        ColorModel colorModel = ImageIO.read(file).getColorModel();// ColorModel.getRGBdefault();
        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
                colorModel, colorModel.createCompatibleSampleModel(32, 32)));
        log.info("结束设定压缩图片参数");
        if (!file.exists()) {
            log.info("Not Found Img File,文件不存在");
            throw new FileNotFoundException("Not Found Img File,文件不存在");
        } else {
            log.info("图片转换前大小" + file.length() + "字节");
            src = ImageIO.read(file);
            out = new FileOutputStream(file);
            imgWrier.reset();
            // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
            // OutputStream构造
            imgWrier.setOutput(ImageIO.createImageOutputStream(out));
            // 调用write方法，就可以向输入流写图片
            imgWrier.write(null, new IIOImage(src, null, null),
                    imgWriteParams);
            out.flush();
            out.close();
            log.info("图片转换后大小" + file.length() + "字节");
            return file;
        }
    }
}
