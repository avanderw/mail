package net.avdw;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.AbstractMatcher;
import net.avdw.color.AnsiColor;
import org.pmw.tinylog.Logger;

import java.lang.reflect.Method;

import static com.google.inject.matcher.Matchers.inSubpackage;
import static com.google.inject.matcher.Matchers.not;

public final class ProfilingModule extends AbstractModule {
    private final String inSubpackage;
    private final String notInSubpackage;

    public ProfilingModule(final String inSubpackage, final String notInSubpackage) {
        this.inSubpackage = inSubpackage;
        this.notInSubpackage = notInSubpackage;
    }

    @Override
    protected void configure() {
        bindInterceptor(inSubpackage(inSubpackage)
                        .and(not(inSubpackage(notInSubpackage))),
                new AbstractMatcher<Method>() {
                    @Override
                    public boolean matches(final Method method) {
                        return !method.isSynthetic();
                    }
                }, (methodInvocation) -> {
                    long start = System.currentTimeMillis();
                    try {
                        Logger.trace(String.format("%s-> %s.%s%s",
                                AnsiColor.MAGENTA,
                                methodInvocation.getMethod().getDeclaringClass().getSimpleName(),
                                methodInvocation.getMethod().getName(),
                                AnsiColor.RESET
                        ));
                        return methodInvocation.proceed();
                    } finally {
                        Logger.trace(String.format("%s<- %s.%s : %,d ms%s",
                                AnsiColor.MAGENTA,
                                methodInvocation.getMethod().getDeclaringClass().getSimpleName(),
                                methodInvocation.getMethod().getName(),
                                System.currentTimeMillis() - start,
                                AnsiColor.RESET
                        ));
                    }
                });
    }
}
