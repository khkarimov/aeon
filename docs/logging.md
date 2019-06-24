# Logging

This guide describes how to create custom appenders and layouts with Log4j2. This is done through the use of 
[Appenders](https://logging.apache.org/log4j/2.0/manual/appenders.html) and 
[Layouts](https://logging.apache.org/log4j/2.x/manual/layouts.html). Within the Aeon project, files which serve these 
purposes can be found within the directory `Aeon.Plugin.Log4J2/src/main/java/com/ultimatesoftware/aeon/extensions/log4j2`.

## Custom Appender and Layout

Creating your own appenders and layouts provides flexibility to log configuration. To set up the skeleton of an appender
or layout, see the [Appenders](https://logging.apache.org/log4j/2.x/manual/extending.html#Appenders) or 
[Layouts](https://logging.apache.org/log4j/2.x/manual/extending.html#Layouts) Apache page, respectively. You must follow
the example provided and include the proper values for the Plugin annotation. This will allow you to add the tag within
`log4j2.xml` later.

Note that even when not logging anything, files defined in appender setup will still exist, although initialized as empty files.

### Builder

If your plugin takes in a large number of configuration options, a builder class can be used instead of the factory method. 
To create a Builder class, see the pattern specified on the 
[Plugin Builders](https://logging.apache.org/log4j/2.x/manual/extending.html#Plugin_Builders) Apache page.

### Changing Logging Destination

To change the logging destination at runtime, the `fileName` attribute of the file appender can be changed within
`createAppender()` or `build()`, depending on the implementation chosen after following the instructions above.

## log4j2.xml Configuration

Log configurations are specified within `log4j2.xml`. Within the Aeon project, this file can be found within the directory
`Aeon.SystemTests.Web/src/test/resources`. When you annotate your implementation, the plugin name becomes the
configuration element name, thus, a configuration with your custom appender and layout might look like this:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN" packages="com.ultimatesoftware.aeon.extensions.log4j2">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%t] %-5p %c{1}:%L - %msg%n"/>
        </Console>
        <RollingFile name="RollingFile" filename="log/AeonLog.log"
                     filepattern="${logPath}/%d{yyyyMMddHHmmss}-AeonLog.log">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%t] %-5p %c{1}:%L - %msg%n"/>
            <Policies>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
            <DefaultRolloverStrategy max="20"/>
        </RollingFile>
        <File name="File" filename="log/AeonLog.html">
            <AeonHtmlLayout/>
        </File>
        <PIdFileAppender name="PIdFileAppender" fileName="log/AeonLog.html">
            <AeonHtmlLayout/>
        </PIdFileAppender>
    </Appenders>
    <Loggers>
        <Logger name="com.ultimatesoftware.aeon" level="info" additivity="false">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="RollingFile"/>
            <AppenderRef ref="PIdFileAppender"/>
            <AppenderRef ref="File" level="error"/>
        </Logger>
        <Root level="error">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="RollingFile"/>
            <AppenderRef ref="PIdFileAppender"/>
            <AppenderRef ref="File"/>
        </Root>
    </Loggers>
</Configuration>
```
