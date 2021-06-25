package net.fabricmc.example.models.renderers;

public class ModelData {
    public String textureLocation;
    public String animationFileLocation;
    public String modelLocation;

    public ModelData(String textureLocation, String animationFileLocation, String modelLocation) {
        this.textureLocation = textureLocation;
        this.animationFileLocation = animationFileLocation;
        this.modelLocation = modelLocation;
    }
}
