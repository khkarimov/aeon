package echo.core.framework_abstraction.adapters;

import echo.core.test_abstraction.product.Configuration;

/**
 * Created by DionnyS on 4/13/2016.
 */
public interface IAdapter {
    IAdapter Configure(Configuration configuration);
}