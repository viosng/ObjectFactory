package aspects;

import org.springframework.stereotype.Component;

import javax.management.MXBean;

/**
 * Created by StudentDB on 17.04.2015.
 */

@Component
public class BenchmarkController implements BenchmarkControllerMBean {
    private volatile boolean isEnabled = true;

    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public void print(String s) {
        System.out.println(s);
    }
}
