import core.service.configuration.ServiceConfiguration;

public class Application {

    public static void main(String[] args) {
        var modeService = ServiceConfiguration.build();
        modeService.launch();
    }
}
