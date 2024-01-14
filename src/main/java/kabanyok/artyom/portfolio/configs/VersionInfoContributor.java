package kabanyok.artyom.portfolio.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class VersionInfoContributor implements InfoContributor {
    @Value("${portfolio.api.version:undefined}")
    private String appVersion;
    final private String md5sum = System.getenv("PORTFOLIO_API_MD5SUM");

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("version", appVersion);

        if (md5sum != null) {
            builder.withDetail("md5sum", md5sum);
        }
    }
}
