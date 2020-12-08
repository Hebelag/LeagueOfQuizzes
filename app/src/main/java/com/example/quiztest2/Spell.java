package com.example.quiztest2;

public class Spell {
    private String id;
    private String name;
    private String description;
    private String tooltip;
    private Leveltip leveltip;
    private Byte maxrank;
    private Float[] cooldown;
    private String cooldownBurn;
    private Float[] cost;
    private String costBurn;
    private Datavalues datavalues;
    private Float[][] effect;
    private String[] effectBurn;
    private Object vars;
    private String costType;
    private String maxammo;
    private Float[] range;
    private String rangeBurn;
    private Image image;
    private String resource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public Leveltip getLeveltip() {
        return leveltip;
    }

    public void setLeveltip(Leveltip leveltip) {
        this.leveltip = leveltip;
    }

    public Byte getMaxrank() {
        return maxrank;
    }

    public void setMaxrank(Byte maxrank) {
        this.maxrank = maxrank;
    }

    public Float[] getCooldown() {
        return cooldown;
    }

    public void setCooldown(Float[] cooldown) {
        this.cooldown = cooldown;
    }

    public String getCooldownBurn() {
        return cooldownBurn;
    }

    public void setCooldownBurn(String cooldownBurn) {
        this.cooldownBurn = cooldownBurn;
    }

    public Float[] getCost() {
        return cost;
    }

    public void setCost(Float[] cost) {
        this.cost = cost;
    }

    public String getCostBurn() {
        return costBurn;
    }

    public void setCostBurn(String costBurn) {
        this.costBurn = costBurn;
    }

    public Datavalues getDatavalues() {
        return datavalues;
    }

    public void setDatavalues(Datavalues datavalues) {
        this.datavalues = datavalues;
    }

    public Float[][] getEffect() {
        return effect;
    }

    public void setEffect(Float[][] effect) {
        this.effect = effect;
    }

    public String[] getEffectBurn() {
        return effectBurn;
    }

    public void setEffectBurn(String[] effectBurn) {
        this.effectBurn = effectBurn;
    }

    public Object getVars() {
        return vars;
    }

    public void setVars(Object vars) {
        this.vars = vars;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getMaxammo() {
        return maxammo;
    }

    public void setMaxammo(String maxammo) {
        this.maxammo = maxammo;
    }

    public Float[] getRange() {
        return range;
    }

    public void setRange(Float[] range) {
        this.range = range;
    }

    public String getRangeBurn() {
        return rangeBurn;
    }

    public void setRangeBurn(String rangeBurn) {
        this.rangeBurn = rangeBurn;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
