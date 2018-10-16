package aeon.platform;

import aeon.platform.services.SessionService;
import dagger.Component;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.function.Supplier;

@Singleton
@Component(modules = SessionModule.class)
public interface SessionComponent {

    public SessionService buildSessionService();
}
