package com.neo;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class AWSHelper {

    private AmazonS3 amazonS3;
    private String IMAGE_BUCKET_NAME;

    public AmazonS3 getClient() {
        if (amazonS3 == null)
            amazonS3 = AmazonS3ClientBuilder.standard()
                    .withCredentials(new InstanceProfileCredentialsProvider(false))
                    .withRegion(Regions.US_EAST_2)
                    .build();
        return amazonS3;
    }

    public String getImageBucketName() {
        if (IMAGE_BUCKET_NAME == null)
            IMAGE_BUCKET_NAME = getClient().listBuckets().get(0).getName();
        return IMAGE_BUCKET_NAME;
    }

    public boolean postFile(String keyname, InputStream inputStream) {
        try {
            getClient().putObject(getImageBucketName(), keyname, inputStream, null);
            return true;
        } catch (AmazonServiceException e) {
            System.out.println(e.getErrorMessage());
            return false;
        }
    }

    public byte[] getFile(String keyname) throws IOException, AmazonServiceException {
        S3Object o = getClient().getObject(getImageBucketName(), keyname);
        S3ObjectInputStream s3is = o.getObjectContent();
        return IOUtils.toByteArray(s3is);
    }

    public boolean deleteFile(String keyname) {
        try {
            getClient().deleteObject(getImageBucketName(), keyname);
            return true;
        } catch (AmazonServiceException e) {
            System.out.println(e.getErrorMessage());
            return false;
        }
    }
}
