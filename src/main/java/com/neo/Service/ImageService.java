package com.neo.Service;

import com.amazonaws.AmazonServiceException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.neo.AWSHelper;
import com.neo.CardImage;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Praveen Gupta on 5/28/2017.
 */

@Service
public class ImageService {
    private final String CARD_KEY_NAME = "cards/", LOGO_KEY_NAME = "logos/", ADS_KEY_NAME = "ads/";
    private AWSHelper awsHelper;
    private final BASE64Decoder decoder;

    @Autowired
    public ImageService(AWSHelper awsHelper) {
        this.awsHelper = awsHelper;
        decoder=new BASE64Decoder();
    }

    private BufferedImage scaleImage(BufferedImage image, int sW, int sH) {
        BufferedImage scaleI = new BufferedImage(sW, sH, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = scaleI.createGraphics();
        graphics2D.drawImage(image.getScaledInstance(sW, sH, BufferedImage.SCALE_SMOOTH), 0, 0, null);
        graphics2D.dispose();
        return scaleI;
    }

    public BufferedImage scaleImage(BufferedImage image, boolean landscape){
        int sW, sH;
        if (landscape) {
            sW = 1200;
            sH = 900;
        } else {
            sW = 900;
            sH = 1200;
        }
        return scaleImage(image, sW, sH);
    }

    public BufferedImage fill(String cc, BufferedImage image) {
        int width = image.getWidth(), height = image.getHeight();

        BufferedImage bufferedImage1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage1.createGraphics();
        graphics2D.setPaint(new Color(new BigInteger(cc, 16).intValue()));
        graphics2D.fillRect(0, 0, width, height);

        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();

        return bufferedImage1;
    }

    public BufferedImage readImage(String encodedImage) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(decoder.decodeBuffer(encodedImage)));
    }

    public ByteArrayInputStream bufferedImageToInputStream(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return inputStream;
    }

    public boolean writeCardImage(InputStream inputStream, String filename) {
        String keyname = CARD_KEY_NAME + filename;
        return awsHelper.postFile(keyname, inputStream);
    }

    public boolean writeCardImage(String encodedImage, String filename) {
        return writeCardImage(getStreamFromBase64(encodedImage), filename);
    }

    public boolean writeCardImage(BufferedImage image, String filename) throws IOException {
        return writeCardImage(bufferedImageToInputStream(image), filename);
    }

    public boolean writeAdImage(InputStream inputStream, String filename) {
        String keyname = ADS_KEY_NAME + filename;
        return awsHelper.postFile(keyname, inputStream);
    }

    public boolean writeAdImage(String base64, String filename) {
        return writeAdImage(getStreamFromBase64(base64), filename);
    }

    public boolean writeLogoImage(InputStream inputStream, String filename) {
        String keyname = LOGO_KEY_NAME + filename;
        return awsHelper.postFile(keyname, inputStream);
    }

    public boolean writeLogoImage(String base64, String filename) {
        return writeLogoImage(getStreamFromBase64(base64), filename);
    }

    private ByteArrayInputStream getStreamFromBase64(String base64) {
        byte[] decodedBytes;
        decodedBytes = Base64.decodeBase64(base64);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
        return inputStream;
    }

    public byte[] getImage(String keyname) throws IOException, AmazonServiceException {
        return awsHelper.getFile(keyname);
    }

    public byte[] getCardImage(String filename) throws IOException, AmazonServiceException {
        return getImage(CARD_KEY_NAME + filename);
    }

    public byte[] getLogoImage(String filename) throws IOException, AmazonServiceException {
        return getImage(LOGO_KEY_NAME + filename);
    }

    private boolean delete(String keyname) {
        return awsHelper.deleteFile(keyname);
    }

    public boolean deleteCard(String filename) {
        String keyname = CARD_KEY_NAME + filename;
        return delete(keyname);
    }

    public boolean deleteLOGO(String filename) {
        String keyname = LOGO_KEY_NAME + filename;
        return delete(keyname);
    }

   /* public String getLogoBase64(String filename) {
        File file = new File(LOGOS, filename);
        try {
            String base64 = Base64.encodeBase64String(Files.readAllBytes(file.toPath()));
            return base64;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public void saveCard(String fileName, CardImage cardImage) {
        try {
            BufferedImage image = ImageIO.read(cardImage.getFile());
            int height = image.getHeight();
            int width = image.getWidth();

            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = result.createGraphics();
            graphics2D.drawImage(image, 0, 0, null);

            for (CardImage.Component component : cardImage.getComponents()) {

                float tH = component.getH() * height, tW = component.getW() * width;
                float tX = component.getX() * width, tY = component.getY() * height;

                FontRenderContext frc = graphics2D.getFontRenderContext();

                TextLayout tl = null;
                Rectangle bounds = null;
                int l = 0, r = 2000;
                while (l < r) {
                    int m = (l + r) / 2;
                    Font font = new Font(null, Font.PLAIN, m);
                    tl = new TextLayout(component.getText(), font, frc);
                    bounds = tl.getBounds().getBounds();
                    if (bounds.getWidth() > tW || bounds.getHeight() > tH) r = m - 1;
                    else if (bounds.getWidth() < tW && bounds.getHeight() < tH) l = m + 1;
                    else break;
                }

                int ox = (int) -bounds.getX();
                int oy = (int) -bounds.getY();

                BufferedImage temp = new BufferedImage((int) bounds.getWidth(), (int) bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D tGraphics2d = temp.createGraphics();
                tGraphics2d.setPaint(Color.BLACK);
                tl.draw(tGraphics2d, ox, oy);

                graphics2D.drawImage(temp, null, (int) tX, (int) tY);
            }

            for (CardImage.ImageComponent component : cardImage.getImageComponents()) {

                float tH = component.getH() * height, tW = component.getW() * width;
                float tX = component.getX() * width, tY = component.getY() * height;

                BufferedImage image1 = ImageIO.read(component.getImage());

                AffineTransform affineTransform = new AffineTransform();
                double sx = tW / image1.getWidth(), sy = tH / image1.getHeight();
                affineTransform.setToTranslation(tX, tY);
                affineTransform.scale(sx, sy);

                graphics2D.drawImage(image1, affineTransform, null);
            }

            CardImage.QRComponent component = cardImage.getQrComponent();
            if (component != null) {
                float tH = component.getH() * height, tW = component.getW() * width;
                float tX = component.getX() * width, tY = component.getY() * height;

                int w = (int) tW, h = (int) tH, x = (int) tX, y = (int) tY;

                try {
                    QRCodeWriter writer = new QRCodeWriter();
                    Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
                    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                    hints.put(EncodeHintType.MARGIN, 0);
                    BitMatrix bitMatrix = writer.encode(component.getQr(), BarcodeFormat.QR_CODE, w, h, hints);
                    for (int i = 0; i < w; i++)
                        for (int j = 0; j < h; j++)
                            if (bitMatrix.get(i, j))
                                result.setRGB(i + x, j + y, 0);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
            writeCardImage(getInputStream(result), fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getInputStream(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return inputStream;
    }
}
