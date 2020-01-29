package net.avdw;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.inject.Inject;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class MustacheTemplateExecutor {
    private Runtime runtime;

    @Inject
    MustacheTemplateExecutor(final Runtime runtime) {
        this.runtime = runtime;
    }

    public String execute(final String view, final Object model) {
        Map<String, Object> context = new HashMap<>();
        context.put("model", model);
        context.put("runtime", runtime);

        Mustache m = new DefaultMustacheFactory().compile(String.format("%s.mustache", view));
        StringWriter writer = new StringWriter();
        m.execute(writer, context);
        return writer.toString();
    }
}
