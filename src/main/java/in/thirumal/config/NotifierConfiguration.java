/**
 * 
 */
package in.thirumal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author thirumal
 *
 */
@Configuration
@EnableScheduling
public class NotifierConfiguration {

	 //    @Autowired private Notifier notifier;
/*
    @Bean
    public LoggingNotifier notifier() {
        return new LoggingNotifier();
    }

    @Bean
    public FilteringNotifier filteringNotifier() {
        return new FilteringNotifier(notifier());
    }

    @Bean
    @Primary
    public RemindingNotifier remindingNotifier() {
        RemindingNotifier remindingNotifier = new RemindingNotifier(filteringNotifier());
        remindingNotifier.setReminderPeriod(TimeUnit.MINUTES.toMillis(5));
        return remindingNotifier;
    }

    @Scheduled(fixedRate = 60_000L)
    public void remind() {
        remindingNotifier().sendReminders();
    }*/
    
}
