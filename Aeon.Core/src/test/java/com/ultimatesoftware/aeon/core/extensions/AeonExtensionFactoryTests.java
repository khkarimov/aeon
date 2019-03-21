package com.ultimatesoftware.aeon.core.extensions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AeonExtensionFactoryTests {

    private AeonExtensionFactory aeonExtensionFactory;

    @Mock
    private ISessionIdProvider sessionIdProvider;

    @BeforeEach
    void setUp() {
        aeonExtensionFactory = new AeonExtensionFactory(this.sessionIdProvider);

        when(this.sessionIdProvider.getCurrentSessionId()).thenReturn("sessionId");
    }

    @Test
    void testCreate_IdDoesNotExit() {

        // Arrange

        // Act
        Object extension = aeonExtensionFactory.create(Object.class);

        // Assert
        assertNotNull(extension);
    }

    @Test
    void testCreate_SessionIdExists_InstanceDoesNotExit() {

        // Arrange
        aeonExtensionFactory.create(String.class);

        // Act
        Object extension = aeonExtensionFactory.create(Object.class);

        // Assert
        assertNotNull(extension);
    }

    @Test
    void testCreate_IdAndInstanceExist() {

        // Arrange
        Object extension1 = aeonExtensionFactory.create(Object.class);

        // Act
        Object extension2 = aeonExtensionFactory.create(Object.class);

        // Assert
        assertEquals(extension1, extension2);
    }

    @Test
    void testCreate_MultipleSessions() {

        // Arrange
        Object extension1 = aeonExtensionFactory.create(Object.class);
        when(this.sessionIdProvider.getCurrentSessionId()).thenReturn("sessionId2");

        // Act
        Object extension2 = aeonExtensionFactory.create(Object.class);

        // Assert
        assertNotEquals(extension1, extension2);
    }

    @Test
    void testCreate_publicStaticCreateInstanceMethodDoesNotExist() {

        // Arrange

        // Act
        Object extension = aeonExtensionFactory.create(ClassWithoutPublicStaticCreateInstanceMethod.class);

        // Assert
        assertTrue(extension instanceof ClassWithoutPublicStaticCreateInstanceMethod);
        assertTrue(((ClassWithoutPublicStaticCreateInstanceMethod) extension).parameterLessConstructorCalled);
    }

    public static class ClassWithoutPublicStaticCreateInstanceMethod {
        boolean parameterLessConstructorCalled;

        public ClassWithoutPublicStaticCreateInstanceMethod() {
            this.parameterLessConstructorCalled = true;
        }
    }

    @Test
    void testCreate_publicStaticCreateInstanceMethodExists() {

        // Arrange

        // Act
        Object extension = aeonExtensionFactory.create(ClassWithPublicStaticCreateInstanceMethod.class);

        // Assert
        assertTrue(extension instanceof ClassWithPublicStaticCreateInstanceMethod);
        assertFalse(((ClassWithPublicStaticCreateInstanceMethod) extension).parameterLessConstructorCalled);
    }

    private static class ClassWithPublicStaticCreateInstanceMethod {
        boolean parameterLessConstructorCalled;

        ClassWithPublicStaticCreateInstanceMethod() {
            this.parameterLessConstructorCalled = true;
        }

        ClassWithPublicStaticCreateInstanceMethod(boolean test) {
        }

        public static Object createInstance() {
            return new ClassWithPublicStaticCreateInstanceMethod(false);
        }
    }

    @Test
    void testCreate_publicStaticCreateInstanceMethodThrowsAnException() {

        // Arrange

        // Act
        Object extension = aeonExtensionFactory.create(ClassWithPublicStaticCreateInstanceMethodThatThrowsAnException.class);

        // Assert
        assertTrue(extension instanceof ClassWithPublicStaticCreateInstanceMethodThatThrowsAnException);
        assertTrue(((ClassWithPublicStaticCreateInstanceMethodThatThrowsAnException) extension).parameterLessConstructorCalled);
    }

    public static class ClassWithPublicStaticCreateInstanceMethodThatThrowsAnException {
        boolean parameterLessConstructorCalled;

        public ClassWithPublicStaticCreateInstanceMethodThatThrowsAnException() {
            this.parameterLessConstructorCalled = true;
        }

        public static Object createInstance() {
            throw new IllegalStateException();
        }
    }
}
