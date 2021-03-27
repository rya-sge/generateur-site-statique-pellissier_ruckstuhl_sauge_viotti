package utils;

import picocli.CommandLine;

public class versionProvider implements CommandLine.IVersionProvider {
    public String[] getVersion() throws Exception{
        String currentVersion = this.getClass().getPackage().getImplementationVersion();
        return new String[]{currentVersion};

    }
}
