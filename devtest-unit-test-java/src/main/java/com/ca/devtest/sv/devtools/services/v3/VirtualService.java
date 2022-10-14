package com.ca.devtest.sv.devtools.services.v3;

/**
 * @author sm632260
 *
 */
public class VirtualService {
    String version;
    String name;
    String description;
    String status;
    int capacity;
    int thinkScale;
    boolean autoRestart;
    boolean startOnDeploy;
    String groupTag;

    public VirtualService() {
        this.version = "1.0";
        this.description = "Deployed using SV-as-Code";
        this.name="";
        this.status="";
        this.groupTag="";
        this.autoRestart=true;
        this.startOnDeploy=true;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getThinkScale() {
        return thinkScale;
    }

    public void setThinkScale(int thinkScale) {
        this.thinkScale = thinkScale;
    }

    public boolean getAutoRestart() {
        return autoRestart;
    }

    public void setAutoRestart(boolean autoRestart) {
        this.autoRestart = autoRestart;
    }

    public boolean getStartOnDeploy() {
        return startOnDeploy;
    }

    public void setStartOnDeploy(boolean startOnDeploy) {
        this.startOnDeploy = startOnDeploy;
    }

    public String getGroupTag() {
        return groupTag;
    }

    public void setGroupTag(String groupTag) {
        this.groupTag = groupTag;
    }

    public static class VirtualServiceBuilder{
        private final VirtualService virtualServiceInstance = new VirtualService();

        private VirtualServiceBuilder VirtualServiceBuilder(){
            return new VirtualServiceBuilder();
        }

        public static VirtualServiceBuilder builder(){
            return new VirtualServiceBuilder();
        }

        public VirtualServiceBuilder withVerison(String version){
            virtualServiceInstance.setVersion(version);
            return this;
        }

        public VirtualServiceBuilder withName(String name){
            virtualServiceInstance.setName(name);
            return this;
        }

        public VirtualServiceBuilder withDescription(String description){
            virtualServiceInstance.setDescription(description);
            return this;
        }

        public VirtualServiceBuilder withStatus(String status){
            virtualServiceInstance.setStatus(status);
            return this;
        }

        public VirtualServiceBuilder withCapacity(int capacity){
            virtualServiceInstance.setCapacity(capacity);
            return this;
        }

        public VirtualServiceBuilder withThinkScale(int thinkScale){
            virtualServiceInstance.setThinkScale(thinkScale);
            return this;
        }

        public VirtualServiceBuilder withAutoRestart(boolean autoRestart){
            virtualServiceInstance.setAutoRestart(autoRestart);
            return this;
        }

        public VirtualServiceBuilder withStartOnDeploy(boolean startOnDeploy){
            virtualServiceInstance.setStartOnDeploy(startOnDeploy);
            return this;
        }

        public VirtualServiceBuilder withGroupTag(String groupTag){
            virtualServiceInstance.setGroupTag(groupTag);
            return this;
        }

        public VirtualService build(){
            return this.virtualServiceInstance;
        }
    }
}
