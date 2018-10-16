package aeon.platform;

import aeon.platform.models.ExecuteCommandBody;

public interface ISession {

    Object executeCommand(ExecuteCommandBody commandBody) throws Exception;
}
