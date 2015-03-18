package com.miaxis.common.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * 图片工具类， 图片水印，文字水印，缩放，补白等
 * @author Carl He
 */
public final class ImageUtils {
    /**图片格式：JPG*/
    private static final String PICTRUE_FORMATE_JPG = "jpg";
    
    private ImageUtils(){}
    /**
     * 添加图片水印
     * @param targetImg 目标图片路径，如：C://myPictrue//1.jpg
     * @param waterImg  水印图片路径，如：C://myPictrue//logo.png
     * @param x 水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y 水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
*/
    public final static void pressImage(String targetImg, String waterImg, int x, int y, float alpha) {
            try {
                File file = new File(targetImg);
                Image image = ImageIO.read(file);
                int width = image.getWidth(null);
                int height = image.getHeight(null);
                BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = bufferedImage.createGraphics();
                g.drawImage(image, 0, 0, width, height, null);
            
                Image waterImage = ImageIO.read(new File(waterImg));    // 水印文件
                int width_1 = waterImage.getWidth(null);
                int height_1 = waterImage.getHeight(null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
                
                int widthDiff = width - width_1;
                int heightDiff = height - height_1;
                if(x < 0){
                    x = widthDiff / 2;
                }else if(x > widthDiff){
                    x = widthDiff;
                }
                if(y < 0){
                    y = heightDiff / 2;
                }else if(y > heightDiff){
                    y = heightDiff;
                }
                g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
                g.dispose();
                ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * 添加文字水印
     * @param targetImg 目标图片路径，如：C://myPictrue//1.jpg
     * @param pressText 水印文字， 如：中国证券网
     * @param fontName 字体名称，    如：宋体
     * @param fontStyle 字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
     * @param fontSize 字体大小，单位为像素
     * @param color 字体颜色
     * @param x 水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y 水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
*/
    public static void pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
        try {
            File file = new File(targetImg);
            
            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            
            int width_1 = fontSize * getLength(pressText);
            int height_1 = fontSize;
            int widthDiff = width - width_1;
            int heightDiff = height - height_1;
            if(x < 0){
                x = widthDiff / 2;
            }else if(x > widthDiff){
                x = widthDiff;
            }
            if(y < 0){
                y = heightDiff / 2;
            }else if(y > heightDiff){
                y = heightDiff;
            }
            
            g.drawString(pressText, x, y + height_1);
            g.dispose();
            ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
     * @param text
     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
*/
    public static int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }

    /**
     * 图片缩放
     * @param filePath 图片路径
     * @param height 高度
     * @param width 宽度
     * @param bb 比例不对时是否需要补白
*/
    public static void resize(String filePath, int height, int width, boolean bb) {
        try {
            double ratio = 0; //缩放比例    
            File f = new File(filePath);   
            BufferedImage bi = ImageIO.read(f);   
            Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);   
            //计算比例   
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {   
                if (bi.getHeight() > bi.getWidth()) {   
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();   
                } else {   
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();   
                }   
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);   
                itemp = op.filter(bi, null);   
            }   
            if (bb) {   
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
                Graphics2D g = image.createGraphics();   
                g.setColor(Color.white);   
                g.fillRect(0, 0, width, height);   
                if (width == itemp.getWidth(null))   
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);   
                else  
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);   
                g.dispose();   
                itemp = image;   
            }
            ImageIO.write((BufferedImage) itemp, "jpg", f);   
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 截图工具
     * @param srcpath 源图片路径名称如:c:\1.jpg 
     * @param subpath 剪切图片存放路径名称.如:c:\2.jpg
     * @param x 剪切点x坐标  
     * @param y 剪切点y坐标 
     * @param width 剪切点宽度  
     * @param height 剪切点高度
     * @throws IOException
     */
    public static void cut(String srcpath,String subpath,int x,int y,int width,int height) throws IOException {  
        FileInputStream is = null;  
        ImageInputStream iis = null;  
        try {  
            // 读取图片文件  
            is = new FileInputStream(srcpath);  
            /* 
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。 
             * 参数：formatName - 包含非正式格式名称 . （例如 "jpeg" 或 "tiff"）等 。 
             */  
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");  
            ImageReader reader = it.next();  
            // 获取图片流  
            iis = ImageIO.createImageInputStream(is);  
            /* 
             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。 
             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。 
             */  
            reader.setInput(iis, true);  
            /* 
             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O 
             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的 
             * getDefaultReadParam 方法中返回 ImageReadParam 的实例。 
             */  
            ImageReadParam param = reader.getDefaultReadParam();
            /* 
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象 
             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。 
             */  
            Rectangle rect = new Rectangle(x, y, width, height);  
            // 提供一个 BufferedImage，将其用作解码像素数据的目标。  
            param.setSourceRegion(rect);  
            /* 
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的 
             * BufferedImage 返回。 
             */  
            BufferedImage bi = reader.read(0, param);  
            // 保存新图片  
            ImageIO.write(bi, "jpg", new File(subpath));  
        } finally {  
            if (is != null)  
                is.close();  
            if (iis != null)  
                iis.close();  
        }  
  
    }

    public static void main(String[] args) throws IOException {
        //pressImage("C://pic//jpg", "C://pic//test.gif", 5000, 5000, 0f);
        pressText("C://2014101191616859_3616.jpg", "2014-09-01", "黑体", Font.BOLD|Font.ITALIC, 14, Color.BLACK, 0, 0, 0.3f);
        //resize("C://pic//4.jpg", 1000, 500, true);
    }
}