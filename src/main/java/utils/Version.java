package utils;

import picocli.CommandLine;
import picocli.CommandLine.IVersionProvider;

public class Version implements IVersionProvider {
    public String[] getVersion() throws Exception{
        String currentVersion = this.getClass().getPackage().getImplementationVersion();
        String appName = this.getClass().getPackage().getImplementationTitle();
        return new String[]{currentVersion};
    }
}
