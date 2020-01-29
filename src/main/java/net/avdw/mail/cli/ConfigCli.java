package net.avdw.mail.cli;

import picocli.CommandLine;

@CommandLine.Command(name = "config", description = "Configure the application", mixinStandardHelpOptions = true,
subcommands = {ConfigCreateCli.class})
public class ConfigCli implements Runnable {

    /**
     * Entry point for picocli.
     */
    @Override
    public void run() {

    }
}
