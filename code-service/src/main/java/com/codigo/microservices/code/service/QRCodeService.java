package com.codigo.microservices.code.service;

import com.codigo.microservices.code.constant.PropertyConstant;
import com.spire.barcode.BarCodeGenerator;
import com.spire.barcode.BarCodeType;
import com.spire.barcode.BarcodeSettings;
import com.spire.barcode.QRCodeECL;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QRCodeService {
    public String generateQRCode(String data, String fileName) {
        BarcodeSettings settings = new BarcodeSettings();
        settings.setType(BarCodeType.QR_Code);
        settings.setData(data);
        settings.setX(2);
        settings.setQRCodeECL(QRCodeECL.M);
        settings.setShowText(false);
        settings.setShowTopText(false);
        settings.hasBorder(false);

        BarCodeGenerator barCodeGenerator = new BarCodeGenerator(settings);
        BufferedImage bufferedImage = barCodeGenerator.generateImage();

        try{
            return this.saveToFile(bufferedImage, fileName);
        } catch (IOException e){
            throw new RuntimeException("Failed to save QR code to file");
        }
    }

    private String saveToFile(BufferedImage image, String fileName) throws IOException {
        String directory = PropertyConstant.QR_CODES_DIR;
        fileName += ".png";

        Path outputPath = Paths.get(directory);
        if (!outputPath.toFile().exists()) {
            outputPath.toFile().mkdirs();
        }

        File outputFile = outputPath.resolve(fileName).toFile();

        ImageIO.write(image, "png", outputFile);

        return fileName;
    }
}
