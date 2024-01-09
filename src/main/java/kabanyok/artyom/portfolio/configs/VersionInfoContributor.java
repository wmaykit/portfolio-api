package kabanyok.artyom.portfolio.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class VersionInfoContributor implements InfoContributor {
    @Value("${portfolio.api.version:undefined}")
    private String appVersion;

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("version", appVersion);
    }
}
