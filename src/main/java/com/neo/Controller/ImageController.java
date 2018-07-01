package com.neo.Controller;

import com.amazonaws.AmazonServiceException;
import com.neo.Constants;
import com.neo.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import static com.neo.Service.ImageService.*;
/**
 * Created by Praveen Gupta on 5/11/2017.
 */

@RestController
@CrossOrigin
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /*@RequestMapping("/getMediumImage/card/{name:.+}")
    public byte[] getCardMediumImage(@PathVariable String name) throws IOException {
        File file = new File(CARDS, name);

        int width = 200, height = 200;
        BufferedImage image = ImageIO.read(file);
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ImageIO.write(newImage, "png", outputStream);
        return outputStream.toByteArray();
    }*/

    /*@RequestMapping("/promo/{name:.+}")
    public byte[] getPromoImage(@PathVariable String name) throws IOException {
        return imageService.getPromoImage(name);
    }*/

    /**
     * @api {get} /image/card/{filename} Card image
     * @apiDescription Get the card image with name "filename".
     * @apiGroup Images
     * @apiError (503) - Image Service not responding.
     * @apiSuccess {byte[]} - Image data in bytes.
     */
    @RequestMapping("/card/{name:.+}")
    public byte[] getCardImage(@PathVariable String name, HttpServletResponse response) throws IOException {
        try {
            return imageService.getCardImage(name);
        }
        catch (AmazonServiceException e){
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return null;
        }
    }

    /*@RequestMapping("/logo/{name:.+}")
    public byte[] getLogo(@PathVariable String name) throws IOException {
        return imageService.getLogoImage(name);
    }*/
}
