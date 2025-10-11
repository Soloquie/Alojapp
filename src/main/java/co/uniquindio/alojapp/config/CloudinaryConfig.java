package co.uniquindio.alojapp.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {

    private final CloudinaryProperties props;

    @Bean
    public Cloudinary cloudinary() {
        Assert.hasText(props.getCloudName(), "Falta cloudinary.cloud-name");
        Assert.hasText(props.getApiKey(),    "Falta cloudinary.api-key");
        Assert.hasText(props.getApiSecret(), "Falta cloudinary.api-secret");

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", props.getCloudName(),
                "api_key", props.getApiKey(),
                "api_secret", props.getApiSecret(),
                "secure", true
        ));
    }
}

