package com.ca.devtest.sv.devtools.services.v3;

/**
 * @author sm632260
 *
 */
class SSLConfig {
    String keystoreFile;
    String keystorePassword;
    String alias;
    String aliasPassword;

    private SSLConfig(){
    }

    public String getKeystoreFile() {
        return keystoreFile;
    }

    public void setKeystoreFile(String keystoreFile) {
        this.keystoreFile = keystoreFile;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAliasPassword() {
        return aliasPassword;
    }

    public void setAliasPassword(String aliasPassword) {
        this.aliasPassword = aliasPassword;
    }

    public static class SSLConfigBuilder{
        private final SSLConfig sslConfigInstance = new SSLConfig();

        private SSLConfigBuilder(){}

        public static SSLConfigBuilder builder(){
            return new SSLConfigBuilder();
        }

        public SSLConfigBuilder withKeystoreFile(String keystoreFile){
            sslConfigInstance.setKeystoreFile(keystoreFile);
            return this;
        }

        public SSLConfigBuilder withKeystorePassword(String password){
            sslConfigInstance.setKeystorePassword(password);
            return this;
        }

        public SSLConfigBuilder withAlias(String alias){
            sslConfigInstance.setAlias(alias);
            return this;
        }

        public SSLConfigBuilder withAliasPassword(String aliasPassword){
            sslConfigInstance.setAliasPassword(aliasPassword);
            return this;
        }

        public SSLConfig build(){
            return sslConfigInstance;
        }
    }
}
