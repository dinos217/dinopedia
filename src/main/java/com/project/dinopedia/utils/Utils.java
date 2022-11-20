package com.project.dinopedia.utils;

import com.project.dinopedia.entities.Image;
import com.project.dinopedia.exceptions.BadRequestException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Utils {

    public static List<Image> buildImages(List<MultipartFile> files) {

        if (!CollectionUtils.isEmpty(files)) {
            List<Image> dinoImages = new ArrayList<>();
            for (MultipartFile file : files) {
                Image image = new Image();
                image.setName(file.getName());
                image.setType(file.getContentType());
                try {
                    image.setImageData(compressImage(file.getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dinoImages.add(image);
            }
            return dinoImages;
        } else
            throw new BadRequestException("List of images is empty");
    }

    public static byte[] compressImage(byte[] data) {

        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
