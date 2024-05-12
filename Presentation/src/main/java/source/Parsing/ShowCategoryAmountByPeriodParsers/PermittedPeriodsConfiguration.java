package source.Parsing.ShowCategoryAmountByPeriodParsers;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Data
@Component
@ConfigurationProperties(prefix = "app.permitted.periods")
public class PermittedPeriodsConfiguration {
    private Collection<String> periods;
}
