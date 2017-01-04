/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p/>
 * This file is part of Dayan techology Co.ltd property.
 * <p/>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.util;

import dayan.eve.exception.ErrorCN;
import dayan.eve.model.topic.TopicImage;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xsg
 */
public class ImageBaseUtil {

    private static final Logger logger = LogManager.getLogger(ImageBaseUtil.class);

    private static final String THUMB_DIR = "thumb/";
    private static final String MIDDLE_DIR = "middle/";
    private static final String LARGE_DIR = "large/";
    private static final String JPG = "jpg";
    private static final String GIF = "gif";
    private static final Integer THUMB_SIZE = 200;//图片缩略图尺寸
    private static final Integer MIDDLE_SIZE = 1000;//图片缩略图尺寸

    public static List<TopicImage> uploadTopicImages(MultipartFile[] files, String urlHeadCreate, String urlHeadRead) {
        String dirUrl = getDirUrl();
        List<TopicImage> topicImages = new LinkedList<>();
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        ByteArrayInputStream bais = null;
        try {
            for (MultipartFile file : files) {
                String imageType = getImageType(file.getOriginalFilename());
                TopicImage topicImage = new TopicImage();
                byte[] bytes = file.getBytes();
                String md5Str = MD5BaseUtil.md5(bytes) + System.currentTimeMillis();
                String imageName = dirUrl + md5Str;
                topicImage.setImageUrl(urlHeadRead + LARGE_DIR + imageName + "." + imageType);
                String largeCreateUrl = urlHeadCreate + LARGE_DIR + imageName + "." + imageType;
                File largeImage = new File(largeCreateUrl);
                if (!largeImage.getParentFile().exists()) {
                    boolean mkdirs = largeImage.getParentFile().mkdirs();
                }
                if (!largeImage.exists()) {
                    boolean newFile = largeImage.createNewFile();
                }
                boolean isGif = imageType.equals(GIF);

                bais = new ByteArrayInputStream(bytes);
                BufferedImage bufImg = ImageIO.read(bais);

                int width = bufImg.getWidth();
                int height = bufImg.getHeight();
                String middleReadUrl = null;
                String middleCreateUrl;
                if (isGif) {
                    fos = new FileOutputStream(largeImage);
                    bos = new BufferedOutputStream(fos);
                    bos.write(bytes);
                } else {
                    Thumbnails.of(bufImg).size(width, height).toFile(largeCreateUrl);
                    if (height > MIDDLE_SIZE || width > MIDDLE_SIZE) {
                        middleReadUrl = urlHeadRead + MIDDLE_DIR + imageName + "." + imageType;
                        middleCreateUrl = urlHeadCreate + MIDDLE_DIR + imageName + "." + imageType;
                        File middle = new File(middleCreateUrl);
                        if (!middle.getParentFile().exists()) {
                            middle.getParentFile().mkdirs();
                        }
                        Thumbnails.of(bufImg).size(MIDDLE_SIZE, MIDDLE_SIZE).toFile(middleCreateUrl);
                    }
                }

                String thumbType = isGif ? JPG : imageType;//如果是gif图片，缩略图默认为jpg
                String thumbnailReadUrl = urlHeadRead + THUMB_DIR + imageName + "." + thumbType;
                String thumbnailCreateUrl = urlHeadCreate + THUMB_DIR + imageName + "." + thumbType;
                File thumb = new File(thumbnailCreateUrl);
                if (!thumb.getParentFile().exists()) {
                    thumb.getParentFile().mkdirs();
                }
                //先判断图片是否过长，过长则先剪切再压缩，没有则直接压缩
                if (checkImgTooLong(width, height)) {
                    int min = width > height ? height : width;
                    Thumbnails.of(bufImg).sourceRegion(0, 0, min, min).size(THUMB_SIZE, THUMB_SIZE).keepAspectRatio(false).toFile(thumbnailCreateUrl);
                } else {
                    Thumbnails.of(bufImg).size(THUMB_SIZE, THUMB_SIZE).toFile(thumbnailCreateUrl);
                }
                topicImage.setThumbnailUrl(thumbnailReadUrl);
                topicImage.setMiddleImageUrl(middleReadUrl);
                topicImages.add(topicImage);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(ErrorCN.Topic.IMAGE_ERROR);
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        return topicImages;
    }

    public static String uploadSingleImage(MultipartFile file, String urlCreatePrefix, String urlReadPrefix) {
        String dirUrl = getDirUrl();
        String imageUrl = null;
        ByteArrayInputStream bais = null;
        String imageType = getImageType(file.getOriginalFilename());
        try {
            byte[] byte1 = file.getBytes();
            String imageName = MD5BaseUtil.md5(byte1) + System.currentTimeMillis();
            imageUrl = dirUrl + imageName;
            File newFile = new File(urlCreatePrefix + imageUrl + "." + imageType);
            if (!newFile.exists()) {
                if (!newFile.getParentFile().exists()) {
                    newFile.getParentFile().mkdirs();
                }
                newFile.createNewFile();
            }

            bais = new ByteArrayInputStream(byte1);
            BufferedImage bi = ImageIO.read(bais);
            Thumbnails.of(bi).size(bi.getWidth(), bi.getHeight()).toFile(newFile);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException ex) {
                logger.info(ex.getMessage());
            }
        }
        return urlReadPrefix + imageUrl + "." + imageType;
    }

    /**
     * @param file            图片文件
     * @param urlCreatePrefix 创建地址
     * @param urlReadPrefix   读取地址
     * @return 图片地址
     */
    public static String uploadSingleImageToThumb(MultipartFile file, String urlCreatePrefix, String urlReadPrefix) {
        String dirUrl = getDirUrl();
        String imageUrl = null;
        ByteArrayInputStream bais = null;
        String imageType = getImageType(file.getOriginalFilename());
        try {
            byte[] byte1 = file.getBytes();
            String imageName = MD5BaseUtil.md5(byte1) + System.currentTimeMillis();
            imageUrl = dirUrl + imageName;
            File newFile = new File(urlCreatePrefix + imageUrl + "." + imageType);
            if (!newFile.exists()) {
                if (!newFile.getParentFile().exists()) {
                    newFile.getParentFile().mkdirs();
                }
                newFile.createNewFile();
            }

            bais = new ByteArrayInputStream(byte1);
            BufferedImage bi = ImageIO.read(bais);
            Thumbnails.of(bi).size(THUMB_SIZE, THUMB_SIZE).toFile(newFile);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException ex) {
                logger.info(ex.getMessage());
            }
        }
        return urlReadPrefix + imageUrl + "." + imageType;
    }

    private static String getDirUrl() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.DAY_OF_MONTH) + "/";
    }

    /*
     *检查图片是否太长，比例大于2判定为过长
     */
    private static boolean checkImgTooLong(int width, int height) {
        int max = width > height ? width : height;
        int min = width < height ? width : height;
        return max / min > 2;
    }


    /**
     * @param imageName 图片文件名
     * @return 图片类型
     */
    private static String getImageType(String imageName) {
        int index = imageName.lastIndexOf(".");
        return imageName.substring(index + 1);
    }

}
