package net.avdw.mail.cli;

import com.google.inject.Inject;
import net.avdw.property.GlobalProperty;
import net.avdw.property.LocalProperty;
import net.avdw.property.PropertyFileWriter;
import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(name = "create", description = "Create config files", mixinStandardHelpOptions = true)
public class ConfigCreateCli implements Runnable {
    @Inject
    @LocalProperty
    private Path localPropertyPath;
    @Inject
    @GlobalProperty
    private Path globalPropertyPath;
    @Inject
    private PropertyFileWriter propertyFileWriter;

    @CommandLine.Option(names = {"-g", "--global"}, description = "Specify global file")
    private boolean isGlobal;

    /**
     * Entry point for picocli.
     */
    @Override
    public void run() {
        if (isGlobal) {
            propertyFileWriter.write(globalPropertyPath.toFile());
        } else {
            propertyFileWriter.write(localPropertyPath.toFile());
        }
    }
}
